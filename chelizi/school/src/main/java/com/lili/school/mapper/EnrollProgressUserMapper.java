package com.lili.school.mapper;

import com.lili.school.dto.EnrollProgressUser;
import com.lili.school.dto.EnrollProgressUserExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EnrollProgressUserMapper {
    int countByExample(EnrollProgressUserExample example);

    int deleteByExample(EnrollProgressUserExample example);

    int deleteByPrimaryKey(Integer cpid);

    int insert(EnrollProgressUser record);

    int insertSelective(EnrollProgressUser record);

    List<EnrollProgressUser> selectByExample(EnrollProgressUserExample example);

    EnrollProgressUser selectByPrimaryKey(Integer cpid);

    int updateByExampleSelective(@Param("record") EnrollProgressUser record, @Param("example") EnrollProgressUserExample example);

    int updateByExample(@Param("record") EnrollProgressUser record, @Param("example") EnrollProgressUserExample example);

    int updateByPrimaryKeySelective(EnrollProgressUser record);

    int updateByPrimaryKey(EnrollProgressUser record);
    
	EnrollProgressUser selectByStudentIdStep(@Param("studentId")Long studentId, @Param("stepId")Integer stepId);
	
	EnrollProgressUser selectByStudentIdTtidOne(@Param("studentId")Long studentId, @Param("ttid")Integer ttid);
	
	EnrollProgressUser selectByStudentIdNextStep(@Param("studentId") Long studentId, @Param("nextStep")Integer nextStep);
    
	List<EnrollProgressUser> selectByStudentId(@Param("studentId") Long studentId);

	EnrollProgressUser selectByStudentIdLatest(@Param("studentId") Long studentId);
	
	int updateIsdelByStudentIdTtid(@Param("isdel")Byte isdel,@Param("studentId")Long studentId, @Param("ttid")Integer ttid);

	List<EnrollProgressUser> selectInfoByStudentId(@Param("studentId") Long studentId);
}