package com.lili.coupon.mapper.dao;

import java.util.List;

import com.lili.coupon.dto.Coupon;

public interface CouponMapper {
    int deleteByPrimaryKey(String coupontmpid);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(String coupontmpid);
    
    List<Coupon> selectByExtra(String extra);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);
    

    
}