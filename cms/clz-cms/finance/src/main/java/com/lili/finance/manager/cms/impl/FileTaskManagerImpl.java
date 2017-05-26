package com.lili.finance.manager.cms.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.finance.manager.cms.IFileTaskManager;
import com.lili.finance.mapper.dao.cms.TaskFailDetailDao;
import com.lili.finance.mapper.dao.cms.TaskFileDao;
import com.lili.finance.model.cms.TaskFailDetail;
import com.lili.finance.model.cms.TaskFile;

public class FileTaskManagerImpl implements IFileTaskManager {
	private static final Logger logger = Logger.getLogger(FileTaskManagerImpl.class);
	
	@Autowired
	TaskFileDao taskFileMapper;

	@Autowired
	TaskFailDetailDao taskFailDetailMapper;
	
	@Override
	public List<TaskFile> queryHandleList() {
		List<TaskFile> taskFileList = null;
		
		try {
			taskFileList = taskFileMapper.queryIsUnHandleTask();
		} 
		catch (Exception e) {
			logger.error("********************************** queryHandleList Error : " + e.getMessage());
			e.printStackTrace();
		}
		
		return taskFileList;
	}

	@Override
	public void sysHandleStatus(TaskFile taskFile) {
		try {
			taskFileMapper.updateByPrimaryKeySelective(taskFile);
		} 
		catch (Exception e) {
			logger.error("********************************** sysHandleStatus Error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void insertTaskFailDetail(TaskFailDetail taskFailDetail) {
		try {
			taskFailDetailMapper.insertSelective(taskFailDetail);
		} 
		catch (Exception e) {
			logger.error("********************************** insertTaskFailDetail Error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void insertTaskFail(TaskFile taskFile) {
		// TODO Auto-generated method stub
		
	}

}
