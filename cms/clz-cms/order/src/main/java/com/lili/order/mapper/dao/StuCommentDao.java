package com.lili.order.mapper.dao;

import java.util.List;

import com.lili.order.model.StuComment;

public interface StuCommentDao {
    int deleteByPrimaryKey(Integer scid);

    int insert(StuComment record);

    int insertSelective(StuComment record);

    StuComment selectByPrimaryKey(Integer scid);
    
    List<StuComment> queryByOrderId(StuComment record);

    int updateByPrimaryKeySelective(StuComment record);

    int updateByPrimaryKey(StuComment record);
}