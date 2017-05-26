package com.lili.share.dao.mapper;


import com.lili.share.dao.po.BigCustomer;

public interface BigCustomerMapper {
    int deleteByPrimaryKey(String phone);

    int insert(BigCustomer record);

    int insertSelective(BigCustomer record);

    BigCustomer selectByPrimaryKey(String phone);

    int updateByPrimaryKeySelective(BigCustomer record);

    int updateByPrimaryKey(BigCustomer record);

}