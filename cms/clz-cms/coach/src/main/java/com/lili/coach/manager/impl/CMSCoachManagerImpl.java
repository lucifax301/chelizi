package com.lili.coach.manager.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.PageUtil;
import com.lili.coach.manager.CMSCoachManager;
import com.lili.coach.mapper.dao.CoachCarMapper;
import com.lili.coach.mapper.dao.CoachMapper;
import com.lili.coach.model.CCOVo;
import com.lili.coach.model.Coach;
import com.lili.coach.model.CoachAccount;
import com.lili.coach.model.CoachAccountVo;
import com.lili.coach.model.CoachBDTO;
import com.lili.coach.model.CoachCar;
import com.lili.coach.model.CoachRegist;
import com.lili.coach.model.CoachVo;
import com.lili.common.util.MD5Security;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.student.model.StudentNBDTO;
import com.lili.student.model.StudentVo;

public class CMSCoachManagerImpl implements CMSCoachManager{

	@Autowired
	CoachMapper coachMapper;
	
	@Autowired
	CoachCarMapper coachCarMapper;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public Coach findOne(long coachId) throws Exception {
		return coachMapper.findOne(coachId);
	}

	@Override
	public Coach findByPhone(String phoneNum) throws Exception {
		return coachMapper.findByPhone(phoneNum);
	}

	@Override
	public Long updateStates(String coachIds) throws Exception {
		return coachMapper.updateStates(coachIds);
	}

	@Override
	public List<CCOVo> findCoachCar(long coachId) throws Exception {
		return coachMapper.findCoachCar(coachId);
	}

	@Override
	public Coach findOneDetails(long coachId) throws Exception {
		return coachMapper.findOneDetails(coachId);
	}

	@Override
	public PagedResult<CoachVo> findBatch(CoachBDTO dto) throws Exception {
		int pageNo = dto.getPageNo()<=0?PageUtil.DEFAULT_PAGE_NO:dto.getPageNo();
		int pageSize = dto.getPageSize()<=0?PageUtil.DEFAULT_PAGE_SIZE:dto.getPageSize();
		int maxRowNum = (pageNo - 1) * pageSize;
		int minRowNum = pageNo * pageSize;
		
		dto.setRowNum(maxRowNum);
		Long max_id = coachMapper.findMID(dto);
		dto.setRowNum(minRowNum);
		Long min_id = coachMapper.findMID(dto);
		
		
		dto.setMax_id((max_id == null?Long.MAX_VALUE:max_id));
		dto.setMin_id(min_id == null?0:min_id);

		Long total = coachMapper.findBatchTotal(dto);
		List<CoachVo> batch = coachMapper.findBatch(dto);
		
		PagedResult<CoachVo> result = new PagedResult<CoachVo>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setDataList(batch);
        result.setTotal(total);
        result.setPages((total/pageSize)+1);
		return result;
//		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
//		return BeanUtil.toPagedResult((coachMapper.findBatch(dto)));
	}

	@Override
	public List<CoachVo> findExportBatch(CoachBDTO dto) throws Exception {
		dto.setRowNum(0);
		Long max_id = coachMapper.findMID(dto);
		dto.setRowNum(1000);
		Long min_id = coachMapper.findMID(dto);

		dto.setMax_id((max_id == null?Long.MAX_VALUE:max_id));
		dto.setMin_id(min_id == null?0:min_id);

		return coachMapper.findBatch(dto);
	}

	@Override
	public CoachAccountVo findAccountVo() throws Exception {
		return coachMapper.findAccountVo();
	}

	@Override
	public long updateOne(Coach coach) throws Exception {
		return coachMapper.updateOne(coach);
	}

	@Override
	public long insertSelective(Coach coach) throws Exception {
		if(coach != null){
			coach.setPassword(MD5Security.md5("123456"));
		}
		return coachMapper.insertSelective(coach);
	}

	@Override
	public Integer findTotalCoach(CoachBDTO dto) throws Exception {
		return coachMapper.findTotalCoach(dto);
	}

	@Override
	public long insertCSRelation(List<StudentVo> list) throws Exception {
		return coachMapper.insertCSRelation(list);
	}

	@Override
	public long updateCCRelation(long c_carId, long o_carId, long coachId)
			throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("c_carId", c_carId);
		param.put("o_carId", o_carId);
		param.put("coachId", coachId);
		redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId);
		redisUtil.delete(REDISKEY.COACH_VO + coachId);
		redisUtil.delete(REDISKEY.COACH + coachId);
		return coachMapper.updateCCRelation(param);
	}

	@Override
	public Long updateOCCRelation(long c_carId, long coachId) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("c_carId", c_carId);
		param.put("coachId", coachId);
		redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId);
		redisUtil.delete(REDISKEY.COACH_VO + coachId);
		redisUtil.delete(REDISKEY.COACH + coachId);
		return coachMapper.updateOCCRelation(param);
	}

	@Override
	public long deleteCCRelation(long coachId, long carId) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("carId", carId);
		param.put("coachId", coachId);
		redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId);
		redisUtil.delete(REDISKEY.COACH_VO + coachId);
		redisUtil.delete(REDISKEY.COACH + coachId);
		return coachMapper.deleteCCRelation(param);
	}

	@Override
	public Long insertCCRelation(long c_carId, long coachId) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("c_carId", c_carId);
		param.put("coachId", coachId);
		redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId);
		redisUtil.delete(REDISKEY.COACH_VO + coachId);
		redisUtil.delete(REDISKEY.COACH + coachId);
		return coachMapper.insertCCRelation(param);
	}

	@Override
	public Integer findCCRelation(long coachId, long carId) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("carId", carId);
		param.put("coachId", coachId);
		return coachMapper.findCCRelation(param);
	}

	@Override
	public CoachAccount findCoachInfo(CoachBDTO dto) throws Exception {
		dto.setStartTimeW(DateUtil.getStartDate());
		dto.setEndTimeW(DateUtil.getEndDate());
		return coachMapper.findCoachInfo(dto);
	}

	@Override
	public Long findMID(CoachBDTO dto) throws Exception {
		return coachMapper.findMID(dto);
	}

	@Override
	public long findBatchTotal(CoachBDTO dto) throws Exception {
		return coachMapper.findBatchTotal(dto);
	}

	@Override
	public List<Coach> findByNums(Coach coach) throws Exception {
		return coachMapper.findByNums(coach);
	}
	
	@Override
	public PagedResult<Coach> getCoach(CoachBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(coachMapper.getCoach(dto));
	}

	@Override
	public Long delSC(StudentNBDTO dto) throws Exception {
		return coachMapper.delSC(dto);
	}

	@Override
	public Long insertSC(StudentNBDTO dto) throws Exception {
		return coachMapper.insertSC(dto);
	}

	@Override
	public Long updateSC(StudentNBDTO dto) throws Exception {
		return coachMapper.updateSC(dto);
	}

	public void addCoachStatTime(Coach coach) throws Exception{
		coachMapper.addCoachTeachTime(coach);
	}

	@Override
	public int getCoachClassTime(CoachBDTO dto) throws Exception {
		return coachMapper.getCoachClassTime(dto);
	}

	@Override
	public int getCoachClassCount(CoachBDTO dto) throws Exception {
		return coachMapper.getCoachClassCount(dto);
	}
	
	@Override
	public int getCoachStudentCount(CoachBDTO dto) throws Exception{
		return coachMapper.getCoachStudentCount(dto);
	}

	@Override
	public PagedResult<CoachRegist> queryRegCoachList(CoachBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(coachMapper.queryRegCoachList(dto));
	}

	@Override
	public Coach queryRegCoachDetail(CoachBDTO dto) {
		return coachMapper.queryRegCoachDetail(dto);
	}

	@Override
	public List<CoachCar> queryCoachCarList(Long coachId) {
		return coachCarMapper.queryCoachCarList(coachId);
	}

	@Override
	public Long checkRegCoach(CoachBDTO dto) {
		return coachMapper.updateRegCoach(dto);
	}

}
