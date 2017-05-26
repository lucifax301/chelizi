package com.lili.coach.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.ErrorAppeal;
import com.lili.coach.dto.ErrorAppealItem;
import com.lili.coach.manager.CoachErrorAppealManager;
import com.lili.coach.mapper.dao.ErrorAppealItemMapper;
import com.lili.coach.mapper.dao.ErrorAppealMapper;

public class CoachErrorAppealManagerImpl implements CoachErrorAppealManager {

	@Autowired
	private ErrorAppealItemMapper errorAppealItemMapper;
	
	@Autowired
	private ErrorAppealMapper errorAppeaMapper;

	@Override
	public List<ErrorAppealItem> getErrorAppealItem() {
		return errorAppealItemMapper.selectByExample(null);
	}

	@Override
	public int addErrorAppeal(ErrorAppeal appeal) {
		return errorAppeaMapper.insert(appeal);
	}
	
}
