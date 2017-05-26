package com.lili.school.mapper.dao;


import java.util.List;

import com.lili.school.model.CommonPrice;

public interface CommonPriceMapper {
	
    int deleteByPrimaryKey(Integer upid);

    int insert(CommonPrice record);

    int insertSelective(CommonPrice record);

    CommonPrice selectByPrimaryKey(Integer upid);

    int updateByPrimaryKeySelective(CommonPrice record);

    int updateByPrimaryKey(CommonPrice record);

	List<CommonPrice> queryComPriceList(String id);

	List<CommonPrice> queryCommonPriceList(CommonPrice commonPrice);
}