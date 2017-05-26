package com.lili.log.mapper.dao;

import com.lili.log.dto.LogCoupon;

public interface LogCouponMapper {
    int insert(LogCoupon record);

    int insertSelective(LogCoupon record);
}