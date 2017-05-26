package com.lili.log.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.log.manager.CMSLogManager;
import com.lili.log.mapper.dao.LogCommonMapper;
import com.lili.log.model.LogCommon;
import com.lili.log.model.LogCommonBDTO;

public class CMSLogCommonManagerImpl implements CMSLogManager{

	@Autowired
	LogCommonMapper logCommonMapper;
	
	@Override
	public PagedResult<LogCommon> findLogList(LogCommonBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((logCommonMapper.findBatch(dto)));
	}

	@Override
	public void inertLogList(List<LogCommon> logCommonList) {
		logCommonMapper.inertLogList(logCommonList);
	}

	@Override
	public void inertLogInfo(LogCommon logCommon) {
		logCommonMapper.insert(logCommon);
	}

	@Override
	public Long addOne(LogCommon logCommon) {
		return logCommonMapper.addOne(logCommon);
	}

}
