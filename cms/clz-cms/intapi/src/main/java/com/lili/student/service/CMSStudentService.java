package com.lili.student.service;

import java.util.Date;
import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.common.vo.ReqResult;
import com.lili.log.model.LogCommon;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.student.model.Student;
import com.lili.student.model.StudentAuthVo;
import com.lili.student.model.StudentBDTO;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.model.StudentStateVo;
import com.lili.student.model.StudentVo;


public  interface CMSStudentService  {

	public   ResponseMessage findLiliBatch(StudentBDTO dto) throws Exception;

	public   ResponseMessage findJxBatch(StudentBDTO dto) throws Exception;

	public   ResponseMessage findLiliBatchTheory(StudentBDTO dto,int theoryId) throws Exception;

	public   ResponseMessage findJxBatchTheory(StudentBDTO dto,int theoryId) throws Exception;

	
	public   List<StudentVo> findExportSource(StudentBDTO dto) throws Exception;

	public   ResponseMessage findCerBatch(StudentBDTO dto) throws Exception;

	public   ResponseMessage findLinBatch(StudentBDTO dto) throws Exception;
	
	public   ResponseMessage findImgById(long studentId) throws Exception;
	/**
	 * 获取未绑定教练的学员
	 * @param params
	 * @return
	 */
	public   ResponseMessage findUnboundBatch(StudentBDTO dto) throws Exception;
	
	/**
	 * 获取学员的驾校分配情况
	 * @param params 
	 * @return
	 */
	public   ResponseMessage findUnSchoolBatch(StudentBDTO dto) throws Exception;
	
    /**
     * 获取教练对应的学员列表
     * @return
     */
	public   ResponseMessage findStudentByCoach(StudentBDTO dto) throws Exception;
	
	public   ResponseMessage getStudent(StudentBDTO dto) throws Exception;
	
	public  ResponseMessage findOne(StudentNBDTO dto) throws Exception;

	/**
	 * 
	 * @param id 学员id
	 * @return
	 * @throws Exception
	 */
	public  StudentAuthVo findStudentAuth(long id) throws Exception;

	public  Student findByPhone(String phoneNum) throws Exception;

	public  List<Student> findByNums(Student student) throws Exception;
	
	public  ResponseMessage updateOne(LogCommon token,Student student) throws Exception;

	public  ResponseMessage updateAuthState(LogCommon token,int type,String ids,StudentAuthVo vo) throws Exception;

	public  Long updateLinState() throws Exception;
	
	public  ResponseMessage insertOne(LogCommon token,Student student) throws Exception;
	
	/**
	 * 
	 * @param params 必含学员ID
	 * @return 学员详情处,关联教练的报表实体
	 */
	public  ResponseMessage findCCO(long studentId) throws Exception;
		/**
	 * 更新学员和教练的关系
	 * @param params 包含c_coachId教练ID、学员id
	 * @return
	 */
	public  ResponseMessage updateSCRelation(LogCommon token,long o_coachId,long c_coachId,long id) throws Exception;
	

	/**
	 * 删除教练和学员的关系
	 * @param params 包含学员ID
	 * @return
	 */
	public  ResponseMessage deleteSCRelation(LogCommon token,long coachId,long id) throws Exception;

	/**
	 * 插入学员和教练的关系
	 * @param params 包含c_coachId教练要绑定的车和学员id
	 * @return
	 */
	public  ResponseMessage insertSCRelation(long c_coachId,long id) throws Exception;
	
	public  String findStudentInfo(StudentBDTO vo, String isEarning, String isBalance, String operateType, String channel) throws Exception;

	/**
	 * 重置学员状态
	 * @param params
	 * @return
	 */
	public  ResponseMessage resetState(LogCommon token,int ttid,String studentIds,StudentStateVo vo) throws Exception;

	/**
	 * 分配驾校
	 * @param stuList
	 */
	public ResponseMessage allot(String studentIdList, String region, String schoolId, String schoolName) throws Exception;

	public Long updateStates(String recombinateStrByComma) throws Exception;
	
	public  Integer queryTotalStudent(StudentBDTO dto) throws Exception;

	public List<Student> queryIsExistNum(List<Student> studentList) throws Exception;
	
	public List<Student> queryIsNotExistNum(List<Student> studentList) throws Exception;

	public List<Student> queryIsNotExist(List<Student> studentList) throws Exception;

	public Integer queryIsHandle(List<Student> studentList) throws Exception;

	public void updateBatchFlowNo(List<Student> studentList, List<Student> notFlowStudent,  LogCommon logCommon) throws Exception;
	
	public ReqResult findPackDetail(String userId, String userType, String ttid) throws Exception;

	public Long insertStudentInfo(Student student) throws Exception;
	
    /**
     * 锁定学员
     * @param userId    用户id
     * @param state        0-正常；1-临时封号；2-永久封号
     * @param reviveTime    解除封号的时间
     * @param note    封号理由
     * @return 0-操作成功；1-操作失败
     */
	public ResponseMessage lockStudent(long userId,int state,Date reviveTime,String note) throws Exception;
	

    /**
     * 批量锁定学员
     * @param ids    用户id
     * @param state        0-正常；1-临时封号；2-永久封号
     * @param reviveTime    解除封号的时间
     * @param note    封号理由
     * @return 0-操作成功；1-操作失败
     */
	public ResponseMessage lockStudents(String ids,int state,Date reviveTime,String note) throws Exception;
	/*
	 * 学员进度数据
	 */
	public ResponseMessage studentProgress(StatisticsStudentProgress vo) throws Exception;
	/**
	 * 进度学员数据
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage findProgressStudent(StatisticsStudentProgress vo) throws Exception ;
	/*
	 * 学员进度统计
	 */
	public List<StatisticsStudentProgress> studentProgressData(StatisticsStudentProgress vo) throws Exception ;
	/**
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage studentProgressReport(StatisticsStudentProgress vo) throws Exception ;
	
	public List<StudentVo> findProgressStudentData(StatisticsStudentProgress vo) throws Exception ;
	
	public  ResponseMessage havePackageStudent(StudentBDTO dto) throws Exception;
	

}
