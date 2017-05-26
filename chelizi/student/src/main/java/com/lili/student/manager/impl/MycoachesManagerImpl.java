package com.lili.student.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.student.dto.Mycoaches;
import com.lili.student.dto.MycoachesExample;
import com.lili.student.manager.MycoachesManager;
import com.lili.student.mapper.dao.MycoachesMapper;

public class MycoachesManagerImpl implements MycoachesManager {
	@Autowired
	private MycoachesMapper mycoachesMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return mycoachesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int addMycoaches(Mycoaches record) {
		return mycoachesMapper.insertSelective(record);
	}

	@Override
	public Mycoaches getMycoachesById(Integer id) {
		return mycoachesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateMycoaches(Mycoaches record) {
		return mycoachesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public long selectByUserId(Mycoaches record) {
		return mycoachesMapper.selectByUserId(record);
	}
	
	@Override
	public int countById(Integer studentId,Integer coachId) {
		MycoachesExample example=new MycoachesExample();
		example.createCriteria().andCoachidEqualTo((long)coachId).andStudentidEqualTo((long)studentId).andStatusEqualTo((byte) 0);
		return mycoachesMapper.countByExample(example);
	}

}
