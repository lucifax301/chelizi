package com.lili.school.manager;

import java.util.List;

import com.lili.common.util.Page;
import com.lili.school.dto.EnrollTheory;
import com.lili.school.dto.EnrollTheoryStudent;

public interface EnrollTheoryManager {
	
	/**
	 * 根据理论课id获取理论课
	 * @param theoryId
	 * @return
	 */
	EnrollTheory getEnrollTheoryByTheoryId(int theoryId);
	
	/**
	 * 根据驾校id分页获取理论课
	 * @param schoolId
	 * @param state
	 * @param pageNo
	 * @param pageSize
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	Page<EnrollTheory> getTheoryBySchoolId(Integer schoolId, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String ctimeBegin,String ctimeEnd);
	
	Page<EnrollTheory> getTheoryBySchoolId(List<Integer> schoolIds, String state, String pageNo, String pageSize, String dateBegin,
			String dateEnd,String ctimeBegin,String ctimeEnd);
	
	/**
	 * 添加理论课	
	 * @param schoolId
	 * @param classDate
	 * @param classTime
	 * @param className
	 * @param cardDesc
	 * @param contactName
	 * @param contactMobile
	 * @param classPlace
	 * @return
	 */
	int addTheory(Integer schoolId, String classDate, String classTime, String className,
			String cardDesc, String contactName, String contactMobile, String classPlace);
	/**
	 * 修改理论课
	 * @param theoryId
	 * @param intValue
	 * @param classDate
	 * @param classTime
	 * @param className
	 * @param cardDesc
	 * @param contactName
	 * @param contactMobile
	 * @param classPlace
	 * @return
	 */
	int modifyTheory(String theoryId, Integer schoolId, String classDate, String classTime, String className, String cardDesc,
			String contactName, String contactMobile, String classPlace);
	
	
	/**
	 * 安排学员--导入学员
	 * @param schoolId
	 * @param theoryId
	 * @param studentIds
	 * @return
	 */
	int addTheoryStudent(Integer schoolId, String theoryId, String studentIds);

	/**
	 * 安排学员--去除已导入学员
	 * @param schoolId
	 * @param theoryId
	 * @param studentIds
	 * @return
	 */
	int deleteTheoryStudent(Integer schoolId, String theoryId, String studentIds);

	/**
	 * 安排学员--获取已导入学员
	 * @param schoolId
	 * @param theoryId
	 * @param pageNo
	 * @param pageSize
	 * @param state
	 * @param isImport
	 * @return
	 */
	Page<EnrollTheoryStudent>  getTheoryStudent(Integer schoolId, String theoryId, String pageNo, String pageSize,
			String state, String isImport);

	/**
	 * 更改课程状态
	 * @param intValue
	 * @param theoryId
	 * @param state
	 * @return
	 */
	int changeTheoryClass(int schoolId, String theoryId, String state);

	/**
	 * 更改学员上课状态
	 * @param intValue
	 * @param theoryId
	 * @param studentIds
	 * @param state
	 * @return
	 */
	int changeTheoryStudentState(int schoolId, String theoryId, String studentIds, String state);
	
	
	
	
	
	
}
