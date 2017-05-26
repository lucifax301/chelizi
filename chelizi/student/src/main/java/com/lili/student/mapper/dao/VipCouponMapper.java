package com.lili.student.mapper.dao;

import com.lili.student.dto.VipCoupon;

import java.util.List;

public interface VipCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipCoupon record);

    int insertSelective(VipCoupon record);

    VipCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipCoupon record);

    int updateByPrimaryKey(VipCoupon record);

    List<VipCoupon> selectValidByVipPackageId(String vipCouponTmpId);
}