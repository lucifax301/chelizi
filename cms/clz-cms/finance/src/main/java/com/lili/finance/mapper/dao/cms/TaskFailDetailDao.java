package com.lili.finance.mapper.dao.cms;


import com.lili.finance.model.cms.TaskFailDetail;

public interface TaskFailDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskFailDetail record);

    int insertSelective(TaskFailDetail record);

    TaskFailDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskFailDetail record);

    int updateByPrimaryKey(TaskFailDetail record);
}