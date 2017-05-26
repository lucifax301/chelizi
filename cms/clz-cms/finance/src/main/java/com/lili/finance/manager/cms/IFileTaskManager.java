package com.lili.finance.manager.cms;

import java.util.List;

import com.lili.finance.model.cms.TaskFailDetail;
import com.lili.finance.model.cms.TaskFile;

public interface IFileTaskManager {

	/**
	 * 查询要处理的任务
	 * @param params
	 * @return
	 */
	public abstract List<TaskFile> queryHandleList() ;
	
	/**
	 * 同步处理的状态
	 * @param taskFile
	 */
	public abstract void sysHandleStatus(TaskFile taskFile);
	
	void insertTaskFailDetail(TaskFailDetail taskFailDetail);
	
	void insertTaskFail(TaskFile taskFile);
}
