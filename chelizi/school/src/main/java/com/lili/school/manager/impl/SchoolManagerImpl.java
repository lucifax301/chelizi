package com.lili.school.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.RedissonClient;
import org.redisson.core.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.authcode.service.AuthcodeService;
import com.lili.coach.dto.Trfield;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.Distance;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.vo.PayVo;
import com.lili.school.dto.EnrollExamPlace;
import com.lili.school.dto.EnrollExamResult;
import com.lili.school.dto.EnrollExamResultExample;
import com.lili.school.dto.EnrollMaterial;
import com.lili.school.dto.EnrollMaterialAddress;
import com.lili.school.dto.EnrollMaterialItems;
import com.lili.school.dto.EnrollOrder;
import com.lili.school.dto.EnrollOrderExample;
import com.lili.school.dto.EnrollPackageTemplateExample;
import com.lili.school.dto.EnrollPackageTemplateWithBLOBs;
import com.lili.school.dto.EnrollProgressTemplate;
import com.lili.school.dto.EnrollProgressUser;
import com.lili.school.dto.School;
import com.lili.school.dto.SchoolWithBLOBs;
import com.lili.school.dto.SearchSchoolList;
import com.lili.school.dto.WechatComment;
import com.lili.school.dto.WechatCommentPraise;
import com.lili.school.dto.WechatCommentPraiseExample;
import com.lili.school.dto.WechatEnrollOrder;
import com.lili.school.dto.WechatEnrollOrderExample;
import com.lili.school.dto.WechatEnrollPackage;
import com.lili.school.dto.WechatEnrollPackageExample;
import com.lili.school.dto.WechatEnrollPackageWithBLOBs;
import com.lili.school.manager.SchoolManager;
import com.lili.school.mapper.EnrollExamPlaceMapper;
import com.lili.school.mapper.EnrollExamResultMapper;
import com.lili.school.mapper.EnrollMaterialAddressMapper;
import com.lili.school.mapper.EnrollMaterialItemsMapper;
import com.lili.school.mapper.EnrollMaterialMapper;
import com.lili.school.mapper.EnrollOrderMapper;
import com.lili.school.mapper.EnrollPackageTemplateMapper;
import com.lili.school.mapper.EnrollProgressTemplateMapper;
import com.lili.school.mapper.EnrollProgressUserMapper;
import com.lili.school.mapper.WechatCommentMapper;
import com.lili.school.mapper.WechatCommentPraiseMapper;
import com.lili.school.mapper.WechatEnrollOrderMapper;
import com.lili.school.mapper.WechatEnrollPackageMapper;
import com.lili.school.mapper.WechatMapper;
import com.lili.school.vo.EnrollExamPlaceVo;
import com.lili.school.vo.EnrollMaterialAddressVo;
import com.lili.school.vo.EnrollMaterialItemsVo;
import com.lili.school.vo.EnrollMaterialVo;
import com.lili.school.vo.EnrollOrderVo;
import com.lili.school.vo.EnrollPackageTemplateVo;
import com.lili.school.vo.EnrollProgressUserBriefVo;
import com.lili.school.vo.EnrollProgressUserVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;
import com.lili.student.vo.StudentMessage;

public class SchoolManagerImpl implements SchoolManager {
	
	private static Logger logger = LoggerFactory.getLogger(SchoolManagerImpl.class);
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private StudentManager studentManager;
	
    @Autowired
    private MoneyManager moneyManager;
    
	@Autowired
	private AuthcodeService authcodeService;
	
	@Autowired
	private EnrollPackageTemplateMapper enrollPackageTemplateMapper;
	
	@Autowired
	private EnrollOrderMapper enrollOrderMapper;
	
	@Autowired
	private EnrollMaterialMapper enrollMaterialMapper;
	
	@Autowired
	private EnrollMaterialAddressMapper enrollMaterialAddressMapper;
	
	@Autowired
	private EnrollMaterialItemsMapper enrollMaterialItemsMapper;
	
	@Autowired
	private EnrollProgressTemplateMapper enrollProgressTemplateMapper;
	
	@Autowired
	private EnrollProgressUserMapper enrollProgressUserMapper;
	
	@Autowired
	private EnrollExamPlaceMapper enrollExamPlaceMapper;
	
	@Autowired
	private WechatEnrollPackageMapper wechatEnrollPackageMapper;
	
	@Autowired
	private WechatEnrollOrderMapper wechatEnrollOrderMapper;
	
	@Autowired
	private WechatMapper wechatMapper;
	
	@Autowired
	private WechatCommentMapper commentMapper;
	
	@Autowired
	private WechatCommentPraiseMapper praiseMapper;
	
	@Autowired
	private EnrollExamResultMapper enrollExamResultMapper;
	
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;

	@Value("${msg.mobles}")
	private String msgMobiles = "";
    @Resource(name="schoolProducer")
    DefaultMQProducer schoolProducer;
    
    @Resource(name="schoolProducer")
    DefaultMQProducer schoolFlowProducer;
    
	//分布式锁
	@Autowired
	private RedissonClient redissonClient;
	
	ExecutorService threadPool = Executors.newCachedThreadPool();

	/**
	 * 获取报名套餐包列表
	 */
	@Override
	public List<EnrollPackageTemplateVo> getEnrollPkgList(String userId, String userType) {

		List<EnrollPackageTemplateVo> data = redisUtil.get(REDISKEY.ENROLL_TEMPLATE);
		if(null == data){
			EnrollPackageTemplateExample example = new EnrollPackageTemplateExample();
			EnrollPackageTemplateExample.Criteria criteria = example.createCriteria();
			criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);//只查询未删除的
			List<EnrollPackageTemplateWithBLOBs> pkgList = enrollPackageTemplateMapper.selectByExampleWithBLOBs(example);
			data = new ArrayList<EnrollPackageTemplateVo>();
			if(null != pkgList && pkgList.size() > 0){
				try {
					data = BeanCopy.copyList(pkgList, EnrollPackageTemplateVo.class, BeanCopy.COPYNOTNULL);
					redisUtil.set(REDISKEY.ENROLL_TEMPLATE, data);
				} catch (Exception e) {
					logger.error("SchoolManagerImpl--getEnrollPkgList--"+ e);
				}
			}

		}
		return data;
	}

	/**
	 * 获取单个报名套餐
	 */
	@Override
	public EnrollPackageTemplateVo getEnrollPkgOne(String userId, String userType,
			String eid) {
		EnrollPackageTemplateVo etv = redisUtil.get(REDISKEY.ENROLL_TEMPLATE + eid);
		if(null == etv){
			EnrollPackageTemplateWithBLOBs pkg = enrollPackageTemplateMapper.selectByPrimaryKey(Integer.parseInt(eid.trim()));
			etv = new EnrollPackageTemplateVo();
			try {
				etv = BeanCopy.copyNotNull(pkg, etv);
				redisUtil.set(REDISKEY.ENROLL_TEMPLATE + eid,etv);
			} catch (Exception e) {
				logger.error("SchoolManagerImpl--getEnrollPkgOne--"+ e);
			}
		}
		return etv;
	}
	

	/**
	 * 立即报名
	 */
	@Override
	public ReqResult postEnrollApply(String userId, String userType, String eid) {
		ReqResult r = ReqResult.getSuccess();
		long t1 = System.currentTimeMillis();
		//为防止客户端短时间重复提交，添加分布式锁
		RLock lock=null;
		try {
			lock=redissonClient.getLock(RedisKeys.REDISKEY.LOCK_PRE+"enroll."+userId);
			//1s等待，10s超时，防止死锁
			boolean hasLock=lock.tryLock(1,10,TimeUnit.SECONDS);
			if(hasLock){
				//0.推动我的进度，只需要设置当前进度情况
				int result = autoPushEnrollProgress(Integer.parseInt(eid.trim()),Long.parseLong(userId.trim()),ReqConstants.PROG_STAGE_BEGIN,ReqConstants.STAGE_STATE_SUCC,null,true);
				if(result == 0){
					//1.生成一个报名订单
					EnrollOrder eo = new EnrollOrder();
					String orderId = StringUtil.getOrderId();
					eo.setOrderId(orderId);
					eo.setStudentId(Long.parseLong(userId.trim()));
					eo.setTtid(Integer.parseInt(eid.trim()));
					eo.setCtime(new Date());
					//通过ttid到模板中找价格
					//EnrollPackageTemplateWithBLOBs tem = enrollPackageTemplateMapper.selectByPrimaryKey(Integer.parseInt(eid.trim()));
					EnrollPackageTemplateVo tem = getEnrollPkgOne(null,null,eid);
					eo.setPrice(tem.getPrice());
					eo.setCityId(tem.getCityId());
					eo.setCityName(tem.getName());
					eo.setPriceDetail(tem.getPriceDetail());
					enrollOrderMapper.insertSelective(eo);
					//3.更新用户信息添加订单
					Student student = new Student();
					student.setStudentId(Long.parseLong(userId));
					student.setApplyorder(orderId);
					student.setApplyttid(Integer.parseInt(eid));
					student.setCityId(tem.getCityId());
					//20160601因有报名失败重新报名的，之前分配的驾校等信息需要清除。
					student.setSchoolId((byte)0);
					student.setImportSrc(null);
					studentManager.updateStudent(student);
					r.setData(eo.getOrderId());
				}else {
		            r.setCode(ResultCode.ERRORCODE.ENROLL_STAGE_ERROR_APPLY);
		            r.setMsgInfo(ResultCode.ERRORINFO.ENROLL_STAGE_ERROR_APPLY);
		            return r;
				}
			}else{
				logger.error("PostEnrollApply error! studentId="+userId+" add apply order LOCK ERROR.");
				r.setCode(ResultCode.ERRORCODE.ORDER_LOCK);
				r.setMsgInfo(ResultCode.ERRORINFO.ORDER_LOCK);
				return r;
			}


		} catch (Exception e) {
			logger.error("PostEnrollApply error! studentId="+userId+" " + e);
		} finally {
			if(lock!=null) {
				try {
					lock.unlock();
				} catch(Exception e){}
			}
		}
		long t2 = System.currentTimeMillis();
		logger.debug("postEnrollApply time takes "+(t2-t1));
		return r;

	}
	
	/**
	 * 获取单个驾校报名套餐
	 */
	@Override
	public WechatEnrollPackageWithBLOBs getEnrollSchoolPkgOne(String userId, String userType,
			String eid) {
		WechatEnrollPackageWithBLOBs etv = redisUtil.get(REDISKEY.ENROLL_SCHOOL_TEMPLATE + eid);
		if(null == etv){
			etv = wechatEnrollPackageMapper.selectByPrimaryKey(Integer.parseInt(eid.trim()));
			redisUtil.set(REDISKEY.ENROLL_SCHOOL_TEMPLATE + eid,etv,1800);
		}
		return etv;
	}
	
	/**
	 * 立即报名驾校
	 */
	@Override
	public ReqResult postEnrollApplySchool(String userId, String userType, String eid) {
		ReqResult r = ReqResult.getSuccess();
		long t1 = System.currentTimeMillis();
		//为防止客户端短时间重复提交，添加分布式锁
		RLock lock=null;
		try {
			lock=redissonClient.getLock(RedisKeys.REDISKEY.LOCK_PRE+"enroll."+userId);
			//1s等待，10s超时，防止死锁
			boolean hasLock=lock.tryLock(1,10,TimeUnit.SECONDS);
			if(hasLock){
				//0.推动我的进度，只需要设置当前进度情况
				int result = autoPushEnrollProgress(Integer.parseInt(eid.trim()),Long.parseLong(userId.trim()),ReqConstants.PROG_STAGE_BEGIN,ReqConstants.STAGE_STATE_SUCC,null,true);
				if(result == 0){
					//1.生成一个报名订单
					EnrollOrder eo = new EnrollOrder();
					String orderId = StringUtil.getOrderId();
					eo.setOrderId(orderId);
					eo.setStudentId(Long.parseLong(userId.trim()));
					eo.setTtid(Integer.parseInt(eid.trim()));
					eo.setCtime(new Date());
					//通过ttid到模板中找价格
					WechatEnrollPackageWithBLOBs tem = getEnrollSchoolPkgOne(null,null,eid);
					eo.setPrice(tem.getPrice());
					eo.setCityId(tem.getCityId());
					eo.setCityName(tem.getName());
					eo.setPriceDetail(tem.getPriceDetail());
					eo.setChannel(2);
					eo.setSchoolId(tem.getSchoolId());
					enrollOrderMapper.insertSelective(eo);
					//3.更新用户信息添加订单
					Student student = new Student();
					student.setStudentId(Long.parseLong(userId));
					student.setApplyorder(orderId);
					student.setApplyttid(Integer.parseInt(eid));
					student.setCityId(tem.getCityId());
					//直接分配的驾校
					student.setSchoolId(tem.getSchoolId().byteValue());
					student.setImportSrc(null);
					student.setApplyChannel(2);
					studentManager.updateStudent(student);
					r.setData(eo.getOrderId());
				}else {
					r.setCode(ResultCode.ERRORCODE.ENROLL_STAGE_ERROR_APPLY);
					r.setMsgInfo(ResultCode.ERRORINFO.ENROLL_STAGE_ERROR_APPLY);
					return r;
				}
			}else{
				logger.error("PostEnrollApply error! studentId="+userId+" add apply order LOCK ERROR.");
				r.setCode(ResultCode.ERRORCODE.ORDER_LOCK);
				r.setMsgInfo(ResultCode.ERRORINFO.ORDER_LOCK);
				return r;
			}
			
			
		} catch (Exception e) {
			logger.error("PostEnrollApply error! studentId="+userId+" " + e);
		} finally {
			if(lock!=null) {
				try {
					lock.unlock();
				} catch(Exception e){}
			}
		}
		long t2 = System.currentTimeMillis();
		logger.debug("postEnrollApply time takes "+(t2-t1));
		return r;
	}


	/**
	 * 获取报名订单
	 */
	@Override
	public ReqResult getEnrollOrder(String userId, String userType,
			String orderId) {
		ReqResult r = ReqResult.getSuccess();
		if(null != orderId && !"".equals(orderId)){
			EnrollOrder eo = enrollOrderMapper.selectByPrimaryKey(orderId);
			EnrollOrderVo eov = new EnrollOrderVo();
			try {
				BeanCopy.copyNotNull(eo, eov);
			} catch (Exception e) {
				logger.error("SchoolManagerImpl--getEnrollOrder--"+ e);
			}
			r.setData(eov);
		}else {
			r = ReqResult.getFailed();
		}
		return r;
	}

	/**
	 * 获取报名订单
	 */
	@Override
	public ReqResult getEnrollOrderByStudentId(String studentId) {
		ReqResult r = ReqResult.getFailed();
		if (StringUtil.isNotNullAndNotEmpty(studentId)) {
			EnrollOrderExample example = new EnrollOrderExample();
			EnrollOrderExample.Criteria criteria = example.createCriteria();
			criteria.andStudentIdEqualTo(Long.parseLong(studentId));
			criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
			List<EnrollOrder> eos = enrollOrderMapper.selectByExample(example);
			if (null != eos && eos.size() > 0) {
				EnrollOrderVo eov = new EnrollOrderVo();
				try {
					BeanCopy.copyNotNull(eos.get(0), eov);
				} catch (Exception e) {
					logger.error("SchoolManagerImpl--getEnrollOrderByStudentId", e);
				}
				r = ReqResult.getSuccess();
				r.setData(eov);
			}
		}
		return r;
	}
	
	public EnrollOrder getEnrollOrderByStudentId(long studentId) {
		EnrollOrderExample example = new EnrollOrderExample();
		EnrollOrderExample.Criteria criteria = example.createCriteria();
		criteria.andStudentIdEqualTo(studentId);
		criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
		List<EnrollOrder> eos = enrollOrderMapper.selectByExample(example);
		if (null != eos && eos.size() > 0) {
			return eos.get(0);
		}
		return null;
	}


	/**
	 * 取消报名订单，需要同时删除已生成的进度流程
	 */
	@Override
	public ReqResult postEnrollOrderCancel(String userId, String userType,
			String orderId) {
		ReqResult r = ReqResult.getSuccess();
		if(null != orderId && !"".equals(orderId)){
			EnrollOrder eo = enrollOrderMapper.selectByPrimaryKey(orderId);
			if(null != eo ){
				//已支付的订单不允许取消
				if(eo.getPayState()== ReqConstants.STAGE_STATE_SUCC){
					r = ReqResult.getFailed();
					return r;
				}
				//删除已生成的进度流程
				enrollProgressUserMapper.updateIsdelByStudentIdTtid(ReqConstants.OPERATE_DELETE, eo.getStudentId(),eo.getTtid() );
				//删除订单
				eo.setIsdel(ReqConstants.OPERATE_DELETE);
				eo.setOrderState(ReqConstants.ENROLL_ORDER_CANCEL);
				enrollOrderMapper.updateByPrimaryKeySelective(eo);
				//更新用户状态
				Student student = new Student();
				student.setStudentId(Long.parseLong(userId.trim()));
				student.setApplyexam(ReqConstants.PROG_STAGE_BEGIN_DELAY);
				student.setApplystate(ReqConstants.STAGE_STATE_NONE +0);
				student.setApplyorder(null);
				studentManager.updateStudent(student);
			}

		}else {
			r = ReqResult.getFailed();
		}

		return r;
	}

	/**
	 * 查询订单状态
	 */
	@Override
	public Byte getPayState(String orderId) {
		if(null != orderId && !"".equals(orderId)){
			EnrollOrder eo = enrollOrderMapper.selectByPrimaryKey(orderId);
			return eo.getPayState();
		}else {
			return -1;
		}
	}
	
	/**
	 * 查询微信端订单状态
	 */
	@Override
	public Byte getWxPayState(String orderId) {
		if(null != orderId && !"".equals(orderId)){
			WechatEnrollOrder eo = wechatEnrollOrderMapper.selectByPrimaryKey(orderId);
			return eo.getPayState();
		}else {
			return -1;
		}
	}


	/**
	 * 更新支付状态
	 */
	@Override
	public int postPayState(PayVo pay, Byte state) {
		String orderId = pay.getPayOrderId();
		if(null != orderId && !"".equals(orderId)){
			EnrollOrder eo = enrollOrderMapper.selectByPrimaryKey(orderId);
			Map<Integer,String> data = new HashMap<Integer, String>();
			//金额
			data.put(1, String.valueOf(eo.getPrice()/100));
			//报考类型
			/*String dltype = "C1";
			if(eo.getDltype() == 2){
				dltype = "C2";
			}
			data.put(2, dltype);*/
			//更新用户进度
			int result = autoPushEnrollProgress(eo.getTtid(),eo.getStudentId(),ReqConstants.PROG_STAGE_PAY,state,data,true);
			if(result == 0){
				eo.setPayState(state);
				eo.setPayTime(new Date());
				eo.setCoupon(pay.getCouponId()); //优惠券id
				eo.setPayTotal(pay.getPayValue());//实际支付金额
				eo.setPayway(pay.getPayWay());	//支付途径
				eo.setCouponName(pay.getRemark()); //优惠券支付说明
				eo.setCouponTotal(eo.getPrice() - pay.getPayValue()); //优惠券抵扣金额
				eo.setIsdel(ReqConstants.OPERATE_DELETE_NO);//20160905 订单有可能支付失败，然后被用户取消掉
				if(state == ReqConstants.STAGE_STATE_SUCC){
					eo.setOrderState(ReqConstants.ENROLL_ORDER_CHECKOUT_WAIT);//20160723付款成功后，订单状态更改为未结款状态
				}
				enrollOrderMapper.updateByPrimaryKeyWithBLOBs(eo);
			}else{
				logger.error("SchoolManagerImpl--postPayState autoPushEnrollProgress--> payVo="+ pay);
			}
			//如果是报名支付成功状态，则短信通知用户
			if(state == ReqConstants.STAGE_STATE_SUCC){
				Student stu = studentManager.getStudentInfo(eo.getStudentId());
				Map<String,String> shortMsgs= new HashMap<String, String>();
				shortMsgs.put("mobile", stu.getPhoneNum());
		    	Date d = eo.getPayTime();
		    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		    	String t = f.format(d);
		    	shortMsgs.put("time", t);
		    	String name = "未填写姓名";
		    	if(null != eo.getName() && !"".equals(eo.getName().trim())){
		    		name = eo.getName();
		    	}else if(null != stu.getName() && !"".equals(stu.getName().trim())) {
		    		name = stu.getName();
		    	}
		    	shortMsgs.put("name", name);
		    	EnrollPackageTemplateWithBLOBs pkg = enrollPackageTemplateMapper.selectByPrimaryKey(eo.getTtid());
		    	shortMsgs.put("city", pkg.getName());
		    	String money = String.valueOf(eo.getPrice()/100);//单位元
		    	shortMsgs.put("money", money);
		    	try {
		    		//短信通知学员已付费成功
		    		//20160616 根据是否使用了优惠券支付报名费，发送的短信内容不同
		    		if(eo.getCouponTotal() != null && eo.getCouponTotal().intValue() > 0){
		    			authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_PAYMENT, stu.getPhoneNum(), shortMsgs);
		    		}else {
		    			authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_PAYMENT_GIFT, stu.getPhoneNum(), shortMsgs);
		    		}
					//短信通知公司客服
					authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_FEEDBACK, msgMobiles, shortMsgs);
//					emailService.send("[用户支付报名费]", shortMsgs.toString());
				} catch (Exception e) {
					logger.error("SchoolManagerImpl--postPayState sendMsg--"+ e);
				}
	            //推送支付成功的消息到mq
				try {
					Message msg=new Message();
					msg.setTopic(schoolProducer.getCreateTopicKey());
					msg.setTags(OrderConstant.RMQTAG.STUDENT_ENROLL_PAID);
					StudentMessage smsg = new StudentMessage();
					smsg.setStudentId(stu.getStudentId());
					smsg.setPhoneNum(stu.getPhoneNum());
					smsg.setMsgType(StudentMessage.MSGTYPE_STUDENT_ENROLL_PAID);
					msg.setBody(SerializableUtil.serialize(smsg));
					schoolProducer.send(msg);
					logger.debug("schoolProducer has sent a message. MSGTYPE_STUDENT_ENROLL_PAID. StudentId: "+stu.getStudentId());
				} catch (Exception e) {
					logger.error("schoolProducer error "+e);
					e.printStackTrace();
				}

			}

			return 0;
		}else {
			return 1;
		}
	}
	
	/**
	 * 微信端 更新支付状态
	 */
	@Override
	public int postWxPayState(PayVo pay, Byte state) {
		String orderId = pay.getPayOrderId();
		if(null != orderId && !"".equals(orderId)){
			WechatEnrollOrder eo = wechatEnrollOrderMapper.selectByPrimaryKey(orderId);
			//微信端报名 修改学员状态为导入  新增
			Student student = new Student();
			student.setStudentId(eo.getStudentId());
			student.setIsImport((byte) 1);
			student.setSchoolId((byte)eo.getSchoolId().intValue());
			student.setApplyttid(eo.getTtid());
			studentManager.updateStudent(student);
			
			Map<Integer,String> data = new HashMap<Integer, String>();
			//金额
			data.put(1, String.valueOf(eo.getPrice()/100));
            //更新订单
			eo.setPayState(state);
			eo.setPayTime(new Date());
			eo.setCoupon(pay.getCouponId()); //优惠券id
			eo.setPayTotal(pay.getPayValue());//实际支付金额
			eo.setPayWay(pay.getPayWay());	//支付途径
			eo.setCouponName(pay.getRemark()); //优惠券支付说明
			eo.setCouponTotal(eo.getPrice() - pay.getPayValue()); //优惠券抵扣金额
			if(state == ReqConstants.STAGE_STATE_SUCC){
				eo.setOrderState(ReqConstants.ENROLL_ORDER_CHECKOUT_WAIT);//20160723付款成功后，订单状态更改为未结款状态
			}
			wechatEnrollOrderMapper.updateByPrimaryKey(eo);

			//如果是报名支付成功状态，则短信通知用户
			if(state == ReqConstants.STAGE_STATE_SUCC){
				Student stu = studentManager.getStudentInfo(eo.getStudentId());
				Map<String,String> shortMsgs= new HashMap<String, String>();
				shortMsgs.put("mobile", stu.getPhoneNum());
		    	Date d = eo.getPayTime();
		    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		    	String t = f.format(d);
		    	shortMsgs.put("time", t);
		    	String name = "未填写姓名";
		    	if(null != eo.getName() && !"".equals(eo.getName().trim())){
		    		name = eo.getName();
		    	}else if(null != stu.getName() && !"".equals(stu.getName().trim())) {
		    		name = stu.getName();
		    	}
		    	shortMsgs.put("name", name);
		    	WechatEnrollPackage pkg = wechatEnrollPackageMapper.selectByPrimaryKey(eo.getTtid());
		    	shortMsgs.put("city", pkg.getName());
		    	String money = String.valueOf(eo.getPrice()/100);//单位元
		    	shortMsgs.put("money", money);
		    	try {
		    		//短信通知学员已付费成功
		    		//20160616 根据是否使用了优惠券支付报名费，发送的短信内容不同
		    		if(eo.getCouponTotal() != null && eo.getCouponTotal().intValue() > 0){
		    			authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_PAYMENT, stu.getPhoneNum(), shortMsgs);
		    		}else {
		    			authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_PAYMENT_GIFT, stu.getPhoneNum(), shortMsgs);
		    		}
					//短信通知公司客服
					authcodeService.sendMsg(ReqConstants.SHORT_MESSAGE_TYPE_ENROLL_FEEDBACK, msgMobiles, shortMsgs);
//					emailService.send("[用户支付报名费]", shortMsgs.toString());
				} catch (Exception e) {
					logger.error("SchoolManagerImpl--postPayState sendMsg--"+ e);
				}
	            //推送支付成功的消息到mq
				try {
					Message msg=new Message();
					msg.setTopic(schoolProducer.getCreateTopicKey());
					msg.setTags(OrderConstant.RMQTAG.STUDENT_ENROLL_PAID);
					StudentMessage smsg = new StudentMessage();
					smsg.setStudentId(stu.getStudentId());
					smsg.setPhoneNum(stu.getPhoneNum());
					smsg.setMsgType(StudentMessage.MSGTYPE_STUDENT_ENROLL_PAID);
					msg.setBody(SerializableUtil.serialize(smsg));
					schoolProducer.send(msg);
					logger.debug("schoolProducer has sent a message. MSGTYPE_STUDENT_ENROLL_PAID. StudentId: "+stu.getStudentId());
				} catch (Exception e) {
					logger.error("schoolProducer error "+e);
					e.printStackTrace();
				}

			}

			return 0;
		}else {
			return 1;
		}
	}

	/**
	 * 添加实名信息，更新订单
	 */
	@Override
	public ReqResult postEnrollIdcard(String userId, String userType,
			String picFront, String picBack, String idNum,
			String name, String drType, String orderId) {
		ReqResult r = ReqResult.getSuccess();
		if(null != orderId && !"".equals(orderId)){
			//为防止客户端短时间重复提交，添加分布式锁
			RLock lock=null;
			try {
				lock=redissonClient.getLock(RedisKeys.REDISKEY.LOCK_PRE+"enroll."+orderId);
				//1s等待，10s超时，防止死锁
				boolean hasLock=lock.tryLock(1,10,TimeUnit.SECONDS);
				if(hasLock){
					//20160413已报名的用户不允许重复报名
					EnrollOrderExample example = new EnrollOrderExample();
					EnrollOrderExample.Criteria criteria = example.createCriteria();
					criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
					criteria.andCardIdEqualTo(idNum.trim());
					List<EnrollOrder> eos = enrollOrderMapper.selectByExample(example);
					if(null != eos && eos.size()>0){
			            r.setCode(ResultCode.ERRORCODE.ENROLL_STAGE_ERROR_APPLY);
			            r.setMsgInfo(ResultCode.ERRORINFO.ENROLL_STAGE_ERROR_APPLY);
			            return r;
					}
					//20160725不能与已有学员重复身份证号
					Student s1 = studentManager.getStudentByIdNumber(idNum);
					if(null != s1){
						if(! s1.getStudentId().toString().equals(userId)){
				            r.setCode(ResultCode.ERRORCODE.IDCARD_NUMBER_EXIST);
				            r.setMsgInfo(ResultCode.ERRORINFO.IDCARD_NUMBER_EXIST);
				            return r;
						}
					}

					EnrollOrder eo = enrollOrderMapper.selectByPrimaryKey(orderId);
					if(null != eo ){
						//更新用户进度
						int result = autoPushEnrollProgress(eo.getTtid(),eo.getStudentId(),ReqConstants.PROG_STAGE_USERINFO,ReqConstants.STAGE_STATE_SUCC,null,true);
						if(result == 0){
							eo.setCardPic1(picFront);
							eo.setCardPic2(picBack);
							eo.setCardId(idNum.trim());
							eo.setName(name);
							eo.setDltype(Byte.parseByte(drType.trim()));
							enrollOrderMapper.updateByPrimaryKeyWithBLOBs(eo);
							//2.更新用户信息冗余添加出车类型及订单
							Student student = new Student();
							student.setStudentId(Long.parseLong(userId));
							student.setApplyorder(eo.getOrderId());
							student.setDrType(Byte.parseByte(drType));
							student.setApplyCarType(drType);//多余的类型
							if(StringUtil.isNotNullAndNotEmpty(name)){
								student.setName(name);
							}
							if(StringUtil.isNotNullAndNotEmpty(picFront)){
								student.setIdPhotoFront(picFront);
							}
							if(StringUtil.isNotNullAndNotEmpty(picBack)){
								student.setIdPhotoBack(picBack);
							}
							student.setIdNumber(idNum);
							studentManager.updateStudent(student);
						}
					}
				}else{
					logger.error("postEnrollIdcard error! studentId="+userId+" postEnrollIdcard LOCK ERROR.");
					return ReqResult.getFailed();
				}
			}catch(Exception e){
				logger.error("postEnrollIdcard error! studentId="+userId+"."+e);
			}finally{
				if(lock!=null) {
					try {
						lock.unlock();
					} catch(Exception e){}
				}
			}
		}

		return r;
	}

	/**
	 * 获取邮寄相关资料详情
	 */
	@Override
	public ReqResult getMailDetail(String userId, String userType,String ttid,String type) {
		ReqResult r = ReqResult.getSuccess();
		Map<String,Object> data = redisUtil.get(REDISKEY.ENROLL_MAIL_ONE + ttid);
		if(null == data){
			data = new HashMap<String, Object>();
			//获取邮寄地址
			EnrollMaterialAddress address = enrollMaterialAddressMapper.selectByTtid(Integer.parseInt(ttid.trim()));
			EnrollMaterialAddressVo addressVo = new EnrollMaterialAddressVo();
			try {
				BeanCopy.copyNotNull(address, addressVo);
			} catch (Exception e) {
				logger.error("SchoolManagerImpl--EnrollMaterialAddressVo--"+ e);
			}
			//r.setData("address", addressVo);
			data.put("address", addressVo);
			//获取邮寄资料人员信息
			List<EnrollMaterial> ems = enrollMaterialMapper.selectGroupByTtidPtype(Integer.parseInt(ttid.trim()));
			List<EnrollMaterialVo> emsvo = new ArrayList<EnrollMaterialVo>();
			try {
				emsvo = BeanCopy.copyList(ems, EnrollMaterialVo.class, BeanCopy.COPYNOTNULL);
			} catch (Exception e) {
				logger.error("SchoolManagerImpl--EnrollMaterialVo--"+ e);
			}
			//r.setData("people", emsvo);
			data.put("people", emsvo);
			//获取邮寄资料条目信息
			Set<Integer> pms = new HashSet<Integer>();
			for(int i=0;i<ems.size();i++){
				EnrollMaterial em = ems.get(i);
				String pmids = em.getPmid();
				if(pmids.contains(",")){
					String[] aa = pmids.split(",");
					for(int j=0;j<aa.length;j++){
						Integer b = Integer.parseInt(aa[j].trim());
						pms.add(b);
					}
				}else{
					Integer c = Integer.parseInt(pmids.trim());
					pms.add(c);
				}
			}
			if(pms.size()>0){
				List<Integer> pmids = new ArrayList<Integer>(pms);
				List<EnrollMaterialItems> items = enrollMaterialItemsMapper.selectByPrimaryKeys(pmids);
				List<EnrollMaterialItemsVo> itemsvo = new ArrayList<EnrollMaterialItemsVo>();
				try {
					itemsvo = BeanCopy.copyList(items, EnrollMaterialItemsVo.class, BeanCopy.COPYNOTNULL);
				} catch (Exception e) {
					logger.error("SchoolManagerImpl--EnrollMaterialItemsVo--"+ e);
				}
				//r.setData("items", itemsvo);
				data.put("items", itemsvo);
			}

			redisUtil.set(REDISKEY.ENROLL_MAIL_ONE + ttid,data);
		}

		//20160517用户获取缺少的报名资料
		if(null != userId && null != type && type.trim().equals("1")){
			try {
				Student stu = studentManager.getStudentInfo(Integer.parseInt(userId.trim()));
				String extra = stu.getExtra();
				if(null != extra & !"".equals(extra.trim())){
					String[] aa = extra.split("#");
					String ptype = aa[0];
					String pmid = aa[1];
					String note = aa[2];
					List<EnrollMaterialVo> people = (List<EnrollMaterialVo>) data.get("people");
					byte ptypeByte = Byte.parseByte(ptype);
					for(EnrollMaterialVo p:people){
						if(ptypeByte == p.getPtype()){
							List<EnrollMaterialVo> pp = new ArrayList<EnrollMaterialVo>();
							pp.add(p);
							data.put("people", pp);
							break;
						}
					}
					String[] itemMiss = pmid.split(",");
					List<EnrollMaterialItemsVo> items = (List<EnrollMaterialItemsVo>) data.get("items");
					for(EnrollMaterialItemsVo item:items){
						int state = 0;
						for(int i=0;i<itemMiss.length;i++){
							int itemMissid = Integer.parseInt(itemMiss[i].trim());
							if(itemMissid == item.getPmid()){
								state = 1;
								break;
							}
						}
						item.setState(state); //1-标记为需要补充的资料
					}
					data.put("items", items);
					data.put("note", note);

				}
			} catch (Exception e) {
				logger.error("SchoolManagerImpl--getMailDetail--"+ e);
				e.printStackTrace();
			}
		}
		//20160517 cms后台获取邮寄资料信息，不需要地址
		if(null != type && type.trim().equals("2")){
			data.remove("address");
		}

		r.setData(data);
		return r;
	}

	/**
	 * 标记是否邮寄
	 */
	@Override
	public int postMailState(String userId, String userType, String ptype,
			String state,String orderId) {
		if(null != orderId && !"".equals(orderId)){
			byte st = Byte.parseByte(state);
			if(st == ReqConstants.STAGE_STATE_SUBMIT){
				EnrollOrder eo = enrollOrderMapper.selectByPrimaryKey(orderId);
				//更新用户进度
				int result = autoPushEnrollProgress(eo.getTtid(),eo.getStudentId(),ReqConstants.PROG_STAGE_MAIL,ReqConstants.STAGE_STATE_SUBMIT,null,true);
				if(result == 0){
					eo.setPostState(ReqConstants.STAGE_STATE_SUBMIT);
					enrollOrderMapper.updateByPrimaryKeyWithBLOBs(eo);
				}
			}

		}
		return 0;
	}


	/**
	 * 获取考试考场信息
	 */
	@Override
	public ReqResult getExamPlace(String userId, String userType) {
		ReqResult r = ReqResult.getSuccess();
		List<EnrollExamPlaceVo> placesVo = redisUtil.get(REDISKEY.ENROLL_EXAM_PLACE);
		if(null == placesVo){
			List<EnrollExamPlace> places = enrollExamPlaceMapper.selectByExample(null);
			placesVo = new ArrayList<EnrollExamPlaceVo>();
			try {
				placesVo = BeanCopy.copyList(places, EnrollExamPlaceVo.class, BeanCopy.COPYNOTNULL);
				redisUtil.set(REDISKEY.ENROLL_EXAM_PLACE,placesVo);
			} catch (Exception e) {
				logger.error("SchoolManagerImpl--getExamPlace--"+ e);
			}
		}

		r.setData(placesVo);
		return r;
	}

	/**
	 * 约考报名	20160617学员已不能通过系统报名
	 */
/*	@Override
	public int postExamReg(String userId, String userType, String subjectId,
			String cityId, String waterNo, String name, String idcard,
			String drtype, String examPlace,String mobile,String orderId) {
		EnrollExamReg record = new EnrollExamReg();
		record.setWaterNum(Long.parseLong(waterNo.trim()));
		record.setCityId(Integer.parseInt(cityId.trim()));
		record.setCityName("");
		record.setSubjectId(Byte.parseByte(subjectId));
		record.setName(name);
		record.setIdCard(idcard);
		record.setUserId(Long.parseLong(userId.trim()));
		record.setDrtype(Byte.parseByte(drtype.trim()));
		record.setExamPlace(examPlace);
		//record.setUserMobile(mobile.trim());
		enrollExamRegMapper.insertSelective(record);

		//更新用户进度
		//TODO 后面再更新报考科目一二三四的事情
		//autoPushEnrollProgress(eo.getTtid(),eo.getStudentId(),ReqConstants.PROG_STAGE_MAIL,ReqConstants.STAGE_STATE_SUBMIT,null);

		return 0;
	}*/

	/**
	 * 获取我的进度状态，因为有导入的学员，需要直接设置状态
	 */
	@Override
	public ReqResult getUserProgress(String userId, String userType,
			String stepId) {
		ReqResult r = ReqResult.getSuccess();
		Map<String,Object> data = new HashMap<String, Object>();
		Long uid = Long.parseLong(userId.trim());
		//按照进度表中步骤id倒序查询	20160601因为有重复报名，需要主键来倒序查询
		List<EnrollProgressUser> epus= enrollProgressUserMapper.selectByStudentId(uid);
		List<EnrollProgressUserVo> epusvo = new ArrayList<EnrollProgressUserVo>();

		//默认报名包id
		int stuTtid =1;
		//默认阶段为报名
		int stuStage = ReqConstants.PROG_STAGE_BEGIN;
		//默认阶段状态为未开始
		byte stuStageState = ReqConstants.STAGE_STATE_NONE;
		//获取学员表中的状态
		Student stu = studentManager.getStudentInfo(uid);
		if(null != stu.getApplyttid()){
			stuTtid = stu.getApplyttid();
		}
		if(null != stu.getApplyexam() && stu.getApplyexam() >0 ){ //报名阶段0和1都是需要报名
			stuStage = stu.getApplyexam();
		}
		if(null != stu.getApplystate()){
			stuStageState = stu.getApplystate().byteValue();
		}
		//1.获取正常显示的进度
		//数据为空，说明是第一次查询我的进度，需要构造初始数据
		if(null == epus ||epus.size() == 0){
			int result = autoPushEnrollProgress(stuTtid, uid, stuStage, stuStageState, null,false);
			if(result == 0){
				epus= enrollProgressUserMapper.selectByStudentId(uid);
			}

		}else {
			EnrollProgressUser epu = epus.get(0); //倒序中第一个为最新的步骤
			//20160325 cms直接在学员表中更改了进度状态，需要去掉自动状态机的前置检查。如果学员表的状态和进度表的状态不一致，说明学员表被cms修改，以学员表为准。
			if(epu.getStepId().intValue() != stu.getApplyexam().intValue()
					|| epu.getProcessState() != stu.getApplystate().byteValue()
					|| epu.getTtid() != stu.getApplyttid()){
				int result = autoPushEnrollProgress(stuTtid, uid, stuStage, stuStageState, null,false);
				if(result == 0){
					epus= enrollProgressUserMapper.selectByStudentId(uid);
				}
			}
			//20160315 如果进度状态为已完成(手动设置或后天cms设置)，且不是已拿到驾照，则推进下一个动作
			else if (epu.getProcessState() == ReqConstants.STAGE_STATE_SUCC && epu.getStepId() != ReqConstants.PROG_STAGE_END){
				stuTtid = epu.getTtid();
				stuStage = epu.getStepId();
				stuStageState = epu.getProcessState();
				int result = autoPushEnrollProgress(stuTtid, uid, stuStage, stuStageState, null,true);
				if(result == 0){
					epus= enrollProgressUserMapper.selectByStudentId(uid);
				}
			}
		}

		try {
			stuTtid = epus.get(0).getTtid();
			epusvo = BeanCopy.copyList(epus, EnrollProgressUserVo.class, BeanCopy.COPYNOTNULL);
			//r.setData(epusvo);
			data.put("progress", epusvo);
		} catch (Exception e) {
			logger.error("SchoolManagerImpl--getUserProgress--"+ e);
		}

		//2.获取下拉显示的未来的进度
		Integer sid = 1;
		if(null == stepId || "".equals(stepId.trim())){
			sid =stuStage;
		}else {
			sid = Integer.parseInt(stepId.trim());
		}
		try {
			List<EnrollProgressTemplate> predisplay = enrollProgressTemplateMapper.selectPredisplayByTTidStepid(stuTtid, sid);
			List<EnrollProgressUserVo> predisplayVo = new ArrayList<EnrollProgressUserVo>();

			predisplayVo = BeanCopy.copyList(predisplay, EnrollProgressUserVo.class, BeanCopy.COPYNOTNULL);
			//将模板中的title字段放到用户的result字段中
			for(int i=0 ;i<predisplay.size();i++){
				EnrollProgressTemplate epta = predisplay.get(i);
				EnrollProgressUserVo epua = predisplayVo.get(i);
				epua.setResult(epta.getTitle());
				predisplayVo.set(i, epua);
			}
			data.put("predisplay", predisplayVo);
		} catch (Exception e) {
			logger.error("SchoolManagerImpl--getUserProgress--"+ e);
		}

		r.setData(data);
		return r;
	}

	/**
	 * 教练获取学员的进度，只获取整体进度，不涉及细节，不驱动改变学员进度。
	 */
	@Override
	public ReqResult getStudentProgress(String userId, String userType,
			String studentId) {
		ReqResult r = ReqResult.getSuccess();
		Long uid = Long.parseLong(studentId.trim());
		//按照进度表中步骤id倒序查询
		List<EnrollProgressUser> epus= enrollProgressUserMapper.selectInfoByStudentId(uid);
		List<EnrollProgressUserBriefVo> epusvo = new ArrayList<EnrollProgressUserBriefVo>();
		if(null != epus && epus.size()>0){
			for(EnrollProgressUser epu:epus){
				String doc = epu.getResult();
				epu.setResult(doc.replace("您", "学员"));
			}
			try {
				epusvo = BeanCopy.copyList(epus, EnrollProgressUserBriefVo.class, BeanCopy.COPYNOTNULL);
				r.setData(epusvo);
			} catch (Exception e) {
				logger.error("SchoolManagerImpl--getStudentProgress--"+ e);
			}
		}

		return r;
	}

	/**
	 * 自动统一推动各个阶段进行
	 * @param ttid
	 * @param userId
	 * @param progStage
	 * @param execState
	 * @param params
	 * @param preCheck 是否需要前置检查；cms直接修改了学员表进度的不需要做前置检查。
	 * @return	0--成功；-1 --失败
	 */
	private int autoPushEnrollProgress(int ttid,long userId,int progStage,Byte execState,Map<Integer,String> params,Boolean preCheck){
		//--------------0. 增加前置状态检查-------------------------------
		//获取用户最近的阶段
		EnrollProgressUser userProgLatest = getProgressUserLatest(userId);
		//EnrollProgressUser userProgLatest = getProgressUserTtidLatest(userId,ttid);
		if(null != userProgLatest && preCheck){
			int userTtid = userProgLatest.getTtid();
			int userStageLatest = userProgLatest.getStepId();
			byte userStageState = userProgLatest.getProcessState();
			//20160518批量更改学员状态时，可能不能确定学员的报名套餐一致，这时候传的ttid约定为-1
			if(ttid == -1){
				ttid = userTtid;
			}
			if(userTtid != ttid){
				//如果最近是套餐开启状态，则修改开启状态时的默认值
				if(userStageLatest == ReqConstants.PROG_STAGE_BEGIN){
					userProgLatest.setTtid(ttid);
					enrollProgressUserMapper.updateByPrimaryKeySelective(userProgLatest);
				}else{
					//否则如果用户开启新的报名套餐，而原套餐没有完成，也不是开启状态。
					return -1;
				}
			}
			//如果用户最近的阶段还没有完成，来了新的阶段则不接受
			if(progStage > userStageLatest && userStageState != ReqConstants.STAGE_STATE_SUCC){
				return -1;
			}
			//当前阶段不是失败的情况下，推进器状态不允许回退
			if(progStage <userStageLatest && userStageState != ReqConstants.STAGE_STATE_FAIL){
				return -1;
			}
			//如果推动器状态与用户现有状态完全相同，且不是阶段已完成，则不接受
			if(userTtid == ttid && userStageLatest == progStage && userStageState == execState && userStageState != ReqConstants.STAGE_STATE_SUCC){
				return -1;
			}

		}
		//20160624可能由于数据问题，到这里的时候还不知道报名包，则从学员信息中取，没有则给出默认值
		if(ttid == -1){
			if(null != userProgLatest ){
				ttid = userProgLatest.getTtid();
			}else {
				Student s = studentManager.getStudentInfo(userId);
				if(null != s.getApplyttid() ){
					ttid = s.getApplyttid();
				}else {
					ttid = 1;
				}
			}
		}

		//--------------1.设置新的当前状态,并判断显示下一个阶段，并更新学员状态-----------------
		//获取进程模板中的特定阶段的特定状态的描述
		EnrollProgressTemplate ept = getProgressTemplateStep(ttid, progStage);
		if(ept == null){
			return -1;
		}
		//该状态的描述
		String doc = "";
		//该状态是否需要提示下一个阶段
		Integer nextStep = null;
		//该阶段是否还需要显示
		Byte needDisplay =null;
		if(execState == ReqConstants.STAGE_STATE_NONE){
			doc = ept.getNoneDoc();
			needDisplay = ept.getNoneRec();
		}
		else if(execState == ReqConstants.STAGE_STATE_SUBMIT){
			doc = ept.getSubmitDoc();
			needDisplay = ept.getSubmitRec();
		}
		else if(execState == ReqConstants.STAGE_STATE_SUCC){
			doc = ept.getSuccDoc();
			nextStep = ept.getNextStep();
			needDisplay = ept.getSuccRec();
		}
		else if(execState == ReqConstants.STAGE_STATE_FAIL){
			doc = ept.getFailDoc();
			nextStep = ept.getRetryStep();
			needDisplay = ept.getFailRec();
		}else{
			logger.error("SchoolManagerImpl--autoPushEnrollProgress--"+ "wrong STAGE_STATE!!");
			return -1;
		}
		//替换描述中的变量
		String docparsed = parseDocParams(doc,params);
		//获取用户的当前阶段，如没有则创建这个阶段
		EnrollProgressUser epuNow = getProgressUserStep(userId,progStage);
		if(null != epuNow){
			//更新这个阶段的状态
			
			//20161121科目一、二、三、四修改模板添加考试时间
			if(progStage==ReqConstants.PROG_STAGE_SUBJECT_ONE || progStage==ReqConstants.PROG_STAGE_SUBJECT_TWO ||progStage==ReqConstants.PROG_STAGE_SUBJECT_THREE||progStage==ReqConstants.PROG_STAGE_SUBJECT_FOUR){
				int subjectId=1;
				if(progStage == ReqConstants.PROG_STAGE_SUBJECT_ONE){
					subjectId = ReqConstants.SUBJECT_TYPE_ONE;
				}else if(progStage == ReqConstants.PROG_STAGE_SUBJECT_TWO){
					subjectId = ReqConstants.SUBJECT_TYPE_TWO;
				}else if(progStage == ReqConstants.PROG_STAGE_SUBJECT_THREE){
					subjectId = ReqConstants.SUBJECT_TYPE_THREE;
				}else if(progStage == ReqConstants.PROG_STAGE_SUBJECT_FOUR){
					subjectId = ReqConstants.SUBJECT_TYPE_FOUR;
				}
				EnrollExamResultExample example=new EnrollExamResultExample();
				example.createCriteria().andStudentIdEqualTo(userId).andSubjectIdEqualTo(subjectId);
				example.setOrderByClause("ctime desc");
				List<EnrollExamResult> results= enrollExamResultMapper.selectByExample(example);
				if(results!=null && results.size()>0){
					EnrollExamResult enExamResult=results.get(0);
					Map<Integer,String> map=new HashMap<>();
					map.put(1,TimeUtil.getDateFormat(enExamResult.getExamDate(), "yyyy-MM-dd"));
				    docparsed = parseDocParams(doc,map);
				}
			}
			epuNow.setResult(docparsed);
			if(null != needDisplay){
				epuNow.setNeedShow(needDisplay);
			}else{
				epuNow.setNeedShow((byte)0);
			}
			epuNow.setProcessState(execState);
			epuNow.setMtime(new Date());
			enrollProgressUserMapper.updateByPrimaryKeySelective(epuNow);
		}else {
			EnrollProgressUser epuNew = new EnrollProgressUser();
			try {
				BeanCopy.copyNotNull(ept, epuNew);
				epuNew.setStudentId(userId);
				epuNew.setResult(docparsed);
				if(null != needDisplay){
					epuNew.setNeedShow(needDisplay);
				}else{
					epuNow.setNeedShow((byte)0);
				}
				epuNew.setProcessState(execState);
				epuNew.setCpid(null);
				epuNew.setCtime(new Date());
				enrollProgressUserMapper.insertSelective(epuNew);
			} catch (Exception e) {
				logger.error("SchoolManagerImpl--autoPushEnrollProgress--"+ e);
			}
		}
		//如果是已成功或已失败状态，需要增加后续阶段
		//也有变量替换  默认给出字段为用户的名字，后面如有新配置，id依次累加即可
		if(null != nextStep){
			EnrollProgressTemplate eptNext = getProgressTemplateStep(ttid, nextStep);
			EnrollProgressUser epuNext = new EnrollProgressUser();
			try {
				String eptDoc = eptNext.getNoneDoc();
				Map<Integer,String> paramsDoc = new HashMap<Integer, String>();
				paramsDoc.put(1, "学员");
				String eptDocParsed = parseDocParams(eptDoc,paramsDoc);
				BeanCopy.copyNotNull(eptNext, epuNext);
				epuNext.setStudentId(userId);
				epuNext.setResult(eptDocParsed);
				epuNext.setProcessState(ReqConstants.STAGE_STATE_NONE);
				epuNext.setCpid(null);
				epuNext.setCtime(new Date());
				epuNext.setNeedShow(eptNext.getNoneRec());
				enrollProgressUserMapper.insertSelective(epuNext);
			} catch (Exception e) {
				logger.error("SchoolManagerImpl--autoPushEnrollProgress--"+ e);
			}
			//更新学员报名状态
			Student student = new Student();
			student.setStudentId(userId);
			student.setApplyexam(nextStep);
			student.setApplystate(ReqConstants.STAGE_STATE_NONE+0);
			studentManager.updateStudent(student);
		}else{
			//更新学员报名状态
			Student student = new Student();
			student.setStudentId(userId);
			student.setApplyexam(progStage);
			student.setApplystate(execState+0);
			studentManager.updateStudent(student);
		}
		
		//20160901 未做前置检查的推动，需要将跳跃过的步骤设置为已完成状态
		if(!preCheck && null != userProgLatest){
			int userStageLatest = userProgLatest.getStepId();
			byte userStageState = userProgLatest.getProcessState();
			if(userStageState != ReqConstants.STAGE_STATE_SUCC && userStageLatest< progStage){
				userProgLatest.setProcessState(ReqConstants.STAGE_STATE_SUCC);
				enrollProgressUserMapper.updateByPrimaryKeySelective(userProgLatest);
			}
		}

		//--------------2.更改旧的状态（不需要更改）-----------------
		return 0;

	}

	/**
	 * 字符串变量替换
	 * @param doc
	 * @param params
	 * @return
	 */
	private String parseDocParams(String doc, Map<Integer, String> params) {
		if(null == doc || !doc.contains("{")|| null == params ||params.size() == 0){
			return doc;
		}
		for(Integer s:params.keySet()){
			doc = doc.replaceAll("\\{".concat(s.toString()).concat("\\}"), params.get(s));
		}
		return doc;
	}

	/**
	 * 获取当前步骤模板信息
	 * @param ttid
	 * @param stepId
	 * @return
	 */
	private EnrollProgressTemplate getProgressTemplateStep(Integer ttid, Integer stepId){
		if(null == ttid){
			ttid =1;
		}
		EnrollProgressTemplate ept = redisUtil.get(REDISKEY.ENROLL_PROGRESS_TEMPLATE_ONE + ttid + "-"+stepId);
		if(null == ept){
			ept = enrollProgressTemplateMapper.selectByTTidStepid(ttid,stepId);
			redisUtil.set(REDISKEY.ENROLL_PROGRESS_TEMPLATE_ONE + ttid + "-"+stepId,ept);
		}
		return ept;
	}

	/**
	 * 获取用户的指定阶段
	 * @param studentId
	 * @param stepId
	 * @return
	 */
	private EnrollProgressUser getProgressUserStep(Long studentId, Integer stepId){
		return enrollProgressUserMapper.selectByStudentIdStep(studentId,stepId);
	}


	/**
	 * 获取用户某个报名包中的最近的一条状态
     * @param studentId
     * @param ttid
	 * @return
	 */
	private EnrollProgressUser getProgressUserTtidLatest(Long studentId, Integer ttid){
		return enrollProgressUserMapper.selectByStudentIdTtidOne(studentId,ttid);
	}

	/**
	 * 获取用户整体流程中的最近一条状态；整体流程可能包含多个报名套餐。
	 * @param studentId
	 * @return
	 */
	private EnrollProgressUser getProgressUserLatest(Long studentId){
		return enrollProgressUserMapper.selectByStudentIdLatest(studentId);
	}
	
	

	@Override
	public int changeState(int ttid, Long studentId, int stage, int state,
			Map<String, String> params) {
		List<Long> studentIds = new ArrayList<Long>();
		studentIds.add(studentId);
		return this.changeState(ttid, studentIds, stage, state, params);
	}

	@Override
	public int changeState(int ttid, List<Long> studentIds, int stage,
			int state, Map<String, String> params) {
		this.handleChangeState(ttid, studentIds, stage, state, params);
		return 0;

	}

	public void handleChangeState(final int ttid,final List<Long> studentIds, final int stage,
			final int state, final Map<String, String> params){
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {

				boolean hasWrong = false;
				Map<Integer,String> msgs = new HashMap<Integer, String>();
				/**
				 * 需要根据业务需求针对不同的阶段状态做特殊处理。
				 */
				switch(stage){
					case ReqConstants.PROG_STAGE_MAIL:
						switch(state){
						case ReqConstants.STAGE_STATE_NONE:
						case ReqConstants.STAGE_STATE_SUBMIT:	//20160711根据运营要求，cms也可以设置资料已邮寄状态
							for(Long userId:studentIds){
								Student stu = studentManager.getStudentInfo(userId);
								//如果学员没有身份证等相关信息，则不能直接设置资料已邮寄
								if(null != stu.getIdNumber() && !"".equals(stu.getIdNumber()) && null != stu.getApplyCarType()){
									int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, null, false); //20160901根据运营要求，cms可以跳过填写资料设置资料已邮寄，故这里取消前置检查
									if(res != 0){
										hasWrong = false;
										continue;
									}
								}else {
									hasWrong = false;
									continue;
								}
							}
							break;
						case ReqConstants.STAGE_STATE_SUCC:
							//(4,100)资料齐全
							for(Long userId:studentIds){
								int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, null, false); //20160901根据运营要求，cms可以跳过填写资料设置资料已邮寄，故这里取消前置检查
								if(res != 0){
									hasWrong = false;
									continue;
								}
							}
							break;
						case ReqConstants.STAGE_STATE_FAIL:
							//(4,101)资料不全，将反馈信息临时添加到学员信息表中
							for(Long userId:studentIds){
								int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, null, true);
								if(res == 0){
									try {
										String extra = params.get("ptype")+"#"+params.get("pmid")+"#"+params.get("note");
										Student student = new Student();
										student.setStudentId(userId);
										student.setExtra(extra);
										studentManager.updateStudent(student);
									} catch (Exception e) {
										e.printStackTrace();
										logger.error("SchoolManagerImpl--changeState--"+ e);
										hasWrong = false;
									}
								}else{
									hasWrong = false;
									continue;
								}
							}
							break;
						}
						break;
					case ReqConstants.PROG_STAGE_TABLE_SCHOOL:	
						switch(state){
						case ReqConstants.STAGE_STATE_NONE:
						case ReqConstants.STAGE_STATE_SUBMIT:
							//(5,1)表已寄出。
							for(Long userId:studentIds){
								int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, null, true);
								if(res != 0){
									hasWrong = false;
									continue;
								}
							}
							break;
						case ReqConstants.STAGE_STATE_SUCC:
							//(5,100)驾校已收表。
							for(Long userId:studentIds){
								int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, null, true);
								if(res != 0){
									hasWrong = false;
									continue;
								}else{
									Student stu = studentManager.getStudentInfo(userId);
									//20160725驾校收表后，将驾校信息冗余到报名订单中
									EnrollOrder enrollOrder = getEnrollOrderByStudentId(stu.getStudentId());
									if(null != enrollOrder){
										enrollOrder.setSchoolId((int)stu.getSchoolId());
										enrollOrder.setSchoolName(stu.getImportSrc());
										enrollOrderMapper.updateByPrimaryKeySelective(enrollOrder);
									}
									}
								}
							}
							break;
					case ReqConstants.PROG_STAGE_TABLE_OFFICAIL:
						switch(state){
						case ReqConstants.STAGE_STATE_NONE:
							break;
						case ReqConstants.STAGE_STATE_SUBMIT:
							//(6,1)报名受理中
							for(Long userId:studentIds){
								int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, null, true);
								if(res != 0){
									hasWrong = false;
									continue;
								}
							}
							break;
						case ReqConstants.STAGE_STATE_SUCC:
							//(6,100)报名成功，将反馈信息临时添加到学员信息表中
							for(Long userId:studentIds){
								Student stu = studentManager.getStudentInfo(userId);
								msgs.put(1, stu.getImportSrc());
								//byte drType = stu.getDrType();
								String drtype = stu.getApplyCarType();
								if(drtype.equals("1")){
									msgs.put(2, "C1");
								}else{
									msgs.put(2, "C2");
								}
								msgs.put(3, stu.getFlowNo());
								int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, msgs, true);
								if(res == 0){
								}else{
									hasWrong = false;
									continue;
								}
								doStudentPush(stu); //报名成功增加推送
								
							}
							break;
						case ReqConstants.STAGE_STATE_FAIL:
							//(6,101)报名失败，将反馈信息临时添加到学员信息表中
							for(Long userId:studentIds){
								int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, null, true);
								if(res == 0){
									try {
										String extra = params.get("note");
										Student student = new Student();
										student.setStudentId(userId);
										student.setExtra(extra);
										studentManager.updateStudent(student);
									} catch (Exception e) {
										e.printStackTrace();
										logger.error("SchoolManagerImpl--changeState--"+ e);
										hasWrong = false;
									}
								}else{
									hasWrong = false;
									continue;
								}
							}
							break;
						}

						break;
						
					case ReqConstants.PROG_STAGE_THEORY:
					case ReqConstants.PROG_STAGE_SUBJECT_LONGTRAIN:
						switch(state){
						case ReqConstants.STAGE_STATE_NONE:
						case ReqConstants.STAGE_STATE_SUBMIT:
						case ReqConstants.STAGE_STATE_SUCC:
						case ReqConstants.STAGE_STATE_FAIL:
							//(101,1) 已安排您在{1}进行理论课培训，地址为{2}
							if(null != params && params.size() > 0){
								try {
									msgs.put(1, params.get("dstart"));
								} catch (Exception e) {
									e.printStackTrace();
								}
								try {
									msgs.put(2, params.get("classPlace"));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							for(Long userId:studentIds){
								int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, msgs, false);//预约成功后还可能取消预约，所以关闭前置校验
								if(res != 0){
									hasWrong = false;
									continue;
								}
							}
							break;
						}
						break;
					case ReqConstants.PROG_STAGE_SUBJECT_ONE_LINEUP:
					case ReqConstants.PROG_STAGE_SUBJECT_TWO_LINEUP:
					case ReqConstants.PROG_STAGE_SUBJECT_THREE_LINEUP:
					case ReqConstants.PROG_STAGE_SUBJECT_FOUR_LINEUP:
					case ReqConstants.PROG_STAGE_SUBJECT_ONE:
					case ReqConstants.PROG_STAGE_SUBJECT_TWO:
					case ReqConstants.PROG_STAGE_SUBJECT_THREE:
					case ReqConstants.PROG_STAGE_SUBJECT_FOUR:
						if(null != params && params.size() > 0){
							msgs.put(1, params.get("examTime"));
							msgs.put(2, params.get("examPlace"));
						}
						for(Long userId:studentIds){
							int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, msgs, false);//预约成功后还可能取消预约，所以关闭前置校验
							if(res != 0){
								hasWrong = false;
								continue;
							}
						}
						break;
					default:
/*						switch(state){
						case ReqConstants.STAGE_STATE_NONE:
							break;
						case ReqConstants.STAGE_STATE_SUBMIT:
							break;
						case ReqConstants.STAGE_STATE_SUCC:
							break;
						case ReqConstants.STAGE_STATE_FAIL:
							break;
						}*/
						for(Long userId:studentIds){
							int res = autoPushEnrollProgress(ttid, userId, stage, (byte)state, null, true);
							if(res != 0){
								hasWrong = false;
								continue;
							}
						}
						break;
						
				}
				
			}
		});

	}

	private void doStudentPush(Student stu) {
		try {
			String redisKey = "QuartzJPush_student_enroll_suc_" +stu.getStudentId();
			if (!redisUtil.isExist(redisKey)) {
				JpushMsg jmsg = new JpushMsg();
				jmsg.setAlter("恭喜您已成功报名啦！");
				jmsg.setUserId(stu.getStudentId());
				jmsg.setOperate(JpushConstant.OPERATE.STUENROLLSUC);
				Message jpush = new Message();
				jpush.setTopic(jpushProducer.getCreateTopicKey());
				jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
				jpush.setBody(SerializableUtil.serialize(jmsg));
				jpushProducer.send(jpush);
				redisUtil.set(redisKey, 1, 3600);
				logger.info("************************doStudentPush Enroll Success! studentId : " + stu.getStudentId());
			}
			
			//推送报名成功成功的消息到mq
			Message msg=new Message();
			msg.setTopic("rmq_school_flow_key");
			msg.setTags(OrderConstant.RMQTAG.STUDENT_FLOW_OUT);
			StudentMessage smsg = new StudentMessage();
			smsg.setStudentId(stu.getStudentId());
			smsg.setPhoneNum(stu.getPhoneNum());
			smsg.setMsgType(StudentMessage.MSGTYPE_STUDENT_FLOW_PAID);
			msg.setBody(SerializableUtil.serialize(smsg));
			schoolFlowProducer.send(msg);
			logger.debug("schoolProducer has sent a message. MSGTYPE_STUDENT_ENROLL_PAID. StudentId: "+stu.getStudentId());

		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//废弃。。由cms直接调用changeState接口。因分配驾校和上传流水号，cms已完成相关功能，现在更改为分配驾校和上传流水号之后成功后，调用changeState 接口更改状态。
	@Override
	public int allotSchool(int ttid, List<Long> studentIds, int schoolId) {
		boolean hasWrong = false;
		for(Long userId:studentIds){
			//分配驾校完成后，自动变更为未交表状态 (5,0)
			int res = autoPushEnrollProgress(ttid, userId, ReqConstants.PROG_STAGE_TABLE_SCHOOL, ReqConstants.STAGE_STATE_NONE, null, true);
			if(res != 0){
				hasWrong = true;
				continue;
			}else{
				//更新学员驾校信息
				Student student = new Student();
				student.setStudentId(userId);
				student.setSchoolId((byte)schoolId);
				studentManager.updateStudent(student);
			}
		}
		if(hasWrong){
			return -1;
		}
		return 0;
	}

    @Override
	public ReqResult checkoutEnroll2School(String orderId, String checkouter) {
        ReqResult result = ReqResult.getFailed();

        if (StringUtil.isNotNullAndNotEmpty(orderId) && StringUtil.isNotNullAndNotEmpty(checkouter)) {
            try {
                EnrollOrder enrollOrder = enrollOrderMapper.selectByPrimaryKey(orderId);

                if (null != enrollOrder) {
                    Student student = studentManager.getStudentInfo(enrollOrder.getStudentId());

                    //只有再给学员分配完驾校之后，才能给驾校结款！
                    Byte schoolId = student.getSchoolId();
                    if (null != schoolId && schoolId != 0){
                        if (enrollOrder.getPayState() == ReqConstants.STAGE_STATE_SUCC) {//只有支付成功的,才能结款

                            enrollOrder.setCheckouter(checkouter);
                            enrollOrder.setCheckoutTime(new Date());
                            //enrollOrder.setCheckoutState(ReqConstants.CHECKOUT_DONE); //避免异常情况下结算多次，在结算完成后设置结算状态

                            if (enrollOrderMapper.updateByPrimaryKey(enrollOrder) > 0) {
                                moneyManager.handleCheckoutEnrollMoneyFlow(enrollOrder); // 记录资金流动
                                result = ReqResult.getSuccess();
                            }else {
                            	logger.error("checkoutEnroll2School error! enrollOrderMapper.updateByPrimaryKey error !", orderId);
                            }
                        }else {
                        	logger.error("checkoutEnroll2School error! enrollOrder.getPayState() !", enrollOrder.getPayState());
                        	result = ReqResult.getParamError();
                        }
                    }else{
                    	logger.error("checkoutEnroll2School error! schoolId is !", schoolId);
                    	result.setMsgInfo(ResultCode.ERRORINFO.ENROLL_CHECKOUT_NO_SCHOOL);
                    	result.setMsgKey(ResultCode.ERRORCODE.ENROLL_CHECKOUT_NO_SCHOOL);
                    }
                } else {
                	logger.error("checkoutEnroll2School error! enrollOrder is null!", orderId);
                    result = ReqResult.getParamError();
                }
            } catch (Exception e) {
                logger.error("checkoutEnroll2School error! orderid:{},checkouter:{}", orderId, checkouter);
                result.setCode(ResultCode.ERRORCODE.EXCEPTION);
                result.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
            }
        } else {
            result = ReqResult.getParamError();
        }

        return result;
    }

	@Override
	public int postEnrollSource(String agent, String orderId) {
		try {
			EnrollOrder eo = enrollOrderMapper.selectByPrimaryKey(orderId);
			eo.setExtra(agent);
			return enrollOrderMapper.updateByPrimaryKeySelective(eo);
		} catch (Exception e) {
			logger.error("null orderId error! orderId:{}", orderId);
			return 0;
		}
	}
	
	@Override
	public ReqResult getDriveSchoolList(Double lat,Double lon,String pageSize,String cityId){
		int size=10;
		if(pageSize!=null && !pageSize.equals("")){
			size=Integer.parseInt(pageSize);
		}
		ReqResult r = ReqResult.getSuccess();
		
		List<SchoolWithBLOBs> allListSearch = redisUtil.get(REDISKEY.WECHAT_SCHOOL_ALL);
		if (allListSearch == null){
			allListSearch = wechatMapper.searchSchool(null);	
			 if(allListSearch.size()!=0){
				 redisUtil.set(REDISKEY.WECHAT_SCHOOL_ALL, allListSearch); 
			 }
		}
		//过滤城市
		List<SchoolWithBLOBs> allList=new ArrayList<>();
		if(cityId!=null && !cityId.equals("")){
			int city_id=Integer.parseInt(cityId);
			for(int i=0;i<allListSearch.size();i++){
				SchoolWithBLOBs schoolWithBLOBs=allListSearch.get(i);
				if(schoolWithBLOBs.getCityId()==city_id){
					allList.add(schoolWithBLOBs);
				}
			}
		}else{
			allList.addAll(allListSearch);
		}
		
		Integer total =allList.size();
			for(int i=0;i<allList.size();i++){
				SchoolWithBLOBs school=allList.get(i);
				int schoolId=school.getSchoolId();
				int count=wechatMapper.countBySchoolId(schoolId);
				
				List<Trfield> trfieldList = redisUtil.get(REDISKEY.WECHAT_TRFIELD_LIST + Integer.toString(schoolId));
				if (trfieldList == null){
					trfieldList = wechatMapper.searchTrfieldBySchoolId(schoolId);
					if(trfieldList.size()!=0){
						redisUtil.set(REDISKEY.WECHAT_TRFIELD_LIST + Integer.toString(schoolId), trfieldList);
					}
				}
				
				int distance=0;
				for(int j=0;j<trfieldList.size();j++){
					Trfield trfield=trfieldList.get(j);
					if(trfield.getLae()==null||trfield.getLge()==null){
						continue;
					}
					double lae=trfield.getLae();
					double lge=trfield.getLge();
					int num=Distance.getDistatce(lat, lon, lae, lge);
					if(j==0){
						distance=num;
					}
					if(num<distance){
						distance=num;
					}
				}
				school.setOrderNum(count);
				school.setExtra(String.valueOf(distance));
			}
			Collections.sort(allList, new Comparator<SchoolWithBLOBs>() {// 内部类
				public int compare(SchoolWithBLOBs arg0,SchoolWithBLOBs arg1) {
					Integer question0=Integer.parseInt(arg0.getExtra());
					Integer question1 = Integer.parseInt(arg1.getExtra());
					if(question0==0){  //把空数据排到最后
						question0=1000;
					}
					if(question1==0){
						question1=1000;
					}
					return question0.compareTo(question1);
				}
			});
			Collections.sort(allList, new Comparator<SchoolWithBLOBs>() {// 内部类
				public int compare(SchoolWithBLOBs arg0, SchoolWithBLOBs arg1) {
					Integer int1=arg0.getPackageCount();
					Integer int2 =arg1.getPackageCount();
					return int2.compareTo(int1);
				}
			});
			List<SchoolWithBLOBs> list=new ArrayList<>();
			for(int i=0;i<allList.size()&&i<size;i++){
				list.add(allList.get(i));
			}
		
		
		r.setData(list);
		r.setData("total",total);
		return r;
	}
	
	@Override
	public ReqResult getDriveSchoolListNew(Double lat,Double lon,String pageSize,String cityId){
		ReqResult r = ReqResult.getSuccess();
		
		School school = new School();
		if (StringUtil.isNullOrEmpty(cityId)) { //没有城市默认深圳
			cityId = "100100";
		}
		school.setCityId(Integer.parseInt(cityId));
		
		// 有班别的一组
		redisUtil.delete(REDISKEY.SEARCH_SCHOOL_HAS_CLASS + cityId);
		redisUtil.delete(REDISKEY.SEARCH_SCHOOL_NOT_CLASS + cityId);
		List<School> schHasClassList = redisUtil.get(REDISKEY.SEARCH_SCHOOL_HAS_CLASS + cityId);
		if (schHasClassList == null) {
			schHasClassList = wechatMapper.searchSchoolHasClass(school);	
			if (schHasClassList != null && schHasClassList.size()!=0) {
				redisUtil.set(REDISKEY.SEARCH_SCHOOL_HAS_CLASS + cityId, schHasClassList, 3600); 
			}
		}
		schHasClassList = distanceSchool(schHasClassList, lat, lon);
		
		// 没有班级的一组
		List<School> schNoClassList = redisUtil.get(REDISKEY.SEARCH_SCHOOL_NOT_CLASS + cityId);
		if (schNoClassList == null) {
			schNoClassList = wechatMapper.searchSchoolNoClass(school);	
			if (schNoClassList != null && schNoClassList.size()!=0) {
				redisUtil.set(REDISKEY.SEARCH_SCHOOL_NOT_CLASS + cityId, schHasClassList, 3600); 
			}
		}
		schNoClassList =  distanceSchool(schNoClassList, lat, lon);
		
		SearchSchoolList searchSchoolList = new SearchSchoolList();
		searchSchoolList.setSchHasClassList(schHasClassList);
		searchSchoolList.setSchNoClassList(schNoClassList);
		
		r.setData(searchSchoolList);
		
		return r;
	}
	
	/**
	 * 根据驾校获取驾校下所有的训练场坐标，取与用户距离最小的训练场作为驾校的坐标
	 * @param schoolList
	 * @param lat
	 * @param lon
	 * @return
	 */
	private List<School> distanceSchool(List<School> schoolList, Double lat, Double lon) {
		if (schoolList != null && schoolList.size() > 0) {
			for (School school : schoolList) {
				// 根据驾校获取驾校下所有的训练场坐标
				List<Trfield> trfieldList = redisUtil.get(REDISKEY.WECHAT_TRFIELD_LIST + Integer.toString(school.getSchoolId()));
				if (trfieldList == null) {
					trfieldList = wechatMapper.searchTrfieldBySchoolId(school.getSchoolId());
					if (trfieldList != null && trfieldList.size() > 0) {
						redisUtil.set(REDISKEY.WECHAT_TRFIELD_LIST + Integer.toString(school.getSchoolId()), trfieldList);
					}
				}
				
				// 驾校下所有训练场，取与用户距离最小的训练场作为驾校的坐标
				int distance = 0;
				if (trfieldList != null && trfieldList.size() > 0) {
					for (Trfield trfield : trfieldList) {
						if (trfield.getLae() == null || trfield.getLge() == null) {
							continue;
						}
						double lae = trfield.getLae();
						double lge = trfield.getLge();
						int num = Distance.getDistatce(lat, lon, lae, lge);
						if (distance == 0) { //赋初始值
							distance = num;
						}
						if (num < distance) { // 比较2个大小，取小值
							distance = num;
						}
					}
				}
				school.setExtra(String.valueOf(distance));
			}
			
			Collections.sort(schoolList, new Comparator<School>() {
				public int compare(School school1,School school2) {
					if (school1 != null && school2 != null) {
						Integer question0 = Integer.parseInt(school1.getExtra());
						Integer question1 = Integer.parseInt(school2.getExtra());
						if (question0 == 0) {  //把空数据排到最后
							question0 = 1000;
						}
						if (question1 == 0) {
							question1 = 1000;
						}
						return question0.compareTo(question1);
					}
					return 0;
				}
			});
		}
		
		return schoolList;
	}
	
	@Override
	public ReqResult getTrfieldBySchoolId(String schoolId,String size){
		ReqResult r = ReqResult.getSuccess();
		int num=Integer.parseInt(size);
		
		List<Trfield> trfield = redisUtil.get(REDISKEY.WECHAT_TRFIELD_LIST + schoolId);
		if (trfield == null){
			trfield = wechatMapper.searchTrfieldBySchoolId(Integer.parseInt(schoolId));
			if(trfield.size()!=0){
				redisUtil.set(REDISKEY.WECHAT_TRFIELD_LIST + schoolId, trfield);
			}
		}
		Integer total =trfield.size();
		List<Trfield> result=new ArrayList<>();
		for(int i=0;i<trfield.size()&& i<num;i++){
			result.add(trfield.get(i));
		}
		
		r.setData(result);
		r.setData("total",total);
		return r;
	}
	
	@Override
	public ReqResult getDriveSchoolById(String id,Double lat,Double lon){
		ReqResult r = ReqResult.getSuccess();
		
		SchoolWithBLOBs schoolWithBLOBs = redisUtil.get(REDISKEY.WECHAT_SCHOOL + id);
		if (schoolWithBLOBs == null){
			List<SchoolWithBLOBs> list = wechatMapper.searchSchool(id);
			if(list.size()==0){
				r=ReqResult.getParamError();
				return r;
			}
			schoolWithBLOBs = list.get(0);
			redisUtil.set(REDISKEY.WECHAT_SCHOOL + id, schoolWithBLOBs);
		}
		
		int count=wechatMapper.countBySchoolId(Integer.parseInt(id));
		schoolWithBLOBs.setOrderNum(count);

		List<Trfield> trfieldList = redisUtil.get(REDISKEY.WECHAT_TRFIELD_LIST + id);
		if (trfieldList == null){
			trfieldList = wechatMapper.searchTrfieldBySchoolId(Integer.parseInt(id));
			if(trfieldList.size()!=0){
				redisUtil.set(REDISKEY.WECHAT_TRFIELD_LIST + id, trfieldList);
			}
			
		}
		
		int distance=0;
		for(int j=0;j<trfieldList.size();j++){
			Trfield trfield=trfieldList.get(j);
			if(trfield.getLae()==null||trfield.getLge()==null){
				continue;
			}
			double lae=trfield.getLae();
			double lge=trfield.getLge();
			int num=Distance.getDistatce(lat, lon, lae, lge);
			if(j==0){
				distance=num;
			}
			if(num<distance){
				distance=num;
			}
		}
		schoolWithBLOBs.setExtra(String.valueOf(distance));
		r.setData(schoolWithBLOBs);
		return r;
	}
	
	@Override
	public ReqResult getGroupBySchoolId(String id){
		ReqResult r = ReqResult.getSuccess();
		
		List<WechatEnrollPackageWithBLOBs> wechatEnrollPackageList= redisUtil.get(REDISKEY.WECHAT_ENROLL_PACKAGE_LIST + id);
		
		if (wechatEnrollPackageList == null){
			WechatEnrollPackageExample example=new WechatEnrollPackageExample();
			//ostate 1停止招生 2开始招生  cstate 1未审核 2审核通过 3审核不通过
			example.createCriteria().andSchoolIdEqualTo(Integer.parseInt(id)).andOstateEqualTo(2).andCstateEqualTo(2);
			wechatEnrollPackageList = wechatEnrollPackageMapper.selectByExampleWithBLOBs(example);
			if(wechatEnrollPackageList.size()!=0){
				redisUtil.set(REDISKEY.WECHAT_ENROLL_PACKAGE_LIST + id, wechatEnrollPackageList);
			}
		}
		r.setData(wechatEnrollPackageList);
		return r;
	}
	
	@Override
	public ReqResult getPackageById(String ttid){
		ReqResult r = ReqResult.getSuccess();
		WechatEnrollPackageWithBLOBs wechatEnrollPackage = redisUtil.get(REDISKEY.WECHAT_ENROLL_PACKAGE + ttid);
		if (wechatEnrollPackage == null){
			wechatEnrollPackage = wechatEnrollPackageMapper.selectByPrimaryKey(Integer.parseInt(ttid));
			if(wechatEnrollPackage!=null){
				redisUtil.set(REDISKEY.WECHAT_ENROLL_PACKAGE + ttid, wechatEnrollPackage);
			}
		}
		r.setData(wechatEnrollPackage);
		return r;
	}
	
	@Override
	public int addWechatOrder(WechatEnrollOrder wechatEnrollOrder){
		wechatMapper.deleteOrder(wechatEnrollOrder.getStudentId());
		return wechatEnrollOrderMapper.insert(wechatEnrollOrder);
	}
	
	/**
	 * 获取微信报名订单
	 */
	@Override
	public WechatEnrollOrder getWechatOrder(String orderId) {
	    WechatEnrollOrder wOrder = wechatEnrollOrderMapper.selectByPrimaryKey(orderId);
		return wOrder;
	}
	
	/**
	 * 获取微信报名订单
	 */
	@Override
	public List<WechatEnrollOrder> getWechatOrderByStudentId(String studentId){
		 WechatEnrollOrderExample example=new WechatEnrollOrderExample();
		 example.createCriteria().andStudentIdEqualTo(Long.parseLong(studentId)).andIsdelEqualTo((byte) 0);
		 List<WechatEnrollOrder> wOrder = wechatEnrollOrderMapper.selectByExampleWithBLOBs(example);
		 return wOrder;
	}
	
	/**
	 * 保存评论信息
	 */
	 public int saveWechatComment(WechatComment wechatComment) {
		long schoolId=wechatComment.getSchoolId();
		if (StringUtil.isNullOrEmpty(wechatComment.getPic()) && wechatComment.getStudentId() != null) {
			Student student = studentManager.getStudentInfo(wechatComment.getStudentId());
			if (student != null) {
				wechatComment.setPic(student.getHeadIcon());
			}
		}
		
		//驾校里有评论评分 删除驾校信息
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL +schoolId);
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL_ALL);
		return commentMapper.insert(wechatComment);
	};
	
	/**
	 * 删除评论信息
	 */
	 public int deleteComment(String commentId) {
		 WechatComment wechatComment=commentMapper.selectByPrimaryKey(commentId);
		 long schoolId=wechatComment.getSchoolId();
		//驾校里有评论评分 删除驾校信息
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL +schoolId);
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL_ALL);
		WechatCommentPraiseExample example=new WechatCommentPraiseExample();
		example.createCriteria().andCommentIdEqualTo(commentId);
		//删除点赞
		praiseMapper.deleteByExample(example);
		return commentMapper.deleteByPrimaryKey(commentId);
	};
	
	/**
	 * 获取评论信息
	 */
	@Override
    public ReqResult getCommentListBySchoolId(String schoolId,String studentId,int start,int end){
		ReqResult r = ReqResult.getSuccess();
		List<WechatComment> comments = wechatMapper.getCommentListBySchoolId(schoolId,studentId,start,end);
		r.setData(comments);

		return r;
	}
	
	/**
	 * 修改点赞信息
	 */
	@Override
    public ReqResult updateCommentPraise(String commitId,String studentId){
		ReqResult r=ReqResult.getSuccess();
		WechatCommentPraiseExample example=new WechatCommentPraiseExample();
		example.createCriteria().andCommentIdEqualTo(commitId).andStudentIdEqualTo(Integer.parseInt(studentId));
		List<WechatCommentPraise> comments=praiseMapper.selectByExample(example);
		if(comments.size()==0){
			String praiseId=StringUtil.getOrderId();
			WechatCommentPraise praise=new WechatCommentPraise();
			praise.setPraiseId(praiseId);
			praise.setCommentId(commitId);
			praise.setStudentId(Integer.parseInt(studentId));
			praise.setCreateTime(new Date());
			praiseMapper.insert(praise);
		}else{
			praiseMapper.deleteByPrimaryKey(comments.get(0).getPraiseId());
		}
		return r;
	}

	@Override
	public SchoolWithBLOBs getDriveSchoolInfo(School school) {
		return wechatMapper.querySchoolInfo(school);
	}
	


}
