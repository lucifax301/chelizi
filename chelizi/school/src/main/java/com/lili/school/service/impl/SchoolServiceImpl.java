package com.lili.school.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.common.util.IDCard;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.school.dto.EnrollExamNotice;
import com.lili.school.dto.EnrollTheory;
import com.lili.school.dto.EnrollTheoryStudent;
import com.lili.school.dto.School;
import com.lili.school.dto.SchoolClassDetail;
import com.lili.school.dto.SchoolWithBLOBs;
import com.lili.school.dto.WechatComment;
import com.lili.school.dto.WechatEnrollOrder;
import com.lili.school.manager.EnrollExamNoticeManager;
import com.lili.school.manager.EnrollTheoryManager;
import com.lili.school.manager.EnrollTheoryStudentManager;
import com.lili.school.manager.SchoolManager;
import com.lili.school.service.SchoolService;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

public class SchoolServiceImpl implements SchoolService {

	private static Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);
	
	@Autowired
	SchoolManager schoolManager;
	
	@Autowired
	private StudentManager studentManager;
	
	@Autowired
	private EnrollTheoryStudentManager enrollTheoryStudentManager;
	
	@Autowired
	private EnrollTheoryManager enrollTheoryManager;
	
	@Autowired
	private EnrollExamNoticeManager enrollExamNoticeManager;
	
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 查看报名包
	 */
	@Override
	public ReqResult getEnrollPkgList(String userId, String userType) {
		ReqResult r = ReqResult.getSuccess();
		Object data = schoolManager.getEnrollPkgList(userId,userType);
		r.setData(data);
		return r;
	}

	@Override
	public ReqResult getEnrollPkgOne(String userId, String userType, String eid) {
		ReqResult r = ReqResult.getSuccess();
		Object data = schoolManager.getEnrollPkgOne(userId,userType,eid);
		r.setData(data);
		return r;
	}
	
	@Override
	public ReqResult getEnrollSchoolPkgOne(String userId, String userType, String eid) {
		ReqResult r = ReqResult.getSuccess();
		Object data = schoolManager.getEnrollSchoolPkgOne(userId,userType,eid);
		r.setData(data);
		return r;
	}

	/**
	 * 立即报名
	 */
	@Override
	public ReqResult postEnrollApply(String userId, String userType, String eid) {
		return schoolManager.postEnrollApply(userId,userType,eid);
	}
	
	/**
	 * 立即报名
	 */
	@Override
	public ReqResult postEnrollApplySchool(String userId, String userType, String eid) {
		return schoolManager.postEnrollApplySchool(userId,userType,eid);
	}

	@Override
	public ReqResult postEnrollPay(String userId, String userType, String eid,
			String payType, String payValue) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 添加身份信息，并更新订单
	 */
	@Override
	public ReqResult postEnrollIdcard(String userId, String userType,
			String picFront, String picBack, String idNum,
			String name, String drType,String orderId) {
		ReqResult r = ReqResult.getSuccess();
		//校验身份证信息
		if (!IDCard.IDCardValidate(idNum.trim()))
        {	// 身份证号码错误
            r.setCode(ResultCode.ERRORCODE.IDCARD_NUMBER_ERROR);
            r.setMsgInfo(ResultCode.ERRORINFO.IDCARD_NUMBER_ERROR);
            return r;
        }
		r = schoolManager.postEnrollIdcard(userId,userType,picFront,picBack,idNum,name,drType,orderId);
		return r;
	}

	/**
	 * 获取邮寄资料信息
	 */
	@Override
	public ReqResult getMailDetail(String userId, String userType, String ttid,String type) {
		return schoolManager.getMailDetail(userId,userType,ttid,type);
	}

	@Override
	public ReqResult postMailState(String userId, String userType,
			String ptype, String state,String orderId) {
		ReqResult r = ReqResult.getSuccess();
		int obj = schoolManager.postMailState(userId,userType,ptype,state,orderId);
		logger.info("******************************** getMailDetail obj : " + obj);
		return r;
	}

	@Override
	public ReqResult getMailState(String userId, String userType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReqResult getUserProgress(String userId, String userType,
			String stepId) {
		return schoolManager.getUserProgress(userId,userType,stepId);
	}
	
/*	@Override
	public ReqResult postExamReg(String userId, String userType,
			String subjectId, String cityId, String waterNo, String name,
			String idcard, String drtype, String examPlace,String mobile,String orderId) {
		ReqResult r = ReqResult.getSuccess();
		Object data = schoolManager.postExamReg(userId,userType,subjectId,cityId,waterNo,name,idcard,drtype,examPlace,mobile,orderId);
		r.setData(data);
		return r;
	}*/

	@Override
	public ReqResult getStudentProgress(String userId, String userType,
			String studentId) {
		return schoolManager.getStudentProgress(userId,userType,studentId);
	}

	@Override
	public ReqResult getExamPlace(String userId, String userType) {
		return schoolManager.getExamPlace(userId,userType);
	}

	@Override
	public ReqResult getEnrollOrder(String userId, String userType,
			String orderId) {
		return schoolManager.getEnrollOrder(userId,userType,orderId);
	}

	@Override
	public ReqResult postEnrollOrderCancel(String userId, String userType,
			String orderId) {
		return schoolManager.postEnrollOrderCancel(userId,userType,orderId);
	}

	@Override
	public ReqResult postEnrollApplyDelay(String userId, String userType) {
		ReqResult r = ReqResult.getSuccess();
		Student student = new Student();
		student.setStudentId(Long.parseLong(userId.trim()));
		student.setApplyexam(ReqConstants.PROG_STAGE_BEGIN_DELAY);
		student.setApplystate(ReqConstants.STAGE_STATE_NONE +0);
		studentManager.updateStudent(student);
		return r;
	}

	@Override
	public int changeState(int ttid, List<Long> studentIds, int stage,
			int state, Map<String, String> params) {
		return schoolManager.changeState( ttid, studentIds, stage,
				state, params);
	}

	@Override
	public int allotSchool(int ttid, List<Long> studentIds, int schoolId) {
		return schoolManager.allotSchool(ttid,studentIds,schoolId);
	}

	@Override
	public ReqResult getStageDetail(String userId, String userType,
			String stepId) {
		ReqResult r = ReqResult.getSuccess();
		if(stepId ==null || stepId.equals(String.valueOf(ReqConstants.PROG_STAGE_TABLE_OFFICAIL)) ){
			Student stu = studentManager.getStudentInfo(Long.parseLong(userId));
			r.setData(stu.getExtra());
		}else if(stepId.equals(String.valueOf(ReqConstants.PROG_STAGE_THEORY))){
			EnrollTheoryStudent ets =enrollTheoryStudentManager.getEnrollTheoryStudentByStudentId(Long.parseLong(userId));
			if(null != ets){
				EnrollTheory et = enrollTheoryManager.getEnrollTheoryByTheoryId(ets.getTheoryId());
				r.setData(et);
				EnrollExamNotice een = enrollExamNoticeManager.getNoticeById(ReqConstants.SUBJECT_TYPE_ONE);
				r.setData("notice", een);
				
			}
			
		}
		return r;
	}
	
	@Override
	public ReqResult getDriveSchoolList(Double lat,Double lon,String pageSize,String cityId){
		return schoolManager.getDriveSchoolList(lat,lon,pageSize,cityId);
	}
	
	@Override
	public ReqResult getDriveSchoolListNew(Double lat,Double lon,String pageSize,String cityId){
		return schoolManager.getDriveSchoolListNew(lat, lon, pageSize, cityId);
	}
	
	@Override
	public ReqResult getDriveSchoolById(String id,Double lat,Double lon){
		return schoolManager.getDriveSchoolById(id,lat,lon);
	}
	@Override
	public ReqResult getTrfieldBySchoolId(String schoolId,String size){
		return schoolManager.getTrfieldBySchoolId(schoolId, size);
	}
	
	@Override
	public ReqResult getGroupBySchoolId(String id){
		return schoolManager.getGroupBySchoolId(id);
	}
	
	@Override
	public ReqResult getPackageById(String ttid){
		return schoolManager.getPackageById(ttid);
	}
	
	@Override
	public ReqResult addWechatOrder(WechatEnrollOrder wechatEnrollOrder){
		ReqResult r = ReqResult.getSuccess();
		schoolManager.addWechatOrder(wechatEnrollOrder);
		r.setData("orderId",wechatEnrollOrder.getOrderId());
		return r;
	}
	
	public WechatEnrollOrder getWechatOrder(String orderId){
		return schoolManager.getWechatOrder(orderId);
	}
	
	public List<WechatEnrollOrder> getWechatOrderByStudentId(String studentId){
		return schoolManager.getWechatOrderByStudentId(studentId);
	}
	
	@Override
	public ReqResult saveWechatComment(WechatComment wechatComment){
		ReqResult r = ReqResult.getSuccess();
		schoolManager.saveWechatComment(wechatComment);
		return r;
	}
	
	@Override
	public ReqResult  deleteComment(String commentId){
		ReqResult r = ReqResult.getSuccess();
		schoolManager.deleteComment(commentId);
		return r;
	}
	
	public ReqResult getCommentListBySchoolId(String schoolId,String studentId,int start,int end){
		ReqResult r = ReqResult.getSuccess();
		r =  schoolManager.getCommentListBySchoolId(schoolId,studentId,start,end);
		return r;
	}
	
	public ReqResult updateCommentPraise(String commitId,String studentId){
		return schoolManager.updateCommentPraise(commitId, studentId);
	}

	@Override
	public ReqResult getDriveSchoolInfo(Integer schoolId, String lon, String lat) {
		ReqResult r = ReqResult.getSuccess();
		
		//统计教练 统计教练车 统计训练场 客服电话 报名须知
		SchoolClassDetail schoolClassDetail = new SchoolClassDetail();
		School school = new School();
		school.setSchoolId(schoolId);
		SchoolWithBLOBs schoolInfo = schoolManager.getDriveSchoolInfo(school);
		if (schoolInfo != null) {
			//放redis，1小时更新一次
			redisUtil.set(REDISKEY.SCHOOL_CLASS_INFO + schoolId, schoolInfo, 3600);
			try {
				schoolClassDetail = BeanCopy.copyNotNull(schoolInfo, SchoolClassDetail.class);
			} catch (Exception e) {
				logger.error("***************************************** getDriveSchoolInfo Error! schoolId : " + schoolId);
				e.printStackTrace();
				r.setCode(ResultCode.ERRORCODE.EXCEPTION);
				r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			}
		}
		
		//班别列表 班别信息暂时不返回，只提交意向
		/*ReqResult result = schoolManager.getGroupBySchoolId(String.valueOf(schoolId));
		if (result.isSuccess()) {
			@SuppressWarnings("unchecked")
			List<WechatEnrollPackageWithBLOBs> wechatEnrollPackageList = (List<WechatEnrollPackageWithBLOBs>) r.getResult().get(ResultCode.RESULTKEY.DATA);
			if (wechatEnrollPackageList != null && wechatEnrollPackageList.size() > 0 ) {
				schoolClassDetail.setWechatEnrollPackageList(wechatEnrollPackageList);
			}
		}*/
		
		r.setData(schoolClassDetail);
		 
		return  r;
	}
	

}
