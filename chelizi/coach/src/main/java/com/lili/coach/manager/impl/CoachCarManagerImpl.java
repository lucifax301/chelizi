package com.lili.coach.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.manager.CoachCarManager;
import com.lili.coach.mapper.dao.CoachCarMapper;

public class CoachCarManagerImpl implements CoachCarManager {

	@Autowired
	private CoachCarMapper coachCarMapper;
	
}
