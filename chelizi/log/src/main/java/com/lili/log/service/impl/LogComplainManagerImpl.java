package com.lili.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.log.dto.LogComplain;
import com.lili.log.mapper.dao.LogComplainMapper;
import com.lili.log.service.LogComplainManager;

public class LogComplainManagerImpl implements LogComplainManager {

	
	@Autowired
	private LogComplainMapper logComplainMapper;
	
	@Override
	public List<LogComplain> getLogComplain(LogComplain dto) {
		return logComplainMapper.getAll(dto);
	}

	@Override
	public LogComplain getLogComplainInfo(Integer id) {
		return logComplainMapper.selectByPrimaryKey(id);
	}

	@Override
	public int addLogComplain(LogComplain dto) {
		return logComplainMapper.insertSelective(dto);
	}

	@Override
	public int updateLogComplain(LogComplain dto) {
		return logComplainMapper.updateByPrimaryKeySelective(dto);
	}

	@Override
	public int deleteLogComplain(Integer id) {
		return logComplainMapper.deleteByPrimaryKey(id);
	}

}
