package com.lili.log.mapper.dao;

import com.lili.log.dto.LogPay;

public interface LogPayMapper {
    int deleteByPrimaryKey(Integer payid);

    int insert(LogPay record);

    int insertSelective(LogPay record);

    LogPay selectByPrimaryKey(Integer payid);

    int updateByPrimaryKeySelective(LogPay record);

    int updateByPrimaryKey(LogPay record);
    
    int insertAndGetId(LogPay record);

	int queryIsExitLogByOrderId(String payOrderId);
}