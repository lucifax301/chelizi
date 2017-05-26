package com.lili.exam.manager;

import java.util.List;
import java.util.Map;

import com.lili.common.vo.ReqResult;
import com.lili.exam.dto.ExamPlaceClass;
import com.lili.exam.dto.ExamPlaceClassDate;
import com.lili.exam.dto.ExamPlaceClassVo;

/**
 * 考场排班管理
 * @author yangpeng
 *
 */
public interface ExamPlaceClassManager {
	/**
	 * 新增排班
	 * @return
	 */
	ReqResult addExamPlaceClass(ExamPlaceClass record);
	/**
	 * 获取某一天的排班
	 * @return
	 */
	List<ExamPlaceClass> getExamPlaceClass(String placeId,String pdate);
	
	/**
	 * 根据排班id获取排班信息
	 * @param classId
	 * @return
	 */
	ExamPlaceClass getExamPlaceClassOne(Integer classId);
	
	/**
	 * 更新排班
	 * @param record
	 * @return
	 */
	int updateExamPlaceClass(ExamPlaceClass record);
	
	/**
	 * 获取指定日期的排班情况
	 * @param placeId
	 * @param pdate
	 * @return
	 */
	List<ExamPlaceClassDate> getExamPlaceClassDate(String placeId,String pdate,String days);
	
	/**
	 * 根据用户情况返回相应课程信息
	 * @param userId
	 * @param userType
	 * @param placeId
	 * @param pdate
	 * @param drtype
	 * @return
	 */
	List<ExamPlaceClassVo> getExamPlaceClassInfo(String userId,
			String userType, String placeId, String pdate, String drtype);
	
	/**
	 * 复制排班
	 * @param classId
	 * @param pdate
	 * @param pstart
	 * @return
	 */
	ReqResult copyExamPlaceClass(String classId, String pdate, String pstart);
	
	/**
	 * 设置延班
	 * @param classId	排班id。多个用逗号隔开
	 * @param num		时间（分钟）
	 * @param remark	备注
	 * @return
	 */
	ReqResult delayExamPlaceClass(String classId, String num, String remark);
	
	/**
	 * 关闭排班
	 * @param classId	排班id。多个用逗号隔开
	 * @param remark	备注
	 * @return
	 */
	ReqResult closeExamPlaceClass(String classId, String remark);
	
	/**
	 * 教练是否已约了排班
	 * @param coachId
	 * @param classId
	 * @param drtype
	 * @return
	 */
	boolean hasBookClass(long coachId, Integer classId,Byte drtype);
	/**
	 * 教练是否在白名单中
	 * @param phoneNum
	 * @param schoolId
	 * @return
	 */
	boolean isInWhitelist(String phoneNum, Integer schoolId);
}
































