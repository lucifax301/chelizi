package com.lili.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.school.dto.EnrollMaterialItems;
import com.lili.school.dto.EnrollMaterialItemsExample;

public interface EnrollMaterialItemsMapper {
    int countByExample(EnrollMaterialItemsExample example);

    int deleteByExample(EnrollMaterialItemsExample example);

    int deleteByPrimaryKey(Integer pmid);

    int insert(EnrollMaterialItems record);

    int insertSelective(EnrollMaterialItems record);

    List<EnrollMaterialItems> selectByExampleWithBLOBs(EnrollMaterialItemsExample example);
    List<EnrollMaterialItems> selectByPrimaryKeys(@Param("pmids") List<Integer> pmids);

    List<EnrollMaterialItems> selectByExample(EnrollMaterialItemsExample example);

    EnrollMaterialItems selectByPrimaryKey(Integer pmid);

    int updateByExampleSelective(@Param("record") EnrollMaterialItems record, @Param("example") EnrollMaterialItemsExample example);

    int updateByExampleWithBLOBs(@Param("record") EnrollMaterialItems record, @Param("example") EnrollMaterialItemsExample example);

    int updateByExample(@Param("record") EnrollMaterialItems record, @Param("example") EnrollMaterialItemsExample example);

    int updateByPrimaryKeySelective(EnrollMaterialItems record);

    int updateByPrimaryKeyWithBLOBs(EnrollMaterialItems record);

    int updateByPrimaryKey(EnrollMaterialItems record);
}