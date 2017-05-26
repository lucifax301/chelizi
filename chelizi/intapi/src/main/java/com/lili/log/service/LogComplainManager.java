package com.lili.log.service;

import java.util.List;

import com.lili.log.dto.LogComplain;

public interface LogComplainManager {

	/**
	 * 获取投诉列表
	 * @return
	 */
	public List<LogComplain> getLogComplain(LogComplain dto);

	/**
	 * 根据id获取投诉信息
	 * @param id
	 * @return
	 */
	public LogComplain getLogComplainInfo(Integer id);

	/**
	 * 新增投诉信息
	 * @param dto
	 * @return
	 */
	public int addLogComplain(LogComplain dto);

	/**
	 * 更新投诉信息
	 * @param dto
	 * @return
	 */
	public int updateLogComplain(LogComplain dto);

	/**
	 * 根据id删除投诉信息
	 * @param id
	 * @return
	 */
	public int deleteLogComplain(Integer id);

}
