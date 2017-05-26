package com.lili.school.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.school.dto.EnrollMaterial;
import com.lili.school.dto.EnrollMaterialExample;
import com.lili.school.manager.EnrollMaterialManager;
import com.lili.school.mapper.EnrollMaterialMapper;

public class EnrollMaterialManagerImpl implements EnrollMaterialManager {
	@Autowired
	private EnrollMaterialMapper enrollMaterialMapper;
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public int addEnrollMaterial(EnrollMaterial record) {
		redisUtil.delete(REDISKEY.ENROLL_MAIL_ONE + record.getTtid());
		return enrollMaterialMapper.insertSelective(record);
	}

	@Override
	public int updateEnrollMaterial(EnrollMaterial record) {
		redisUtil.delete(REDISKEY.ENROLL_MAIL_ONE + record.getTtid());
		return enrollMaterialMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<EnrollMaterial> getEnrollMaterial(EnrollMaterialExample example) {
		return enrollMaterialMapper.selectByExample(example);
	}

}
