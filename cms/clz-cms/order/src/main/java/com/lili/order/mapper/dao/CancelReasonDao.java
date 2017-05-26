package com.lili.order.mapper.dao;

import java.util.List;

import com.lili.order.vo.CancelReason;

public interface CancelReasonDao {
    int deleteByPrimaryKey(Integer crid);

    int insert(CancelReason record);

    int insertSelective(CancelReason record);

    CancelReason selectByPrimaryKey(Integer crid);

    int updateByPrimaryKeySelective(CancelReason record);

    int updateByPrimaryKey(CancelReason record);

	List<CancelReason> queryCancelReasonList(CancelReason cancelReason);

	List<CancelReason> queryCancelList(String id);
}