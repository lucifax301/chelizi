package com.lili.school.manager;

import java.util.List;

import com.lili.common.util.Page;
import com.lili.school.dto.EnrollLongtrain;
import com.lili.school.dto.EnrollLongtrainStudent;

public interface EnrollLongtrainManager {
	/**
	 * 添加长训课程
	 * @param schoolId
	 * @param contactMobile
	 * @param carNo
	 * @param classDate
	 * @param classTime
	 * @param classPlace
	 * @param carrys
	 * @return
	 */
	public int addLongtrain(Integer schoolId, String contactMobile, String carNo, String classDate,
			String classTime, String classPlace, String carrys);

	/**
	 * 分页获取长训课
	 * @param intValue
	 * @param state
	 * @param pageNo
	 * @param pageSize
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	public Page<EnrollLongtrain> getLongtrain(int schoolId, String state, String pageNo, String pageSize,
			String dateBegin, String dateEnd,String ctimeBegin,String ctimeEnd);
	
	public Page<EnrollLongtrain> getLongtrain(List<Integer> schoolIds, String state, String pageNo, String pageSize,
			String dateBegin, String dateEnd,String ctimeBegin,String ctimeEnd);

	/**
	 * 获取长训课单个
	 * @param intValue
	 * @param ltId
	 * @return
	 */
	public EnrollLongtrain getLongtrainOne(Integer ltId);

	/**
	 * 修改长训
	 * @param intValue
	 * @param ltId
	 * @param contactMobile
	 * @param carNo
	 * @param classDate
	 * @param classTime
	 * @param classPlace
	 * @param carrys
	 * @return
	 */
	public int modifyLongtrain(int schoolId, String ltId, String contactMobile, String carNo, String classDate,
			String classTime, String classPlace, String carrys);

	/**
	 * 添加长训学员
	 * @param schoolId
	 * @param ltId
	 * @param studentIds
	 * @return
	 */
	public int addLongtrainStudent(int schoolId, String ltId, String studentIds);

	/**
	 * 删除长训学员
	 * @param schoolId
	 * @param ltId
	 * @param studentIds
	 * @return
	 */
	public int deleteLongtrainStudent(int schoolId, String ltId, String studentIds);

	/**
	 * 更改长考课程状态--确认开课，取消开课--短信通知学员
	 * @param schoolId
	 * @param ltId
	 * @param state
	 * @return
	 */
	public int changeLongtrainClass(int schoolId, String ltId, String state);

	/**
	 * 更改长考学员成绩状态  ：成绩状态：0-未记成绩；1-合格；2-不合格
	 * @param schoolId
	 * @param ltId
	 * @param studentIds
	 * @param state
	 * @return
	 */
	public int changeLongtrainStudentState(int schoolId, String ltId, String studentIds, String state);

	/**
	 * 获取长训学员
	 * @param schoolId
	 * @param ltId
	 * @param pageNo
	 * @param pageSize
	 * @param state
	 * @param isImport
	 * @return
	 */
	public Page<EnrollLongtrainStudent> getLongtrainStudent(int schoolId, String ltId, String pageNo, String pageSize,
			String state, String isImport);
	
	
	
	
	
	
	
	
}
