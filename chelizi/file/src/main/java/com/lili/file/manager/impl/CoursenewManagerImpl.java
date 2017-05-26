package com.lili.file.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.file.dto.Coursenew;
import com.lili.file.dto.CoursenewExample;
import com.lili.file.manager.CoursenewManager;
import com.lili.file.mapper.CoursenewMapper;

public class CoursenewManagerImpl implements CoursenewManager {
	@Autowired
	CoursenewMapper coursenewMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public List<Coursenew> getCoursenew(CoursenewExample example) {
		return coursenewMapper.selectByExample(example);
	}

	@Override
	public int postCoursenew(Coursenew course) {
		redisUtil.delete(REDISKEY.COURSES);
		return coursenewMapper.insertSelective(course);
	}

	@Override
	public int putCoursenew(Coursenew course) {
		redisUtil.delete(REDISKEY.COURSES);
		return coursenewMapper.updateByPrimaryKeySelective(course);
	}

	@Override
	public int putCoursenew(Coursenew record, CoursenewExample example) {
		return coursenewMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int putCoursenew(List<String> coursenewNo, byte isdel) {
		Coursenew record = new Coursenew();
		record.setIsdel(isdel);
		CoursenewExample example = new CoursenewExample();
		CoursenewExample.Criteria criteria = example.createCriteria();
		criteria.andCoursenewnoIn(coursenewNo);
		return putCoursenew(record, example);
	}
	
	

}
