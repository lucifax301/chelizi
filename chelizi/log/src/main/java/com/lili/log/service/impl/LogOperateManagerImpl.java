package com.lili.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.log.dto.LogOperate;
import com.lili.log.mapper.dao.LogOperateMapper;
import com.lili.log.service.LogOperateManager;

public class LogOperateManagerImpl implements LogOperateManager {

	@Autowired
	private LogOperateMapper logOperateMapper;
	
	@Override
	public List<LogOperate> getLogOperate(LogOperate dto) {
		return null;
	}

	@Override
	public LogOperate getLogComplainInfo(Integer id) {
		return logOperateMapper.selectByPrimaryKey(id);
	}

	@Override
	public int addLogOperate(LogOperate dto) {
		return logOperateMapper.insertSelective(dto);
	}

	@Override
	public int updateLogOperate(LogOperate dto) {
		return logOperateMapper.updateByPrimaryKeySelective(dto);
	}

	@Override
	public int deleteLogOperate(Integer id) {
		return logOperateMapper.deleteByPrimaryKey(id);
	}

}
