package com.lili.coach.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.lili.coach.dto.School;
import com.lili.coach.manager.SchoolManager;
import com.lili.coach.mapper.dao.SchoolMapper;

public class SchoolManagerImpl implements SchoolManager {

	@Autowired
	private SchoolMapper schoolMapper;
	
	@Override
	public List<School> getSchool() {
		return schoolMapper.getAll();
	}

	@Override
	public School getSchoolInfo(int id) {
		return schoolMapper.selectByPrimaryKey(id);
	}

	@Override
	public int getCount() {
		return schoolMapper.countAll();
	}

	@Override
	public int addSchool(School school) {
		return schoolMapper.insert(school);
	}

	@Override
	public int updateSchool(School school) {
		return schoolMapper.updateByPrimaryKeySelective(school);
	}

	@Override
	public int deleteSchool(int id) {
		return schoolMapper.deleteByPrimaryKey(id);
	}

}
