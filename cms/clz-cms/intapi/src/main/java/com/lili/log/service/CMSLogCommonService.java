package com.lili.log.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.log.model.LogCommon;
import com.lili.log.model.LogCommonBDTO;


/**
 * 日志表操作
 * @author lzb
 *
 */
public  interface CMSLogCommonService   {

	/**
	 * 查询日志表
	 * @param params
	 * @return
	 */
	public ResponseMessage findLogList(LogCommonBDTO dto);

	/**
	 * 批量插入日志表
	 * @param logCommonList
	 */
	public  ResponseMessage insertLogList(List<LogCommon> logCommonList);
	
	/**
	 * 插入日志表
	 * @param logCommonInfo
	 */
	public  ResponseMessage insertLogInfo(LogCommon logCommon);
	
	public  ResponseMessage insertOne(LogCommon logCommon);

}
