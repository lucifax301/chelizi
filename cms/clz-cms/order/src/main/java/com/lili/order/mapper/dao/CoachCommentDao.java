package com.lili.order.mapper.dao;

import com.lili.order.model.CoachComment;

public interface CoachCommentDao {
    int deleteByPrimaryKey(Integer ccid);

    int insert(CoachComment record);

    int insertSelective(CoachComment record);

    CoachComment selectByPrimaryKey(Integer ccid);
    
    CoachComment queryByOrderId(CoachComment record);

    int updateByPrimaryKeySelective(CoachComment record);

    int updateByPrimaryKey(CoachComment record);
}