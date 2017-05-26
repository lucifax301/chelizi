package com.lili.finance.mapper.dao.cms;


import java.util.List;

import com.lili.finance.model.cms.TaskFile;



public interface TaskFileDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskFile record);

    int insertSelective(TaskFile record);

    TaskFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskFile record);

    int updateByPrimaryKey(TaskFile record);

	List<TaskFile> queryIsUnHandleTask();
}