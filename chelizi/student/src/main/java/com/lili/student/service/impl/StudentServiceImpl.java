package com.lili.student.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jboss.netty.handler.ssl.SslBufferPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.coach.dto.Car;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.service.CoachService;
import com.lili.coach.vo.CoachVo;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.DateUtil;
import com.lili.common.util.IDCard;
import com.lili.common.util.LogUtil;
import com.lili.common.util.MobileUtil;
import com.lili.common.util.Page;
import com.lili.common.util.SecurityUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.VersionUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.dto.Coupon;
import com.lili.coupon.service.CouponService;
import com.lili.event.vo.CourseStatusEventVo;
import com.lili.location.service.LocationService;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.OrderService;
import com.lili.order.vo.CoachClassQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.PlantClassVo;
import com.lili.order.vo.SearchVo;
import com.lili.pay.manager.BankManager;
import com.lili.pay.service.PayServiceNew;
import com.lili.pay.vo.UserMoneyVo;
import com.lili.school.dto.EnrollOrder;
import com.lili.school.manager.EnrollOrderManager;
import com.lili.student.dto.MicroClass;
import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAccount;
import com.lili.student.dto.StudentAuth;
import com.lili.student.dto.StudentVip;
import com.lili.student.dto.SubjectVideo;
import com.lili.student.manager.StudentAuthManager;
import com.lili.student.manager.StudentManager;
import com.lili.student.service.RechargeService;
import com.lili.student.service.StudentService;
import com.lili.student.service.VipCustomService;
import com.lili.student.vo.MicroClassVo;
import com.lili.student.vo.MycoachesVo;
import com.lili.student.vo.RechargePlanVo;
import com.lili.student.vo.StudentInfoVo;
import com.lili.student.vo.StudentMessage;
import com.lili.student.vo.SubjectVideoVo;
import com.lili.student.vo.VipCustomVo;

public class StudentServiceImpl implements StudentService {
	private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	StudentManager studentManager;
	@Autowired
	CoachManager coachManager;
	@Autowired
	NoticeManager noticeManager;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	private CoachClassService coachClassService;
	@Resource(name = "studentProducer")
	DefaultMQProducer studentProducer;
	@Resource(name = "studentRegisterProducer")
	DefaultMQProducer studentRegisterProducer;
	@Autowired
	CouponService couponService;
	@Autowired
	BankManager bankManager;
	@Autowired
	PayServiceNew payServiceNew;
	@Autowired
	StudentAuthManager studentAuthManager;

	@Autowired
	RechargeService rechargeService;

	@Autowired
	VipCustomService vipCustomService;

	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;

	@Autowired
	private OrderService orderService;

	@Autowired
	EnrollOrderManager enrollOrderManager;
	@Autowired
	private CarManager carManager;

	@Autowired
	private LocationService locationService;

       
	@Autowired
	private CoachService coachService;


	@Override
	public ReqResult addStudentAndLogin(String mobile, String codeInput, String password) {

		ReqResult r = ReqResult.getSuccess();
		String mo = mobile.trim();

		String authcode = redisUtil.get(REDISKEY.STUDENT_AUTHCODE + mo);

		if (authcode == null || !authcode.equals(codeInput.trim())) { // 验证码错误
			r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
			return r;
		} else if (!MobileUtil.isMobile(mo)) { // 手机号码错误
			r.setCode(ResultCode.ERRORCODE.MOBILE_NUMBER_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.MOBILE_NUMBER_ERROR);
			return r;
		}
		/*
		 * else if ("".equals(password.trim())) { // 密码不能为空
		 * 20160115改为手机号码登录，注册时可以为空
		 * r.setCode(ResultCode.ERRORCODE.PASSWORD_EMPTY_ERROR);
		 * r.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_EMPTY_ERROR); return r; }
		 */
		else {
			// redis中删除已保存的验证码信息
			redisUtil.delete(REDISKEY.STUDENT_AUTHCODE + mo);
			long studentId = 0L;
			// 查看手机号码是否存在，忘记密码重置密码流程中手机号码已存在，只需更新密码
			Student ss = studentManager.getStudentByPhoneNum(mo);
			Date now = new Date();
			if (null != ss) {
				studentId = ss.getStudentId();
				ss.setPassword(password);
				if (ss.getFirstLogin() == null)
					ss.setFirstLogin(now);
				ss.setLastLogin(now);
				studentManager.updateStudent(ss);
			} else {
				// 保存用户到MySQL中
				Student s = new Student();
				s.setPhoneNum(mobile);
				s.setPassword(password);
				s.setAgreement((byte) 1);
				s.setFirstLogin(now);
				s.setLastLogin(now);
				studentManager.addStudent(s);
				// 获取生成的用户id
				studentId = s.getStudentId();

				// 注册成功后，保存到用户消息中心
//				Notice notice = new Notice();
//				notice.setTitle("系统小秘书");
//				notice.setUserId(studentId);
//				notice.setType((byte) 4); 
//				notice.setUserType((byte) ReqConstants.USER_TYPE_STUDENT);
//				notice.setDigest("欢迎您成为我们的新用户！");
//				notice.setTime(new Date());
//				noticeManager.addNotice(notice);

				// 推送注册成功的消息到mq
				try {
					Message msg = new Message();
					msg.setTopic(studentRegisterProducer.getCreateTopicKey());
					msg.setTags(OrderConstant.RMQTAG.STUDENT_REGISTER);
					StudentMessage smsg = new StudentMessage();
					smsg.setStudentId(studentId);
					smsg.setPhoneNum(mobile);
					smsg.setMsgType(StudentMessage.MSGTYPE_STUDENT_REGISTER);
					msg.setBody(SerializableUtil.serialize(smsg));
					studentRegisterProducer.send(msg);
					logger.debug("studentRegisterProducer has sent a message. MSGTYPE_STUDENT_REGISTER. StudentId: "
							+ studentId);
				} catch (Exception e) {
					logger.error("studentRegisterProducer error " + e);
					e.printStackTrace();
				}
			}

			// 生成token
			String token = SecurityUtil.getUUID();

			// 将token保存到redis中
			redisUtil.setAll(REDISKEY.STUDENT_TOKEN + studentId, token, 0);

			// 返回数据到客户端
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("studentId", studentId);
			data.put("token", token);
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(data);
			return r;
		}
	}

	@Override
	public ReqResult login(String mobile, String password) {

		ReqResult r = ReqResult.getSuccess();
		String mo = mobile.trim();

		if (!MobileUtil.isMobile(mo)) { // 手机号码错误
			r.setCode(ResultCode.ERRORCODE.MOBILE_NUMBER_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.MOBILE_NUMBER_ERROR);
			return r;
		} else {
			// 获取密码
			Student s = studentManager.getStudentByPhoneNum(mo);
			if (s != null) {
				Map<String, Object> data = new HashMap<String, Object>();
				// 20160526增加锁定功能
				int state = s.getState();
				if (state == ReqConstants.USER_STATE_LOCKED_FOREVER) {
					data.put("reason", s.getExtra());
					r.setData(data);
					r.setCode(ResultCode.ERRORCODE.USER_LOCKED);
					r.setMsgInfo(ResultCode.ERRORINFO.USER_LOCKED);
					return r;
				} else if (state == ReqConstants.USER_STATE_LOCKED_TEMP) {
					Date reviveTime = s.getReviveTime();
					Date now = new Date();
					if (reviveTime.after(now)) {
						data.put("reason", s.getExtra());
						data.put("reviveTime", reviveTime);
						r.setData(data);
						r.setCode(ResultCode.ERRORCODE.USER_LOCKED);
						r.setMsgInfo(ResultCode.ERRORINFO.USER_LOCKED);
						return r;
					} else {
						// 用户复活时间已到，恢复状态为正常
						s.setState(ReqConstants.USER_STATE_NORMAL);
						studentManager.updateStudent(s);
					}
				}

				String pass = s.getPassword();
				if (pass.equals(password)) {
					// 生成token
					String token = SecurityUtil.getUUID();
					long studentId = s.getStudentId();

					// 将token保存到redis中
					redisUtil.setAll(REDISKEY.STUDENT_TOKEN + studentId, token, 0);

					Date now = new Date();
					if (s.getFirstLogin() == null)
						s.setFirstLogin(now);
					s.setLastLogin(now);
					studentManager.updateStudent(s);
					data.put("studentId", studentId);
					data.put("token", token);
					r.setCode(ResultCode.ERRORCODE.SUCCESS);
					r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
					r.setData(data);
					return r;
				} else {
					r.setCode(ResultCode.ERRORCODE.PASSWORD_ERROR);
					r.setMsgInfo(ResultCode.ERRORINFO.PASSWORD_ERROR);
					return r;
				}
			} else {
				r.setCode(ResultCode.ERRORCODE.NO_USER);
				r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
				return r;
			}

		}
	}

	@Override
	public ReqResult login2(String mobile, String code, String reqType) {
		ReqResult r = ReqResult.getSuccess();
		String mo = mobile.trim();
		// 20160414 超级登录
		if (null != reqType && !"".equals(reqType)) {
			int rt = Integer.parseInt(reqType);
			if (rt == ReqConstants.REQ_TYPE_SUPER_LOGIN) {
				Boolean isSuper = redisUtil.get(REDISKEY.TAG_SUPER_STUDENT + mo);
				if (null != isSuper && isSuper) {
					redisUtil.delete(REDISKEY.TAG_SUPER_STUDENT + mo);
					return this.addStudentAndLogin(mobile, code, null);
				}
			}
		}

		// 获取短信验证码
		String co = redisUtil.get(REDISKEY.STUDENT_AUTHCODE + mo);
		// 获取密码
		Student s = studentManager.getStudentByPhoneNum(mo);
		if (s != null) {
			Map<String, Object> data = new HashMap<String, Object>();

			// 20160526增加锁定功能
			int state = s.getState();
			if (state == ReqConstants.USER_STATE_LOCKED_FOREVER) {
				data.put("reason", s.getExtra());
				r.setData(data);
				r.setCode(ResultCode.ERRORCODE.USER_LOCKED);
				r.setMsgInfo(ResultCode.ERRORINFO.USER_LOCKED);
				return r;
			} else if (state == ReqConstants.USER_STATE_LOCKED_TEMP) {
				Date reviveTime = s.getReviveTime();
				Date now = new Date();
				if (reviveTime.after(now)) {
					data.put("reason", s.getExtra());
					data.put("reviveTime", reviveTime);
					r.setData(data);
					r.setCode(ResultCode.ERRORCODE.USER_LOCKED);
					r.setMsgInfo(ResultCode.ERRORINFO.USER_LOCKED + "\n原因：" + s.getExtra() + " \n恢复时间："
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reviveTime));
					return r;
				} else {
					// 用户复活时间已到，恢复状态为正常
					s.setState(ReqConstants.USER_STATE_NORMAL);
					studentManager.updateStudent(s);
				}
			}

			if (null != code && code.trim().equals(co)) {
				redisUtil.delete(REDISKEY.STUDENT_AUTHCODE + mo);
				// 生成token
				String token = SecurityUtil.getUUID();
				long studentId = s.getStudentId();
				// 将token保存到redis中
				redisUtil.setAll(REDISKEY.STUDENT_TOKEN + studentId, token, 0);
				Date now = new Date();
				if (s.getFirstLogin() == null)
					s.setFirstLogin(now);
				s.setLastLogin(now);
				studentManager.updateStudent(s);
				data.put("studentId", studentId);
				data.put("token", token);
				r.setCode(ResultCode.ERRORCODE.SUCCESS);
				r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
				r.setData(data);
				return r;
			} else {
				r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
				return r;
			}
		} else {
			r.setCode(ResultCode.ERRORCODE.NO_USER);
			r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
			return r;
		}

	}

	@Override
	public ReqResult autoLogin(String studentId, String tokenId) {
		ReqResult r = ReqResult.getSuccess();

		if (redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId)) {
			return r;
		} else {
			r.setCode(ResultCode.ERRORCODE.NO_USER);
			r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
			return r;
		}

	}

	@Override
	public ReqResult logout(String studentId, String tokenId) {

		ReqResult r = ReqResult.getSuccess();
		if (redisUtil.isExist(REDISKEY.STUDENT_TOKEN + studentId, tokenId)) {
			// redis中删除已保存的
			redisUtil.delete(REDISKEY.STUDENT_TOKEN + studentId);
			redisUtil.delete(REDISKEY.STUDENT_INFO + studentId);
			redisUtil.delete(REDISKEY.ENROLL_STUDENT_INFO + studentId);
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			return r;
		} else {
			r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
			r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
			return r;
		}

	}

	@Override
	public ReqResult getUserVipInfo(String studentId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			StudentVip studentVip = studentManager.getStudentVipInfoByStudentId(Long.parseLong(studentId));
			r.setData(studentVip);
		} catch (Exception e) {
			logger.error("getUserVipInfo error! studentId:{}", studentId, e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}

	public Student getStudent(long studentId) {
		Student s = null;
		try {
			s = studentManager.getStudentInfo(studentId);
			return s;
		} catch (Exception e) {
			logger.error("getUserInfo error! studentId:{}", studentId, e);
		}
		return s;
	}


    @Override
    public ReqResult getUserInfo(String studentId, String tokenId)
    {
        ReqResult r = ReqResult.getSuccess();
        try
        {
            StudentInfoVo si = new StudentInfoVo();
            Student s = studentManager.getStudentInfo(Long.parseLong(studentId));
            si = BeanCopy.copy2Null(s, si);
            // Vtype=1表示大客户，Vtype=2表示为普惠
            //20160913 如果有参与活动，屏蔽充值送
            if (!"100".equals(si.getVipPackageId())) {
	        	RechargePlanVo rechargePlanVo =  rechargeService.getRechargePlanByUserId(Long.parseLong(studentId));
	        	if(rechargePlanVo != null) {
	        		if (rechargePlanVo.getCouponTemplate() != null && !"".equals(rechargePlanVo.getCouponTemplate())) {
	        			Coupon coupon = couponService.getCouponById(rechargePlanVo.getCouponTemplate());
	        			if (coupon != null) {
	        				r.setData("Coupon", coupon);
	        			}
	        		}
        			r.setData("RechargePlan", rechargePlanVo);
        			VipCustomVo custom=vipCustomService.queryVipCustomById(Long.parseLong(studentId));
        			if(custom!=null && custom.getIsdel() ==OrderConstant.ISDEL.NOTDELETE ) {
        				//如果是大客户审核通过的extra为2
        				if (custom.getVstate()==OrderConstant.VERIFY.PASS) {
        					si.setExtra("2");
        				}
        				//如果是大客户未审核通过的，显示extra为1
        				else if(custom.getVstate()==OrderConstant.VERIFY.WAIT){
        					si.setExtra("1");
        				}
        			}
	        	}
            }
        	
            r.setData(si);
            
            //判断是否有未评价的订单，订单必须在下课后
            OrderQuery oq = new OrderQuery();
            oq.setGroupBy(" and order_state in (4,5)");
            oq.setorderBy("order by pstart desc");
            OrderVo ov = new OrderVo();
            ov.setStudentId(s.getStudentId());
            List<OrderVo> list = orderService.queryByObjectAnd(ov, oq);
            if (list != null && list.size() > 0) {
            	if(list.get(0).getOrderId() != null && !"".equals(list.get(0).getOrderId())){
            		String orderId =list.get(0).getOrderId();
            		String redisKey = "QuartzJPush_stuUnappOrder_" + orderId;
            		if (!redisUtil.isExist(redisKey)) {
            			JpushMsg jmsg = new JpushMsg();
            			jmsg.setAlter("您有未评价的订单，快来帮喱喱评价一下吧！");
            			jmsg.setUserId(s.getStudentId());
            			jmsg.setOrderId(orderId);
            			jmsg.setOperate(JpushConstant.OPERATE.STUUNAPPORDER);
            			Message jpush = new Message();
            			jpush.setKeys(orderId);
            			jpush.setTopic(jpushProducer.getCreateTopicKey());
            			jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
            			jpush.setBody(SerializableUtil.serialize(jmsg));
            			jpushProducer.send(jpush);
            			redisUtil.set(redisKey, 1, 86400);
            			
						Notice notice = new Notice();
						notice.setUserId(s.getStudentId());
						notice.setType((byte) 2);
						notice.setUserType((byte) 2);
						notice.setOrderId(orderId);
						notice.setTime(new Date());
						notice.setTitle("未评价订单");
						notice.setDigest("您有未评价的订单，快来帮喱喱评价一下吧！");
						Notice exticeNotice = noticeManager.getNoticeByOrderId(orderId, (byte) 2);
						if (exticeNotice != null) {
							notice.setNoticeId(exticeNotice.getNoticeId());
							noticeManager.updateNotice(notice);
						} else {
							noticeManager.addNotice(notice);
						}
            	}
            }
         }
            return r;
        }catch (Exception e) {
            logger.error("getUserInfo error! studentId:{},tokenId:{}", studentId, tokenId, e);
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return r;
        }
    }



	@Override
	public ReqResult updatePass(String studentId, String password, String headIcon, String name, String sex, String age,
			String phoneNum, String codeInput) {

		ReqResult r = ReqResult.getSuccess();
		Student s = new Student();
		s.setStudentId(Long.parseLong(studentId));

		if (null != password && !"".equals(password)) {
			s.setPassword(password);
		}
		if (null != headIcon && !"".equals(headIcon)) {
			s.setHeadIcon(headIcon);
		}
		if (null != name && !"".equals(name)) {
			s.setName(name);
		}
		if (null != sex && !"".equals(sex)) {
			s.setSex(Byte.parseByte(sex));
		}
		if (null != age && !"".equals(age)) {
			s.setAge(Short.parseShort(age));
		}
		if (null != phoneNum && !"".equals(phoneNum) && null != codeInput && !"".equals(codeInput)) {
			String mo = phoneNum.trim();
			// 修改的手机号码需要验证是否已被注册 已在获取验证码时验证；20160129产品逻辑混乱，同时开启此处的校验更保险
			boolean sExist = studentManager.isExist(mo);
			if (sExist) {
				r.setCode(ResultCode.ERRORCODE.USER_EXIST);
				r.setMsgInfo(ResultCode.ERRORINFO.USER_EXIST);
				return r;
			}
			String authcode = redisUtil.get(REDISKEY.STUDENT_AUTHCODE + mo);
			if (authcode == null || !authcode.equals(codeInput.trim())) { // 验证码错误
				r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
				r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
				return r;
			}
			// redis中删除已保存的验证码信息
			redisUtil.delete(REDISKEY.STUDENT_AUTHCODE + mo);
			s.setPhoneNum(mo);
		}

		studentManager.updateStudent(s);

		// 返回数据到客户端
		return r;
	}

	@Override
	public ReqResult updateHeadIcon(String userId, String picPath) {
		ReqResult r = ReqResult.getSuccess();
		Student s = new Student();
		s.setStudentId(Long.parseLong(userId));
		s.setHeadIcon(picPath);
		studentManager.updateStudent(s);

		// 返回数据到客户端
		return r;
	}

	@Override
	public ReqResult addIdCardInfo(String studentId, String name, String idCard, String picPath1, String picPath2,
			String tokenId) {
		ReqResult r = ReqResult.getSuccess();

		if (!IDCard.IDCardValidate(idCard)) { // 身份证号码错误
			r.setCode(ResultCode.ERRORCODE.IDCARD_NUMBER_ERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.IDCARD_NUMBER_ERROR);
			return r;
		}
		try {
			Student student = new Student();
			student.setStudentId(Long.parseLong(studentId)); // 学员id
			// student.setName(name); // 学员名称
			// student.setIdNumber(idCard); // 身份证号码
			// student.setIdPhotoFront(picPath1); // 身份证正面
			// student.setIdPhotoBack(picPath2); // 身份证反面
			student.setCheckIdState((byte) ReqConstants.CHECK_STATE_PROCESS);
			studentManager.updateStudent(student);
			// 20160509用户实名认证通过后，才更改主表，实名认证信息保存在认证表中
			StudentAuth studentAuth = new StudentAuth();
			studentAuth.setAuthName(name);
			studentAuth.setStudentId(Long.parseLong(studentId));
			studentAuth.setCreateTime(new Date());
			studentAuth.setFileNo(idCard.trim());
			studentAuth.setPhoto(picPath1);
			studentAuth.setPhoto2(picPath2);
			studentAuth.setType(ReqConstants.CHECK_TYPE_IDCARD);
			studentAuth.setState(ReqConstants.CHECK_STATE_PROCESS);
			studentAuthManager.addStudentAuth(studentAuth);
		} catch (NumberFormatException e) {
			r = ReqResult.getFailed();
			e.printStackTrace();
		}

		return r;

	}

	@Override
	public ReqResult getStatus(String studentId, String tokenId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReqResult getWallet(String studentId, String v) {
		ReqResult r = ReqResult.getSuccess();
		Long sid = Long.parseLong(studentId);
		StudentAccount sa = studentManager.getStudentMoney(sid);
		int money = 0;
		if (null != sa) {
			money = sa.getMoney();
		}
		// 20160318 兼容之前的数据结构
		if (null != v && !"".equals(v)) {

			if (VersionUtil.compareVersion(v, "1.6.0") >= 0) {
				int couponCount = couponService.getUseableCouponCount(sid);
				int bankCount = bankManager.queryBankSize(sid, ReqConstants.USER_TYPE_STUDENT);
				Map<String, Integer> data = new HashMap<String, Integer>();
				data.put("money", money);
				data.put("couponCount", couponCount);
				data.put("bankCount", bankCount);
				r.setData(data);
				return r;
			} else {
				// 1.6版本之前，用之前的版本格式
				r.setData(money);
				return r;
			}
		} else {
			// 1.6版本之前，用之前的版本格式
			r.setData(money);
			return r;
		}

	}

	@Override
	public ReqResult getBills(String studentId, String userType) {
		ReqResult r = ReqResult.getSuccess();
		List<UserMoneyVo> mm = new ArrayList<>();
		UserMoneyVo mv = new UserMoneyVo();
		mv.setChangevalue(1000);
		mv.setLeftvalue(10000);
		mv.setOperatetime(new Date());
		mv.setOperatetype((byte) 0);// 操作类型(0充值，1提现，2奖金，3补贴)
		mv.setPayway(null);
		mv.setRemark("充值");
		mm.add(mv);

		r.setData(mm);
		return r;
	}

	@Override
	public ReqResult getCoupons(String studentId, String tokenId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReqResult getMessages(String userId, String userType, String pageNo, String pageSize, String time) {
		ReqResult r = ReqResult.getSuccess();

		/*
		 * List<Notice> noticeList =
		 * noticeManager.getNoticByUserId(Long.parseLong(userId),
		 * Integer.parseInt(userType), Integer.parseInt(pageNo),
		 * Integer.parseInt(pageSize),time); List<NoticeVo> ms = new
		 * ArrayList<NoticeVo>(); try { ms = BeanCopy.copyList(noticeList,
		 * NoticeVo.class, BeanCopy.COPYNOTNULL); r.setData(ms); return r; }
		 * catch (Exception e1) { e1.printStackTrace();
		 * r.setCode(ResultCode.ERRORCODE.EXCEPTION);
		 * r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION); return r; }
		 */
		try {
			Student student = studentManager.getStudentInfo(Long.parseLong(userId));
			int pNo = 1;
			int pSize = 100;
			if (StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)) {
				pNo = Integer.parseInt(pageNo.trim());
				if (pNo <= 0) {
					pNo = 1;
				}
				pSize = Integer.parseInt(pageSize.trim());
				if (pSize <= 0) {
					pSize = 100;
				}
			}
			Date etime = null;
			if (null != time && !"".equals(time)) {
				etime = new Date(Long.parseLong(time));
			}
			byte type = 1;
			Integer schoolId = null;
			if (student != null && student.getSchoolId() != null && !"".equals(student.getSchoolId())){
				schoolId = student.getSchoolId().intValue();
			}
			Page<Notice> pagesData = noticeManager.getNoticesByStudentId(Long.parseLong(userId),
					schoolId, student.getCityId(), type, etime, pNo, pSize);
			r.setData(pagesData.getDataList());
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return r;
		}

	}

	@Override
	public ReqResult getNotices(String userId, String type, String pageNo, String pageSize) {
		ReqResult r = ReqResult.getSuccess();

		try {
			Student student = null;
			if (!"3".equals(type)) {   //type=3 喱喱头条 不需要登录
				if (!userId.matches("\\d+")) {
					r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
					r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
					return r;
				}
				student = studentManager.getStudentInfo(Long.parseLong(userId));
				if (student == null && !"3".equals(type)) {
					r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
					r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
					return r;
				}
			}

			int pNo = 1;
			int pSize = 100;
			if (StringUtil.isNotNullAndNotEmpty(pageNo) && StringUtil.isNotNullAndNotEmpty(pageSize)) {
				pNo = Integer.parseInt(pageNo.trim());
				if (pNo <= 0) {
					pNo = 1;
				}
				pSize = Integer.parseInt(pageSize.trim());
				if (pSize <= 0) {
					pSize = 100;
				}
			}
			Page<Notice> pagesData = noticeManager.getNotices(student, Byte.parseByte(type), pNo, pSize);
			r.setData(pagesData.getDataList());
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return r;
		}

	}

	@Override
	public ReqResult getNoticeIndex(String userId) {
		ReqResult r = ReqResult.getSuccess();

		try {
			Student student = studentManager.getStudentInfo(Long.parseLong(userId));
			if (student == null) {
				r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
				r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
				return r;
			}
			List<Notice> list = noticeManager.getNoticeIndex(student);
			r.setData(list);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return r;
		}
        
    }
    
    

    @Override
    public ReqResult getSkills(String studentId, String tokenId)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public List<MycoachesVo> getBlurCoach(Long studentId,String coachName,Double lge, Double lae) throws Exception{
    	List<MycoachesVo> mc = new ArrayList<MycoachesVo>();
    	if(coachName!=null&&coachName.length()>0){
    		Coach c=new Coach();
    		if(coachName!=null){
    			coachName=coachName.replaceAll("'", "").replaceAll("\"", "");
    		}
    		c.setName(coachName);
    		
    		//模糊查询匹配的教练
    		List<Coach> coaches= coachManager.getCoach(c);
    		java.util.Date now=new java.util.Date();
    		logger.debug("===============================blur coach:"+coaches.size()+" ");
	    	if(coaches.size()>0){
	    		for(int i=coaches.size()-1;i>=0;i--){
	    			Coach coach=coaches.get(i);
		    		if(coach.getIsImport()!=1&&coach.getCheckDrState()!=2){//注册教练没有认证
						coaches.remove(i);
					}
	    		}
	    		List<Long> coachIds=new ArrayList();
	    		for(Coach coach:coaches){
	    			coachIds.add(coach.getCoachId());
	    		}
	    		//获取匹配教练当天的排班
	    		Map<Long,List<CoachClassVo>> ccmap=coachClassService.queryByCoachDateWithPrice(new Date(), coachIds,3);
	    		List<CoachVo> coachList=(List<CoachVo>)coachService.getCoachsByIds(coachIds).getResult().get(ResultCode.RESULTKEY.DATA);
	    		for(Coach coach:coaches){
	    			
		    			List<CoachClassVo> classes=ccmap.get(coach.getCoachId());
		    			if(classes!=null&&classes.size()>0){//有排班
		    				//填充每个排班的学员明细记录
		    				//List<CoachClassVo> newclasses= coachClassService.fillPlantData(classes);
		    				//MycoachesVo mycoachesVo = addMyCoachInfo(coach, newclasses, studentId);
		    				MycoachesVo mycoachesVo = addMyCoachInfo(coach, null, studentId);
		    				mycoachesVo.setBookSum(coach.getTeachStudent());
		    				mycoachesVo.setCarId(coach.getCoachCarId());
		    				mycoachesVo.setClasses(classes);
		    				mc.add(mycoachesVo); //加到我的教练列表中
		    				
		    				CoachVo cvo= locationService.getCoach(mycoachesVo.getCoachId());
		                	if(cvo!=null){
		                		mycoachesVo.setLae(cvo.getLae());
		                		mycoachesVo.setLge(cvo.getLge());
		                		//mycoachesVo.setDistance(getDistance(cvo.getLae(),cvo.getLge(),Double.valueOf(lae),Double.valueOf(lge)));
		                	}
		                	
		                	for(CoachVo vo:coachList){
		                		if(vo.getCoachId()!=null)
		                		if(mycoachesVo.getCoachId().longValue()==vo.getCoachId().longValue()){
		                			mycoachesVo.setCarNo(vo.getCarNo());
		                			if(vo.getDriveType()!=null)
		                				mycoachesVo.setDrType((byte)vo.getDriveType().intValue());
		                			
		                		}
		                	}
		                	for(int j=classes.size()-1;j>=0;j--){
		                		CoachClassVo ccv=classes.get(j);
			                	//if(ccv.getCend().before(now)){
		                		if(ccv.getCstart().before(now)){
			                		classes.remove(j);
								}else{
									//ccv.setPrice(ccv.getPrice()/ccv.getMaxNum());
								}
		                	}
		    			}else{//没有排班
		    				MycoachesVo mycoachesVo = addMyCoachInfo(coach, classes, studentId);
		    				mycoachesVo.setBookSum(coach.getTeachStudent());
		    				mycoachesVo.setCarId(coach.getCoachCarId());
		    				mc.add(mycoachesVo); //加到我的教练列表中
		    				
		    				CoachVo cvo= locationService.getCoach(mycoachesVo.getCoachId());
		                	if(cvo!=null){
		                		mycoachesVo.setLae(cvo.getLae());
		                		mycoachesVo.setLge(cvo.getLge());
		                		//mycoachesVo.setDistance(getDistance(cvo.getLae(),cvo.getLge(),Double.valueOf(lae),Double.valueOf(lge)));
		                	}
		                	
		                	for(CoachVo vo:coachList){
		                		if(vo.getCoachId()!=null)
		                		if(mycoachesVo.getCoachId().longValue()==vo.getCoachId().longValue()){
		                			mycoachesVo.setCarNo(vo.getCarNo());
		                			mycoachesVo.setCarId(vo.getCoachCarId());
		                			if(vo.getDriveType()!=null)
		                				mycoachesVo.setDrType((byte)vo.getDriveType().intValue());
		                			
		                		}
		                	}
		    			}
	    		}
	    		
	    		Collections.sort(mc,mycoachComparator);
	    		
	    		logger.debug("=========================blur coach end:");
    		}
    	}
    	return mc;
    }
    
    /**
     * 获取预约教练
     * @param studentId
     * @param tokenId
     * @param s
     * @param lge
     * @param lae
     * @param mark
     * @param date
     * @param courseId
     * @return
     */
    @Override
    public ReqResult getCoaches(String studentId, String tokenId, Student s, Double lge, Double lae, String mark,Date date,String courseId,String coachName,String cityId)
    {
        ReqResult r = ReqResult.getSuccess();
        //20160606	驾校学员获取驾校绑定的教练；喱喱学员获取现约约过的教练
        Student student = studentManager.getStudentInfo(Long.parseLong(studentId));
        byte type = student.getIsImport();//0-自动创建（非导入学员）；1-系统录入(导入学员)；
        //模糊查找教练
        if(coachName!=null&&coachName.length()>0){
        	try
	        {
        		List<MycoachesVo> ms=getBlurCoach(s.getStudentId(),coachName,lge,lae);
        		
        		r.setData(ms);
        		return r;
	        }catch (Exception e1)
	        {
	            e1.printStackTrace();
	            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
	            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
	            return r;
	        }
        }else{
	        List<Coach> cs = coachManager.getCoachesByStudentId(Long.parseLong(studentId),type);
	        List<MycoachesVo> ms = new ArrayList<MycoachesVo>();
	        
	        try
	        {
	            if (cs.size() > 0)
	            {
	                ms = BeanCopy.copyList(cs, MycoachesVo.class, BeanCopy.COPYNOTNULL);
	                // 需要添加我的教练出班情况
	                List<Long> coachIds = new ArrayList<Long>();
	                
	                for (int i = 0; i < cs.size(); i++)
	                {
	                	if(student.getApplyexam()>6){//报名成功, 只能是学员的drtype
	                		if(student.getDrType()!=null&&courseId!=null){
		                		if(Integer.parseInt(courseId)<10&&student.getDrType()==1){//c1
		                			coachIds.add(cs.get(i).getCoachId());
								}else if(Integer.parseInt(courseId)>10&&student.getDrType()==2){//c2
									coachIds.add(cs.get(i).getCoachId());
								}
	                		}else{
	                			coachIds.add(cs.get(i).getCoachId());
	                		}
	                		
	                	}else{//没有报名成功，所有绑定教练
	                		coachIds.add(cs.get(i).getCoachId());
	                	}
	                }
	                
	                
	                if(!"1".equals(mark)){//预约教练列表mark!=1, 省去绑定教练的显示
	                	coachIds.clear();
	                	ms.clear();
	                }
	                
	                /*
	                 *  * arrange代表教练排班数量
	                 * maxnum代表教练最多可待人数
	                 * booknum代表教练目前已约人数
	                 * mine代表我已约课数
	                 * 1.arrage为0表示该教练未开放排班，不可预约
	                 * 2.maxnum=booknum为教练被学生约满，不可预约
	                 * 3.mine=arrage表示该学生约了该教练所有排班，不可预约
	                 * 4.mine>0但mine<arrage代表预约成功，且可继续预约
	                 */
	                List<MycoachesVo> aa = coachClassService.getCoachsArrangeWithPrice(ms,coachIds, Long.parseLong(studentId),date,courseId);
	                
	        		
	                for(int i=aa.size()-1;i>=0;i--){
	                	MycoachesVo mvo=aa.get(i);
	                	mvo.setBookSum(mvo.getTeachStudent());
	                	CoachVo cvo= locationService.getCoach(mvo.getCoachId());
	                	if(cvo!=null){
	                		mvo.setLae(cvo.getLae());
	                		mvo.setLge(cvo.getLge());
	                	}
	                }	
	                Collections.sort(aa,mycoachComparator);
	                
	                //屏蔽附近教练及特约教练
	                if(lge != null && lae != null && !"1".equals(mark)){
	                	List<MycoachesVo> msAdd = addMyCoaches(s, lge, lae, ms,courseId,date,cityId);
	                	
	                	for (MycoachesVo myCoachAdd : msAdd) {
	                		aa.add(myCoachAdd);
	                		coachIds.add(myCoachAdd.getCoachId());
	                	}
	                }
	                
	                if(!"1".equals(mark)){
		                List<CoachVo> coachList=(List<CoachVo>)coachService.getCoachsByIds(coachIds).getResult().get(ResultCode.RESULTKEY.DATA);
		                removeMycoach(aa, coachList);
	                }
	                
	                r.setData(aa);
	            }
	           //屏蔽附近教练及特约教练
	            else {
		        	  if(lge != null && lae != null && !"1".equals(mark)){
		        		  
		        		  List<MycoachesVo> msAdd = addMyCoaches(s, lge, lae, ms,courseId,date,cityId);
		        		  
		        		  List<Long> coachIds = new ArrayList<Long>();
			              for (int i = 0; i < msAdd.size(); i++)
			              {
			            	  coachIds.add(msAdd.get(i).getCoachId());
			              }
		        		  List<CoachVo> coachList=(List<CoachVo>)coachService.getCoachsByIds(coachIds).getResult().get(ResultCode.RESULTKEY.DATA);
		        		  removeMycoach(msAdd, coachList);
		        		  
		        		  r.setData(msAdd);
		        	  }
	            }
	            return r;
	        }
	        catch (Exception e1)
	        {
	            logger.warn(LogUtil.getStackMsg(e1));
	            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
	            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
	            return r;
	        }
        }
    }
    
    private void removeMycoach(List<MycoachesVo> msAdd,List<CoachVo> coachList){
    	List<Long> remove=new ArrayList();
		  for(MycoachesVo mvo:msAdd){
          	
          	for(CoachVo vo:coachList){
          		if(vo.getCoachId()!=null)
          		if(mvo.getCoachId().longValue()==vo.getCoachId().longValue()){
          			mvo.setCarNo(vo.getCarNo());
          			if(vo.getDriveType()!=null)
              			mvo.setDrType((byte)vo.getDriveType().intValue());
              		else
              			remove.add(vo.getCoachId());
          		}
          	}
          	
        }
		  for(Long id:remove){
          	for(int i=msAdd.size()-1;i>=0;i--){
          		MycoachesVo mvo=msAdd.get(i);
          		if(id.longValue()==mvo.getCoachId().longValue()){
          			msAdd.remove(i);
          			break;
          		}
          	}
          }
    }

	/**
	 * 获取定向教练
	 */
	@Override
	public ReqResult getCoaches(String studentId, String tokenId, Student s, Double lge, Double lae, String mark) {
		ReqResult r = ReqResult.getSuccess();
		// 20160606 驾校学员获取驾校绑定的教练；喱喱学员获取现约约过的教练
		Student student = studentManager.getStudentInfo(Long.parseLong(studentId));
		byte type = student.getIsImport();// 0-自动创建（非导入学员）；1-系统录入(导入学员)；
		List<Coach> cs = coachManager.getCoachesByStudentId(Long.parseLong(studentId), type);
		List<MycoachesVo> ms = new ArrayList<MycoachesVo>();
		try {
			if (cs.size() > 0) {
				ms = BeanCopy.copyList(cs, MycoachesVo.class, BeanCopy.COPYNOTNULL);
				// 需要添加我的教练出班情况
				List<Long> coachIds = new ArrayList<Long>();
				for (int i = 0; i < cs.size(); i++) {
					coachIds.add(cs.get(i).getCoachId());
				}
				/*
				 * * arrange代表教练排班数量 maxnum代表教练最多可待人数 booknum代表教练目前已约人数
				 * mine代表我已约课数 1.arrage为0表示该教练未开放排班，不可预约
				 * 2.maxnum=booknum为教练被学生约满，不可预约
				 * 3.mine=arrage表示该学生约了该教练所有排班，不可预约
				 * 4.mine>0但mine<arrage代表预约成功，且可继续预约
				 */
				List<Map<String, Long>> aa = coachClassService.getCoachsArrange(coachIds, Long.parseLong(studentId));
				for (int i = 0; i < ms.size(); i++) {
					MycoachesVo mv = ms.get(i);
					Map<String, Long> bb = aa.get(i);
					long ar = bb.get("arrange");
					long mx = bb.get("maxnum");
					long bo = bb.get("booknum");
					long mi = bb.get("mine");
					if (ar == 0l) {
						mv.setOpenType(0); // 未开放排班
					} else if (mi > 0) {
						mv.setOpenType(2); // 预约成功
					} else {
						mv.setOpenType(1); // 可以预约
					}
					ms.set(i, mv);

				}
				// 屏蔽附近教练及特约教练
				if (lge != null && lae != null && !"1".equals(mark)) {
					List<MycoachesVo> msAdd = addMyCoaches(s, lge, lae, ms);
					for (MycoachesVo myCoachAdd : msAdd) {
						ms.add(myCoachAdd);
					}
				}
				r.setData(ms);
			}
			// 屏蔽附近教练及特约教练
			else {
				if (lge != null && lae != null && !"1".equals(mark)) {
					List<MycoachesVo> msAdd = addMyCoaches(s, lge, lae, ms);
					r.setData(msAdd);
				}
			}
			return r;
		} catch (Exception e1) {
			e1.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return r;
		}
	}

	/**
	 * 喱喱学员我的教练中增加特约教练（有排班）、附近有排班的10个教练
	 * 
	 * @param s
	 * @param ms
	 * @param double1
	 * @param double2
	 * @return
	 */
	private List<MycoachesVo> addMyCoaches(Student s, Double lge, Double lae, List<MycoachesVo> ms) {

		long startTime = System.currentTimeMillis();
		List<MycoachesVo> mc = new ArrayList<MycoachesVo>();
		StringBuffer sb = new StringBuffer();
		int FINDDATE = 5;
		try {
			// 根据学员的进度拼接查询语句
			int c1 = s.getCourse1() == null ? 0 : s.getCourse1(); // 科目一成绩
			String flowNo = s.getFlowNo(); // 流水
			Date now = new Date();
			String justStart = TimeUtil.getDateFormat(now, "yyyy-MM-dd HH:mm:ss");
			String justEnd = TimeUtil.getDateFormat(DateUtil.dateAfterMinute(now, 1440 * FINDDATE), "yyyy-MM-dd")
					+ " 23:59:59";
			sb.append(" AND cstart BETWEEN '" + justStart + "' AND '" + justEnd
					+ "' AND book_num < max_num and price > 0 ");
			if (s.getDrType() != null) {
				sb.append(" AND dltype=" + s.getDrType().intValue());
			}

			if (s.getCheckDriveIdState() == 2) { // 有驾照认证，返回所有
				// 返回有价格的排班(防止教练在低版本有排班后升级版本，再排班有价格的，导致学员拉取到无价格的班次)
			} else { // 无驾照认证按照流水处理
				if (flowNo == null || "".equals(flowNo)) { // 新用户、报名无流水，只能约科目二基础
					if (s.getDrType() != null && s.getDrType() == (byte) 1) { // C1
						sb.append(" AND course_id=" + String.valueOf(ReqConstants.COURSE_TYPE_C1_TWO_BASIC)); // 返回科目二基础
					}
					if (s.getDrType() != null && s.getDrType() == (byte) 2) { // C2
						sb.append(" AND course_id=" + String.valueOf(ReqConstants.COURSE_TYPE_C2_TWO_BASIC)); // 返回科目二基础
					}
				} else if (flowNo != null && !"".equals(flowNo) && c1 < 90) { // 报名有流水且科目一未过，不能约科目三
					if (s.getDrType() != null && s.getDrType() == (byte) 1) { // C1
						sb.append(" AND course_id in(1,2,6)"); // 返回科目二所有课程
					}
					if (s.getDrType() != null && s.getDrType() == (byte) 2) { // C2
						sb.append(" AND course_id in(11,12,16)"); // 返回科目二所有课程
					}
				} else if (c1 >= 90) { // 考过科目一，所有都可以约
					// 返回有价格的排班(防止教练在低版本有排班后升级版本，再排班有价格的，导致学员拉取到无价格的班次)
				}
			}

			CoachClassVo search = new CoachClassVo();
			search.setCtype(OrderConstant.OTYPE.BOOKORDER);
			search.setIsdel(OrderConstant.ISDEL.NOTDELETE);

			// 查询特约教练；
			List<Coach> coachTy = coachManager.getTYCoaches();
			List<Long> coachIds = BeanCopy.getFieldList(ms, "coachId");
			for (int i = 0; i < coachTy.size(); i++) {
				if (coachIds.contains(coachTy.get(i).getCoachId())) {
					coachTy.remove(i);
				}
			}

			MycoachesVo mycoachesVo = null;
			Coach coach = null;
			CoachClassQuery ccOne = null;
			CoachClassQuery ccq = null;
			StringBuffer sbTy = null;
			List<CoachClassVo> coachClassListTy = null;
			String keyTYCoach = null;
			String keypcList = null;
			boolean isOver = false;
			if (coachTy.size() > 0) {
				EnrollOrder enroll = enrollOrderManager.getEnrollOrderByStudentId(s.getStudentId());
				for (int i = 0; i < coachTy.size(); i++) {
					boolean isSameCity = false;
					coach = coachManager.getCoachInfo(coachTy.get(i).getCoachId());
					if (enroll != null && enroll.getCityId() != null) {
						if (coach.getCityId().equals(enroll.getCityId())) {
							isSameCity = true;
						}
					} else if (coach.getCityId().equals(s.getCityId())) {
						isSameCity = true;
					}
					if (isSameCity) { // 判断学员跟特约教练是否是同一个城市
						keyTYCoach = RedisKeys.REDISKEY.TEYUE_COACH_CLASS + coachTy.get(i).getCoachId();
						keypcList = RedisKeys.REDISKEY.COACH_CLASS_LIST + coachTy.get(i).getCoachId() + ".studentId."
								+ s.getStudentId();
						coachClassListTy = redisUtil.get(keyTYCoach); // 从缓存里取特约教练的排班

						if (coachClassListTy == null || coachClassListTy.size() == 0) { // 如果缓存为空，则查询表
							sbTy = new StringBuffer();
							ccq = new CoachClassQuery();
							sbTy.append(" and coach_id=" + coachTy.get(i).getCoachId());
							ccq.setGroupBy(sb.toString() + sbTy.toString());
							coachClassListTy = coachClassService.queryByObjectAnd(search, ccq);
							redisUtil.set(keyTYCoach, coachClassListTy);
						}

						if (coachClassListTy.size() > 0) { // 教练有排班
							for (CoachClassVo coachClassVo : coachClassListTy) {
								if (TimeUtil.calcDistanceMillis(coachClassVo.getCstart(), now) < 0) { // 判断缓存里的时间是否超过系统当前时间
									if (s.getDrType() != null) { // 判断教练排班是否跟学员报考类型一致
										if (coachClassVo.getDltype().equals(s.getDrType().intValue())) {
											isOver = true;
										}
									} else {
										isOver = true;
									}
								}
							}
							if (isOver) { // 缓存有可能没更新，要做时间判断
								mycoachesVo = new MycoachesVo();
								if (coach != null) {
									mycoachesVo = addMyCoachInfo(coach, coachClassListTy, keypcList, s.getStudentId());
									mc.add(mycoachesVo); // 加到我的教练列表中
								}
								isOver = false; // 重置
							} else {
								redisUtil.delete(keyTYCoach);
							}
						}
					}
				}
			}

			// 查询附近10公里内10个有排班的教练
			String groupStr = " AND round(6378.138*2*asin(sqrt(pow(sin((lae*pi()/180-" + lae
					+ "*pi()/180)/2),2)+cos(lae*pi()/180)*cos(" + lae + "*pi()/180)* pow(sin( (lge*pi()/180-" + lge
					+ "*pi()/180)/2),2)))*1000) < 10000  GROUP BY coach_id ";
			StringBuffer sbMyCoach = new StringBuffer();
			Long startTime1 = System.currentTimeMillis();
			if (lge != null || lae != null) {
				if (ms.size() > 0) {
					sbMyCoach.append(" and coach_id not in (");
					for (int i = 0; i < ms.size(); i++) { // 过滤绑定教练，防止重复显示
						sbMyCoach.append(ms.get(i).getCoachId());
						if (i != (ms.size() - 1)) {
							sbMyCoach.append(",");
						}
					}
					if (coachTy.size() > 0) {
						if (coachTy.size() > 0) {
							sbMyCoach.append(",");
						}
						for (int j = 0; j < coachTy.size(); j++) { // 过滤特约教练，防止重复显示
							sbMyCoach.append(coachTy.get(j).getCoachId());
							if (j != (coachTy.size() - 1)) {
								sbMyCoach.append(",");
							}
						}
					}
					sbMyCoach.append(")");
				} else {
					if (coachTy.size() > 0) {
						sbMyCoach.append(" and coach_id not in (");
						for (int j = 0; j < coachTy.size(); j++) { // 过滤特约教练，防止重复显示
							sbMyCoach.append(coachTy.get(j).getCoachId());
							if (j != (coachTy.size() - 1)) {
								sbMyCoach.append(",");
							}
						}
						sbMyCoach.append(")");
					}
				}

				ccq = new CoachClassQuery();
				ccq.setGroupBy(sb.toString() + sbMyCoach.toString() + groupStr);
				ccq.setPageSize(10);

				// 查出附近10个有排班的教练
				List<CoachClassVo> ccNear = coachClassService.queryByObjectAnd(search, ccq);
				Long endTime1 = System.currentTimeMillis();
				logger.info(
						"********************************************************* Query nearby ten coach  Cost Time : "
								+ (endTime1 - startTime1) + "ms");
				logger.info("********************************************************* Query nearby ten coach  Sum  : "
						+ ccNear.size());
				StringBuffer sbNear = null;
				List<CoachClassVo> ccNearOne = null;
				String keyCoach = null;
				for (CoachClassVo coachClassVo : ccNear) {
					keyCoach = RedisKeys.REDISKEY.TEYUE_COACH_CLASS + coachClassVo.getCoachId();
					keypcList = RedisKeys.REDISKEY.COACH_CLASS_LIST + coachClassVo.getCoachId() + ".studentId."
							+ s.getStudentId();
					ccNearOne = redisUtil.get(keyCoach); // 从缓存里取教练的排班

					if (ccNearOne == null || ccNearOne.size() == 0) { // 缓存为空
						ccOne = new CoachClassQuery();
						sbNear = new StringBuffer();
						sbNear.append(" and coach_id=" + coachClassVo.getCoachId());
						ccOne.setGroupBy(sb.toString() + sbNear);
						ccOne.setPageSize(10);
						ccNearOne = coachClassService.queryByObjectAnd(search, ccOne);

						redisUtil.delete(keyCoach);
						redisUtil.set(keyCoach, ccNearOne);
					}

					if (ccNearOne.size() > 0) {
						if (TimeUtil.calcDistanceMillis(coachClassVo.getCstart(), now) < 0) {
							if (s.getDrType() != null) { // 判断教练排班是否跟学员报考类型一致
								if (coachClassVo.getDltype().equals(s.getDrType().intValue())) {
									isOver = true;
								}
							} else {
								isOver = true;
							}
						}
						if (isOver) { // 缓存有可能没更新，要做时间判断
							coach = coachManager.getCoachInfo(coachClassVo.getCoachId());
							if (coach != null) {
								mycoachesVo = addMyCoachInfo(coach, ccNearOne, keypcList, s.getStudentId());
								mc.add(mycoachesVo);
							}
							isOver = false; // 重置
						} else {
							redisUtil.delete(keyCoach);
						}
					}
				}
			}

			long endTime = System.currentTimeMillis();
			logger.info("********************************************************* Cost Time : " + (endTime - startTime)
					+ "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mc;
	}

	/**
	 * 喱喱学员我的教练中增加特约教练（有排班）、附近有排班的10个教练
	 * 
	 * @param s
	 * @param ms
	 * @param double1
	 * @param double2
	 * @return
	 */
	private List<MycoachesVo> addMyCoaches(Student s, Double lge, Double lae, List<MycoachesVo> ms, String courseId,
			Date date,String cityId) {
		Date now=new Date();
		String nowdate=TimeUtil.getDateFormat(now, "yyyy-MM-dd");
		
		long startTime = System.currentTimeMillis();
		List<MycoachesVo> mc = new ArrayList<MycoachesVo>();
		StringBuffer sb = new StringBuffer();
		/// int FINDDATE=5;
		try {
			// 根据学员的进度拼接查询语句
			// int c1 = s.getCourse1() == null ? 0 : s.getCourse1(); // 科目一成绩
			// String flowNo = s.getFlowNo(); // 流水
			// Date now=new Date();
			String justdate = TimeUtil.getDateFormat(date, "yyyy-MM-dd");
			// String justStart = TimeUtil.getDateFormat(now,"yyyy-MM-dd
			// HH:mm:ss");
			String justStart = justdate + " 00:00:00";
			
			if(nowdate.equals(justdate)){//今天
				justStart = TimeUtil.getDateFormat(now,"yyyy-MM-dd HH:mm:ss");
			}
			
			// String
			// justEnd=TimeUtil.getDateFormat(DateUtil.dateAfterMinute(now,
			// 1440),"yyyy-MM-dd")+" 23:59:59";
			String justEnd = TimeUtil.getDateFormat(date, "yyyy-MM-dd") + " 23:59:59";
			// sb.append(" AND cstart BETWEEN '" + justStart + "' AND '" +
			// justEnd + "' AND book_num < max_num and price > 0 ");
			sb.append(" AND cstart BETWEEN '" + justStart + "' AND '" + justEnd + "' and price > 0 ");
			if(courseId!=null)
			sb.append(" AND course_id=" + courseId);
			// if (s.getDrType() != null) {
			// sb.append(" AND dltype=" +s.getDrType().intValue());
			// }
			//
			// if(s.getCheckDriveIdState() == 2) { //有驾照认证，返回所有
			// //返回有价格的排班(防止教练在低版本有排班后升级版本，再排班有价格的，导致学员拉取到无价格的班次)
			// }
			// else { //无驾照认证按照流水处理
			// if ( flowNo == null || "".equals(flowNo)) { //新用户、报名无流水，只能约科目二基础
			// if (s.getDrType() != null && s.getDrType() == (byte) 1) { //C1
			// sb.append(" AND course_id=" +
			// String.valueOf(ReqConstants.COURSE_TYPE_C1_TWO_BASIC)); //返回科目二基础
			// }
			// if (s.getDrType() != null && s.getDrType() == (byte) 2) { //C2
			// sb.append(" AND course_id=" +
			// String.valueOf(ReqConstants.COURSE_TYPE_C2_TWO_BASIC)); //返回科目二基础
			// }
			// }
			// else if (flowNo != null && !"".equals(flowNo) && c1 < 90) {
			// //报名有流水且科目一未过，不能约科目三
			// if (s.getDrType() != null && s.getDrType() == (byte) 1) { //C1
			// sb.append(" AND course_id in(1,2,6)"); //返回科目二所有课程
			// }
			// if (s.getDrType() != null && s.getDrType() == (byte) 2) { //C2
			// sb.append(" AND course_id in(11,12,16)"); //返回科目二所有课程
			// }
			// }
			// else if( c1 >= 90) { //考过科目一，所有都可以约
			// //返回有价格的排班(防止教练在低版本有排班后升级版本，再排班有价格的，导致学员拉取到无价格的班次)
			// }
			// }

			CoachClassVo search = new CoachClassVo();
			search.setCtype(OrderConstant.OTYPE.BOOKORDER);
			search.setIsdel(OrderConstant.ISDEL.NOTDELETE);

			// 查询特约教练；
			List<Coach> coachTy = coachManager.getTYCoaches();
			List<Long> coachIds = BeanCopy.getFieldList(ms, "coachId");
			
			for (int i = 0; i < coachTy.size(); i++) {
				if (coachIds.contains(coachTy.get(i).getCoachId())) {
					coachTy.remove(i);
				}
			}

			List<CoachClassVo> coachClassListTy = null;
			
			logger.debug("================================================begin ty coach" + coachTy.size());
			if (coachTy.size() > 0) {
				// 获取特约教练的排班记录
				
				List<Long> coachTyIds=new ArrayList();
				for(Coach coach:coachTy){
					coachTyIds.add(coach.getCoachId());
				}
				
				
				Map<Long,List<CoachClassVo>> map=coachClassService.queryByCoachDateWithPrice(date, coachTyIds,
						OrderConstant.OTYPE.BOOKORDER);
				
				
				List<CoachVo> vcoaches=coachManager.getCoachesByIds(coachTyIds);
				
				Map<Long,CoachVo> coachmap=new HashMap();
				for(CoachVo v:vcoaches){
					coachmap.put(v.getCoachId(), v);
				}
				
				for (int i = 0; i < coachTy.size(); i++) {
					
					//获取教练所有有价格的排班
					coachClassListTy=map.get(coachTy.get(i).getCoachId());

					if(coachClassListTy==null||coachClassListTy.size()==0){//没有对应课程的排班, 查找教练的出车支持课程or 绑定车
						List<Car> cars = carManager.getCarByCoachId(coachTy.get(i).getCoachId());
						
						boolean hascar=false;
						if(cars!=null){
							for(Car car:cars){//看看教练车是否有匹配的
								if(car.getDriveType()!=null){//车型匹配
									if(Integer.parseInt(courseId)<10&&car.getDriveType()==1){//c1
										hascar=true;
										break;
									}else if(Integer.parseInt(courseId)>10&&car.getDriveType()==2){//c2
										hascar=true;
										break;
									}
								}
							}
						}

						if(!hascar){//没有匹配C1,C2的车辆
							continue;
						}
					}
					
					if(coachClassListTy!=null){
						for(int j=coachClassListTy.size()-1;j>=0;j--){
							CoachClassVo ccv=coachClassListTy.get(j);
							
							if(!ccv.getCourseId().equals(courseId)){
								coachClassListTy.remove(j);continue;
							}
							
							//if(ccv.getCend().before(now)){
							if(ccv.getCstart().before(now)){
								coachClassListTy.remove(j);
							}else{
								//ccv.setPrice(ccv.getPrice()/ccv.getMaxNum());
							}
						}
					}
					
					// 填充每个排班的学员明细记录
					//List<CoachClassVo> classes = coachClassService.fillPlantData(coachClassListTy);

					CoachVo coach=coachmap.get(coachTy.get(i).getCoachId());
					if (coach != null) {
//						if(coach.getIsImport()!=1&&coach.getCheckDrState()!=2){//注册教练没有认证
//							continue;
//						}
						
						if(!courseId.equals("5")&&!courseId.equals("15")&&!courseId.equals("2")&&!courseId.equals("12")&&!courseId.equals("4")&&!courseId.equals("14")){//不是陪驾
							if(coach.getIsImport()!=1){//新注册教练
								continue;
							}
						}

						MycoachesVo mycoachesVo = addMyCoachInfo(coach, null, s.getStudentId());
						mycoachesVo.setBookSum(coach.getTeachStudent());
						mycoachesVo.setClasses(coachClassListTy);
						mc.add(mycoachesVo); // 加到我的教练列表中
					}
					
				}
				
			}
			logger.debug("================================================end ty coach:" + mc.size());
			//特约排序
			Collections.sort(mc,mycoachComparator);
			
			try{
				List<Long> tcoachIds=new ArrayList();
				for(MycoachesVo vo:mc){
					tcoachIds.add(vo.getCoachId());
				}
				
				List<CoachVo> csss=locationService.getCoaches(tcoachIds);
				
				for(CoachVo cvo:csss){
					for(MycoachesVo mycoachesVo:mc){
						if(cvo.getCoachId().longValue()==mycoachesVo.getCoachId().longValue()){
							mycoachesVo.setLae(cvo.getLae());
	                		mycoachesVo.setLge(cvo.getLge());
	                		mycoachesVo.setDistance(getDistance(cvo.getLae(),cvo.getLge(),Double.valueOf(lae),Double.valueOf(lge)));
	                		break;
						}
					}
					
				}
			}catch(Exception ex){
					logger.warn(LogUtil.getStackMsg(ex));
			}
			
			for(int i=mc.size()-1;i>=0;i--){
				MycoachesVo mvo=mc.get(i);
				if(cityId!=null&&!cityId.equals(mvo.getCityId()+"")){//如果有city 参数进来就比较城市
					mc.remove(i);continue;
				}
				
				if(mvo.getDistance()>30*1000){
					mc.remove(i);
				}
			}
			
    		List<MycoachesVo> rm=new ArrayList();
    		for(int i=mc.size()-1;i>=0;i--){
    			MycoachesVo mvo=mc.get(i);
    			if(mvo.getClasses()==null||mvo.getClasses().size()==0){
    				rm.add(mc.remove(i));
    			}
    		}
    		
    		Collections.sort(rm,mycoachDisComparator);
    		for(MycoachesVo vo:rm){
    			mc.add(vo);
    		}
			    		
			/**
			 * 获取附近10公里的教练并获取其排班，如果有排班，就过滤条件，没有排班也显示
			 */
			try{
			List<MycoachesVo> nears = getNearCoachClass(s, lge, lae, courseId, ms, coachTy,date,cityId);
			List<MycoachesVo> msAddclass=new ArrayList();
			
			for(int i=nears.size()-1;i>=0;i--){
				if(nears.get(i).getClasses()!=null&&nears.get(i).getClasses().size()>0){
					for(int j=nears.get(i).getClasses().size()-1;j>=0;j--){
						if(nears.get(i).getClasses().get(j).getCend().before(now)){
							nears.get(i).getClasses().remove(j);
						}
					}
					msAddclass.add(nears.remove(i));
				}
			}
			
			Collections.sort(msAddclass,mycoachComparator);
			
			for(int i=0;i<msAddclass.size();i++){
				mc.add(msAddclass.get(i));
			}
			
			int left=0;
			if(mc.size()<50) left=50-mc.size();
			left=nears.size()>left?left:nears.size();
			for(int i=0;i<left;i++){
				mc.add(nears.get(i));
			}
			
			}catch(Exception ee){
				ee.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			logger.info("====================================================== Cost Time : " + (endTime - startTime)
					+ "ms");
		} catch (Exception e) {
			logger.warn(LogUtil.getStackMsg(e));
		}

		return mc;
	}
	
	private MycoachComparator mycoachComparator=new MycoachComparator();
	
	class MycoachComparator implements Comparator<MycoachesVo>{
		@Override
		public int compare(MycoachesVo arg0, MycoachesVo arg1) {
			if(arg0.getClasses()!=null){
				if(arg1.getClasses()!=null){
					if(arg0.getClasses().size()>arg1.getClasses().size()){
						return -1;
					}else{
						return 1;
					}
				}else{
					return -1;
				}
			}else{
				if(arg1.getClasses()!=null){
					return 1;
				}else{
					return 0;
				}
			}
			
		}
	}
	
	private MycoachDisComparator mycoachDisComparator=new MycoachDisComparator();
	
	class MycoachDisComparator implements Comparator<MycoachesVo>{
		@Override
		public int compare(MycoachesVo arg0, MycoachesVo arg1) {
			if(arg0.getDistance()<arg1.getDistance()){
				return -1;
			}else if(arg0.getDistance()>arg1.getDistance()){
				return 1;
			}else{
				return 0;
			}
			
		}
	}
	
	private final static double EARTH_RADIUS = 6378.137;//地球半径
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}
	
	public  double getDistance(double lat1, double lng1, double lat2, double lng2)
	{
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);

	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = Math.round(s * 10000) / 10;
	   return s;
	}

	private List<MycoachesVo> getNearCoachClass(Student s, Double lge, Double lae, String courseId,
			List<MycoachesVo> ms, List<Coach> coachTy,Date date,String cityId) throws Exception {
		SearchVo lsearch = new SearchVo();
		lsearch.setLae(Double.valueOf(lae));
		lsearch.setLge(Double.valueOf(lge));
		lsearch.setPageSkip(0);
		lsearch.setPageLimit(50);
		lsearch.setDistance(10D);
		List<CoachVo> coaches = locationService.getNearBy(lsearch);
		List<Long> coachIds = new ArrayList();
		List<Long> tcoachIds = new ArrayList();
		List<MycoachesVo> result = new ArrayList();
		
		for (CoachVo vo : coaches) {
			boolean find = false;
			for (MycoachesVo mc : ms) {
				if (vo.getCoachId().longValue() == mc.getCoachId().longValue()) {
					find = true;
					break;
				}
			}
			if (!find) {// 判断是否存在特约教练里
				for (Coach ty : coachTy) {
					if (ty.getCoachId().longValue() == vo.getCoachId().longValue()) {
						find = true;
						break;
					}
				}
			}
			
			if (!find) {
				tcoachIds.add(vo.getCoachId());
			}
		}
		
		List<CoachVo> vcoaches=coachManager.getCoachesByIds(tcoachIds);
		
		Map<Long,CoachVo> coachmap=new HashMap();
		for (CoachVo vo : vcoaches) {
			if(vo==null){
				continue;
			}
			
			if(vo.getIsImport()==null||vo.getCheckDrState()==null){
				continue;
			}
			
			if(vo.getIsImport()!=1&&vo.getCheckDrState()!=2){//注册教练没有认证
				continue;
			}
			
			if(!courseId.equals("5")&&!courseId.equals("15")&&!courseId.equals("2")&&!courseId.equals("12")&&!courseId.equals("4")&&!courseId.equals("14")){//不是陪驾
				if(vo.getIsImport()!=1){//新注册教练
					continue;
				}
			}
			
			if(cityId!=null&&!cityId.equals(vo.getCityId()+"")){//如果有city 参数进来就比较城市
				continue;
			}
			
			coachIds.add(vo.getCoachId());
			coachmap.put(vo.getCoachId(), vo);
		}
		
		logger.debug("==================================near coach:"+coachIds.size());
		// 教练排班数据
		
		Map<Long, List<CoachClassVo>> ccmap = coachClassService.queryByCoachDateWithPrice(date, coachIds,
				OrderConstant.OTYPE.BOOKORDER);
		
		for (CoachVo vo : coaches) {
			
			//教练已经被过滤
			boolean flag=false;
			for(int i=0;i<coachIds.size();i++){
				if(coachIds.get(i).longValue()==vo.getCoachId().longValue()){
					flag=true;
					break;
				}
			}
			if(!flag){
				continue;
			}
			
			CoachVo coach =coachmap.get(vo.getCoachId());
			
			if (ccmap.get(vo.getCoachId()) != null && ccmap.get(vo.getCoachId()).size() > 0) {// 有排班数据
				boolean hasclass=false;
				for (CoachClassVo cco : ccmap.get(vo.getCoachId())) {
					if(cco.getPrice()!=null){
						//cco.setPrice(cco.getPrice()/cco.getMaxNum());
						if (cco.getCourseId().equals(courseId)) {// 教练排班表有匹配的课程
							result.add(getMycoachInfo(coach, vo, s, ccmap.get(vo.getCoachId())));
							hasclass=true;
							break;
						}
					}
				}
				
				if(!hasclass){//没有匹配排班, 查看看是否匹配c1,c2
					processNoneClasses(result, courseId, coach, vo, s, null);
				}
			} else {// 没有排班数据都显示
				processNoneClasses(result, courseId, coach, vo, s, null);
				
			}
		}
		return result;
	}
	
	/**
	 * 处理没有排班的教练数据
	 * @param result
	 * @param courseId
	 * @param coach
	 * @param vo
	 * @param s
	 * @param classes
	 */
	private void processNoneClasses(List<MycoachesVo> result,String courseId,CoachVo coach,CoachVo vo,Student s,List<CoachClassVo> classes){
		List<Car> cars = carManager.getCarByCoachId(vo.getCoachId());
		if(cars!=null){
			for(Car car:cars){//看看教练车是否有匹配的
				if(car.getDriveType()!=null){//车型匹配
					if(Integer.parseInt(courseId)<10&&car.getDriveType()==1){//c1
						result.add(getMycoachInfo(coach, vo, s, null));
						break;
					}else if(Integer.parseInt(courseId)>10&&car.getDriveType()==2){//c2
						result.add(getMycoachInfo(coach, vo, s, null));
						break;
					}
				}
			}
		}
	}
	
	private MycoachesVo getMycoachInfo(CoachVo coach,CoachVo vo,Student s,List<CoachClassVo> classes){
		MycoachesVo mycoachesVo = addMyCoachInfo(coach, null, s.getStudentId());
		mycoachesVo.setBookSum(coach.getTeachStudent());
		mycoachesVo.setClasses(classes);
		mycoachesVo.setLae(vo.getLae());
		mycoachesVo.setLge(vo.getLge());
		return mycoachesVo;
	}

	private MycoachesVo addMyCoachInfo(Coach coach, List<CoachClassVo> ccNearOne, String keypcList, Long studentId) {
		MycoachesVo mycoachesVo = null;
		try {
			mycoachesVo = BeanCopy.copyNotNull(coach, MycoachesVo.class);
			// 这里每一次循环耗时200-300毫秒，一共30个教练，共耗时7-9秒
			/*
			 * PlantClassQuery pq=new PlantClassQuery(); String ccidin =
			 * "and ccid in ("; int j=0; for (;j<ccNearOne.size()-1;j++) {
			 * ccidin += ccNearOne.get(j).getCcid()+","; } ccidin +=
			 * ccNearOne.get(j).getCcid()+")"; pq.setGroupBy(ccidin);
			 * PlantClassVo plantSearch = new PlantClassVo();
			 * plantSearch.setIsdel(OrderConstant.ISDEL.NOTDELETE);
			 * plantSearch.setStudentId(studentId); if (pcList == null ||
			 * pcList.size() == 0) { //pcList =
			 * plantClassService.queryByObjectAnd(plantSearch, pq);
			 * //redisUtil.set(keypcList, pcList); }
			 */
			List<PlantClassVo> pcList = redisUtil.get(keypcList); // 直接从缓存里取
			if (pcList != null && !pcList.isEmpty()) { // 预约成功
				mycoachesVo.setOpenType(2); // 预约成功
			} else {
				mycoachesVo.setOpenType(1); // 可以预约
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mycoachesVo;
	}

	/**
	 * 生成展示的教练信息
	 * 
	 * @param coach
	 * @param ccNearOne
	 * @param studentId
	 * @return
	 */
	private MycoachesVo addMyCoachInfo(Coach coach, List<CoachClassVo> ccNearOne, Long studentId) {
		MycoachesVo mycoachesVo = null;
		try {
			mycoachesVo = BeanCopy.copyNotNull(coach, MycoachesVo.class);
			boolean find = false;
			boolean hasclass = false;
			if (ccNearOne != null) {
				for (CoachClassVo v : ccNearOne) {
					if (v.getPlantClassList() != null) {

						for (PlantClassVo p : v.getPlantClassList()) {// 遍历每个排班具体明细
							hasclass = true;
							if (p.getStudentId().longValue() == studentId.longValue()) {// 学员约了排班
								find = true;
								break;
							}
						}
						if (find) {// 学员已经约了教练某个排班
							break;
						}
					}
				}
			}
			if (find) {// 学员已经约了教练某个排班
				mycoachesVo.setOpenType(2); // 预约成功
			} else {
				if (hasclass) {
					mycoachesVo.setOpenType(1); // 可以预约
				} else {// 没有排班
					mycoachesVo.setOpenType(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mycoachesVo;
	}
	
	private MycoachesVo addMyCoachInfo(CoachVo coach, List<CoachClassVo> ccNearOne, Long studentId) {
		MycoachesVo mycoachesVo = null;
		try {
			mycoachesVo = BeanCopy.copyNotNull(coach, MycoachesVo.class);
			boolean find = false;
			boolean hasclass = false;
			if (ccNearOne != null) {
				for (CoachClassVo v : ccNearOne) {
					if (v.getPlantClassList() != null) {

						for (PlantClassVo p : v.getPlantClassList()) {// 遍历每个排班具体明细
							hasclass = true;
							if (p.getStudentId().longValue() == studentId.longValue()) {// 学员约了排班
								find = true;
								break;
							}
						}
						if (find) {// 学员已经约了教练某个排班
							break;
						}
					}
				}
			}
			if (find) {// 学员已经约了教练某个排班
				mycoachesVo.setOpenType(2); // 预约成功
			} else {
				if (hasclass) {
					mycoachesVo.setOpenType(1); // 可以预约
				} else {// 没有排班
					mycoachesVo.setOpenType(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mycoachesVo;
	}

	@Override
	public ReqResult getCoachesOne(String studentId, String coachId, String tokenId) {
		ReqResult r = ReqResult.getSuccess();
		Coach c = coachManager.getCoachInfo(Long.parseLong(coachId));
		// 查询带教人数
//		CoachClassQuery coachclassQuery = new CoachClassQuery();
//		coachclassQuery.setSqlPost(" and isdel=0 and ctype=3 and rend < NOW() ");
//		Integer bookSum = coachClassService.queryBookNumByCoachId(c.getCoachId(), coachclassQuery);// 预约
//		CoachClassQuery coachclassQuery2 = new CoachClassQuery();
//		coachclassQuery2.setSqlPost(" and isdel=0 and ctype=1");
//		Integer bookSumOrder = coachClassService.queryBookNumByCoachId2(c.getCoachId(), coachclassQuery2);// 现约
//		c.setBookSum(bookSum + bookSumOrder);
		c.setBookSum(c.getTeachStudent());
		try {
			MycoachesVo ms = new MycoachesVo();
			ms = BeanCopy.copy2Null(c, ms);
			ms.setBookSum(c.getTeachStudent());
			try {
				Car car = carManager.getCarInfo(c.getCoachCarId()); // 获取车辆信息
				if(car!=null){
				ms.setCarId(car.getCarId());
				ms.setCarNo(car.getCarNo());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			r.setData(ms);
			return r;
		} catch (Exception e1) {
			e1.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return r;
		}
	}

	@Override
	public ReqResult getStudentInfoByCoachId(String coachId, String tokenId, String pageNum, String pageSize) {
		ReqResult r = ReqResult.getSuccess();
		List<Student> studentList = studentManager.getStudentByCoachId(Long.parseLong(coachId),
				Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		List<StudentInfoVo> ms = new ArrayList<StudentInfoVo>();
		try {
			ms = BeanCopy.copyList(studentList, StudentInfoVo.class, BeanCopy.COPYNOTNULL);
			r.setData(ms);
			return r;
		} catch (Exception e1) {
			e1.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return r;
		}
	}

	@Override
	public ReqResult doCourseStatus(String userId, String orderId, String coachId, String status) {

		ReqResult r = ReqResult.getSuccess();

		byte st = 0;
		try {
			st = Byte.parseByte(status.trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r;
		}
		// Student s = new Student();
		// s.setStudentId(Long.parseLong(userId));

		// 20151130目前只允许学员点下课
		if (st == ReqConstants.STUDENT_STATUS_OFF_CLASS) {
			pushStatus(Long.parseLong(coachId), Long.parseLong(userId), orderId, status, "finish", 2);
		} else {
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r;
		}

		return r;
	}

	private void pushStatus(long coachId, long studentId, String orderId, String status, String tag, int targetType) {
		CourseStatusEventVo statusEventVo = new CourseStatusEventVo();
		statusEventVo.setCoachId(coachId);
		statusEventVo.setOrderId(orderId);
		statusEventVo.setStatus(status);
		statusEventVo.setStudentId(studentId);
		statusEventVo.setTargetType(targetType);
		statusEventVo.setTime(new Date());

		Message message = new Message(studentProducer.getCreateTopicKey(), tag,
				SerializableUtil.serialize(statusEventVo));
		try {
			studentProducer.send(message);
			logger.debug("CoachServiceImpl----->pushStatus:" + message);
		} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
			logger.error("coachId:" + coachId + "studentId:" + studentId + "|orderId:" + orderId + "|tag:" + tag, e);
		}
	}

	@Override
	public int addMoney(long studentId, int money) {
		int leftMoney = 0;
		StudentAccount studentAccount = studentManager.getStudentMoney(studentId);
		if (studentAccount == null) {
			if (money >= 0) {
				studentAccount = new StudentAccount();
				studentAccount.setMoney(money);
				studentAccount.setStudentId(studentId);
				leftMoney = money;
				studentManager.insertStudentAccount(studentAccount);
			} else {
				return Integer.MIN_VALUE;
			}
		} else {
			int curMoney = studentAccount.getMoney();
			curMoney += money;
			if (curMoney >= 0) {
				leftMoney = curMoney;
				studentAccount.setMoney(curMoney);
				studentManager.updateStudentAccount(studentAccount);
			} else {
				return Integer.MIN_VALUE;
			}
		}
		return leftMoney;
	}

	@Override
	public ReqResult addDriveCard(String userId, String drType, String drLicence, String drPhoto, String drPhoto2,
			String drExpires) {
		ReqResult r = ReqResult.getSuccess();
		// 20160519根据运营要求，驾驶证审核自动通过
		Student s = new Student();
		s.setStudentId(Long.parseLong(userId));
		if (drLicence != null && !"".equals(drLicence)) {
			s.setDrLicence(drLicence);
		}
		s.setDrPhoto(drPhoto);
		s.setDrPhoto2(drPhoto2);
		if (drType != null && !"".equals(drType)) {
			s.setDrType(Byte.parseByte(drType.trim()));
		}
		if (null != drExpires && !"".equals(drExpires.trim())) {
			try {
				Date d = new Date(Long.parseLong(drExpires) * 1000);
				s.setDrExpires(d);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		s.setCheckDriveIdState((byte) ReqConstants.CHECK_STATE_SUCCESS);
		studentManager.updateStudent(s);
		// 20160509用户实名认证通过后，才更改主表，实名认证信息保存在认证表中（作废 //20160519根据运营要求，驾驶证审核自动通过）
		StudentAuth studentAuth = new StudentAuth();
		studentAuth.setStudentId(Long.parseLong(userId));
		studentAuth.setCreateTime(new Date());
		if (drLicence != null && !"".equals(drLicence)) {
			studentAuth.setFileNo(drLicence.trim());
		}
		if (drType != null && !"".equals(drType)) {
			studentAuth.setDrtype(Byte.parseByte(drType.trim()));
		}
		studentAuth.setPhoto(drPhoto);
		studentAuth.setPhoto2(drPhoto2);
		studentAuth.setType(ReqConstants.CHECK_TYPE_DRIVECARD);
		studentAuth.setState(ReqConstants.CHECK_STATE_SUCCESS);
		studentAuthManager.addStudentAuth(studentAuth);
		return r;
	}

	@Override
	public ReqResult postAgreement(String userId, String agreement) {
		ReqResult r = ReqResult.getSuccess();
		Student s = new Student();
		s.setStudentId(Long.parseLong(userId));
		s.setAgreement(Byte.parseByte(agreement.trim()));
		studentManager.updateStudent(s);
		// 返回数据到客户端
		return r;
	}

	@Override
	public ReqResult postOpenId(String userId, String userType, String code) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String openId = payServiceNew.getOpenId(code);
			if (null != openId && !"".equals(openId)) {
				Student s = new Student();
				s.setStudentId(Long.parseLong(userId));
				s.setOpenId(openId);
				studentManager.updateStudent(s);
			}
		} catch (Exception e) {
			logger.error("userId=" + userId + ",userType=" + userType + ",code=" + code + " postOpenId Exception:"
					+ e.getMessage(), e);
			r = ReqResult.getFailed();
		}
		return r;
	}

	@Override
	public String getOpenId(String userId, String userType) {
		Student s = studentManager.getStudentInfo(Long.parseLong(userId));
		if (null != s) {
			return s.getOpenId();
		}
		return null;
	}

	@Override
	public int updateStudent(Student student) {
		return studentManager.updateStudent(student) == 0 ? 0 : 1;
	}

	@Override
	public int lockStudent(long userId, int state, Date reviveTime, String note) {
		Student s = studentManager.getStudentInfo(userId);
		s.setState(state);
		if (null != reviveTime) {
			s.setReviveTime(reviveTime);
		}
		if (null != note) {
			s.setExtra(note);
		}
		studentManager.updateStudent(s);
		try {
			JpushMsg jmsg = new JpushMsg();
			jmsg.setAlter(note);
			jmsg.setUserId(s.getStudentId());
			jmsg.setOrderId(s.getStudentId() + JpushConstant.OPERATE.USER_LOCK);
			jmsg.setOperate(JpushConstant.OPERATE.USER_LOCK);
			Message jpush = new Message();
			jpush.setKeys(s.getStudentId() + JpushConstant.OPERATE.USER_LOCK);
			jpush.setTopic(jpushProducer.getCreateTopicKey());
			jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
			jpush.setBody(SerializableUtil.serialize(jmsg));
			jpushProducer.send(jpush);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 如果用户锁定，则强制用户下线
		if (state != ReqConstants.USER_STATE_NORMAL) {
			redisUtil.delete(REDISKEY.STUDENT_TOKEN + userId);
		}
		return 0;
	}

	@Override
	public ReqResult getMicroClass(MicroClass mocroClass) {

		ReqResult r = ReqResult.getSuccess();
		List<MicroClassVo> mcvo = new ArrayList<MicroClassVo>();
		try {
			List<MicroClass> mcList = studentManager.getMicroClass(mocroClass);
			mcvo = BeanCopy.copyList(mcList, MicroClassVo.class, BeanCopy.COPYNOTNULL);
			r.setData(mcvo);
		} catch (Exception e) {
			logger.error("************************************ getMicroClass Error: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}

	@Override
	public ReqResult getSubjectVideo(String userId, String userType, String cityId, String examId, String fileType,
			String subject, String id, String pageNo, String pageSize) {
		ReqResult r = ReqResult.getSuccess();

		List<SubjectVideo> studentList = studentManager.getSubjectVideo(cityId, examId, subject, fileType, id, pageNo,
				pageSize);
		List<SubjectVideoVo> ms = new ArrayList<SubjectVideoVo>();
		try {
			ms = BeanCopy.copyList(studentList, SubjectVideoVo.class, BeanCopy.COPYNOTNULL);
			r.setData(ms);
			return r;
		} catch (Exception e1) {
			e1.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return r;
		}
	}

	private ReqResult isLockedStudent(Student s) {
		ReqResult r = ReqResult.getSuccess();
		int state = s.getState();
		Map<String, Object> data = new HashMap<String, Object>();
		if (state == ReqConstants.USER_STATE_LOCKED_FOREVER) {
			data.put("reason", s.getExtra());
			r.setData(data);
			r.setCode(ResultCode.ERRORCODE.USER_LOCKED);
			r.setMsgInfo(ResultCode.ERRORINFO.USER_LOCKED);
			return r;
		} else if (state == ReqConstants.USER_STATE_LOCKED_TEMP) {
			Date reviveTime = s.getReviveTime();
			Date now = new Date();
			if (reviveTime.after(now)) {
				data.put("reason", s.getExtra());
				data.put("reviveTime", reviveTime);
				r.setData(data);
				r.setCode(ResultCode.ERRORCODE.USER_LOCKED);
				r.setMsgInfo(ResultCode.ERRORINFO.USER_LOCKED + "\n原因：" + s.getExtra() + " \n恢复时间："
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reviveTime));
				return r;
			} else {
				// 用户复活时间已到，恢复状态为正常
				s.setState(ReqConstants.USER_STATE_NORMAL);
				studentManager.updateStudent(s);
			}
		}
		return null;
	}

	
    
	/**
	 * 获取第三方帐号绑定状态，已绑定则返回登录成功
	 */
	@Override
	public ReqResult getBindState(String unionId, String openId, String accType) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if (null == unionId || "".equals(unionId.trim())){
				r.setCode(ResultCode.ERRORCODE.EXCEPTION);
				r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
				return r;
			}
			unionId = unionId.trim();
			List<Student> students = studentManager.queryByUnionId(unionId, accType);
			if (students != null && students.size() == 1){
				Student s = students.get(0);
				ReqResult temp = isLockedStudent(s);
				if (temp != null)
					return temp;
				Map<String, Object> data = new HashMap<String, Object>();
				String token = SecurityUtil.getUUID();
                long studentId = s.getStudentId();
                // 将token保存到redis中
                redisUtil.setAll(REDISKEY.STUDENT_TOKEN + studentId, token, 0);
                Date now = new Date(); 
                if (s.getFirstLogin() == null)
                	s.setFirstLogin(now);
                s.setLastLogin(now);
                if (openId != null && !"".equals(openId))
                	s.setOpenId(openId);
                studentManager.updateStudent(s);
                data.put("studentId", studentId);
                data.put("token", token);
                data.put("studentInfo", BeanCopy.copyNotNull(s, StudentInfoVo.class));
                r.setCode(ResultCode.ERRORCODE.SUCCESS);
                r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
                r.setData(data);
                return r;
			} else {
				r.setCode(ResultCode.ERRORCODE.NO_USER);
	            r.setMsgInfo(ResultCode.ERRORINFO.NO_USER);
	            return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            return r;
		}
	}

	

	/**
	 * 绑定第三方帐号，并登录
	 */
	@Override
	public ReqResult bindThirdAccount(String unionId, String accType, String openId, String phoneNum, String code, String name,
			String headIcon) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if (null == unionId || "".equals(unionId.trim())){
				r.setCode(ResultCode.ERRORCODE.EXCEPTION);
				r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
				return r;
			}
			unionId = unionId.trim();
			List<Student> check = studentManager.queryByUnionId(unionId, accType);
			if (check != null && check.size() > 0) {
				r.setCode(ResultCode.ERRORCODE.USER_EXIST);
				r.setMsgInfo(ResultCode.ERRORINFO.USER_EXIST);
				return r;
			}
			Student s = studentManager.getStudentByPhoneNum(phoneNum);
			if (s != null) {
				ReqResult temp = isLockedStudent(s);
				if (temp != null)
					return temp;
				String co = redisUtil.get(REDISKEY.STUDENT_AUTHCODE + phoneNum);
				redisUtil.delete(REDISKEY.STUDENT_AUTHCODE + phoneNum);
				if (co != null && co.equals(code.trim())) {
					if ("1".equals(accType.trim())) {
						if (s.getUnionId() != null) {
							r.setCode(ResultCode.ERRORCODE.OPENID_EXIST);
							r.setMsgInfo(ResultCode.ERRORINFO.OPENID_EXIST);
							return r;
						}
						s.setUnionId(unionId);
						s.setOpenId(openId);
					} else {
						if (s.getQqOpenId() != null) {
							r.setCode(ResultCode.ERRORCODE.OPENID_EXIST);
							r.setMsgInfo(ResultCode.ERRORINFO.OPENID_EXIST);
							return r;
						}
						s.setQqOpenId(unionId);
					}
					Map<String, Object> data = new HashMap<String, Object>();
					String token = SecurityUtil.getUUID();
					long studentId = s.getStudentId();
					// 将token保存到redis中
					redisUtil.setAll(REDISKEY.STUDENT_TOKEN + studentId, token, 0);
					Date now = new Date();
					if (s.getFirstLogin() == null)
						s.setFirstLogin(now);
					s.setLastLogin(now);
					if (name != null && (s.getName() == null || "".equals(s.getName()) || "喱喱同学".equals(s.getName())))
						s.setName(name.trim());
					if (s.getHeadIcon() == null && headIcon != null)
						s.setHeadIcon(headIcon.trim());
					studentManager.updateStudent(s);
					data.put("studentId", studentId);
					data.put("token", token);
					data.put("studentInfo", BeanCopy.copyNotNull(s, StudentInfoVo.class));
					r.setCode(ResultCode.ERRORCODE.SUCCESS);
					r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
					r.setData(data);
					return r;
				} else {
					r.setCode(ResultCode.ERRORCODE.AUTHCODE_ERROR);
					r.setMsgInfo(ResultCode.ERRORINFO.AUTHCODE_ERROR);
					return r;
				}
			} else {
				r = addStudentAndLogin(phoneNum, code, null);
				if (r.isSuccess()){
					s = studentManager.getStudentByPhoneNum(phoneNum);
					if (name != null && !"".equals(name))
						s.setName(name);
					if (headIcon != null && !"".equals(headIcon))
						s.setHeadIcon(headIcon);
					if ("1".equals(accType.trim())){
						s.setUnionId(unionId);
						s.setOpenId(openId);
					}
					else
						s.setQqOpenId(unionId);
					studentManager.updateStudent(s);
					@SuppressWarnings("unchecked")
					Map<String, Object> data = (Map<String, Object>) r.getResult().get("data");
					data.put("studentInfo", BeanCopy.copyNotNull(s, StudentInfoVo.class));
					r.setData(data);
				}
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			return r;
		}
	}

}
