package com.lili.httpaccess.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.common.util.StringUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.school.dto.WechatComment;
import com.lili.school.dto.WechatEnrollOrder;
import com.lili.school.service.SchoolService;

@Controller
@RequestMapping("/open/driveSchool")
public class DriveSchoolController {
	
private Logger log = Logger.getLogger(SchoolController.class);
	
	@Autowired
	private SchoolService schoolService;
	
	/**
	 * 获取驾校列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getDriveSchoolList", method = RequestMethod.GET)
	@ResponseBody
	public Object getDriveSchoolList(HttpServletRequest request,@RequestParam(required=true)String lon,@RequestParam(required=true)String lat,@RequestParam(required=true) String pageSize) {
		ReqResult r = ReqResult.getSuccess();
		if(!pageSize.matches("\\d+")){
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		String cityId=request.getParameter("cityId");
		try {
			r = schoolService.getDriveSchoolList(Double.parseDouble(lat), Double.parseDouble(lon),pageSize,cityId);
		} catch (Exception e) {
			log.error("getDriveSchoolList: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 通过id获取驾校信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getDriveSchoolById", method = RequestMethod.GET)
	@ResponseBody
	public Object getDriveSchoolById(@RequestParam String id,
			@RequestParam(required=false) String lon,@RequestParam(required=false) String lat) {
		ReqResult r = ReqResult.getSuccess();
		if(!id.matches("\\d+")){
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
	 * 获取驾校列表
	 * 分组 有班别的一组，没有的一组
	 * 去掉分页
	 * 简化返回的字段，比如驾校简介不需要在列表返回
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getDriveSchoolListNew", method = RequestMethod.GET)
	@ResponseBody
	public Object getDriveSchoolListNew(HttpServletRequest request, @RequestParam(required=true) String lon,@RequestParam(required=true) String cityId,
				@RequestParam(required=true) String lat, @RequestParam String pageSize) {
		ReqResult r = ReqResult.getSuccess();
		if (!pageSize.matches("\\d+")) {
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		try {
			r = schoolService.getDriveSchoolListNew(Double.parseDouble(lat), Double.parseDouble(lon),pageSize,cityId);
		} 
		catch (Exception e) {
			log.error("getDriveSchoolListNew: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 获取驾校详情
	 * @param request
	 * @param schoolId
	 * @return
	 */
	@RequestMapping(value = "/getDriveSchoolInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getDriveSchoolInfo(@RequestParam String schoolId, @RequestParam(required=false) String lon, @RequestParam(required=false) String lat) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = schoolService.getDriveSchoolInfo(Integer.parseInt(schoolId), lon, lat);
		} 
		catch (Exception e) {
			log.error("getDriveSchoolInfo error , Exception: " + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 通过schoolId获取训练场信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getTrfieldBySchoolId", method = RequestMethod.GET)
	@ResponseBody
	public Object getTrfieldBySchoolId(@RequestParam String schoolId,@RequestParam String size) {
		ReqResult r = ReqResult.getSuccess();
		if(!schoolId.matches("\\d+") || !size.matches("\\d+") ){
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		try {
			r = schoolService.getTrfieldBySchoolId(schoolId,size);
		} catch (Exception e) {
			log.error("getDriveSchoolById: " + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	
	/**
	 * 通过学校id获取驾校班级
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getGroupBySchoolId", method = RequestMethod.GET)
	@ResponseBody
	public Object getGroupBySchoolId(@RequestParam String id) {
		ReqResult r = ReqResult.getSuccess();
		if(!id.matches("\\d+") ){
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		try {
			r = schoolService.getGroupBySchoolId(id);
		} catch (Exception e) {
			log.error("getGroupBySchoolId: get enroll list=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 通过班级ttid获取班级信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getPackageById", method = RequestMethod.GET)
	@ResponseBody
	public Object getPackageById(@RequestParam String ttid) {
		ReqResult r = ReqResult.getSuccess();
		if(!ttid.matches("\\d+") ){
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
	 * 提交订单
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/addWechatOrder", method = RequestMethod.POST)
	@ResponseBody
	public Object addWechatOrder(WechatEnrollOrder wechatEnrollOrder) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String orderId=StringUtil.getOrderId();
			wechatEnrollOrder.setOrderId(orderId);
			wechatEnrollOrder.setOrderId(orderId);
			wechatEnrollOrder.setPayState((byte) 0);  //支付状态：0未开始，1已提交，100支付成功，101支付失败
			wechatEnrollOrder.setPostState((byte)0);  //资料邮寄状态：0资料未邮寄，1资料已邮寄，2资料已收到
			wechatEnrollOrder.setTestState((byte)0);  //报考状态：0未报考，其他为进度步骤step_id
			wechatEnrollOrder.setIsdel((byte)0);      //状态：0代表未删除，1代表已删除
			wechatEnrollOrder.setOrderState((byte)1);  //订单状态：0已取消，1已下单，2未结款，3已结款，4退款中，5已退款，6退款失败
			wechatEnrollOrder.setCtime(new Date());
			wechatEnrollOrder.setMtime(new Date());
		//	wechatEnrollOrder.setPayTime(new Date());
			r = schoolService.addWechatOrder(wechatEnrollOrder);
		} catch (Exception e) {
			log.error("addWechatOrder: add=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 获取订单
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getWechatOrder", method = RequestMethod.GET)
	@ResponseBody
	public Object getWechatOrder(@RequestParam String orderId) {
		ReqResult r = ReqResult.getSuccess();
		if(!orderId.matches("[0-9a-zA-z]+") ){
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		try {
			WechatEnrollOrder wOrder = schoolService.getWechatOrder(orderId);
			r.setData(wOrder);
		} catch (Exception e) {
			log.error("addWechatOrder: add=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}
	
	/**
	 * 获取订单
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getWechatOrderByStudentId", method = RequestMethod.GET)
	@ResponseBody
	public Object getWechatOrderByStudentId(@RequestParam String studentId) {
		ReqResult r = ReqResult.getSuccess();
		if(!studentId.matches("\\d+") ){
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		try {
			List<WechatEnrollOrder> wOrder = schoolService.getWechatOrderByStudentId(studentId);
			r.setData(wOrder);
		} catch (Exception e) {
			log.error("addWechatOrder: add=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}
	
	/**
	 * 保存评论
	 * 
	 * @return
	 */
	@RequestMapping(value = "/saveComment", method = RequestMethod.POST)
	@ResponseBody
	public Object saveComment(WechatComment wechatComment) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String commitId=StringUtil.getOrderId();
			wechatComment.setCommentId(commitId);
			wechatComment.setCreateTime(new Date());
			r = schoolService.saveWechatComment(wechatComment);
		} catch (Exception e) {
			log.error("saveComment: add=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}
	
	
	/**
	 * 保存评论 -new
	 * 
	 * @return
	 */
	@RequestMapping(value = "/saveCommentNew", method = RequestMethod.POST)
	@ResponseBody
	public Object saveCommentNew(WechatComment wechatComment) {
		ReqResult r = ReqResult.getSuccess();
		try {
			String commitId=StringUtil.getOrderId();
			wechatComment.setCommentId(commitId);
			wechatComment.setCreateTime(new Date());
			r = schoolService.saveWechatComment(wechatComment);
		} catch (Exception e) {
			log.error("saveCommentNew: add=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 删除评论
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteSelfComment(String commentId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = schoolService.deleteComment(commentId);
		} catch (Exception e) {
			log.error("deleteComment: delete=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}
	
	/**
	 * 删除评论
	 * @return
	 */
	@RequestMapping(value = "/deleteCommentNew", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteCommentNew(String commentId) {
		ReqResult r = ReqResult.getSuccess();
		try {
			r = schoolService.deleteComment(commentId);
		} catch (Exception e) {
			log.error("deleteComment: delete=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 获得评论
	 * @return
	 */
	@RequestMapping(value = "/getCommentListBySchoolId", method = RequestMethod.GET)
	@ResponseBody
	public Object getCommentListBySchoolId(@RequestParam String schoolId,@RequestParam String studentId,@RequestParam String pageNo,@RequestParam String pageSize) {
		ReqResult r = ReqResult.getSuccess();
		try {
			int start=0;
			int end=10;
			if(pageNo!=null && !pageNo.equals("") && pageSize!=null && !pageSize.equals("")){
				start=(Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize);
				 end=Integer.parseInt(pageSize);
			}
			r= schoolService.getCommentListBySchoolId(schoolId,studentId,start,end);
		} catch (Exception e) {
			log.error("getCommentListBySchoolId: get=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}
	
	/**
	 * 获得评论
	 * @return
	 */
	@RequestMapping(value = "/getCommentListBySchoolIdNew", method = RequestMethod.GET)
	@ResponseBody
	public Object getCommentListBySchoolIdNew(@RequestParam String schoolId,@RequestParam String studentId,@RequestParam String pageNo,@RequestParam String pageSize) {
		ReqResult r = ReqResult.getSuccess();
		try {
			int start=0;
			int end=10;
			if(pageNo!=null && !pageNo.equals("") && pageSize!=null && !pageSize.equals("")){
				start=(Integer.parseInt(pageNo)-1)*Integer.parseInt(pageSize);
				end=Integer.parseInt(pageSize);
			}
			r= schoolService.getCommentListBySchoolId(schoolId,studentId,start,end);
		} catch (Exception e) {
			log.error("getCommentListBySchoolId: get=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	/**
	 * 修改点赞数
	 * @return
	 */
	@RequestMapping(value = "/updatePraise", method = RequestMethod.POST)
	@ResponseBody
	public Object updatePraise(@RequestParam String studentId,@RequestParam String commentId) {
		ReqResult r = ReqResult.getSuccess();
		if(!studentId.matches("\\d+") || !commentId.matches("[0-9a-zA-Z]+")){
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		try {
			r = schoolService.updateCommentPraise(commentId,studentId);
		} catch (Exception e) {
			log.error("saveComment: add=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r;
	}
	
	/**
	 * 修改点赞数
	 * @return
	 */
	@RequestMapping(value = "/updatePraiseNew", method = RequestMethod.POST)
	@ResponseBody
	public Object updatePraiseNew(@RequestParam String studentId,@RequestParam String commentId) {
		ReqResult r = ReqResult.getSuccess();
		if(!studentId.matches("\\d+") || !commentId.matches("[0-9a-zA-Z]+")){
			r.setCode(ResultCode.ERRORCODE.PARAMERROR);
			r.setMsgInfo(ResultCode.ERRORINFO.PARAMERROR);
			return r.getResult();
		}
		try {
			r = schoolService.updateCommentPraise(commentId,studentId);
		} catch (Exception e) {
			log.error("saveComment: add=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		return r.getResult();
	}
	
	

}
