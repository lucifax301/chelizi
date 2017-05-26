package com.lili.log.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.log.model.LogCommon;
import com.lili.log.model.LogCommonBDTO;

public interface CMSLogManager {


	/**
	 * 查询日志表
	 * @param params
	 * @return
	 */
	public  PagedResult<LogCommon> findLogList(LogCommonBDTO dto);

	/**
	 * 批量插入日志表
	 * @param logCommonList
	 */
	public  void inertLogList(List<LogCommon> logCommonList);
	
	/**
	 * 插入日志表
	 * @param logCommonInfo
	 */
	public  void inertLogInfo(LogCommon logCommon);
	
	public  Long addOne(LogCommon logCommon);
	
}
