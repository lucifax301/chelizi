package com.lili.httpaccess.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.RegionManager;
import com.lili.coach.vo.CoachVo;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.Page;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.VersionUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.log.dto.LogComplain;
import com.lili.log.service.LogService;
import com.lili.logic.service.ICoachStateManager;
import com.lili.logic.service.IStudentStateManager;
import com.lili.logic.service.OrderLogic;
import com.lili.order.dto.StudentClass;
import com.lili.order.dto.StudentClassPool;
import com.lili.order.service.CancelReasonService;
import com.lili.order.service.CoachClassService;
import com.lili.order.service.CoachCommentService;
import com.lili.order.service.CommentTagService;
import com.lili.order.service.OrderService;
import com.lili.order.service.StuCommentService;
import com.lili.order.service.StudentClassService;
import com.lili.order.vo.CancelReasonQuery;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.CoachCommentQuery;
import com.lili.order.vo.CoachCommentVo;
import com.lili.order.vo.CommentTagQuery;
import com.lili.order.vo.CommentTagVo;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;
import com.lili.order.vo.StuOrderCommentHourVo;
import com.lili.order.vo.StuOrderCommentVo;
import com.lili.pay.service.PayService;
import com.lili.school.dto.WechatEnrollPackage;
import com.lili.school.service.SchoolService;
import com.lili.school.service.WechatSchoolService;
import com.lili.student.dto.ExerciseError;
import com.lili.student.dto.ExerciseExam;
import com.lili.student.dto.MicroClass;
import com.lili.student.dto.Student;
import com.lili.student.manager.MycoachesManager;
import com.lili.student.manager.StudentManager;
import com.lili.student.service.StudentExerciseService;
import com.lili.student.service.StudentService;
import com.lili.student.service.StudentVipService;
import com.lili.student.vo.MycoachesVo;
import com.lili.student.vo.StudentInfoVo;

@Controller
@RequestMapping("/v1/students")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private OrderLogic orderLogic;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CoachClassService coachClassService;
	@Autowired
	private CoachCommentService coachCommentService;
	@Autowired
	private CancelReasonService cancelReasonService;
	@Autowired
	private PayService payService;
	@Autowired
	private IStudentStateManager stateManager;
	@Autowired
	private ICoachStateManager coachStateManager;
	@Autowired
	private StuCommentService stuCommentService;
	@Autowired
	private LogService logService;
	@Autowired
	private StudentVipService studentVipService;
	@Autowired
	private StudentExerciseService studentExerciseService;
	@Autowired
	CoachManager coachManager;
	@Autowired
	CommentTagService commentTageService;
	@Autowired
	StudentClassService studentClassService;
	@Autowired
	RegionManager regionManager;
	@Autowired
	SchoolService schoolService;
	@Autowired
	MycoachesManager mycoachesManager;
	@Autowired
	WechatSchoolService wechatSchoolService;

	private Logger log = LoggerFactory.getLogger(StudentController.class);

	/**
	 * 校验验证码是否正确 如果正确，要在实现中调用底层接口保存用户信息
	 *
	 * @param mobile
	 *            手机号
	 * @param codeInput
	 *            用户输入的验证码
	 * @param password
	 *            密码，客户端md5加密后的字符串
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public Object addStudentAndLogin(@RequestParam String mobile, @RequestParam String codeInput,
			@RequestParam String password, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();

		try {
			r = studentService.addStudentAndLogin(mobile, codeInput, password);
		} catch (Exception e) {
			log.error("controller: student post register failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 用户登录
	 *
	 * @param mobile
	 * @param password
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(@RequestParam String mobile, @RequestParam String password, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();

		try {
			r = studentService.login(mobile, password);
		} catch (Exception e) {
			log.error("controller: student post login failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	@RequestMapping(value = "/5date", method = RequestMethod.GET)
	@ResponseBody
	public Object _5date() {
		ReqResult r = ReqResult.getSuccess();

		try {
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			List<String> dates = new ArrayList();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(today);
			for (int i = 0; i < 5; i++) {
				dates.add(format.format(calendar.getTime()));
				calendar.add(Calendar.DAY_OF_YEAR, 1);
			}
			r.setData(dates);
		} catch (Exception e) {
			log.error("controller: student post login failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 通过手机验证码进行登录
	 *
	 * @param mobile
	 * @param code
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/login2", method = RequestMethod.POST)
	@ResponseBody
	public Object login2(@RequestParam String mobile, @RequestParam String code,
			@RequestParam(required = false) String reqType, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();

		try {
			r = studentService.login2(mobile, code, reqType);
		} catch (Exception e) {
			log.error("controller: student post login2 failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 用户自动登录
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/autoLogin", method = RequestMethod.POST)
	@ResponseBody
	public Object autoLogin(@RequestParam String userId, @RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		// 设置登录时间
		Date now = new Date();
		Student s = studentManager.getStudentInfo(Long.parseLong(userId));
		if (s.getFirstLogin() == null)
			s.setFirstLogin(now);
		s.setLastLogin(now);
		studentManager.updateStudent(s);
		// 已在过滤器中判断是否已登录
		return r.getResult();
	}

	/**
	 * 用户注销
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/logout", method = RequestMethod.POST)
	@ResponseBody
	public Object logout(@PathVariable String userId, @RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = studentService.logout(userId, tokenId);
		} catch (Exception e) {
			log.error("controller: student post logout failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取用户基本信息
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserInfo(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();

		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = studentService.getUserInfo(userId, tokenId);

		} catch (Exception e) {
			log.error("controller: student get info failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 导入的学员引导同意用户协议
	 *
	 * @param userId
	 * @param userType
	 * @param agreement
	 *            0-未同意；1-已同意
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/agreement", method = RequestMethod.POST)
	@ResponseBody
	public Object postAgreement(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String agreement, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();

		try {
			r = studentService.postAgreement(userId, agreement);

		} catch (Exception e) {
			log.error("controller: student post agreement failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 修改用户信息
	 *
	 * @param userId
	 * @param userType
	 * @param password
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @param headIcon
	 * @param name
	 * @param sex
	 * @param age
	 * @param mobile
	 * @param codeInput
	 * @return
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	@ResponseBody
	public Object updateUser(@PathVariable String userId, @RequestParam String userType,
			@RequestParam(required = false) String password, @RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers, @RequestParam(required = false) String headIcon,
			@RequestParam(required = false) String name, @RequestParam(required = false) String sex,
			@RequestParam(required = false) String age, @RequestParam(required = false) String mobile,
			@RequestParam(required = false) String codeInput) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = studentService.updatePass(userId, password, headIcon, name, sex, age, mobile, codeInput);
		} catch (Exception e) {
			log.error("controller: student put password failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 用户添加头像
	 *
	 * @param @param
	 *            userId
	 * @param @param
	 *            userType
	 * @param @param
	 *            picPath
	 * @param @param
	 *            timestamp
	 * @param @param
	 *            sign
	 * @param @param
	 *            headers
	 * @param @return
	 *            参数
	 * @return Object 返回类型
	 * @throws @Title:
	 *             updateHeadIcon
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 */
	@RequestMapping(value = "/{userId}/headIcon", method = RequestMethod.POST)
	@ResponseBody
	public Object updateHeadIcon(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String picPath, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentService.updateHeadIcon(userId, picPath);
		} catch (Exception e) {
			log.error("controller: student update headIco failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 添加用户身份证信息
	 *
	 * @param userId
	 *            用户id
	 * @param userType
	 * @param name
	 *            身份证姓名
	 * @param idCard
	 *            身份证号码
	 * @param picPath1
	 *            身份证正面保存地址
	 * @param picPath2
	 *            身份证背面保存地址
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "/{userId}/idCard", method = RequestMethod.POST)
	@ResponseBody
	public Object addIdCardInfo(@PathVariable String userId, @RequestParam String userType, @RequestParam String name,
			@RequestParam String idCard, @RequestParam String picPath1, @RequestParam String picPath2,
			@RequestParam String timestamp, @RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = studentService.addIdCardInfo(userId, name, idCard, picPath1, picPath2, tokenId);
		} catch (Exception e) {
			log.error("controller: student post idcard failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 上传驾照信息
	 *
	 * @param userId
	 * @param userType
	 * @param drType
	 *            准驾驶车型
	 * @param drLicence
	 *            驾照编号
	 * @param drPhoto2
	 *            驾驶证图片地址
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/driveCard", method = RequestMethod.POST)
	@ResponseBody
	public Object addDriveCard(@PathVariable String userId, @RequestParam String userType, @RequestParam String drType,
			@RequestParam String drLicence, @RequestParam(required = false) String drPhoto2,
			@RequestParam String drPhoto, @RequestParam(required = false) String drExpires,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentService.addDriveCard(userId, drType, drLicence, drPhoto, drPhoto2, drExpires);
		} catch (Exception e) {
			log.error("controller: student post driveCard failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员发起约课请求，获取周围的教练列表，分页查询
	 *
	 * @param userId
	 *            用户id
	 * @param userType
	 * @param lge
	 *            地理经度
	 * @param lae
	 *            地理纬度
	 * @param coachStatus
	 *            教练状态 0：全部教练；1：休息；2：听单中；3：在忙
	 * @param driveType
	 *            驾考类型：1:C1 2:C2 3:C1&C2
	 * @param carLevel
	 *            考试车型：1:C1 2:C2 3:C1&C2
	 * @param courseId
	 *            科目：科目二(正常练习、考前强化)、科目三(正常练习、考前强化)、陪练、模拟器训练
	 * @param pageNo
	 *            分页第几页
	 * @param pageSize
	 *            分页每页个数
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "/{userId}/nearby", method = RequestMethod.POST)
	@ResponseBody
	public Object getNearby(@PathVariable String userId, @RequestParam(required = false) String userType,
			@RequestParam String lge, @RequestParam String lae, @RequestParam String coachStatus,
			@RequestParam String driveType, String carLevel, @RequestParam String courseId, @RequestParam String pageNo,
			@RequestParam String pageSize, @RequestParam String timestamp, @RequestParam String sign,
			@RequestParam(required = false) String v) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = orderLogic.reservation(userId, lge, lae, coachStatus, driveType, carLevel, courseId, pageNo, pageSize,
					null);
			if ((null != v && !"".equals(v) && ((VersionUtil.compareVersion(v, "2.0.0") < 0))
					|| (VersionUtil.compareVersion(v, "2.1.0")) >= 0)) {
				// 20160907 改了价格后，原预约订单需要使用原价格，相差默认的接送费20元
				// 20161221 接送费改为40元
				if (r.isSuccess()) {
					@SuppressWarnings("unchecked")
					List<CoachVo> list = (List<CoachVo>) r.getResult().get(ResultCode.RESULTKEY.DATA);
					if (list != null && list.size() > 0 && !(courseId.endsWith("2") || courseId.endsWith("4") || courseId.endsWith("5"))) {
						for (CoachVo cv : list) {
							cv.setPrice(cv.getPrice() + 4000);
						}
					}
					r.setData(list);
				}
			}

		} catch (Exception e) {
			log.error("controller: student get nearby failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员针对某个课程对教练进行评价
	 *
	 * @param userId
	 *            学员id
	 * @param userType
	 * @param orderId
	 *            订单id
	 * @param coachId
	 *            教练id
	 * @param score
	 *            得分
	 * @param reason
	 *            评分原因
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "/{userId}/evaluations/{coachId}", method = RequestMethod.POST)
	@ResponseBody
	public Object doEvaluation(@PathVariable String userId, @RequestParam String userType, @PathVariable String coachId,
			@RequestParam String orderId, @RequestParam(required = false) String anonymity, @RequestParam String tagId,
			@RequestParam String score, @RequestParam String reason, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			if (StringUtil.isEmptyOrWhitespaceOnly(score) || StringUtil.isEmptyOrWhitespaceOnly(tagId)
					|| "0".equals(score.trim())) {
				r.setCode(ResultCode.ERRORCODE.SCORE_ZERO);
				r.setMsgInfo(ResultCode.ERRORINFO.SCORE_ZERO);
				return r.getResult();
			}
			r = orderLogic.stuCommentCoach(coachId, userId, orderId, score, tagId, reason, tokenId, anonymity);
		} catch (Exception e) {
			log.error("controller: student do evaluation failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员查看自己对教练的评价
	 *
	 * @param userId
	 *            学员id
	 * @param userType
	 * @param orderId
	 *            订单id
	 * @param coachId
	 *            教练id
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "/{userId}/evaluations/{coachId}", method = RequestMethod.GET)
	@ResponseBody
	public Object getEvaluation(@PathVariable String userId, @RequestParam String userType,
			@PathVariable String coachId, @RequestParam String orderId, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			List<CoachCommentVo> data = coachCommentService.queryByOrderId(orderId, new CoachCommentQuery());
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: student get evaluation failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员查看教练的所有评论
	 *
	 * @param userId
	 * @param userType
	 * @param coachId
	 * @param pageNo
	 * @param pageSize
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/evaluations/{coachId}", method = RequestMethod.GET)
	@ResponseBody
	public Object getEvaluationAll(@RequestParam(required = false) String userId,
			@RequestParam(required = false) String userType, @RequestParam(required = false) String v,
			@PathVariable String coachId, @RequestParam String pageNo, @RequestParam String pageSize,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			CoachCommentQuery ccq = new CoachCommentQuery();
			ccq.setPageIndex(Integer.parseInt(pageNo));
			ccq.setPageSize(Integer.parseInt(pageSize));
			ccq.setorderBy("order by cotime desc");
			List<CoachCommentVo> data = coachCommentService.queryByCoachId(Long.parseLong(coachId), ccq);
			// 添加分页信息
			int totalRow = coachCommentService.countByCoachId(Long.parseLong(coachId));
			List<CoachCommentVo> data2 = new ArrayList<CoachCommentVo>();
			if (null != data && data.size() > 0) {
				List<Long> stuIds = BeanCopy.getFieldList(data, "studentId");
				List<Student> sts = studentManager.getStudentsByIds(stuIds);
				BeanCopy.copyList(sts, data, BeanCopy.COPY2NULL, "studentId");

				// 20160805登录后移增加返回值
				if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.1") > 0))) {
					CommentTagVo vo = new CommentTagVo();
					vo.setType(1);
					CommentTagQuery query = new CommentTagQuery();
					query.setPageSize(500);

					List<CommentTagVo> list = commentTageService.queryByObjectAnd(vo, query);
					Map<Integer, String> tagMap = new HashMap<Integer, String>();
					for (int i = 0; i < list.size(); i++) {
						tagMap.put(list.get(i).getCtid(), list.get(i).getTag());
					}

					StringBuffer tags = null;
					for (CoachCommentVo coachCommentVo : data) {
						if (coachCommentVo.getCtid() != null && !"".equals(coachCommentVo.getCtid())) {
							String[] ctids = coachCommentVo.getCtid().split(",");
							Integer ctid;
							if (ctids != null && ctids.length > 0) {
								tags = new StringBuffer();
								for (int i = 0; i < ctids.length; i++) {
									ctid = Integer.parseInt(ctids[i]);
									tags.append(tagMap.get(ctid));
									if (i != (ctids.length - 1)) {
										tags.append("|");
									}
								}
							}
						}
						if (tags != null) {
							coachCommentVo.setTags(tags.toString());
						}
						data2.add(coachCommentVo);
						tags = null;
					}
				}
			}

			if (data2.size() > 0) {
				Page<CoachCommentVo> pageData = new Page<CoachCommentVo>(data, Integer.parseInt(pageNo),
						Integer.parseInt(pageSize), totalRow);
				r.setData(pageData);

				return r.getResult();
			}

			Page<CoachCommentVo> pageData = new Page<CoachCommentVo>(data, Integer.parseInt(pageNo),
					Integer.parseInt(pageSize), totalRow);
			r.setData(pageData);
		} catch (Exception e) {
			log.error("controller: student get evaluation failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取学员历史被打分详情
	 *
	 * @param userId
	 * @param userType
	 * @param pageNo
	 * @param pageSize
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/evaluations", method = RequestMethod.GET)
	@ResponseBody
	public Object getEvaluationSelf(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String pageNo, @RequestParam String pageSize, @RequestParam(required = false) String v,
			@RequestParam(required = false) String subjectId, @RequestParam(required = false) String courseId,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		Integer cid = null;
		Integer course = null;
		try {
			if (null != subjectId && !"".equals(subjectId.trim())) {
				cid = Integer.parseInt(subjectId.trim());
			}
			if (null != courseId && !"".equals(courseId.trim())) {
				course = Integer.parseInt(courseId.trim());
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		try {
			Object data = stuCommentService.getStuCommentList(Long.parseLong(userId), Integer.parseInt(pageNo),
					Integer.parseInt(pageSize), course, cid, v);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: student get evaluations failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取对教练评价的标签
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{userId}/evaluations/tags", method = RequestMethod.GET)
	@ResponseBody
	public Object getCoachTags(@PathVariable String userId, @RequestParam(required = false) String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = orderLogic.getCommentTag(String.valueOf(ReqConstants.USER_TYPE_COACH), null, tokenId);
		} catch (Exception e) {
			log.error("controller: student get tags failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员向教练针对某个订单中课程付款
	 *
	 * @param userId
	 *            学员id
	 * @param userType
	 * @param orderId
	 *            订单id
	 * @param coachId
	 *            教练id
	 * @param money
	 *            付款金额
	 * @param payMethod
	 *            付款方式
	 * @param useCoupon
	 *            是否使用优惠券 0：否，1：是
	 * @param couponId
	 *            优惠券id
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            签名字符串
	 * @return
	 */
	@RequestMapping(value = "/{userId}/pay/{coachId}", method = RequestMethod.POST)
	@ResponseBody
	public Object doPayment(@PathVariable String userId, @RequestParam String userType, @RequestParam String orderId,
			@PathVariable String coachId, @RequestParam String money, @RequestParam String payMethod,
			@RequestParam String useCoupon, @RequestParam String couponId, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = payService.pay(orderId, Long.parseLong(couponId), Long.parseLong(userId), payMethod, tokenId);
		} catch (Exception e) {
			log.error("controller: student do pay failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取支付配置
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{userId}/pay", method = RequestMethod.GET)
	@ResponseBody
	public Object getPayConfig(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = payService.getPayConfig(userId, tokenId);
		} catch (Exception e) {
			log.error("controller: student get pay config failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取用户状态，如果是上课中，返回课程对应的订单信息
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/status", method = RequestMethod.GET)
	@ResponseBody
	public Object getStatus(@PathVariable String userId, @RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = stateManager.genStateContextDesc(userId);
			// 获取未支付订单
			Object o = orderService.getStudentWait(Long.parseLong(userId), null);
			r.setData("wait", o);
		} catch (Exception e) {
			log.error("controller: student get status failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取订单信息
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/orders", method = RequestMethod.GET)
	@ResponseBody
	public Object getOrders(@PathVariable String userId, @RequestParam String userType, @RequestParam String oStatus,
			@RequestParam String pageNo, @RequestParam String pageSize, @RequestParam String timestamp,
			@RequestParam(required = false) String orderType, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			OrderQuery oq = new OrderQuery();
			oq.setPageIndex(Integer.parseInt(pageNo));
			oq.setPageSize(Integer.parseInt(pageSize));
			if (orderType != null && !"".equals(orderType)) {
				if ("1".equals(orderType)) { // 1-待付款
					oq.setGroupBy(" and pay_state in (0,2)  and order_state not in (0,9)");
				} else if ("2".equals(orderType)) { // 2-待评价
					oq.setGroupBy(" and order_state in (3,4,5)");
				}
			}
			oq.setorderBy("order by pstart desc");
			OrderVo ov = new OrderVo();
			try {
				int oSt = Integer.parseInt(oStatus);
				ov.setOrderState(oSt);
			} catch (Exception e) {
			}
			ov.setStudentId(Long.parseLong(userId));
			List<OrderVo> list = orderService.queryByObjectAnd(ov, oq);

			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(list);
		} catch (Exception e) {
			log.error("controller: student get orders failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	@RequestMapping(value = "/{userId}/class/orders", method = RequestMethod.GET)
	@ResponseBody
	public Object getClassOrders(@PathVariable String userId, @RequestParam String userType,
			@RequestParam(required = false) String oStatus, @RequestParam String pageNo, @RequestParam String pageSize,
			@RequestParam String timestamp, @RequestParam(required = false) String orderType, @RequestParam String sign,
			@RequestHeader HttpHeaders headers, @RequestParam(required = false) String v) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			OrderQuery oq = new OrderQuery();
			oq.setPageIndex(Integer.parseInt(pageNo));
			oq.setPageSize(Integer.parseInt(pageSize));
			

			if (orderType != null && !"".equals(orderType)) {
				if ("1".equals(orderType)) { // 1-待付款 待评价 待上课
					oq.setGroupBy(
							" and ( order_state in (3,4,5) or ( pay_state in (0,2)  and order_state not in (0,9) ) or order_state=2 )");
				} else if ("2".equals(orderType)) { // 2- 已完成
					oq.setGroupBy(" and ( order_state in (0,7,9,10) or  (order_state=6  and pay_state=1))");
				}
			}
			oq.setorderBy("order by operate_time desc, pstart desc");

			OrderVo ov = new OrderVo();
			try {
				int oSt = Integer.parseInt(oStatus);
				ov.setOrderState(oSt);
			} catch (Exception e) {
			}
			ov.setStudentId(Long.parseLong(userId));
			List<OrderVo> list = orderService.queryByObjectAnd(ov, oq);
			List<StudentClass> scs = null;

			if ("1".equals(pageNo)) {// 第一页

				if ("1".equals(orderType)) {
					List<Byte> states = new ArrayList();
					states.add((byte) 0);

					ReqResult cr = studentClassService.getPageStudentsClass(userId, userType, states);
					scs = (List<StudentClass>) cr.getResult().get(ResultCode.RESULTKEY.DATA);
				} else if ("2".equals(orderType)) {
					List<Byte> states = new ArrayList();
					states.add((byte) 1);
					states.add((byte) 2);
					states.add((byte) 3);
					states.add((byte) 4);
					ReqResult cr = studentClassService.getPageStudentsClass(userId, userType, states);
					scs = (List<StudentClass>) cr.getResult().get(ResultCode.RESULTKEY.DATA);
				}
				System.out.println("***********order size:" + scs.size());
			}

			LinkedList llist=new LinkedList();
        	llist.addAll(list);
			if (scs != null)
				for (StudentClass sc : scs) {
					boolean find = false;
					for (OrderVo order : list) {
						if (sc.getOrderId().equals(order.getOrderId())) {
							find = true;
							order.setDirect(sc.getDirect());
							order.setPreOrder(0);
							break;
						}
					}
					if (!find) {// 预订单，还没成单
						if(sc.getState().intValue()==1){
							List<OrderVo> os= orderService.queryByOrderId(sc.getOrderId(), new OrderQuery());
							if(os!=null&&os.size()>0){
								//order 订单状态为未完成,过滤掉
								if(os.get(0).getOrderState()==3||os.get(0).getOrderState()==4||os.get(0).getOrderState()==5||os.get(0).getOrderState()==2||(os.get(0).getOrderState()!=0&&os.get(0).getOrderState()!=9&&(os.get(0).getPayState()==0||os.get(0).getPayState()==2))){
									continue;
								}
							}
						}
						
						OrderVo vo = new OrderVo();

						vo.setOrderId(sc.getOrderId());
						vo.setDirect(sc.getDirect());
						vo.setStudentId(sc.getStudentId());
						vo.setStuName(sc.getStuName());
						vo.setStuMobile(sc.getStuMobile());
						vo.setDltype(sc.getDltype().intValue());
						vo.setPayTotal(sc.getPrice());
						vo.setCourseName(sc.getCourseName());
						vo.setCstart(sc.getCstart());
						vo.setStuImg(sc.getStuImg());
						vo.setCourseId(sc.getCourseId() + "");
						vo.setCend(sc.getCend());
						vo.setClzNum(sc.getClznum().intValue());
						vo.setPrice(sc.getPrice());
						vo.setLge(sc.getLge());
						vo.setLae(sc.getLae());
						vo.setPreOrder(1);
						vo.setOtype(3);
						vo.setPreOrderState(sc.getState().intValue());
						vo.setOperateTime(sc.getMtime());
						//list.add(vo);
						llist.addFirst(vo);
					}
				}
			Collections.sort(llist, new Comparator<OrderVo>(){

				@Override
				public int compare(OrderVo arg0, OrderVo arg1) {
					if(arg0.getOperateTime().after(arg1.getOperateTime()))
						return -1;
					else
						return 1;
				}

				
            	
            });

			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(llist);
		} catch (Exception e) {
			log.error("controller: student get orders failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

    /**
     * 获取订单信息
     *
     * @param userId
     * @param userType
     * @param timestamp
     * @param sign
     * @return
     */
    @RequestMapping(value = "/{userId}/orders/one", method = RequestMethod.GET)
    @ResponseBody
    public Object getOrdersOne(@PathVariable String userId, @RequestParam String userType,
                               @RequestParam String orderId,
                               @RequestParam String timestamp,
                               @RequestParam String sign, @RequestHeader HttpHeaders headers) {
        ReqResult r = ReqResult.getSuccess();
        try {
            String tokenId = headers.getFirst(ReqConstants.TOKEN);
            OrderVo o = orderService.queryOrderById(orderId,new OrderQuery());
            if(o!=null){
            o.setClzNum(TimeUtil.timeSpan(o.getPstart(), o.getPend(),"hour"));
            r.setCode(ResultCode.ERRORCODE.SUCCESS);
            r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
            r.setData(o);
            }else{
            	StudentClass sc=studentClassService.getStudentClass(userId,orderId);
            	List<StudentClassPool> scps=studentClassService.getStudentClassPool(userId,orderId);
            	if(sc!=null){
            		OrderVo vo=new OrderVo();
            		vo.setOrderId(sc.getOrderId());
            		vo.setDirect(sc.getDirect());
            		vo.setStudentId(sc.getStudentId());
            		vo.setStuName(sc.getStuName());
            		vo.setStuMobile(sc.getStuMobile());
            		vo.setDltype(sc.getDltype().intValue());
            		vo.setCourseName(sc.getCourseName());
            		vo.setCstart(sc.getCstart());
            		vo.setStuImg(sc.getStuImg());
            		vo.setCourseId(sc.getCourseId()+"");
            		vo.setCend(sc.getCend());
            		vo.setClzNum(sc.getClznum().intValue());
            		vo.setPayTotal(sc.getPrice());
            		vo.setPrice(sc.getPrice());
            		vo.setLge(sc.getLge());
            		vo.setLae(sc.getLae());
            		vo.setPreOrder(1);
            		vo.setOtype(3);
            		vo.setPreOrderState(sc.getState().intValue());
            		
            		r.setData(vo);
            		r.setData("coachs", scps);
            	}else{
            		r.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
                    r.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOTEXIST);
            	}
            	
            }
        } catch (Exception e) {
            log.error("controller: student get one order failed=" + e.getMessage(), e);
            e.printStackTrace();
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }

		return r.getResult();
	}


	/**
	 * 获取钱包信息 总金额
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/wallet", method = RequestMethod.GET)
	@ResponseBody
	public Object getWallet(@PathVariable String userId, @RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestParam(required = false) String v) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentService.getWallet(userId, v);
		} catch (Exception e) {
			log.error("controller: student get wallet failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取账单详情
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/wallet/bills", method = RequestMethod.GET)
	@ResponseBody
	public Object getBills(@PathVariable String userId, @RequestParam String userType, @RequestParam String pageNo,
			@RequestParam String pageSize, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// r = studentService.getBills(userId, userType);
			r = payService.getMoneyLog(Long.parseLong(userId), Integer.parseInt(userType), Integer.parseInt(pageNo),
					Integer.parseInt(pageSize));
		} catch (Exception e) {
			log.error("controller: student get bills failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取礼券详情
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/wallet/coupons", method = RequestMethod.GET)
	@ResponseBody
	public Object getCoupons(@PathVariable String userId, @RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = studentService.getCoupons(userId, tokenId);
		} catch (Exception e) {
			log.error("controller: student get coupons failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取消息中心消息
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/messages", method = RequestMethod.GET)
	@ResponseBody
	public Object getMessages(@PathVariable String userId, @RequestParam String userType, @RequestParam String pageNo,
			@RequestParam String pageSize, @RequestParam String timestamp, @RequestParam String sign,
			@RequestParam(required = false) String time) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentService.getMessages(userId, userType, pageNo, pageSize, time);
		} catch (Exception e) {
			log.error("controller: student get messages failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取消息中心消息2.1版
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/notice", method = RequestMethod.GET)
	@ResponseBody
	public Object getNotices(@PathVariable String userId, @RequestParam String type, @RequestParam String pageNo,
			@RequestParam String pageSize, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentService.getNotices(userId, type, pageNo, pageSize);
		} catch (Exception e) {
			log.error("controller: student get getNotices failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取消息中心消息2.1版首页
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/noticeIndex", method = RequestMethod.GET)
	@ResponseBody
	public Object noticeIndex(@PathVariable String userId, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if (!userId.matches("\\d+")) {
				r.setCode(ResultCode.ERRORCODE.NEEDLOGIN);
				r.setMsgInfo(ResultCode.ERRORINFO.NEEDLOGIN);
				return r;
			}
			r = studentService.getNoticeIndex(userId);
		} catch (Exception e) {
			log.error("controller: student get getNoticeIndex failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取用户技能信息
	 *
	 * @param userId
	 * @param userType
	 * @param subjectId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{userId}/skills", method = RequestMethod.GET)
	@ResponseBody
	public Object getSkills(@PathVariable String userId, @RequestParam String userType, @RequestParam String subjectId,
			@RequestParam(required = false) String courseId, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			Integer drtype = null;
			Student s = studentManager.getStudentInfo(Long.parseLong(userId));
			// 20160317此处不再使用驾照类型进行区分；因为C1的学员也可以上C2的课程。如果没有传课程id，则认为需要区分驾照类型。
			if (null == courseId || "".equals(courseId.trim())) {
				int dr = Integer.parseInt(s.getApplyCarType());
				if (dr == ReqConstants.DRIVE_TYPE_C1 || dr == ReqConstants.DRIVE_TYPE_C2) {
					drtype = dr;
				}
			}
			r = orderLogic.getStudentScore(userId, subjectId, courseId, null, drtype);
			// 20160302 兼容导入的学员，默认有考过的科目则设置相应技能。
			if (null == courseId || "".equals(courseId.trim())) {
				int c1 = s.getCourse1() == null ? 0 : s.getCourse1();
				int c2 = s.getCourse2() == null ? 0 : s.getCourse2();
				int c3 = s.getCourse3() == null ? 0 : s.getCourse3();
				int c4 = s.getCourse4() == null ? 0 : s.getCourse4();

				int sid = ReqConstants.SUBJECT_TYPE_ONE;
				if (c3 != 0) {
					sid = ReqConstants.SUBJECT_TYPE_THREE;
				} else if (c2 != 0) {
					sid = ReqConstants.SUBJECT_TYPE_TWO;
				}

				List<StuOrderCommentVo> scvs = (List<StuOrderCommentVo>) r.getResult().get(ResultCode.RESULTKEY.DATA);
				boolean hasChanged = false;
				for (int i = 0; i < scvs.size(); i++) {
					StuOrderCommentVo scv = scvs.get(i);
					int sid2 = scv.getSubjectId();
					if (sid2 <= sid) {
						List<Integer> scores = scv.getScores();
						boolean needReset = false;
						for (int j = 0; j < scores.size(); j++) {
							if (scores.get(i) < 90) { // 实际科目已通过的考生，如果分数没有达到预考线，则重新设置分数为100分。
								needReset = true;
								break;
							}
						}
						if (needReset) {
							for (int m = 0; m < scores.size(); m++) {
								scores.set(m, 100);
							}
							scv.setScores(scores);
							scvs.set(i, scv);
							hasChanged = true;
						}
					}
				}
				if (hasChanged) {
					r.setData(scvs);
				}

			}
		} catch (Exception e) {
			log.error("controller: student get skill failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取定向预约的教练
	 *
	 * @param userId
	 * @param userType
	 * @param mark
	 *            标示 ：1 我的教练
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/coaches", method = RequestMethod.GET)
	@ResponseBody
	public Object getCoaches(@PathVariable String userId, @RequestParam String userType,
			@RequestParam(required = false) String mark, @RequestParam(required = false) String lge,
			@RequestParam(required = false) String lae, @RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers, @RequestParam(required = false) String v) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			// 20160624付费预约，只针对1.8.0及更高版本有效，只针对喱喱学员有效，喱喱学员不返回我的教练列表
			Student s = studentManager.getStudentInfo(Long.parseLong(userId));
			if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.0") < 0))) {
				if (s.getIsImport() == (byte) 0) { // 如果是喱喱学员
					return r.getResult(); // 低于1.8.0版本的喱喱学员不返回我的教练
				}
			}

			// 20160727 喱喱学员我的教练：1、约过的教练；2、特约教练；3、附近10有排班的教练
			if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.1") > 0))) {
				if (s.getIsImport() == (byte) 0) { // 如果是喱喱学员
					if (lae != null && !"".equals(lae) && lge != null && !"".equals(lge)) {
						r = studentService.getCoaches(userId, tokenId, s, Double.valueOf(lge), Double.valueOf(lae),
								mark);
						return r.getResult();
					} else {
						r.setCode(ResultCode.ERRORCODE.PARAMERROR);
						r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
						return r.getResult();
					}
				}
			}

			r = studentService.getCoaches(userId, tokenId, s, null, null, null);
		} catch (Exception e) {
			log.error("controller: student get coaches failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	public boolean isHanzi(String str) {
		if (str.getBytes().length == str.length()) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 获取预约教练
	 *
	 * @param userId
	 * @param userType
	 * @param mark
	 *            标示 ：1 我的教练
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/book/coaches")
	@ResponseBody
	public Object getBookCoaches(@PathVariable String userId, @RequestParam String userType,
			@RequestParam(required = false) String mark, @RequestParam(required = false) String lge,
			@RequestParam(required = false) String lae, @RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers, @RequestParam(required = false) String v,
			@RequestParam(required = false) String courseId, @RequestParam(required = false) String date,
			@RequestParam(required = false) String coachName, @RequestParam(required = false) String hasclass,
			@RequestParam(required = false) String cityName) {
		ReqResult r = ReqResult.getSuccess();
		String cityId=null;
		
			if (null == cityName || "".equals(cityName.trim())) {
				//cityId = "100100";// 默认深圳
			} else {
				System.out.println("cityname:" + cityName);
				Integer city = regionManager.getRidByName(cityName);
				if(city!=null)
				if (city != 0) {
					cityId = String.valueOf(city);
				} else {
					//cityId = "100100";
				}
			}
		
		
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			// 20160624付费预约，只针对1.8.0及更高版本有效，只针对喱喱学员有效，喱喱学员不返回我的教练列表
			Student s = studentManager.getStudentInfo(Long.parseLong(userId));
			if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.0") < 0))) {
				if (s.getIsImport() == (byte) 0) { // 如果是喱喱学员
					return r.getResult(); // 低于1.8.0版本的喱喱学员不返回我的教练
				}
			}
			if (coachName != null) {
				//System.out.println("coachname:" + coachName);
				//System.out.println("coachname:" + new String(coachName.getBytes("iso8859-1")));
				//System.out.println("coachname:" + new String(coachName.getBytes("utf-8")));
				if (coachName.indexOf("%") >= 0)
					coachName = java.net.URLDecoder.decode(coachName, "utf-8");
				else {
					if (!isHanzi(coachName)) {// 不是中文
						coachName = new String(coachName.getBytes("iso8859-1"));
					}
				}
				System.out.println("coachname:" + coachName);
			}
			if(coachName!=null&&coachName.trim().length()==0){
				coachName=null;
			}

			// 20160727 喱喱学员我的教练：1、约过的教练；2、特约教练；3、附近10有排班的教练
			if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.1") > 0))) {
				if (s.getIsImport() == (byte) 0) { // 如果是喱喱学员
					if (coachName != null) {
						Double dlge = null;
						Double dlae = null;
						if (lge != null && lge.length() > 0) {
							try {
								dlge = Double.valueOf(lge);
								dlae = Double.valueOf(lae);
							} catch (Exception ex) {
							}
						}
						r = studentService.getCoaches(userId, tokenId, s, dlge, dlae, mark, null, courseId, coachName,cityId);
						if ("1".equals(hasclass)&&(coachName==null||coachName.length()==0)) {
							List<MycoachesVo> list = (List<MycoachesVo>) r.getResult().get(ResultCode.RESULTKEY.DATA);
							if(list!=null)
							for (int i = list.size() - 1; i >= 0; i--) {
								if (list.get(i).getClasses() == null || list.get(i).getClasses().size() == 0) {
									list.remove(i);
								}
							}
						}
						return r.getResult();
					} else if (lae != null && !"".equals(lae) && lge != null && !"".equals(lge)) {
						r = studentService.getCoaches(userId, tokenId, s, Double.valueOf(lge), Double.valueOf(lae),
								mark, new Date(Long.parseLong(date)), courseId, coachName,cityId);
						if ("1".equals(hasclass)&&(coachName==null||coachName.length()==0)) {
							List<MycoachesVo> list = (List<MycoachesVo>) r.getResult().get(ResultCode.RESULTKEY.DATA);
							if(list!=null)
							for (int i = list.size() - 1; i >= 0; i--) {
								if (list.get(i).getClasses() == null || list.get(i).getClasses().size() == 0) {
									list.remove(i);
								}
							}
						}
						return r.getResult();
					} else {
						r.setCode(ResultCode.ERRORCODE.PARAMERROR);
						r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
						return r.getResult();
					}
				}
			}
			Double dlge = null;
			Double dlae = null;
			if (lge != null && lge.length() > 0) {
				try {
					dlge = Double.valueOf(lge);
					dlae = Double.valueOf(lae);
				} catch (Exception ex) {
				}
			}
			Date cdate = null;
			if (date != null) {
				try {
					cdate = new Date(Long.parseLong(date));
				} catch (Exception ex) {
				}
			}
			// r = studentService.getCoaches(userId, tokenId, s, null, null,
			// null);
			//驾校学员
			r = studentService.getCoaches(userId, tokenId, s, dlge, dlae, mark, cdate, courseId, coachName,cityId);
			//返回的教练排班数据
			List<MycoachesVo> list=(List<MycoachesVo>)r.getResult().get(ResultCode.RESULTKEY.DATA);
			//我绑定的教练
			List<Coach> cs = coachManager.getCoachesByStudentId(Long.parseLong(userId),s.getIsImport());
			if(list!=null){
				for(int i=list.size()-1;i>=0;i--){
					for(Coach c:cs){
						if(list.get(i).getCoachId().longValue()==c.getCoachId().longValue()){//我绑定的教练
							if(list.get(i).getClasses()!=null&&list.get(i).getClasses().size()>0){//设置BM班价格
								fillBMClass(s,list.get(i).getClasses(),c.getCoachId().intValue());
								break;
							}
						}
						//不是绑定的教练价格不用再处理,原价
					}
				}
				if ("1".equals(hasclass)&&(coachName==null||coachName.length()==0)) {
					for (int i = list.size() - 1; i >= 0; i--) {
						if (list.get(i).getClasses() == null || list.get(i).getClasses().size() == 0) {
							list.remove(i);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("controller: student get coaches failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	private void fillBMClass(Student s,List<CoachClassVo> ccvs,int coachId){
		try{
    	if(s.getPackageId()!=null && s.getPackageId()!=0 && ccvs.size()>0){
			System.out.println("-------------------------set package price ");
			int ttid=s.getPackageId();
			ReqResult reqResult=schoolService.getPackageById(String.valueOf(ttid));
            WechatEnrollPackage enrollPackage=(WechatEnrollPackage)reqResult.getResult().get("data");
            if(enrollPackage.getCstate()==2){ // cstate=2 bm班 班别审核通过
            	List<OrderVo> list = orderService.searchBmClass(s.getStudentId().intValue(),coachId);
				int userdHours=0;
				for(int i=0;i<list.size();i++){
					OrderVo vo=list.get(i);
					Date pstart=vo.getPstart();
					Date pend=vo.getPend();
					long courseHours = ((pend.getTime() - pstart.getTime()) / 1000) % (24 * 3600) / 3600;
					int maxNum=vo.getClzNum();
					userdHours+=courseHours/maxNum;
					//userdHours+=courseHours;
				}
				
				int hours = enrollPackage.getHours();
				int price = enrollPackage.getHoursPrice();
				int bmHour=s.getBmHour();   
				int left = hours - userdHours-bmHour;	
            		
	            for(int i=0;i<ccvs.size();i++){
	            	CoachClassVo classVo=ccvs.get(i);
	            	Date cstart=classVo.getCstart();
	            	Date cend=classVo.getCend();
	            	long num=((cend.getTime()-cstart.getTime())/1000)%(24*3600)/3600;//鎺掔彮璇炬椂
	            	System.out.println("-------------------------price left:"+left+" "+num);
	            	if(left<=0 || left<num){
	            		if(left>0 && left<num){
	            			classVo.setPrice((int) (price*(num-left)));
	            		}else{
	            			classVo.setPrice((int) (price*num));
	            		}
	            			
	            	}else{//还有剩免费课时
	            		classVo.setPrice(0);
	            	}
	            	System.out.println("-------------------------price "+classVo.getPrice());
	            }
            }else{//不是bm班, 绑定教练是0元
            	for(int i=0;i<ccvs.size();i++){
            		CoachClassVo classVo=ccvs.get(i);
            		classVo.setPrice(0);
            	}
            }
           
		}else{
			//不是BM班,绑定教练是0元
			for(int i=0;i<ccvs.size();i++){
        		CoachClassVo classVo=ccvs.get(i);
        		classVo.setPrice(0);
        	}
		}
		}catch(Exception e){
			log.error("controller: order post order failed=" + e.getMessage(), e);
		}
    }

	/**
	 * 获取指定教练信息
	 *
	 * @param userId
	 * @param userType
	 * @param coachId
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/{userId}/coaches/{coachId}", method = RequestMethod.GET)
	@ResponseBody
	public Object getCoachesOne(@PathVariable String userId, @RequestParam(required = false) String userType,
			@PathVariable String coachId, @RequestParam String timestamp, @RequestParam String sign,
			@RequestHeader HttpHeaders headers) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = studentService.getCoachesOne(userId, coachId, tokenId);

		} catch (Exception e) {
			log.error("controller: student get one coach failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取排班表
	 *
	 * @param userId
	 * @param date
	 *            指定日期格式yyyy-MM-dd
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/arrangements", method = RequestMethod.GET)
	@ResponseBody
	public Object getArrangements(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String coachId, @RequestParam String date, @RequestParam String timestamp,
			@RequestParam(required = false) String course, @RequestParam String sign,
			@RequestHeader HttpHeaders headers, @RequestParam(required = false) String v) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// 判断学员是否是驾校学员
			Student s = studentManager.getStudentInfo(Long.parseLong(userId));

			List<CoachClassVo> ccvs = null;
			Coach coach = null;
			MycoachesVo mycoachesVo = null;
			boolean mybind=false;
			List<Coach> cs = coachManager.getCoachesByStudentId(Long.parseLong(userId), s.getIsImport()); // 我的教练
			if (cs.size() > 0) {
				if (!"".equals(coachId) && coachId != null) { // 喱喱学员查教练信息
					coach = coachManager.getCoachInfo(Long.parseLong(coachId));
					mycoachesVo = BeanCopy.copyAll(coach, MycoachesVo.class);
				} else { // 驾校学员查教练信息
					coach = coachManager.getCoachInfo(cs.get(0).getCoachId());
					mycoachesVo = BeanCopy.copyAll(coach, MycoachesVo.class);
				}
				
				if (s.getIsImport() == (byte) 1) {
					for(Coach cc:cs){
						if(cc.getCoachId().longValue()==coach.getCoachId().longValue()){//看的教练是我绑定的教练
							mybind=true;
							break;
						}
					}
				}
			}else{
				if (s.getIsImport() == (byte) 1) {
					coach = coachManager.getCoachInfo(Long.parseLong(coachId));
				}
			}

			// 20160803 1.8.2新增推送教练，跟学员没有绑定关系
			if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.1") > 0))) {
				if (s.getIsImport() == (byte) 0 && mycoachesVo == null) {
					coach = coachManager.getCoachInfo(Long.parseLong(coachId));
					mycoachesVo = BeanCopy.copyAll(coach, MycoachesVo.class);
				}
			}

			if (s.getIsImport() == (byte) 1) { // 如果是驾校学员
				if (coach != null) {
					//获取教练所有排班，不管是否有价格，返回的数据price是为null的
					if(mybind)
					ccvs = coachClassService.queryByCoachDateAllWithNoPrice(new Date(Long.parseLong(date)), coach.getCoachId(),
							OrderConstant.ISDEL.NOTDELETE, OrderConstant.OTYPE.BOOKORDER, 1, 100);
					else
						ccvs = coachClassService.queryByCoachDateLiliAll(new Date(Long.parseLong(date)), coach.getCoachId(),
								OrderConstant.ISDEL.NOTDELETE, OrderConstant.OTYPE.BOOKORDER, 1, 100,v,s);
					
					if(VersionUtil.compareVersion(v, "2.1.0") >= 0&&mybind){
						// 20160929已关联班别的驾校学员学员超过限定课时设置价格
						if (s.getPackageId() != null && s.getPackageId() != 0 && ccvs.size() > 0) {
							int ttid = s.getPackageId();
							ReqResult reqResult = schoolService.getPackageById(String.valueOf(ttid));
							WechatEnrollPackage enrollPackage = (WechatEnrollPackage) reqResult.getResult().get("data");
							if (enrollPackage.getCstate() == 2) { // cstate=2班别审核通过
								
								List<OrderVo> list = orderService.searchBmClass(Integer.parseInt(userId),Integer.parseInt(coachId));
								int userdHours=0;
								for(int i=0;i<list.size();i++){
									OrderVo vo=list.get(i);
									Date pstart=vo.getPstart();
									Date pend=vo.getPend();
									long courseHours = ((pend.getTime() - pstart.getTime()) / 1000) % (24 * 3600) / 3600;
									int maxNum=vo.getClzNum();   //总人数
									userdHours+=courseHours/maxNum;
								}
								int bmHour=s.getBmHour();  //原来未统计到的已使用课时
								int hours = enrollPackage.getHours();   
								int price = enrollPackage.getHoursPrice();
								int left = hours - userdHours-bmHour;
								for (int i = 0; i < ccvs.size(); i++) {
									CoachClassVo classVo = ccvs.get(i);
									Date cstart = classVo.getCstart();
									Date cend = classVo.getCend();
									long TimeNum = ((cend.getTime() - cstart.getTime()) / 1000) % (24 * 3600) / 3600;
									long num=TimeNum/classVo.getMaxNum();
									if (left <= 0 || left < num) {
										if (left > 0 && left < num) {
											classVo.setPrice((int) (price * (num - left)));
										} else {
											classVo.setPrice((int) (price * num));
										}
	
									}
								}
							}
	
						}
					}
					
					// 1.8.1 新增过滤课程
					if ((null != v && !"".equals(v) && (VersionUtil.compareVersion(v, "1.8.0") > 0))) {
						int c2 = s.getCourse2() == null ? 0 : s.getCourse2();
						int c3 = s.getCourse3() == null ? 0 : s.getCourse3();
						List<CoachClassVo> ccvs2 = new ArrayList<CoachClassVo>();
						if(!(VersionUtil.compareVersion(v, "2.1.0") >= 0)){
							 if(course != null && !"".equals(course)) {
							 if (ccvs != null && c2 >= 90 && c3 < 90) { //返回对应课程
							 if("3".equals(course)){
							 ccvs2 = filterCourse(ccvs, course);
							 ccvs = ccvs2;
							 }
							 if("2".equals(course)){
							 ccvs = ccvs2;
							 }
							 }
							 else if (ccvs != null && c3 >= 90) {
							 ccvs = ccvs2; //返回空
							 }
							 else {//根据课程返回所有
							 ccvs2 = filterCourse(ccvs, course);
							 ccvs = ccvs2;
							 }
							 }
							 else {
							 if (ccvs != null && c2 >= 90 && c3 < 90) { //返回对应课程
							 ccvs2 = filterCourse(ccvs, "3");
							 ccvs = ccvs2;
							 }
							 else if (ccvs != null && c3 >= 90) { //返回对应课程
							 ccvs = ccvs2;
							 }
							 }
						}
					}
				} else {
					r.setCode(ResultCode.ERRORCODE.EXCEPTION);
					r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
					return r.getResult();
				}
			} else { // 如果是喱喱学员
				
				ccvs = coachClassService.queryByCoachDateLiliAll(new Date(Long.parseLong(date)),
						Long.parseLong(coachId), OrderConstant.ISDEL.NOTDELETE, OrderConstant.OTYPE.BOOKORDER, 1, 100,
						v, s);
			}

			//for (int i = 0; i < ccvs.size(); i++) {
			for (int i = ccvs.size()-1; i >= 0; i--) {
				CoachClassVo ccv = ccvs.get(i);
				//Date a = (Date) ccv.getCend();
				Date a = (Date) ccv.getCstart();
				Date b = new Date(System.currentTimeMillis());
				// 如果当前时间超过了排班时间表的结束时间，则认为这个时间表已过期，不能再操作了
				if (b.after(a)) {
					//ccv.setOperate(1);
					//ccvs.set(i, ccv);
					ccvs.remove(i);
				}
			}
			
			
			
			// 20160309兼容之前的版本，原版本学员端查看排班时，无法看到教练排班中新增的排班
			if (null == v || "".equals(v)) {
				List<CoachClassVo> ccvsnew = new ArrayList<CoachClassVo>();
				for (int i = 0; i < ccvs.size(); i++) {
					Integer courseid = null;
					try {
						courseid = Integer.parseInt(ccvs.get(i).getCourseId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (courseid == ReqConstants.COURSE_TYPE_C1_TWO_BASIC
							|| courseid == ReqConstants.COURSE_TYPE_C1_TWO_EXAM_SIMULATION
							|| courseid == ReqConstants.COURSE_TYPE_C1_THREE_BASIC
							|| courseid == ReqConstants.COURSE_TYPE_C1_THREE_EXAM_SIMULATION
							|| courseid == ReqConstants.COURSE_TYPE_C1_PEIJIA) {
						ccvsnew.add(ccvs.get(i));
					}
				}
				r.setCode(ResultCode.ERRORCODE.SUCCESS);
				r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
				r.setData(ccvsnew);
				r.setData("mycoachesVo", mycoachesVo);
			} else {
				r.setCode(ResultCode.ERRORCODE.SUCCESS);
				r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
				r.setData(ccvs);
				r.setData("mycoachesVo", mycoachesVo);
			}
		} catch (Exception e) {
			log.error("controller: student get arrangement failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 通用过滤科目： 1、如果用查询来区分科目，in的效率比较慢，教练的排班不会太多，用普通过滤比in快 2、由于queryByCoachDateAll
	 * 学员查教练排班与教练查询排班同一个接口，故在结果集里过滤
	 * 
	 * @param ccvs
	 * @return
	 */
	private List<CoachClassVo> filterCourse(List<CoachClassVo> ccvs, String course) {
		List<CoachClassVo> ccv = new ArrayList<CoachClassVo>();
		try {
			int courseInt;
			for (CoachClassVo coachClassVo : ccvs) {
				courseInt = Integer.parseInt(coachClassVo.getCourseId().trim());
				if ("2".equals(course)) { // 返回科目二
					if (courseInt == ReqConstants.COURSE_TYPE_C1_TWO_BASIC
							|| courseInt == ReqConstants.COURSE_TYPE_C1_TWO_EXAM_SIMULATION
							|| courseInt == ReqConstants.COURSE_TYPE_C1_TWO_EXAM_TRAINING
							|| courseInt == ReqConstants.COURSE_TYPE_C2_TWO_BASIC
							|| courseInt == ReqConstants.COURSE_TYPE_C2_TWO_EXAM_SIMULATION
							|| courseInt == ReqConstants.COURSE_TYPE_C2_TWO_EXAM_TRAINING) {
						ccv.add(coachClassVo);
					}
				} else if ("3".equals(course)) { // 返回科目三
					if (courseInt == ReqConstants.COURSE_TYPE_C1_THREE_BASIC
							|| courseInt == ReqConstants.COURSE_TYPE_C1_THREE_EXAM_SIMULATION
							|| courseInt == ReqConstants.COURSE_TYPE_C1_THREE_EXAM_TRAINING
							|| courseInt == ReqConstants.COURSE_TYPE_C2_THREE_BASIC
							|| courseInt == ReqConstants.COURSE_TYPE_C2_THREE_EXAM_SIMULATION
							|| courseInt == ReqConstants.COURSE_TYPE_C2_THREE_EXAM_TRAINING) {
						ccv.add(coachClassVo);
					}
				} else if ("5".equals(course)) { // 返回陪驾
					if (courseInt == ReqConstants.COURSE_TYPE_C1_PEIJIA
							|| courseInt == ReqConstants.COURSE_TYPE_C2_PEIJIA) {
						ccv.add(coachClassVo);
					}
				}
			}
		} catch (Exception e) {
			log.error("******************************** filterCourser Error: " + e.getMessage());
			e.printStackTrace();
		}
		return ccv;
	}

	/**
	 * 获取五天内排班表基本情况
	 *
	 * @param userId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/arrangeInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getArrangeInfo(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String coachId, @RequestParam String timestamp, @RequestParam String sign,
			@RequestParam(required = false) String v) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// 目前学员未考试预约，默认只返回三天数据。后面走到考试预约后，再根据条件调整天数。
			// 20160216根据产品要求先默认5天。
			int days = 5;
			/**
			 * date为日期 arrange为0则未开放 student如果不为0则为有约 arrange不为0且studnet为0则开放未约
			 */
			// 判断学员是否是驾校学员
			Student s = studentManager.getStudentInfo(Long.parseLong(userId));
			List<Map<String, Long>> date = null;
			if (s.getIsImport() == (byte) 1) {
				if(VersionUtil.compareVersion(v, "2.1.0") >= 0){
					date = coachClassService.getCoach5Date(Long.parseLong(coachId), Long.parseLong(userId), days);
				}else{
					List<Coach> cs = coachManager.getCoachesByStudentId(Long.parseLong(userId), s.getIsImport());
					if (cs.size() > 0) {
						date = coachClassService.getCoach5Date(cs.get(0).getCoachId(), Long.parseLong(userId), days);
					}
				}
			} else {
				date = coachClassService.getCoach5Date(Long.parseLong(coachId), Long.parseLong(userId), days);
			}
			r.setData(date);
		} catch (Exception e) {
			log.error("controller: student get arrangeInfo failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员获取联系客服的原因
	 *
	 * @return
	 */
	@RequestMapping(value = "/{userId}/service/reason", method = RequestMethod.GET)
	@ResponseBody
	public Object getServiceReason(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// t_cancel_reason 4代表学员联系客服的原因
			Object o = cancelReasonService.queryByUtype(4, new CancelReasonQuery());
			r.setData(o);
		} catch (Exception e) {
			log.error("controller: coach get reason failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员提交联系客服
	 *
	 * @return
	 */
	@RequestMapping(value = "/{userId}/service", method = RequestMethod.POST)
	@ResponseBody
	public Object contactService(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String orderId, @RequestParam String rId, @RequestParam String reason,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			// t_cancel_reason 4代表学员联系客服的原因
			LogComplain lc = new LogComplain();
			lc.setUserId(Long.parseLong(userId));
			lc.setUserType(Integer.parseInt(userType));
			lc.setOrderId(orderId);
			lc.setDescription(reason);
			logService.logCompain(lc);

		} catch (Exception e) {
			log.error("controller: coach post service failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员可以点下课
	 *
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param coachId
	 * @param status
	 *            0-下课
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/course/status", method = RequestMethod.POST)
	@ResponseBody
	public Object doCourseStatus(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String orderId, @RequestParam String coachId, @RequestParam String status,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = stateManager.handleDoCourseStatus(userId, orderId, coachId, status);
			log.debug("userId=" + userId + ",status=" + status + ",orderId=" + orderId + " with student result="
					+ r.getResult().get(ResultCode.RESULTKEY.CODE));
			if (r.getResult().get(ResultCode.RESULTKEY.CODE).equals(ResultCode.ERRORCODE.SUCCESS)) {
				ReqResult rc = coachStateManager.handleDoCourseStatus(coachId, orderId, userId, status, "");
				log.debug("userId=" + userId + ",status=" + status + ",orderId=" + orderId + " with coach result="
						+ rc.getResult().get(ResultCode.RESULTKEY.CODE));
			} else {
				log.error("userId=" + userId + ",status=" + status + ",orderId=" + orderId + " with student result="
						+ r.getResult().get(ResultCode.RESULTKEY.CODE) + ", so do NOT modify coachState.");
			}
		} catch (Exception e) {
			log.error("userId=" + userId + ",status=" + status + ",orderId=" + orderId
					+ " controller: student do course status failed=" + e.getMessage(), e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	@RequestMapping(value = "/{userId}/openId", method = RequestMethod.POST)
	@ResponseBody
	public Object postOpenId(@PathVariable String userId, @RequestParam String userType, @RequestParam String code,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentService.postOpenId(userId, userType, code);
		} catch (Exception e) {
			log.error("controller: student postOpenId failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取Vip的基本信息
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/vip", method = RequestMethod.GET)
	@ResponseBody
	public Object getVip(@PathVariable String userId, @RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentVipService.getStudentVipByStudentId(Long.parseLong(userId));
		} catch (Exception e) {
			log.error("controller: student getVip failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取大客户充值优惠的列表信息
	 *
	 * @param userId
	 *            用户id
	 * @param userType
	 *            用户类型
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getVipChargeDiscountList", method = RequestMethod.GET)
	@ResponseBody
	public Object getVipChargeDiscountList(@PathVariable String userId, @RequestParam String userType) {
		ReqResult r = new ReqResult();
		try {
			long StudentIdNum = Long.parseLong(userId);
			r = studentVipService.getChargeDiscountListStudentId(StudentIdNum);
		} catch (Exception e) {
			log.error("controller: get vip chargeDiscount list failed. userId:{},userType:{}", String.valueOf(userId),
					String.valueOf(userType), e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 大客户学员的添加
	 *
	 * @param mobile
	 * @param codeInput
	 * @param vipId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/addVipStudent", method = RequestMethod.POST)
	@ResponseBody
	public Object addVipStudent(@RequestParam String mobile, @RequestParam String name, @RequestParam String codeInput,
			@RequestParam String vipId, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			Integer vipIdNums = Integer.parseInt(vipId);
			r = studentVipService.addVipStudent(mobile, name, codeInput, vipIdNums);

		} catch (Exception e) {
			log.error("controller: add Vip Student failed!", e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 更新大客户学员的优惠套餐
	 *
	 * @param mobile
	 * @param vipPackageId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/updateVipStudentPackage", method = RequestMethod.POST)
	@ResponseBody
	public Object updateVipStudentPackage(@RequestParam String mobile, @RequestParam String vipPackageId,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		try {
			if (StringUtil.isNullOrEmpty(mobile) || StringUtil.isNullOrEmpty(vipPackageId)) {
				r = ReqResult.getParamError();
				return r;
			}
			r = studentVipService.updateVipStudentVipPackageId(mobile, vipPackageId);
		} catch (Exception e) {
			log.error("controller: update VipStudent Package failed. mobile:{}, vipPackageId:{}", mobile, vipPackageId,
					e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员提交我的错题
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/{userId}/submitmyerrques", method = RequestMethod.POST)
	@ResponseBody
	public Object submitmyerrques(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestParam String v,
			@RequestParam String list) {
		ReqResult r = ReqResult.getFailed();
		try {
			List<ExerciseError> datas = JSON.parseObject(list, new TypeReference<List<ExerciseError>>() {
			});
			r = studentExerciseService.submitmyerrques(v, Long.parseLong(userId), datas);
		} catch (Exception e) {
			log.error("controller: submitmyerrques fail. userId:{}, userType:{}, v:{}, list:{}", userId, userType, v,
					list, e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员移除某个错题
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @param qid
	 * @return
	 */
	@RequestMapping(value = "/{userId}/removemyerrorques", method = RequestMethod.POST)
	@ResponseBody
	public Object removemyerrorques(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestParam String v,
			@RequestParam String qid) {
		ReqResult r = ReqResult.getFailed();
		try {
			r = studentExerciseService.removemyerrorques(v, Long.parseLong(userId), qid);
		} catch (Exception e) {
			log.error("controller: removemyerrorques fail. userId:{}, userType:{}, v:{}, qid", userId, userType, v, qid,
					e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员获取我的错题
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getmyerrques", method = RequestMethod.POST)
	@ResponseBody
	public Object getmyerrques(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestParam String v) {
		ReqResult r = ReqResult.getFailed();
		try {
			r = studentExerciseService.getmyerrques(v, Long.parseLong(userId));
		} catch (Exception e) {
			log.error("controller: getmyerrques fail. userId:{}, userType:{}, v:{}", userId, userType, v, e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员提交我的收藏
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @param subject
	 * @param qid
	 * @param chapter
	 * @param ansSta
	 * @return
	 */
	@RequestMapping(value = "/{userId}/submitmycollection", method = RequestMethod.POST)
	@ResponseBody
	public Object submitmycollection(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestParam String v,
			@RequestParam String subject, @RequestParam String qid, @RequestParam String chapter,
			@RequestParam String ansSta) {
		ReqResult r = ReqResult.getFailed();
		try {
			r = studentExerciseService.submitmycollection(v, Long.parseLong(userId), qid, Integer.parseInt(subject),
					Integer.parseInt(chapter), Integer.parseInt(ansSta));
		} catch (Exception e) {
			log.error(
					"controller: submitmycollection fail. userId:{}, userType:{}, v:{}, subject:{}, qid:{}, chapter:{}, ansSta:{}",
					userId, userType, v, subject, qid, chapter, ansSta, e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员取消收藏
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @param qid
	 * @return
	 */
	@RequestMapping(value = "/{userId}/cancelmycollection", method = RequestMethod.POST)
	@ResponseBody
	public Object cancelmycollection(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestParam String v,
			@RequestParam String qid) {
		ReqResult r = ReqResult.getFailed();
		try {
			r = studentExerciseService.cancelmycollection(v, Long.parseLong(userId), qid);
		} catch (Exception e) {
			log.error("controller: cancelmycollection fail. userId:{}, userType:{}, v:{}, qid:{}", userId, userType, v,
					qid, e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员获取我的收藏
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getmycollection", method = RequestMethod.POST)
	@ResponseBody
	public Object getmycollection(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestParam String v) {
		ReqResult r = ReqResult.getFailed();
		try {
			r = studentExerciseService.getmycollection(v, Long.parseLong(userId));
		} catch (Exception e) {
			log.error("controller: getmycollection fail. userId:{}, userType:{}, v:{}", userId, userType, v, e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员获取我的模拟考试记录
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/{userId}/submitmockexamrecord", method = RequestMethod.POST)
	@ResponseBody
	public Object submitmockexamrecord(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestParam String v,
			@RequestParam String list) {
		ReqResult r = ReqResult.getFailed();
		try {
			List<ExerciseExam> datas = JSON.parseObject(list, new TypeReference<List<ExerciseExam>>() {
			});
			r = studentExerciseService.submitmockexamrecord(v, Long.parseLong(userId), datas);
		} catch (Exception e) {
			log.error("controller: submitmockexamrecord fail. userId:{}, userType:{}, v:{}, list:{}", userId, userType,
					v, list, e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员获取我的模拟考试记录
	 *
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @param subject
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getmockexamrecord", method = RequestMethod.POST)
	@ResponseBody
	public Object getmockexamrecord(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign, @RequestParam String v,
			@RequestParam String subject, @RequestParam String pageNo, @RequestParam String pageSize) {
		ReqResult r = ReqResult.getFailed();
		try {
			r = studentExerciseService.getmockexamrecord(v, Long.parseLong(userId), Integer.parseInt(subject),
					Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		} catch (Exception e) {
			log.error(
					"controller: getmockexamrecord fail. userId:{}, userType:{}, v:{}, subject:{}, pageNo:{}, pageSize:{}",
					userId, userType, v, subject, pageNo, pageSize, e);
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 微课
	 * 
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param v
	 * @param fileType
	 * @param levelOne
	 * @param levelTwo
	 * @param twoName
	 * @param levelThree
	 * @param threeName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/{userId}/getMicroClass", method = RequestMethod.GET)
	@ResponseBody
	public Object getMicroClass(@RequestParam(required = false) String userId,
			@RequestParam(required = false) String userType, @RequestParam String timestamp, @RequestParam String sign,
			@RequestParam(required = false) String v, @RequestParam(required = false) String fileType,
			@RequestParam(required = false) String levelOne, @RequestParam(required = false) String levelTwo,
			@RequestParam(required = false) String twoName, @RequestParam(required = false) String levelThree,
			@RequestParam(required = false) String threeName, @RequestParam(required = false) String pageNo,
			@RequestParam(required = false) String pageSize) {
		ReqResult r = new ReqResult();
		try {
			MicroClass mocroClass = new MicroClass();
			if (fileType != null && !"".equals(fileType)) {
				mocroClass.setFileType(Integer.parseInt(fileType));
			}
			if (levelOne != null && !"".equals(levelOne)) {
				mocroClass.setLevelOne(Integer.parseInt(levelOne));
			}
			if (levelTwo != null && !"".equals(levelTwo)) {
				mocroClass.setLevelTwo(Integer.parseInt(levelTwo));
			}
			if (twoName != null && !"".equals(twoName)) {
				mocroClass.setTwoName(twoName);
			}
			if (levelThree != null && !"".equals(levelThree)) {
				mocroClass.setLevelThree(levelThree);
			}
			r = studentService.getMicroClass(mocroClass);
		} catch (Exception e) {
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			log.error("*************************************** getMicroClass Error : " + e.getMessage());
			e.printStackTrace();
		}

		return r.getResult();
	}

	/**
	 * 学员获取自主预约价格
	 * 
	 * @param userId
	 * @param userType
	 * @param courseId
	 * @param cStart
	 * @param clznum
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/class/price", method = RequestMethod.GET)
	@ResponseBody
	public Object getClassPrice(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String courseId, @RequestParam String cStart, @RequestParam(required = false) String clznum,
			@RequestParam(required = false) String cityId, @RequestParam(required = false) String cityName,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if (null == cityId || "".equals(cityId.trim())) {
				if (null == cityName || "".equals(cityName.trim())) {
					cityId = "100100";// 默认深圳
				} else {
					String cname = new String(cityName.getBytes("iso8859-1"), "UTF-8"); // tomcat对get请求编码不同
					int city = regionManager.getRidByName(cname);
					if (city != 0) {
						cityId = String.valueOf(city);
					} else {
						cityId = "100100";
					}
				}
			}
			r = studentClassService.getClassPrice(userId, userType, courseId, cStart, clznum, cityId);

		} catch (Exception e) {
			log.error("controller: student getClassPrice failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	@RequestMapping(value = "/{userId}/class/price", method = RequestMethod.POST)
	@ResponseBody
	public Object getClassPrice2(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String courseId, @RequestParam String cStart, @RequestParam(required = false) String clznum,
			@RequestParam(required = false) String cityId, @RequestParam(required = false) String cityName,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if (null == cityId || "".equals(cityId.trim())) {
				if (null == cityName || "".equals(cityName.trim())) {
					cityId = "100100";// 默认深圳
				} else {
					int city = regionManager.getRidByName(cityName);
					if (city != 0) {
						cityId = String.valueOf(city);
					} else {
						cityId = "100100";
					}
				}
			}
			r = studentClassService.getClassPrice(userId, userType, courseId, cStart, clznum, cityId);

		} catch (Exception e) {
			log.error("controller: student getClassPrice failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员获取自主预约排班基本信息
	 * 
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/class", method = RequestMethod.GET)
	@ResponseBody
	public Object getStudentClass(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentClassService.getMyClass(userId, userType);

		} catch (Exception e) {
			log.error("controller: student getClassPrice failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员设置自主预约排班
	 * 
	 * @param userId
	 * @param userType
	 * @param dltype
	 * @param courseId
	 * @param cStart
	 * @param cEnd
	 * @param clznum
	 * @param placeInfo
	 * @param lge
	 * @param lae
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/class", method = RequestMethod.POST)
	@ResponseBody
	public Object setClass(@PathVariable String userId, @RequestParam String userType, @RequestParam String dltype,
			@RequestParam String courseId, @RequestParam String cStart, // @RequestParam
																		// String
																		// cEnd,
			@RequestParam String clznum, @RequestParam String placeInfo, @RequestParam String lge,
			@RequestParam String lae, @RequestParam(required = false) String cityId,
			@RequestParam(required = false) String cityName, @RequestParam String timestamp,@RequestParam(required = false) String v,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if (null == cityId || "".equals(cityId.trim())) {
				if (null == cityName || "".equals(cityName.trim())) {
					cityId = "100100";// 默认深圳
				} else {
					int city = regionManager.getRidByName(cityName);
					if (city != 0) {
						cityId = String.valueOf(city);
					} else {
						cityId = "100100";
					}
				}
			}
			Student student = studentManager.getStudentInfo(Long.parseLong(userId.trim()));
			if (null == student) {
				return ReqResult.getParamError().getResult();
			}
			StudentClass studentClass = new StudentClass();
			studentClass.setStudentId(student.getStudentId());
			studentClass.setStuName(student.getName());
			studentClass.setStuMobile(student.getPhoneNum());
			studentClass.setStuImg(student.getHeadIcon());
			studentClass.setDltype(Byte.parseByte(dltype.trim()));
			studentClass.setCourseId(Integer.parseInt(courseId.trim()));// 因为科目是分C1C2的，所以指定了科目后，准驾类型不需要再传也可以。
			studentClass.setCtime(new Date());
			studentClass.setCstart(new Date(Long.parseLong(cStart) * 1000));
			// studentClass.setCend(new Date(Long.parseLong(cEnd) * 1000));
			studentClass.setClznum(Byte.parseByte(clznum));
			studentClass.setLge(Double.parseDouble(lge.trim()));
			studentClass.setLae(Double.parseDouble(lae.trim()));
			studentClass.setPlaceInfo(placeInfo.trim());
			if (null != cityId && !"".equals(cityId.trim())) {
				studentClass.setRegionId(Integer.parseInt(cityId.trim()));
			} else {
				studentClass.setRegionId(100100);// 默认深圳
			}
			r = studentClassService.addStudentClass(studentClass,v);

		} catch (Exception e) {
			log.error("controller: student post arrangement failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员定向预约
	 * 
	 * @param userId
	 * @param userType
	 * @param dltype
	 * @param courseId
	 * @param cStart
	 * @param cEnd
	 * @param clznum
	 * @param placeInfo
	 * @param lge
	 * @param lae
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/target/class", method = RequestMethod.POST)
	@ResponseBody
	public Object targetClass(@PathVariable String userId, @RequestParam String userType, @RequestParam String dltype,
			@RequestParam String courseId, @RequestParam(required = true) String coachId, @RequestParam String cStart, // @RequestParam
																														// String
																														// cEnd,
			@RequestParam String clznum, @RequestParam String placeInfo, @RequestParam String lge,
			@RequestParam String lae, @RequestParam(required = false) String cityId,
			@RequestParam(required = false) String cityName, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			if (null == cityId || "".equals(cityId.trim())) {
				if (null == cityName || "".equals(cityName.trim())) {
					cityId = "100100";// 默认深圳
				} else {
					int city = regionManager.getRidByName(cityName);
					if (city != 0) {
						cityId = String.valueOf(city);
					} else {
						cityId = "100100";
					}
				}
			}
			Student student = studentManager.getStudentInfo(Long.parseLong(userId.trim()));
			if (null == student) {
				return ReqResult.getParamError().getResult();
			}
			StudentClass studentClass = new StudentClass();
			studentClass.setStudentId(student.getStudentId());
			studentClass.setStuName(student.getName());
			studentClass.setStuMobile(student.getPhoneNum());
			studentClass.setStuImg(student.getHeadIcon());
			studentClass.setDltype(Byte.parseByte(dltype.trim()));
			studentClass.setCourseId(Integer.parseInt(courseId.trim()));// 因为科目是分C1C2的，所以指定了科目后，准驾类型不需要再传也可以。
			studentClass.setCtime(new Date());
			studentClass.setCstart(new Date(Long.parseLong(cStart)));
			// studentClass.setCend(new Date(Long.parseLong(cEnd) * 1000));
			studentClass.setClznum(Byte.parseByte(clznum));
			studentClass.setLge(Double.parseDouble(lge.trim()));
			studentClass.setLae(Double.parseDouble(lae.trim()));
			studentClass.setPlaceInfo(placeInfo.trim());
			studentClass.setDirect(1);
			if (null != cityId && !"".equals(cityId.trim())) {
				studentClass.setRegionId(Integer.parseInt(cityId.trim()));
			} else {
				studentClass.setRegionId(100100);// 默认深圳
			}
			r = studentClassService.addStudentClass(studentClass, Long.parseLong(coachId));

		} catch (Exception e) {
			log.error("controller: student post arrangement failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	@RequestMapping(value = "/{userId}/all/class", method = RequestMethod.GET)
	@ResponseBody
	public Object getStudentAllClass(@PathVariable String userId, @RequestParam String userType,@RequestParam(required = false) String both,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			boolean b=false;
			if("1".equals(both))b=true;
			r = studentClassService.getMyClassNew(userId, userType,b);

		} catch (Exception e) {
			log.error("controller: student getClassPrice failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 获取推荐的班次
	 * 
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/class/recommend", method = RequestMethod.GET)
	@ResponseBody
	public Object getClassRecommend(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentClassService.getClassRecommend(userId);

		} catch (Exception e) {
			log.error("controller: student getClassRecommend failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员获取已接单教练
	 * 
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/class/coaches", method = RequestMethod.GET)
	@ResponseBody
	public Object getClassCoaches(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String orderId, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentClassService.getClassCoaches(userId, userType, orderId);

		} catch (Exception e) {
			log.error("controller: student getClassCoaches failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 学员设置自主排班状态
	 * 
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param coachId
	 * @param state
	 *            1-已成功；2-已取消；//成功预约的时候coachId不能为空
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/{userId}/class/state", method = RequestMethod.POST)
	@ResponseBody
	public Object setClassState(@PathVariable String userId, @RequestParam String userType,
			@RequestParam String orderId, @RequestParam(required = false) String coachId, @RequestParam String state,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentClassService.setClassState(userId, userType, orderId, state, coachId);

		} catch (Exception e) {
			log.error("controller: student getClassCoaches failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	@RequestMapping(value = "/subject/video", method = RequestMethod.GET)
	@ResponseBody
	public Object getSubjectVideo(@RequestParam(required = false) String userId,
			@RequestParam(required = false) String userType, @RequestParam(required = false) String cityId,
			@RequestParam(required = false) String examId, @RequestParam(required = false) String fileType,
			@RequestParam(required = false) String subject, @RequestParam(required = false) String id,
			@RequestParam(required = false) String pageNo, @RequestParam(required = false) String pageSize,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentService.getSubjectVideo(userId, userType, cityId, examId, fileType, subject, id, pageNo,
					pageSize);

		} catch (Exception e) {
			log.error("controller: student getSubjectVideo failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/infoAndSkills", method = RequestMethod.GET)
	@ResponseBody
	public Object getStuInfoAndSkills(@RequestParam String ccid, @RequestParam String userId,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			Map<String, Object> resultList = new HashMap<>();
			List<StudentInfoVo> resultStudent = new ArrayList<>();
			List<StuOrderCommentHourVo> resultComment = new ArrayList<>();
			ReqResult rc = null;
			List<StuOrderCommentVo> scvs = null;
			OrderQuery orderQuery = new OrderQuery();
			orderQuery.setSqlPost("and order_state != 0");
			List<OrderVo> orderList = orderService.queryByCcid(Integer.parseInt(ccid), orderQuery);
			if (orderList != null && orderList.size() > 0) {
				for (OrderVo order : orderList) {
					rc = orderLogic.getStudentScore(order.getStudentId().toString(), null, order.getCourseId(), null,
							null);
					scvs = (List<StuOrderCommentVo>) rc.getResult().get(ResultCode.RESULTKEY.DATA);
					StuOrderCommentVo scVo = scvs.get(0);
					StuOrderCommentHourVo scHourVo = new StuOrderCommentHourVo();
					BeanCopy.copyNotNull(scVo, scHourVo);
					scHourVo.setClassHour((Integer) rc.getResult().get("classHour"));
					scHourVo.setStudentId(order.getStudentId());
					resultComment.add(scHourVo);

					Student student = studentManager.getStudentInfo(order.getStudentId());
					StudentInfoVo studentInfoVo = new StudentInfoVo();
					BeanCopy.copy2Null(student, studentInfoVo);
					resultStudent.add(studentInfoVo);
				}
			}
			resultList.put("info", resultStudent);
			resultList.put("skills", resultComment);
			r.setData(resultList);
		} catch (Exception e) {
			log.error("controller: student getSubjectVideo failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * bm班学员剩余免费课时数别
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/{userId}/leftHours", method = RequestMethod.GET)
	@ResponseBody
	public Object leftHours(@PathVariable String userId, String bookCourseNum, String coachId) {
		ReqResult r = ReqResult.getSuccess();
		try {
		     	int courseNum = Integer.parseInt(bookCourseNum);
				Student student = studentService.getStudent(Long.parseLong(userId));
				if (student.getPackageId() == null || student.getPackageId() == 0) {
					r.setMsgInfo("该学员未关联班别");
					return r.getResult();
				}
				//判断是否是绑定教练
				int num = mycoachesManager.countById(Integer.parseInt(userId), Integer.parseInt(coachId));
				if (num == 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("studentId", userId);
					map.put("tips", "");
					r.setData("data", map);
					return r.getResult();
				}
				
				int packageId = student.getPackageId();
				ReqResult reqResult = schoolService.getPackageById(String.valueOf(packageId));
				WechatEnrollPackage enrollPackage = (WechatEnrollPackage) reqResult.getResult().get("data");
				if (enrollPackage == null) {
					r.setMsgInfo("该班别不存在");
					return r.getResult();
				}
				if (enrollPackage.getCstate() != 2) {
					r.setMsgInfo("该学员关联班别未审核通过");
					return r.getResult();
				}
				List<OrderVo> list = orderService.searchBmClass(Integer.parseInt(userId),Integer.parseInt(coachId));
				int userdHours=0;
				for(int i=0;i<list.size();i++){
					OrderVo vo=list.get(i);
					Date pstart=vo.getPstart();
					Date pend=vo.getPend();
					long courseHours = ((pend.getTime() - pstart.getTime()) / 1000) % (24 * 3600) / 3600;
					int maxNum=vo.getClzNum(); //总人数
					userdHours+=courseHours/maxNum;
				}
				int hours = enrollPackage.getHours() == null ? 0 : enrollPackage.getHours();
				int bmHour=student.getBmHour();   //原来未统计到的已使用课时
				int left = hours - userdHours-bmHour;

				int cType = enrollPackage.getcType();
				String type = "";
				if (cType == 1) {
					type = "C1";
				} else {
					type = "C2";
				}
				int hourPrice = enrollPackage.getHoursPrice() == null ? 0 : enrollPackage.getHoursPrice();
				String tips = "";
				if (left == 0 || (userdHours < hours && userdHours + courseNum > hours)) { // 学满课时是提示一次
					tips = "您已学满" + hours + "课时,我们将从第" + (hours + 1) + "个课时进行收费。收费标准" + type + ":" + hourPrice / 100
							+ "元/课时。喱喱学车祝您学车愉快,早日拿证";
				} else if (left == 5 || left==4) { // 剩余5课时时提示一次
					tips = "您还有" + left + "个免费课时,请您合理安排学车进度," + left + "个课时后预约教练将进行收费。喱喱学车祝您学车愉快,早日拿证";
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("left", left);
				map.put("studentId", student.getStudentId());
				map.put("tips", tips);
				r.setData("data", map);
			

		} catch (Exception e) {
			log.error("controller: student get messages failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}

	/**
	 * 校验第三方openId是否已绑定帐号
	 * 
	 * @param openId
	 * @param accType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getBindState", method = RequestMethod.GET)
	@ResponseBody
	public Object getBindState(@RequestParam String unionId, @RequestParam String accType, String openId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentService.getBindState(unionId, openId, accType);
		} catch (Exception e) {
			log.error("controller: student getBindState failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

	@RequestMapping(value = "/bindThirdAccount", method = RequestMethod.POST)
	@ResponseBody
	public Object bindThirdAccount(@RequestParam String unionId, @RequestParam String accType, String openId,
			@RequestParam String phoneNum, @RequestParam String code, String name, String headIcon,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = studentService.bindThirdAccount(unionId, accType, openId, phoneNum, code, name, headIcon);
		} catch (Exception e) {
			log.error("controller: student getBindState failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

	/**
	 * 通过id获取驾校信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/driveSchool/getDriveSchoolById", method = RequestMethod.GET)
	@ResponseBody
	public Object getDriveSchoolById(@RequestParam String id, @RequestParam(required = false) String lon,
			@RequestParam(required = false) String lat) {
		ReqResult r = ReqResult.getSuccess();
		if (!id.matches("\\d+")) {
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		try {
			r = schoolService.getDriveSchoolById(id, Double.parseDouble(lat), Double.parseDouble(lon));
		} catch (Exception e) {
			log.error("getDriveSchoolById: " + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}

	/**
	 * 通过班级ttid获取班级信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/driveSchool/getPackageById", method = RequestMethod.GET)
	@ResponseBody
	public Object getPackageById(@RequestParam String ttid) {
		ReqResult r = ReqResult.getSuccess();
		if (!ttid.matches("\\d+")) {
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		try {
			r = schoolService.getPackageById(ttid);
		} catch (Exception e) {
			log.error("getPackageById: get enroll package=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	/**
	 * 微信-我的教练
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getWeChatMyCoach", method = RequestMethod.GET)
	@ResponseBody
	public Object getWeChatMyCoach(@RequestParam  String openId, @RequestParam(required = false)  String studentId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {

			r = wechatSchoolService.getWeChatMyCoach(openId, studentId);
		} catch (Exception e) {
			log.error("controller: student getWeChatMyCoach failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * APP-报名入口，找教练
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getSearchCoach", method = RequestMethod.GET)
	@ResponseBody
	public Object getSearchCoach(@RequestParam(required = false) String openId, @RequestParam(required = false)  String studentId, @RequestParam(required = false)  String cityId, 
			@RequestParam String pageNo, @RequestParam String pageSize, @RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.getSearchCoach(openId, studentId, cityId, Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		} 
		catch (Exception e) {
			log.error("controller: student getSearchCoach failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	

	/**
	 * 学员提交报名意向
	 * 区分是驾校还是教练
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/subEnrollPurposeNew", method = RequestMethod.POST)
	@ResponseBody
	public Object subEnrollPurposeNew(@RequestParam String studentId, @RequestParam String channel, @RequestParam(required = false) String cType,
			@RequestParam(required = false) String classId,@RequestParam(required = false) String ttid, @RequestParam(required = false) String schoolId, @RequestParam(required = false) String coachId,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = wechatSchoolService.subEnrollPurposeNew(Long.parseLong(studentId), Integer.parseInt(channel), cType,  classId, ttid, schoolId, coachId);
		} catch (Exception e) {
			log.error("controller: student subEnrollPurposeNew failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 微信-我的订单
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getWeChatOrder", method = RequestMethod.GET)
	@ResponseBody
	public Object getWeChatOrder(@RequestParam  String openId, @RequestParam(required = false)  String studentId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = wechatSchoolService.getWeChatOrder(openId, studentId);
		} catch (Exception e) {
			log.error("controller: student getWeChatOrder failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 获取教练排班信息
	 * @param openId
	 * @param studentId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getWeChatClass", method = RequestMethod.GET)
	@ResponseBody
	public Object getWeChatClass(@RequestParam  String openId, @RequestParam(required = false)  String coachId, 
			 @RequestParam(required = false)  String ccid, @RequestParam String orderId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = wechatSchoolService.getWeChatClass(openId, coachId, ccid, orderId);
		} catch (Exception e) {
			log.error("controller: student getWeChatOrder failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 微信班型详情
	 * @param userId
	 * @param userType
	 * @param classId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getWeChatClassInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getWeChatClassInfo(@RequestParam  String openId, @RequestParam(required = false)  String studentId, 
			@RequestParam String classId,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = wechatSchoolService.getWeChatClassInfo(openId, studentId, Integer.parseInt(classId));
		} catch (Exception e) {
			log.error("controller: student getWeChatOrder failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 学员信息
	 * @param openId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/getWeChatStudentInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getWeChatStudentInfo(@RequestParam  String openId, 
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = wechatSchoolService.getWeChatStudentInfo(openId);
		} catch (Exception e) {
			log.error("controller: student getWeChatStudentInfo failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 学员提交报名意向
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/subEnrollPurpose", method = RequestMethod.POST)
	@ResponseBody
	public Object subEnrollPurpose(@RequestParam(required = false)  String openId, @RequestParam(required = false)  String studentId, 
			@RequestParam String classId,@RequestParam(required = false) String className,
			@RequestParam(required = false) String classRemark,@RequestParam(required = false) String cityName,
			@RequestParam(required = false) String drtype,@RequestParam String coachId,@RequestParam(required = false) String payPrice,
			@RequestParam(required = false) String coachName,@RequestParam(required = false) String studentName,
			@RequestParam(required = false) String studentPhone,@RequestParam String codeInput,
			@RequestParam(required = false) String payState,@RequestParam(required = false) String payWay,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = wechatSchoolService.subEnrollPurpose(openId, studentId, Integer.parseInt(classId), className, classRemark, drtype, Long.parseLong(coachId), coachName, studentName, studentPhone, codeInput, payState, cityName, payPrice, payWay);
		} catch (Exception e) {
			log.error("controller: student subEnrollPurpose failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 提交教练分享内容
	 * @param openId
	 * @param studentId
	 * @param coachId
	 * @param coachName
	 * @param studentName
	 * @param studentPhone
	 * @param state
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/subStudentInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object subStudentInfo(@RequestParam(required = false)  String openId, @RequestParam(required = false)  String studentId, 
			@RequestParam String coachId,@RequestParam(required = false) String coachName,
			@RequestParam(required = false) String studentName,@RequestParam(required = false) String studentPhone,
			@RequestParam String wxstatus,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = wechatSchoolService.subStudentInfo(openId, studentId,Long.parseLong(coachId), coachName, studentName, studentPhone, Integer.parseInt(wxstatus));
		} catch (Exception e) {
			log.error("controller: student subEnrollPurpose failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 接受、拒绝、取消课程
	 * @param userId
	 * @param userType
	 * @param classId
	 * @param state
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/handClass", method = RequestMethod.POST)
	@ResponseBody
	public Object handClass(@RequestParam  String openId, @RequestParam(required = false) String ccid,@RequestParam String state,@RequestParam String orderId,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = wechatSchoolService.handClass(openId, ccid, Integer.parseInt(state), orderId);
		} catch (Exception e) {
			log.error("controller: student subEnrollPurpose failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 取消关注
	 * @param userId
	 * @param userType
	 * @param coachId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/abolishCoach", method = RequestMethod.POST)
	@ResponseBody
	public Object abolishCoach(@RequestParam  String openId, @RequestParam(required = false)  String studentId, 
			@RequestParam String coachId, @RequestParam String status,@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = wechatSchoolService.abolishCoach(openId, studentId, Long.parseLong(coachId), Integer.parseInt(status));
		} catch (Exception e) {
			log.error("controller: student abolishCoach failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 微信-绑定教练
	 * @param phone
	 * @param coachId
	 * @param openId
	 * @param codeInput
	 * @param status
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/wechatBoundCoach", method = RequestMethod.POST)
	@ResponseBody
	public Object wechatBoundCoach(@RequestParam String phone, @RequestParam(required = false)  String name, @RequestParam String coachId, @RequestParam String openId,
			 @RequestParam String codeInput, @RequestParam String wxstatus,@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();
		try {
			
			r = wechatSchoolService.wechatBoundCoach(phone,name, Long.parseLong(coachId), openId, codeInput, Integer.parseInt(wxstatus));
		} catch (Exception e) {
			log.error("controller: student wechatBoundCoach failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	

}
