package com.lili.order.service;

import java.util.List;

import com.lili.common.util.Page;
import com.lili.common.vo.ReqResult;
import com.lili.order.dto.StudentClass;
import com.lili.order.dto.StudentClassPool;
import com.lili.order.dto.StudentClassPoolVo;
import com.lili.order.dto.StudentClassVo;

public interface StudentClassService {
	/**
	 * 学员添加排班
	 * @param studentClass
	 * @return
	 */
	public ReqResult addStudentClass(StudentClass studentClass,String v);
	
	/**
	 * 定向预约
	 * @param studentClass
	 * @param coachId
	 * @return
	 */
	public ReqResult addStudentClass(StudentClass studentClass,long coachId);

	/**
	 * 学员获取推荐的班次信息
	 * @param userId
	 * @return
	 */
	public ReqResult getClassRecommend(String userId);

	/**
	 * 学员获取自主预约课程价格
	 * @param userId
	 * @param userType
	 * @param courseId
	 * @param cStart
	 * @param clznum
	 * @return
	 */
	public ReqResult getClassPrice(String userId, String userType,
			String courseId, String cStart, String clznum, String cityId);

	/**
	 * 教练获取学员的自主预约排班信息
	 * @param userId
	 * @param userType
	 * @return
	 */
	public ReqResult getStudentsClass(String userId, String userType);
	
	public StudentClass getStudentClass(String orderid);
	
	public List<StudentClassPool> getStudentClassPool(String userId,String orderid);
	
	public ReqResult getPageStudentsClass(String userId, String userType,List<Byte> states);
	

	/**
	 * 教练设置学员排班订单的状态
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param state	1-教练已接单；2-教练已取消
	 * @return
	 */
	public ReqResult setStudentsClassState(String userId, String userType,
			String orderId, String state, String placeId,String placeName,String placeLge,String placeLae,String v);

	/**
	 * 学员获取已接单教练列表
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @return
	 */
	public ReqResult getClassCoaches(String userId, String userType,
			String orderId);

	/**
	 * 学员设置自主排班状态
	 * @param userId
	 * @param userType
	 * @param orderId
	 * @param state		1-已成功；2-已取消；//成功预约的时候coachId不能为空
	 * @param coachId
	 * @return
	 */
	public ReqResult setClassState(String userId, String userType,
			String orderId, String state, String coachId);

	/**
	 * 学员获取自主预约排班基本信息
	 * @param userId
	 * @param userType
	 * @return
	 */
	public ReqResult getMyClass(String userId, String userType);
	
	/**
	 * 学员获取自主预约排班基本信息（包括发布成功，成单的）
	 * @param userId
	 * @param userType
	 * @return
	 */
	public ReqResult getMyClassNew(String userId, String userType,boolean both);
	
	
	public StudentClass getStudentClass(String userId,String orderid);
	/**
	 * CMS订单调度-获取学员排班订单列表
	 * @param ctime
	 * @param state
	 * @param lastMinutes
	 * @param orderId
	 * @param stuName
	 * @param stuMobile
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<StudentClassVo> getScheduleOrders(String stime,String etime, String state, String lastMinutes, String orderId,
			String stuName, String stuMobile, String pageNo, String pageSize);
	
	/**
	 * CMS订单调度-获取需要调度的学员数量。订单正常待接单中，且无教练接单。
	 * @return
	 */
	public Integer getScheduleNoticeCount();
	
	/**
	 * CMS订单调度-获取可供调度的教练
	 * @param orderId
	 * @param dltype
	 * @param isVip
	 * @return
	 */
	public Page<StudentClassPoolVo> getScheduleCoaches(String orderId, String dltype, String isVip, String pageNo, String pageSize);
	
	/**
	 * CMS订单调度-设置接单调度教练
	 * @param orderId
	 * @param coachId
	 * @param placeId
	 * @param placeName
	 * @param placeLge
	 * @param placeLae
	 * @return
	 */
	public ReqResult addScheduleCoach(String orderId, String coachId, String placeId,String placeName,String placeLge,String placeLae);

}





























