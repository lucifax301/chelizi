package com.lili.pay.mapper.dao;

import java.util.List;

import com.lili.pay.dto.PerformanceDto;
import com.lili.pay.wallet.PerformancePage;

public interface PerformanceDtoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PerformanceDto record);

    int insertSelective(PerformanceDto record);

    PerformanceDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PerformanceDto record);

    int updateByPrimaryKey(PerformanceDto record);
    
    List<PerformanceDto> getPerformanceDtosByPage(PerformancePage page);
}