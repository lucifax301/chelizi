package com.lili.student.manager.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.common.util.MD5Security;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.student.manager.CMSStudentManager;
import com.lili.student.mapper.dao.common.EnrollProgressUserMapper;
import com.lili.student.mapper.dao.common.StudentMapper;
import com.lili.student.model.Student;
import com.lili.student.model.StudentAccount;
import com.lili.student.model.StudentAuthVo;
import com.lili.student.model.StudentBDTO;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.model.StudentParam;
import com.lili.student.model.StudentVo;

public class CMSStudentManagerImpl implements CMSStudentManager{

	@Autowired
	StudentMapper studentMapper;
	
	@Autowired
	EnrollProgressUserMapper enrollProgressUserMapper;
	
	@Override
	public Student findOne(StudentNBDTO dto) throws Exception {
		return studentMapper.findOne(dto);
	}

	@Override
	public StudentAuthVo findAuthState(long id) throws Exception {
		return studentMapper.findAuthState(id);
	}

	@Override
	public Long updateStates(String studentIds) throws Exception {
		return studentMapper.updateStates(studentIds);
	}

	@Override
	public StudentAuthVo findImgById(long studentId) throws Exception {
		return studentMapper.findImgById(studentId);
	}

	@Override
	public PagedResult<StudentAuthVo> findCerBatch(StudentBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(studentMapper.findCerBatch(dto));
	}

	@Override
	public PagedResult<StudentAuthVo> findLinBatch(StudentBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(studentMapper.findLinBatch(dto));
	}

	@Override
	public Student findByPhone(String phoneNum) throws Exception {
		return studentMapper.findByPhone(phoneNum);
	}
	
	

	@Override
	public Student findByIdNumber(String idNumber) throws Exception {
		return studentMapper.findByIdNumber(idNumber);
	}

	@Override
	public PagedResult<StudentVo> findLiliBatch(StudentBDTO dto) throws Exception {
		int pageNo = dto.getPageNo()<=0?PageUtil.DEFAULT_PAGE_NO:dto.getPageNo();
		int pageSize = dto.getPageSize()<=0?PageUtil.DEFAULT_PAGE_SIZE:dto.getPageSize();
		int maxRowNum = (pageNo - 1) * pageSize;
		int minRowNum = pageNo * pageSize;
		
		dto.setStudentRowNum(maxRowNum);
		Long max_id = studentMapper.findLiliMID(dto);
		dto.setStudentRowNum(minRowNum);
		Long min_id = studentMapper.findLiliMID(dto);
		
		
		dto.setMax_id((max_id == null?Long.MAX_VALUE:max_id));
		dto.setMin_id(min_id == null?0:min_id);

		Long total = studentMapper.findLiliBatchTotal(dto);
		List<StudentVo> studentBatch = studentMapper.findLiliBatch(dto);
		
		PagedResult<StudentVo> result = new PagedResult<StudentVo>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setDataList(studentBatch);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
        
		return result;
	}
	
	@Override
	public PagedResult<StudentVo> findLiliBatchTheory(StudentBDTO dto,int theoryId) throws Exception {
		int pageNo = dto.getPageNo()<=0?PageUtil.DEFAULT_PAGE_NO:dto.getPageNo();
		int pageSize = dto.getPageSize()<=0?PageUtil.DEFAULT_PAGE_SIZE:dto.getPageSize();
		int maxRowNum = (pageNo - 1) * pageSize;
		int minRowNum = pageNo * pageSize;
		
		dto.setStudentRowNum(maxRowNum);
		Long max_id = studentMapper.findLiliMID(dto);
		dto.setStudentRowNum(minRowNum);
		Long min_id = studentMapper.findLiliMID(dto);
		
		
		dto.setMax_id((max_id == null?Long.MAX_VALUE:max_id));
		dto.setMin_id(min_id == null?0:min_id);
		dto.setTheoryId(theoryId);
		Long total = studentMapper.findLiliBatchTotalTheory(dto);
		List<StudentVo> studentBatch = studentMapper.findLiliBatchForTheory(dto);
		
		PagedResult<StudentVo> result = new PagedResult<StudentVo>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setDataList(studentBatch);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
        
		return result;
	}

	@Override
	public PagedResult<StudentVo> findUnSchoolBatch(StudentBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((studentMapper.findUnSchoolBatch(dto)));
	}

	@Override
	public PagedResult<StudentVo> findJxBatch(StudentBDTO dto) throws Exception {
		int pageNo = dto.getPageNo()<=0?PageUtil.DEFAULT_PAGE_NO:dto.getPageNo();
		int pageSize = dto.getPageSize()<=0?PageUtil.DEFAULT_PAGE_SIZE:dto.getPageSize();
		int maxRowNum = (pageNo - 1) * pageSize;
		int minRowNum = pageNo * pageSize;
		
		dto.setStudentRowNum(maxRowNum);
		Long max_id = studentMapper.findJxMID(dto);
		dto.setStudentRowNum(minRowNum);
		Long min_id = studentMapper.findJxMID(dto);
		
		
		dto.setMax_id((max_id == null?Long.MAX_VALUE:max_id));
		dto.setMin_id(min_id == null?0:min_id);

		Long total = studentMapper.findJxBatchTotal(dto);
		List<StudentVo> studentBatch = studentMapper.findJxBatch(dto);
		
		PagedResult<StudentVo> result = new PagedResult<StudentVo>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setDataList(studentBatch);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
        
		return result;
	}
	
	@Override
	public PagedResult<StudentVo> findJxBatchTheory(StudentBDTO dto,int theoryId) throws Exception {
		int pageNo = dto.getPageNo()<=0?PageUtil.DEFAULT_PAGE_NO:dto.getPageNo();
		int pageSize = dto.getPageSize()<=0?PageUtil.DEFAULT_PAGE_SIZE:dto.getPageSize();
		int maxRowNum = (pageNo - 1) * pageSize;
		int minRowNum = pageNo * pageSize;
		
		dto.setStudentRowNum(maxRowNum);
		Long max_id = studentMapper.findJxMID(dto);
		dto.setStudentRowNum(minRowNum);
		Long min_id = studentMapper.findJxMID(dto);
		
		
		dto.setMax_id((max_id == null?Long.MAX_VALUE:max_id));
		dto.setMin_id(min_id == null?0:min_id);
		dto.setTheoryId(theoryId);
		Long total = studentMapper.findJxBatchTotalTheory(dto);
		List<StudentVo> studentBatch = studentMapper.findJxBatchForTheory(dto);
		
		PagedResult<StudentVo> result = new PagedResult<StudentVo>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setDataList(studentBatch);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
        
		return result;
	}

	@Override
	public List<StudentVo> findExportBatch(StudentBDTO dto) throws Exception {

//		dto.setStudentRowNum(0);
//		Long max_id = studentMapper.findJxMID(dto);
//		dto.setStudentRowNum(1000);
//		Long min_id = studentMapper.findJxMID(dto);
//		
//		
//		dto.setMax_id((max_id == null?Long.MAX_VALUE:max_id));
//		dto.setMin_id(min_id == null?0:min_id);
		
		return studentMapper.findExportBatch(dto);
	}

	@Override
	public PagedResult<StudentVo> findUnboundBatch(StudentBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(studentMapper.findUnboundBatch(dto));
	}

	@Override
	public String[] findIdsByCoach(long coachId) throws Exception {
		return studentMapper.findIdsByCoach(coachId);
	}

	@Override
	public PagedResult<StudentVo> findByCoach(StudentBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(studentMapper.findByCoach(dto));
	}
	
	@Override
	public PagedResult<StudentVo> getStudent(StudentBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(studentMapper.getStudent(dto));
	}

	@Override
	public Long updateOne(Student student) throws Exception {
		return studentMapper.updateOne(student);
	}

	@Override
	public Long updateAuthState(String ids, StudentAuthVo vo) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("remark", vo.getRemark());
		params.put("state", vo.getState());
		return studentMapper.updateAuthState(params);
	}

	@Override
	public Long insertSelective(Student student) throws Exception {
		if(student != null){
			student.setPassword(MD5Security.md5("123456"));
		}
		return studentMapper.insertSelective(student);
	}

	@Override
	public Long findLiliBatchTotal(StudentBDTO dto) throws Exception {
		return studentMapper.findLiliBatchTotal(dto);
	}

	@Override
	public Long findJxBatchTotal(StudentBDTO dto) throws Exception {
		return studentMapper.findJxBatchTotal(dto);
	}

	@Override
	public Long findSCRelation(long studentId) throws Exception {
		return studentMapper.findSCRelation(studentId);
	}

	@Override
	public Long findLiliMID(StudentBDTO dto) throws Exception {
		return studentMapper.findLiliMID(dto);
	}

	@Override
	public Long updateLinState() throws Exception {
		return studentMapper.updateLinState();
	}

	@Override
	public Long findJxMID(StudentBDTO dto) throws Exception {
		return studentMapper.findJxMID(dto);
	}

	@Override
	public Integer findTotalStudent(StudentBDTO dto) throws Exception {
		return studentMapper.findTotalStudent(dto);
	}

	@Override
	public Long updateSCRelation(long id, long c_coachId, long o_coachId)
			throws Exception {
		StudentParam studentParam = new StudentParam();
		studentParam.setId(id);
		studentParam.setC_coachId(c_coachId);
		studentParam.setO_coachId(o_coachId);
		
		return studentMapper.updateSCRelation(studentParam);
	}

	@Override
	public Long deleteSCRelation(long id, long coachId) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("coachId", coachId);
		return studentMapper.deleteSCRelation(param);
	}

	@Override
	public Long insertSCRelation(long id, long c_coachId) throws Exception {
		StudentParam studentParam = new StudentParam();
		studentParam.setId(id);
		studentParam.setC_coachId(c_coachId);
		
		return studentMapper.insertSCRelation(studentParam);
	}

	@Override
	public StudentAccount findStudentInfo(StudentBDTO StudentDto) throws Exception {
		return studentMapper.findStudentInfo(StudentDto);
	}

	@Override
	public Long resetState(String studentIds, int applyexam, int applystate)
			throws Exception {
		StudentBDTO dto = new StudentBDTO();
		dto.setStudentIds(studentIds);
		dto.setApplyexam(applyexam);
		dto.setApplystate(applystate);
		return studentMapper.resetState(dto);
	}

	@Override
	public void updateSchoolId(List<Student> stuList) throws Exception {
		try {
			studentMapper.updateSchoolId(stuList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Student> findByNums(Student student)
			throws Exception {
		return studentMapper.findByNums(student);
	}

	@Override
	public List<Student> queryIsExistNum(List<Student> studentList) {
		return studentMapper.queryIsExistNum(studentList);
	}

	@Override
	public List<Student> queryIsNotExist(List<Student> studentList) {
		return studentMapper.queryIsNotExist(studentList);
	}

	@Override
	public Integer queryIsHandle(List<Student> studentList) {
		return studentMapper.queryIsHandle(studentList);
	}

	@Override
	public void updateBatchFlowNo(List<Student> studentList) {
		try {
			studentMapper.updateBatchFlowNo(studentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Student> queryIsNotExistNum(List<Student> studentList) {
		return studentMapper.queryIsNotExistNum(studentList);
	}

	@Override
	public Long cancleSCRelation(long id) {
		return studentMapper.cancleSCRelation(id);
	}

	@Override
	public List<StatisticsStudentProgress> queryStudentProgress(
			StatisticsStudentProgress progress) {
		return studentMapper.queryStudentProgress(progress);
	}

	@Override
	public List<StatisticsStudentProgress> queryStudentProgressBySchool(
			StatisticsStudentProgress progress) {
		return studentMapper.queryStudentProgressBySchool(progress);
	}
	

	@Override
	public PagedResult<StudentVo> findProgressStudent(StatisticsStudentProgress dto) throws Exception {
		int pageNo = dto.getPageNo()<=0?PageUtil.DEFAULT_PAGE_NO:dto.getPageNo();
		int pageSize = dto.getPageSize()<=0?PageUtil.DEFAULT_PAGE_SIZE:dto.getPageSize();
		int maxRowNum = (pageNo - 1) * pageSize;
		int minRowNum = pageNo * pageSize;
		
		dto.setStartIndex(maxRowNum);
		dto.setPageSize(pageSize);

		Long total = 0L;
		List<StudentVo> studentBatch = null;
		
		if ((dto.getApplyexam() == 1 && dto.getApplystate() == 0) || dto.getApplyexam() < 1) {
			total = studentMapper.findStudentByProgressLessTotal(dto);
			studentBatch = studentMapper.findStudentByProgressLess(dto);
		} else if (dto.getApplystate() == 101 || dto.getApplyexam() == 3 
				|| (dto.getApplyexam() == 4 && dto.getApplystate() == 0)
				|| (dto.getApplyexam() == 5 && dto.getApplystate() == 0)
				|| (dto.getApplyexam() == 6 && dto.getApplystate() == 1)
				|| (dto.getApplyexam() == 301 && dto.getApplystate() == 1)
				|| (dto.getApplyexam() == 401 && dto.getApplystate() == 1)
				|| (dto.getApplyexam() == 601 && dto.getApplystate() == 1)) {
			total = studentMapper.findStudentByProgressTotal(dto);
			studentBatch = studentMapper.findStudentByProgress(dto);
		} else if ((dto.getApplyexam() == 501 && dto.getApplystate() == 1)
				|| (dto.getApplyexam() == 101 && dto.getApplystate() == 1)
				|| (dto.getApplyexam() == 6 && dto.getApplystate() == 0)){
			total = studentMapper.findStudentByProgressGreaterEqualTotal(dto);
			studentBatch = studentMapper.findStudentByProgressGreaterEqual(dto);
		} else {
			//301_100显示为302_0，中间过度状态全部显示为最终状态
			//401_100显示为402_0，中间过度状态全部显示为最终状态
			//601_100显示为602_0，中间过度状态全部显示为最终状态
			//701_100显示为702_0，中间过度状态全部显示为最终状态
			if ((dto.getApplyexam() == 302 || dto.getApplyexam() == 402 || dto.getApplyexam() == 602 || dto.getApplyexam() == 702) 
					&& dto.getApplystate() == 0) {
				dto.setApplyexam(dto.getApplyexam() - 1);
				dto.setApplystate(100);
			}
			total = studentMapper.findStudentByProgressGreaterTotal(dto);
			studentBatch = studentMapper.findStudentByProgressGreater(dto);
		}
		
		PagedResult<StudentVo> result = new PagedResult<StudentVo>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setDataList(studentBatch);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
        
		return result;
	}

	@Override
	public PagedResult<StudentVo> havePackageStudent(StudentBDTO dto) throws Exception {
		int pageNo = dto.getPageNo()<=0?PageUtil.DEFAULT_PAGE_NO:dto.getPageNo();
		int pageSize = dto.getPageSize()<=0?PageUtil.DEFAULT_PAGE_SIZE:dto.getPageSize();
		int maxRowNum = (pageNo - 1) * pageSize;
		int minRowNum = pageNo * pageSize;
		
		dto.setStudentRowNum(maxRowNum);
		Long max_id = studentMapper.findRelevancePackageMID(dto);
		dto.setStudentRowNum(minRowNum);
		Long min_id = studentMapper.findRelevancePackageMID(dto);
		
		
		dto.setMax_id((max_id == null?Long.MAX_VALUE:max_id));
		dto.setMin_id(min_id == null?0:min_id);

		Long total = studentMapper.findRelevancePackageTotal(dto);
		List<StudentVo> studentBatch = studentMapper.havePackageStudent(dto);
		
		PagedResult<StudentVo> result = new PagedResult<StudentVo>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setDataList(studentBatch);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
        
		return result;

	}
	
	@Override
	public List<StudentVo> findProgressStudentData(StatisticsStudentProgress dto) throws Exception {
				Long total = studentMapper.findStudentByProgressTotal(dto);
		dto.setPageSize(total.intValue());
		dto.setStartIndex(0);
		List<StudentVo> studentBatch = studentMapper.findStudentByProgress(dto);
	    
		return studentBatch;
	}
	
	

	
}
