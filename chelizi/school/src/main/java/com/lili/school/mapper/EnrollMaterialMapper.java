package com.lili.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.school.dto.EnrollMaterial;
import com.lili.school.dto.EnrollMaterialExample;

public interface EnrollMaterialMapper {
    int countByExample(EnrollMaterialExample example);

    int deleteByExample(EnrollMaterialExample example);

    int deleteByPrimaryKey(Integer tmid);

    int insert(EnrollMaterial record);

    int insertSelective(EnrollMaterial record);

    List<EnrollMaterial> selectByExample(EnrollMaterialExample example);

    EnrollMaterial selectByPrimaryKey(Integer tmid);

    int updateByExampleSelective(@Param("record") EnrollMaterial record, @Param("example") EnrollMaterialExample example);

    int updateByExample(@Param("record") EnrollMaterial record, @Param("example") EnrollMaterialExample example);

    int updateByPrimaryKeySelective(EnrollMaterial record);

    int updateByPrimaryKey(EnrollMaterial record);

	List<EnrollMaterial> selectGroupByTtidPtype(@Param("ttid") Integer ttid);
}