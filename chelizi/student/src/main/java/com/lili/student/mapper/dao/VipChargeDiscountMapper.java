package com.lili.student.mapper.dao;

import com.lili.student.dto.VipChargeDiscount;

import java.util.List;

public interface  VipChargeDiscountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipChargeDiscount record);

    int insertSelective(VipChargeDiscount record);

    VipChargeDiscount selectByPrimaryKey(Integer id);

    List<VipChargeDiscount> selectValidByvipPackageId(String vipPackageId);

    int updateByPrimaryKeySelective(VipChargeDiscount record);

    int updateByPrimaryKey(VipChargeDiscount record);
}