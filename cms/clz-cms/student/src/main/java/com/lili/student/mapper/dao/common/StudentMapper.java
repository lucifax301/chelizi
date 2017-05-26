/**
 * 
 */
package com.lili.student.mapper.dao.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.student.model.Student;
import com.lili.student.model.StudentAccount;
import com.lili.student.model.StudentAuthVo;
import com.lili.student.model.StudentBDTO;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.model.StudentParam;
import com.lili.student.model.StudentVo;

public interface StudentMapper {

	public Student findOne(StudentNBDTO dto) throws Exception;
	
	/**
	 * 获取学员认证状实体
	 * @param id 实体id
	 * @return
	 * @throws Exception
	 */
	public StudentAuthVo findAuthState(long id) throws Exception;

	/**
	 * 批量修改学员状态为0
	 * @param params 必含studentIds
	 * @return
	 */
	public Long updateStates(String studentIds) throws Exception;

	/**
	 * 根据studentId获取学员的驾照照片链接和学员身份证照片链接
	 * @param params 
	 * @return
	 */
    public StudentAuthVo findImgById(long studentId) throws Exception;

	/**
	 * 获取实名认证列表
	 * @param params 
	 * @return
	 */
    public List<StudentAuthVo> findCerBatch(StudentBDTO dto) throws Exception;
    
	/**
	 * 获取驾照认证列表
	 * @param params 
	 * @return
	 */
    public List<StudentAuthVo> findLinBatch(StudentBDTO dto) throws Exception;
	
	/**
	 * 通过手机号查学员，参数中包含学员ID，查其他学员有没有使用参数中的手机号
	 * @param params
	 * @return
	 */
	public Student findByPhone(String phoneNum) throws Exception;
	
	public Student findByIdNumber(String idNumber) throws Exception;

	public List<Student> findByNums(Student student) throws Exception;
	
	
	/**
	 * 获取喱喱学员列表
	 * @param params 
	 * @return
	 */
    public List<StudentVo> findLiliBatch(StudentBDTO dto) throws Exception;
    
    /**
	 * 获取喱喱学员列表
	 * @param params 
	 * @return
	 */
    public List<StudentVo> findLiliBatchForTheory(StudentBDTO dto) throws Exception;
    
    
    

	/**
	 * 获取学员的驾校分配情况
	 * @param params 
	 * @return
	 */
    public List<StudentVo> findUnSchoolBatch(StudentBDTO dto) throws Exception;
    
    

	/**
	 * 获取驾校学员列表
	 * @param params 
	 * @return
	 */
    public List<StudentVo> findJxBatch(StudentBDTO dto) throws Exception;
    
    public List<StudentVo> findJxBatchForTheory(StudentBDTO dto) throws Exception;
    
    /**
     * 获取需要导出的学员数据,格式和findBatch一致,只是改了筛选条件
     * @param params 必含studentIds
     * @return
     */
    public List<StudentVo> findExportBatch(StudentBDTO dto) throws Exception;

	
    /**
     * 获取未绑定学员列表
     * @return
     */
    public List<StudentVo> findUnboundBatch(StudentBDTO dto) throws Exception;
    

    /**
     * 获取教练对应的学员Id列表
     * @return
     */
    public String[] findIdsByCoach(long coachId) throws Exception;

    /**
     * 获取教练对应的学员列表
     * @return
     */
    public List<StudentVo> findByCoach(StudentBDTO dto) throws Exception;
    
    public List<StudentVo> getStudent(StudentBDTO dto) throws Exception;
    
	public Long updateOne(Student student) throws Exception;
	
	public Long updateAuthState(HashMap<String, Object> params) throws Exception;
//	public Long updateAuthState(String ids,StudentAuthVo vo) throws Exception;

	public Long insertSelective(Student student) throws Exception;
	

	public Long findLiliBatchTotal(StudentBDTO dto) throws Exception;

	public Long findJxBatchTotal(StudentBDTO dto) throws Exception;
	
	public Long findLiliBatchTotalTheory(StudentBDTO dto) throws Exception;

	public Long findJxBatchTotalTheory(StudentBDTO dto) throws Exception;


	/**
	 * 通过学员ID和教练ID来查找关系绑定
	 * @param params
	 * @return
	 */
	public Long findSCRelation(long studentId) throws Exception;
	
	public Long findLiliMID(StudentBDTO dto) throws Exception;
	
	public Long updateLinState() throws Exception;

	public Long findJxMID(StudentBDTO dto) throws Exception;
	
	public Integer findTotalStudent(StudentBDTO dto) throws Exception;

	/**
	 * 更新学员和教练的关系
	 * @param params 包含c_coachId教练ID、学员id
	 * @return
	 */
	public Long updateSCRelation(StudentParam param) throws Exception;
	

	/**
	 * 删除教练和学员的关系
	 * @param params 包含学员ID
	 * @return
	 */
	public Long deleteSCRelation(Map<String, Object> param) throws Exception;

	/**
	 * 插入学员和教练的关系
	 * @param params 包含c_coachId教练要绑定的车和学员id
	 * @return
	 */
	public Long insertSCRelation(StudentParam param) throws Exception;

	
	public StudentAccount findStudentInfo(StudentBDTO StudentDto) throws Exception;
	

	/**
	 * 重置学员状态
	 * @param params
	 * @return
	 */
	public Long resetState(StudentBDTO dto) throws Exception;

	/**
	 * 分配驾校
	 * @param stuList
	 */
	public void updateSchoolId(List<Student> stuList) throws Exception;

	public List<Student> queryIsExistNum(List<Student> studentList);

	public List<Student> queryIsNotExist(List<Student> studentList);

	public Integer queryIsHandle(List<Student> studentList);

	public void updateBatchFlowNo(List<Student> studentList);

	public List<Student> queryIsNotExistNum(List<Student> studentList);
    
	/**
	 * 将该学员在关系表中的关系全部置为无效,即status=1
	 * @param id 学员Id
	 * @return
	 */
	public Long cancleSCRelation(long id);
	
	public List<StatisticsStudentProgress> queryStudentProgress(StatisticsStudentProgress progress);
	
	public List<StatisticsStudentProgress> queryStudentProgressBySchool(StatisticsStudentProgress progress);
	
	public List<StudentVo> findStudentByProgress(StatisticsStudentProgress progress) throws Exception;
	
	public Long findStudentByProgressTotal(StatisticsStudentProgress progress) throws Exception;
	
	public List<StudentVo> findStudentByProgressGreater(StatisticsStudentProgress progress) throws Exception;
	
	public Long findStudentByProgressGreaterTotal(StatisticsStudentProgress progress) throws Exception;
	
	public List<StudentVo> findStudentByProgressGreaterEqual(StatisticsStudentProgress progress) throws Exception;
	
	public Long findStudentByProgressGreaterEqualTotal(StatisticsStudentProgress progress) throws Exception;
	
	public List<StudentVo> findStudentByProgressLess(StatisticsStudentProgress progress) throws Exception;
	
	public Long findStudentByProgressLessTotal(StatisticsStudentProgress progress) throws Exception;
	
	/**
	 * 获取有班别学员列表
	 * @param params 
	 * @return
	 */
    public List<StudentVo> havePackageStudent(StudentBDTO dto) throws Exception;

    public Long findRelevancePackageMID(StudentBDTO dto) throws Exception;
    
    public Long findRelevancePackageTotal(StudentBDTO dto) throws Exception;
    
    

}
