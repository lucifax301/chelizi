package com.lili.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.school.dto.EnrollMaterialAddress;
import com.lili.school.dto.EnrollMaterialAddressExample;

public interface EnrollMaterialAddressMapper {
    int countByExample(EnrollMaterialAddressExample example);

    int deleteByExample(EnrollMaterialAddressExample example);

    int deleteByPrimaryKey(Integer paid);

    int insert(EnrollMaterialAddress record);

    int insertSelective(EnrollMaterialAddress record);

    List<EnrollMaterialAddress> selectByExample(EnrollMaterialAddressExample example);

    EnrollMaterialAddress selectByPrimaryKey(Integer paid);

    int updateByExampleSelective(@Param("record") EnrollMaterialAddress record, @Param("example") EnrollMaterialAddressExample example);

    int updateByExample(@Param("record") EnrollMaterialAddress record, @Param("example") EnrollMaterialAddressExample example);

    int updateByPrimaryKeySelective(EnrollMaterialAddress record);

    int updateByPrimaryKey(EnrollMaterialAddress record);

	EnrollMaterialAddress selectByTtid(Integer ttid);
}