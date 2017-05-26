package com.lili.school.mapper;

import com.lili.school.dto.EnrollProgressTemplate;
import com.lili.school.dto.EnrollProgressTemplateExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EnrollProgressTemplateMapper {
    int countByExample(EnrollProgressTemplateExample example);

    int deleteByExample(EnrollProgressTemplateExample example);

    int deleteByPrimaryKey(Integer cpid);

    int insert(EnrollProgressTemplate record);

    int insertSelective(EnrollProgressTemplate record);

    List<EnrollProgressTemplate> selectByExample(EnrollProgressTemplateExample example);

    EnrollProgressTemplate selectByPrimaryKey(Integer cpid);

    int updateByExampleSelective(@Param("record") EnrollProgressTemplate record, @Param("example") EnrollProgressTemplateExample example);

    int updateByExample(@Param("record") EnrollProgressTemplate record, @Param("example") EnrollProgressTemplateExample example);

    int updateByPrimaryKeySelective(EnrollProgressTemplate record);

    int updateByPrimaryKey(EnrollProgressTemplate record);
    
	EnrollProgressTemplate selectByTTidStepid(@Param("ttid") Integer ttid, @Param("stepId") Integer stepId);

	EnrollProgressTemplate selectByTTidNextStep(@Param("ttid")Integer ttid, @Param("nextStep")Integer nextStep);

    List<EnrollProgressTemplate> selectPredisplayByTTidStepid(@Param("ttid") Integer ttid, @Param("stepId") Integer stepId);
	
}