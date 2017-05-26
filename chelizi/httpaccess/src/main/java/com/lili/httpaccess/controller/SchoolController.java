package com.lili.httpaccess.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.school.service.SchoolService;

@Controller
@RequestMapping("/v1/schools")
public class SchoolController {
	
	private Logger log = Logger.getLogger(SchoolController.class);
	
	@Autowired
	private SchoolService schoolService;
	
	/**
	 * 获取报名包套餐列表
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/enroll/list", method = RequestMethod.GET)
	@ResponseBody
	public Object getEnrollPkgList(@RequestParam(required=false) String userId,@RequestParam(required=false) String userType,
			 @RequestParam String timestamp,@RequestParam String sign) {
		ReqResult r = ReqResult.getSuccess();

		try {
			r = schoolService.getEnrollPkgList(userId, userType);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 获取单个报名包详情
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param ttid	报名包id
	 * @return
	 */
	@RequestMapping(value = "/enroll/one", method = RequestMethod.GET)
	@ResponseBody
	public Object getEnrollPkgOne(@RequestParam(required=false) String userId,@RequestParam(required=false) String userType,
			 @RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String ttid) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.getEnrollPkgOne(userId, userType,ttid);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取单个报名包详情
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param ttid	报名包id
	 * @return
	 */
	@RequestMapping(value = "/enroll/oneSchool", method = RequestMethod.GET)
	@ResponseBody
	public Object getEnrollSchoolPkgOne(@RequestParam(required=false) String userId,@RequestParam(required=false) String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String ttid) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.getEnrollSchoolPkgOne(userId, userType,ttid);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 立即报名某个套餐
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param ttid
	 * @return
	 */
	@RequestMapping(value = "/enroll/apply", method = RequestMethod.POST)
	@ResponseBody
	public Object postEnrollApply(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String ttid) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.postEnrollApply(userId, userType,ttid);
		} catch (Exception e) {
			log.error("SchoolController: post apply=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 立即报名驾校某个套餐
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param ttid
	 * @return
	 */
	@RequestMapping(value = "/enroll/applySchool", method = RequestMethod.POST)
	@ResponseBody
	public Object postEnrollApplySchool(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String ttid) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.postEnrollApplySchool(userId, userType,ttid);
		} catch (Exception e) {
			log.error("SchoolController: post apply=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 暂不报名
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/enroll/applyDelay", method = RequestMethod.POST)
	@ResponseBody
	public Object postEnrollApplyDelay(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.postEnrollApplyDelay(userId, userType);
		} catch (Exception e) {
			log.error("SchoolController: postEnrollApplyDelay=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	/**
	 * 获取指定报名订单
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/enroll/order", method = RequestMethod.GET)
	@ResponseBody
	public Object getEnrollOrder(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String orderId) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.getEnrollOrder(userId, userType,orderId);
		} catch (Exception e) {
			log.error("SchoolController: getEnrollOrder=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 取消报名订单
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/enroll/order/cancel", method = RequestMethod.POST)
	@ResponseBody
	public Object postEnrollOrderCancel(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String orderId) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.postEnrollOrderCancel(userId, userType,orderId);
		} catch (Exception e) {
			log.error("SchoolController: postEnrollOrderCancel=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 支付报名费
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param ttid
	 * @param payType
	 * @param payValue
	 * @return
	 */
	@RequestMapping(value = "/enroll/pay", method = RequestMethod.POST)
	@ResponseBody
	public Object postEnrollPay(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String ttid,@RequestParam String payType,@RequestParam String payValue) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.postEnrollPay(userId, userType,ttid,payType,payValue);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 上传身份证等信息
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param picFront
	 * @param picBack
	 * @param idNum
	 * @param cityId
	 * @param name
	 * @param drType
	 * @param orderId 报考订单
	 * @return
	 */
	@RequestMapping(value = "/enroll/idcard", method = RequestMethod.POST)
	@ResponseBody
	public Object postEnrollIdcard(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam(required = false) String picFront,@RequestParam(required = false) String picBack,
			@RequestParam String idNum,
			@RequestParam String name,@RequestParam String drType,@RequestParam String orderId) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.postEnrollIdcard(userId, userType,picFront,picBack,idNum,name,drType,orderId);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取邮寄资料信息
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param ptype
	 * @return
	 */
	@RequestMapping(value = "/enroll/mailDetail", method = RequestMethod.GET)
	@ResponseBody
	public Object getMailDetail(@RequestParam(required=false) String userId,@RequestParam(required=false) String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String ttid, @RequestParam(required=false) String type
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.getMailDetail(userId, userType,ttid,type);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	/**
	 * 标记是否已经邮寄资料
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param ptype
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "/enroll/mailState", method = RequestMethod.POST)
	@ResponseBody
	public Object postMailState(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String ptype,@RequestParam String state,@RequestParam String orderId
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.postMailState(userId, userType,ptype,state,orderId);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	/**
	 * 获取邮寄状态
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/enroll/mailState", method = RequestMethod.GET)
	@ResponseBody
	public Object getMailState(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.getMailState(userId, userType);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取阶段详情。例如报名失败后，可以获取失败理由。默认获取报名失败理由。
	 * @param userId
	 * @param userType
	 * @param stepId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/enroll/stageDetail", method = RequestMethod.GET)
	@ResponseBody
	public Object getStageDetail(@RequestParam String userId,@RequestParam String userType,
			@RequestParam(required=false) String stepId,
			@RequestParam String timestamp,@RequestParam String sign
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.getStageDetail(userId, userType,stepId);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取我的进度
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/enroll/progress", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserProgress(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam(required=false) String stepId
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.getUserProgress(userId, userType,stepId);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 教练获取学员的进度状态
	 * @param userId
	 * @param userType
	 * @param studentId
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/enroll/student/progress", method = RequestMethod.GET)
	@ResponseBody
	public Object getStudentProgress(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String studentId,
			@RequestParam String timestamp,@RequestParam String sign
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.getStudentProgress(userId, userType,studentId);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 自主约考登记预约
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param cityId
	 * @param waterNo
	 * @param name
	 * @param idcard
	 * @param drtype
	 * @param examPlace
	 * @return
	 */
	@RequestMapping(value = "/enroll/exam", method = RequestMethod.POST)
	@ResponseBody
	public Object postExamReg(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign,
			@RequestParam String subjectId,@RequestParam(required=false) String mobile,
			@RequestParam String cityId,@RequestParam String waterNo,
			@RequestParam String name,@RequestParam String idcard,
			@RequestParam String drtype,@RequestParam String examPlace,@RequestParam String orderId
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			//20160617学员已不能通过系统报名
			r = ReqResult.getSuccess();
			//r = schoolService.postExamReg(userId, userType,subjectId,cityId,waterNo,name,idcard,drtype,examPlace,mobile,orderId);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	@RequestMapping(value = "/enroll/exam/place", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlace(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp,@RequestParam String sign
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			r = schoolService.getExamPlace(userId, userType);
		} catch (Exception e) {
			log.error("SchoolController: get enroll place=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	//---------------仅供测试使用---------------
	/*@RequestMapping(value = "/enroll/state", method = RequestMethod.POST)
	@ResponseBody
	public Object postState(@RequestParam String ttid,@RequestParam String stage,
			@RequestParam String state,@RequestParam String studentIds,
			@RequestParam String params1,@RequestParam String params2
			) {
		ReqResult r = ReqResult.getSuccess();
		
		try {
			List<Long> st = new ArrayList<Long>();
			String[] sts = studentIds.split(",");
			for(String student:sts){
				st.add(Long.parseLong(student));
			}
			Map<String,String> pa = new HashMap<String, String>();
			String[] pas = params1.split(",");
			String[] pas2 = params2.split(",");
			for(int i=0;i<pas.length;i++){
				pa.put(pas[i], pas2[i]);
			}
			int res = schoolService.changeState(Integer.parseInt(ttid), st, Integer.parseInt(stage), Integer.parseInt(state), pa);
			r.setData(res);
		} catch (Exception e) {
			log.error("SchoolController: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}*/

}
