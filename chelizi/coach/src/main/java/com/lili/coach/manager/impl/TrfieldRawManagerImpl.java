package com.lili.coach.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.TrfieldRaw;
import com.lili.coach.manager.TrfieldRawManager;
import com.lili.coach.mapper.dao.TrfieldRawMapper;

public class TrfieldRawManagerImpl implements TrfieldRawManager {
	@Autowired
	TrfieldRawMapper rawMapper;

	@Override
	public int add(TrfieldRaw raw) {
		return rawMapper.insertSelective(raw);

	}

}
