package com.lili.school.manager.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.school.manager.CMSSchoolManager;
import com.lili.school.mapper.dao.SchoolMapper;
import com.lili.school.model.School;
import com.lili.school.model.SchoolAccountApply;
import com.lili.school.model.SchoolBDTO;
import com.lili.school.model.WechatEnrollPackage;
import com.lili.school.model.WechatEnrollPackageBDTO;

public class CMSSchoolManagerImpl implements CMSSchoolManager{

	@Autowired
	SchoolMapper schoolMapper;
	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public String findSchoolNameById(long schoolId) throws Exception {
		return schoolMapper.findSchoolNameById(schoolId);
	}

	@Override
	public PagedResult<School> findSchoolList(SchoolBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((schoolMapper.findSchoolList(dto)));
	}

	@Override
	public List<School> findSchoolArray(SchoolBDTO dto) throws Exception {
		return schoolMapper.findSchoolArray(dto);
	}

	@Override
	public void addSchool(SchoolBDTO dto) throws Exception {
		Long schoolId = schoolMapper.queryMaxIdAddOne();
		dto.setSchoolId(schoolId);
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL_ALL);
		schoolMapper.insertScholl(dto);
		
	}

	@Override
	public void updateSchool(SchoolBDTO dto)throws Exception {
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL +dto.getSchoolId());
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL_ALL);
		schoolMapper.updateScholInfo(dto);
	}

	@Override
	public Long findSchoolIdByName(String name)throws Exception {
		return schoolMapper.findSchoolIdByName(name);
	}
	
	
	
	@Override
	public School findSchoolById(long schoolId) throws Exception {
		return schoolMapper.findSchoolById(schoolId);
	}

	@Override
	public PagedResult<SchoolBDTO> findWxSchoolList(SchoolBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((schoolMapper.findWxSchoolList(dto)));
	}
	
	@Override
	public SchoolBDTO findWxSchoolById(int schoolId) throws Exception {
		SchoolBDTO wSchool=schoolMapper.findWxSchoolById(schoolId);
		return wSchool;
	}
	
	@Override
	public void addWxSchool(SchoolBDTO dto)throws Exception {
		schoolMapper.insertWxSchool(dto);
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL +dto.getSchoolId());
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL_ALL);
	}
	@Override
	public void updateWxSchool(SchoolBDTO dto)throws Exception {
		schoolMapper.updateWxSchool(dto);
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL + dto.getSchoolId());
		redisUtil.delete(REDISKEY.WECHAT_SCHOOL_ALL);
	}
	
	@Override
	public PagedResult<WechatEnrollPackage> findPackageList(WechatEnrollPackageBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(schoolMapper.findPackageList(dto));
	}
	
	@Override
	public WechatEnrollPackage findPackageById(String ttid) throws Exception {
		return schoolMapper.findPackageById(ttid);
	}
	
	@Override
	public void addPackage(WechatEnrollPackageBDTO dto)throws Exception {
		//班别的城市id和驾校id一致
		if(dto.getCityId()==null){ 
			int cityId=schoolMapper.findWxSchoolById(dto.getSchool_id()).getCityId();
			dto.setCityId(cityId);
		}
		int ttid = schoolMapper.queryPackageMaxIdAddOne();
		dto.setTtid(ttid);
		schoolMapper.insertPackage(dto);
		redisUtil.delete(REDISKEY.WECHAT_ENROLL_PACKAGE_LIST +dto.getSchool_id());
		
	}
	@Override
	public void updatePackage(WechatEnrollPackageBDTO dto) throws Exception{
		//班别的城市id和驾校id一致
		if(dto.getCityId()==null){
			int cityId=schoolMapper.findWxSchoolById(dto.getSchool_id()).getCityId();
			dto.setCityId(cityId);
		}
		schoolMapper.updatePackage(dto);
		redisUtil.delete(REDISKEY.WECHAT_ENROLL_PACKAGE +String.valueOf(dto.getTtid()));
		redisUtil.delete(REDISKEY.WECHAT_ENROLL_PACKAGE_LIST +dto.getSchool_id());
		
	}
	
	@Override
	public void updatePackageState(WechatEnrollPackageBDTO dto,String ttids) {
		try {
			String arg[]=ttids.split(",");
			for(int i=0;i<arg.length;i++){
				String ttid=arg[i];
				if(ttid==null || ttid.equals("")){
					continue;
				}
				dto.setTtid(Integer.parseInt(ttid));
				schoolMapper.updatePackageState(dto);
				
				redisUtil.delete(REDISKEY.WECHAT_ENROLL_PACKAGE +ttid);
				
				WechatEnrollPackage pack=schoolMapper.findPackageById(ttid);
				redisUtil.delete(REDISKEY.WECHAT_ENROLL_PACKAGE_LIST +pack.getSchoolId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addPackageStudent(int studentId,int ttid)throws Exception {
		
		schoolMapper.changeStudentPackage(studentId,ttid);
		redisUtil.delete(REDISKEY.STUDENT_INFO +studentId);
	}
	
	@Override
	public void applySchoolAccount(SchoolAccountApply apply) throws Exception {
		schoolMapper.insertSchoolApply(apply);
	}

	@Override
	public List<SchoolAccountApply> findSchoolApply(SchoolAccountApply apply)
			throws Exception {
		return schoolMapper.findApply(apply);
	}

	@Override
	public void updateSchoolApply(SchoolAccountApply apply) throws Exception {
		schoolMapper.updateSchoolApply(apply);
		
	}

	@Override
	public SchoolAccountApply getApply(SchoolAccountApply apply)
			throws Exception {
		return schoolMapper.getApply(apply);
	}

	@Override
	public int findApplyTotal(SchoolAccountApply apply) throws Exception {
		return schoolMapper.findApplyTotal(apply);
	}

	@Override
	public List<School> findAllSchool(SchoolBDTO dto){
		return schoolMapper.findSchoolArray(dto);
	}
	
}
