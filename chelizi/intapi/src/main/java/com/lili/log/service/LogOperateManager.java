package com.lili.log.service;

import java.util.List;

import com.lili.log.dto.LogOperate;

public interface LogOperateManager {

	/**
	 * 获取操作列表
	 * @return
	 */
	public List<LogOperate> getLogOperate(LogOperate dto);

	/**
	 * 根据id获取操作信息
	 * @param id
	 * @return
	 */
	public LogOperate getLogComplainInfo(Integer id);

	/**
	 * 新增操作信息
	 * @param dto
	 * @return
	 */
	public int addLogOperate(LogOperate dto);

	/**
	 * 更新投诉信息
	 * @param dto
	 * @return
	 */
	public int updateLogOperate(LogOperate dto);

	/**
	 * 根据id删除操作信息
	 * @param id
	 * @return
	 */
	public int deleteLogOperate(Integer id);
}
