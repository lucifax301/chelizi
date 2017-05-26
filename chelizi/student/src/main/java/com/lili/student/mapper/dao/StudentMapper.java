/**
 * 
 */
package com.lili.student.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAccount;

public interface StudentMapper {

	/**
	 * 根据id删除学员信息
	 * @param studentId
	 * @return
	 */
    long deleteByPrimaryKey(Long studentId);

    /**
     * 新增学员信息
     * @param student
     * @return
     */
    long insert(Student student);
    
    /**
     * 新增
     * @param student
     * @return
     */
    long insertAndGetStudentId(Student student);

    /**
     * 新增学员信息
     * @param student
     * @return
     */
    long insertSelective(Student student);

    /**
     * 根据id获取学员信息
     * @param studentId
     * @return
     */
    Student selectByPrimaryKey(Long studentId);
    
    /**
     * 根据手机号获取学员
     * @param phoneNum
     * @return
     */
    Student selectByPhoneNum(String phoneNum);

    /**
     * 修改学员信息
     * @param student
     * @return
     */
    long updateByPrimaryKeySelective(Student student);

    /**
     * 修改学员信息
     * @param student
     * @return
     */
    long updateByPrimaryKey(Student student);
    
    /**
     * 获取学员列表
     * @return
     */
    List<Student> getAll(Student student);
    
    List<Student> getStudentsByIds(List<Long> studentIds);
    
    /**
     * 获取学员数量
     * @return
     */
    long countAll(Student student);

    /**
     * 根据学员id获取学员金额
     * @param studentId
     * @return
     */
	StudentAccount getStudentMoney(Long studentId);
	
	/**
	 * 根据教练id分页获取学员信息
	 * @param page
	 * @return
	 */
	List<Student> getStudentByCoachId(StudentPage page);
	
	/**
	 * 更新学员帐户
	 * @param studentAccount
	 */
	void updateStudentMoney(StudentAccount studentAccount);
	
	/**
	 * 添加学员帐户
	 * @param studentAccount
	 */
	void insertStudentAccount(StudentAccount studentAccount);
	
	/** 
	 * 用户密码是否正确
	 * @param pw
	 * @param userId
	 * @return
	 */
	long checkStudentCorrectPw(@Param("passwd") String passwd,@Param("studentId")  long studentId);
	
	/**
	 * 设置用户密码
	 * @param pw
	 * @param userId
	 */
	long  updateStudentPassword(@Param("passwd") String passwd, @Param("studentId") long studentId);

	/**
	 * 根据身份证号获取学员
	 * @param idNumber
	 * @return
	 */
	Student selectByIdNumber(String idNumber);
	
	List<Student> queryByObjectAnd(Student student,String post);
	int countByObjectAnd(Student student,String post);
	
	/**
	 * 获取当日同步数据中新注册的学员
	 * @param date1
	 * @param date2
	 * @param schoolId
	 * @return
	 */
	List<String> selectNewRegisterMobile(@Param("date1")String date1, @Param("date2")String date2,@Param("schoolId") Integer schoolId);

	Student getStudentNear(Student student);
}
