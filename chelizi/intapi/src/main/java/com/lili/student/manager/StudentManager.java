package com.lili.student.manager;

import java.util.List;

import com.lili.common.vo.ReqResult;
import com.lili.student.dto.MicroClass;
import com.lili.student.dto.Student;
import com.lili.student.dto.StudentAccount;
import com.lili.student.dto.StudentVip;
import com.lili.student.dto.SubjectVideo;

public interface StudentManager {

	/**
	 * 获取学员列表
	 * @return
	 */
	List<Student> getStudent();

	/**
	 * 根据学员id获取学员的vip信息
	 *
	 * @param studentId
	 * @return
	 */
	StudentVip getStudentVipInfoByStudentId(Long studentId);

	/**
	 * 根据大客户id获取学员的vip信息
	 *
	 * @param vipId
	 * @return
	 */
	StudentVip getStudentVipInfoByVipId(Integer vipId);

	/**
     * 根据id获取学员信息
     * @param id
     * @return
     */
	Student getStudentInfo(long id);
	
	/**
	 * 根据手机号获取学员
	 * @param phoneNum
	 * @return
	 */
	Student getStudentByPhoneNum(String phoneNum);
	
	/**
	 * 根据身份证号获取学员
	 * @param phoneNum
	 * @return
	 */
	Student getStudentByIdNumber(String idNumber);

	/**
	 * 获取学员数量
	 * @return
	 */
	long getCount();

	/**
	 * 新增学员信息
	 * @param student
	 * @return
	 */
	long addStudent(Student student);

	/**
	 * 更新学员信息
	 * @param student
	 * @return
	 */
	long updateStudent(Student student);

	/**
	 * 根据id删除学员信息
	 * @param id
	 * @return
	 */
	long deleteStudent(long id);
	
	/**
	 * 校验手机号是否存在
	 * @param phoneNum
	 * @return
	 */
	boolean isExist(String phoneNum);
	
	/**
	 * 根据学员id获取金额
	 * @param studentId
	 * @return
	 */
	StudentAccount getStudentMoney(Long studentId);
	
	/**
	 * 更新帐户余额
	 * @param studentAccount
	 */
	void updateStudentAccount(StudentAccount studentAccount);
	
	/**
	 * 添加余额帐户
	 * @param studentAccount
	 */
	void insertStudentAccount(StudentAccount studentAccount);
	
    /**
     * 给单个学员发钱
     *
     * @param coachAccount
     * @return
     */
    boolean payStudentMoney(StudentAccount studentAccount);

    /**
     * 给多个学员发钱
     *
     * @param coachAccounts
     * @return
     */
    boolean payStudentMoney(List<StudentAccount> studentAccount);
	
	/**
	 * 根据教练id获取学员信息
	 * @param coachId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<Student> getStudentByCoachId(Long coachId, int pageNum, int pageSize);
	
	/**
	 * 判断用户密码是否正确
	 * @param pw
	 * @param userId
	 * @return
	 */
	long  checkStudentCorrectPw(String pw, Long userId);
	
	/**
	 * 设置用户密码
	 * @param pw
	 * @param userId
	 * @return
	 */
	void  updateStudentPassword(String pw, Long userId);

	List<Student> getStudentsByIds(List<Long> stuIds);
	
	/**
	 * 批量新增学员信息，cms使用
	 * @param students
	 * @return
	 */
	long addStudentList(List<Student> students);

	public List<MicroClass> getMicroClass(MicroClass mocroClass);
	
	public List<Student> queryByObjectAnd(Student student,int start,int pageSize);
	public int countByObjectAnd(Student student);

	List<SubjectVideo> getSubjectVideo(String cityId,String examId, String subject, String fileType,String id, String pageNo, String pageSize);
	
	/**
	 * 通过微信UnionId或者QQ的openId获取学员信息
	 * @param openId
	 * @param accType
	 * @return
	 */
	List<Student> queryByUnionId(String unionId, String accType);
	
	/**
	 * 获取当日同步数据中新注册的学员
	 * @param date1
	 * @param date2
	 * @param schoolId
	 * @return
	 */
	List<String> getNewRegisterMobile(String date1,String date2,Integer schoolId);

	Student getStudentNear(Student student);
	

	

}
