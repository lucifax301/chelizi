package com.lili.school.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.school.model.School;
import com.lili.school.model.SchoolAccountApply;
import com.lili.school.model.SchoolBDTO;
import com.lili.school.model.WechatEnrollPackage;
import com.lili.school.model.WechatEnrollPackageBDTO;

public interface CMSSchoolManager {
    

	/**
	 * 根据驾校ID获取驾校名
	 * @param params 必包含驾校ID：schoolId
	 * @return
	 */
    public String findSchoolNameById(long schoolId) throws Exception;
    
	public PagedResult<School> findSchoolList(SchoolBDTO dto) throws Exception;
	
	public List<School> findSchoolArray(SchoolBDTO dto) throws Exception;
	
	public School findSchoolById(long schoolId) throws Exception;

	public void addSchool(SchoolBDTO dto) throws Exception;

	public void updateSchool(SchoolBDTO dto) throws Exception;
	
	public Long findSchoolIdByName(String name) throws Exception;
	
	public PagedResult<SchoolBDTO> findWxSchoolList(SchoolBDTO dto) throws Exception;
	
	public  SchoolBDTO findWxSchoolById(int schoolId) throws Exception;
	
	public void addWxSchool(SchoolBDTO dto) throws Exception;

	public void updateWxSchool(SchoolBDTO dto) throws Exception;
	
    public PagedResult<WechatEnrollPackage> findPackageList(WechatEnrollPackageBDTO dto) throws Exception;
    
    public WechatEnrollPackage findPackageById(String ttid) throws Exception;
	
	public void addPackage(WechatEnrollPackageBDTO dto) throws Exception;

	public void updatePackage(WechatEnrollPackageBDTO dto) throws Exception;
	
	public void updatePackageState(WechatEnrollPackageBDTO dto,String ttids) throws Exception;
	
	public void addPackageStudent(int studentId,int ttid) throws Exception ;
	
	public void applySchoolAccount(SchoolAccountApply apply) throws Exception;
	
	public List<SchoolAccountApply> findSchoolApply(SchoolAccountApply apply) throws Exception;
	
	public int findApplyTotal(SchoolAccountApply apply) throws Exception;
	
	public void updateSchoolApply(SchoolAccountApply apply) throws Exception;
	
	public SchoolAccountApply getApply(SchoolAccountApply apply) throws Exception;

	public List<School> findAllSchool(SchoolBDTO dto) throws Exception;
}
