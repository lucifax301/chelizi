package com.lili.school.mapper;

import com.lili.school.dto.EnrollPackageTemplate;
import com.lili.school.dto.EnrollPackageTemplateExample;
import com.lili.school.dto.EnrollPackageTemplateWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EnrollPackageTemplateMapper {
    int countByExample(EnrollPackageTemplateExample example);

    int deleteByExample(EnrollPackageTemplateExample example);

    int deleteByPrimaryKey(Integer ttid);

    int insert(EnrollPackageTemplateWithBLOBs record);

    int insertSelective(EnrollPackageTemplateWithBLOBs record);

    List<EnrollPackageTemplateWithBLOBs> selectByExampleWithBLOBs(EnrollPackageTemplateExample example);

    List<EnrollPackageTemplate> selectByExample(EnrollPackageTemplateExample example);

    EnrollPackageTemplateWithBLOBs selectByPrimaryKey(Integer ttid);

    int updateByExampleSelective(@Param("record") EnrollPackageTemplateWithBLOBs record, @Param("example") EnrollPackageTemplateExample example);

    int updateByExampleWithBLOBs(@Param("record") EnrollPackageTemplateWithBLOBs record, @Param("example") EnrollPackageTemplateExample example);

    int updateByExample(@Param("record") EnrollPackageTemplate record, @Param("example") EnrollPackageTemplateExample example);

    int updateByPrimaryKeySelective(EnrollPackageTemplateWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(EnrollPackageTemplateWithBLOBs record);

    int updateByPrimaryKey(EnrollPackageTemplate record);
}