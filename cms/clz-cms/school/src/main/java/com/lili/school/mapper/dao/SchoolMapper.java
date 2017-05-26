/**
 * 
 */
package com.lili.school.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.school.model.School;
import com.lili.school.model.SchoolAccountApply;
import com.lili.school.model.SchoolBDTO;
import com.lili.school.model.WechatEnrollPackage;
import com.lili.school.model.WechatEnrollPackageBDTO;

public interface SchoolMapper {

	/**
	 * 根据驾校ID获取驾校名
	 * @param params 必包含驾校ID：schoolId
	 * @return
	 */
    public String findSchoolNameById(long schoolId);
    
	public List<School> findSchoolList(SchoolBDTO dto);
	
	public List<School> findSchoolArray(SchoolBDTO dto);
	
	public School findSchoolById(long schoolId);

	public void insertScholl(SchoolBDTO dto);

	public void updateScholInfo(SchoolBDTO dto);

	public Long queryMaxIdAddOne();
	
	public Long findSchoolIdByName(String name);
	
	public List<SchoolBDTO> findWxSchoolList(SchoolBDTO school);
	
	public SchoolBDTO findWxSchoolById(int schoolId);
	
	public void insertWxSchool(SchoolBDTO school);

	public void updateWxSchool(SchoolBDTO school);
	
    public List<WechatEnrollPackage> findPackageList(WechatEnrollPackageBDTO wBdto);
    
    public WechatEnrollPackage findPackageById(String ttid);
	
	public int queryPackageMaxIdAddOne();
	
	public void insertPackage(WechatEnrollPackageBDTO wBdto);

	public void updatePackage(WechatEnrollPackageBDTO wBdto);
	
	public void updatePackageState(WechatEnrollPackageBDTO wBdto);
	
	public void changeStudentPackage(@Param("studentId")int studentId,@Param("ttid")int ttid);
	

	/**
	 * add 2016-08-22
	 */
	public void insertSchoolApply(SchoolAccountApply apply);
	
	public List<SchoolAccountApply> findApply(SchoolAccountApply apply);
	
	public int findApplyTotal(SchoolAccountApply apply);
	
	public void updateSchoolApply(SchoolAccountApply apply);
	
	public SchoolAccountApply getApply(SchoolAccountApply apply);
	
	
	
}
