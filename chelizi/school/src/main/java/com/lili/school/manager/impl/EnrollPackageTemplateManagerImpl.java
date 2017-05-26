package com.lili.school.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.school.dto.EnrollPackageTemplateExample;
import com.lili.school.dto.EnrollPackageTemplateWithBLOBs;
import com.lili.school.manager.EnrollPackageTemplateManager;
import com.lili.school.mapper.EnrollPackageTemplateMapper;

public class EnrollPackageTemplateManagerImpl implements
		EnrollPackageTemplateManager {
	@Autowired
	private EnrollPackageTemplateMapper enrollPackageTemplateMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public int addEnrollPackageTemplate(EnrollPackageTemplateWithBLOBs record) {
		redisUtil.delete(REDISKEY.ENROLL_TEMPLATE);
		return enrollPackageTemplateMapper.insertSelective(record);
	}

	@Override
	public int updateEnrollPackageTemplate(
			EnrollPackageTemplateWithBLOBs record,
			EnrollPackageTemplateExample example) {
		redisUtil.delete(REDISKEY.ENROLL_TEMPLATE);
		return enrollPackageTemplateMapper.updateByExampleWithBLOBs(record, example);
	}

	@Override
	public int updateEnrollPackageTemplate(List<Integer> ttid, byte isdel) {
		EnrollPackageTemplateWithBLOBs record = new EnrollPackageTemplateWithBLOBs();
		record.setIsdel(isdel);
		EnrollPackageTemplateExample example = new EnrollPackageTemplateExample();
		EnrollPackageTemplateExample.Criteria criteria = example.createCriteria();
		criteria.andTtidIn(ttid);
		redisUtil.delete(REDISKEY.ENROLL_TEMPLATE);
		return enrollPackageTemplateMapper.updateByExampleWithBLOBs(record, example);
	}

	@Override
	public List<EnrollPackageTemplateWithBLOBs> getEnrollPackageTemplate(
			EnrollPackageTemplateExample example) {
		return enrollPackageTemplateMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public int getCityByTtid(int ttid) {
		EnrollPackageTemplateWithBLOBs ept = enrollPackageTemplateMapper.selectByPrimaryKey(ttid);
		return ept.getCityId();
	}



	
	
}
