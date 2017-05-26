package com.lili.coach.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.lili.coach.dto.ArrangeTime;
import com.lili.coach.manager.ArrangeTimeManager;
import com.lili.coach.mapper.dao.ArrangeTimeMapper;

public class ArrangeTimeManagerImpl implements ArrangeTimeManager {

	@Autowired
	private ArrangeTimeMapper arrangeTimeMapper;

	@Override
	public List<ArrangeTime> getArrangeTime(ArrangeTime arrangeTime) {
		return arrangeTimeMapper.getAll(arrangeTime);
	}

	@Override
	public ArrangeTime getArrangeTimeInfo(int id) {
		return arrangeTimeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int getCount() {
		return arrangeTimeMapper.countAll();
	}

	@Override
	public int addArrangeTime(ArrangeTime arrangeTime) {
		return arrangeTimeMapper.insert(arrangeTime);
	}

	@Override
	public int updateArrangeTime(ArrangeTime arrangeTime) {
		return arrangeTimeMapper.updateByPrimaryKeySelective(arrangeTime);
	}

	@Override
	public int deleteArrangeTime(int id) {
		return arrangeTimeMapper.deleteByPrimaryKey(id);
	}

}
