package com.lili.school.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.school.dto.EnrollMaterialAddress;
import com.lili.school.dto.EnrollMaterialAddressExample;
import com.lili.school.manager.EnrollMaterialAddressManager;
import com.lili.school.mapper.EnrollMaterialAddressMapper;

public class EnrollMaterialAddressManagerImpl implements
		EnrollMaterialAddressManager {
	@Autowired
	private EnrollMaterialAddressMapper enrollMaterialAddressMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public int addEnrollMaterialAddress(EnrollMaterialAddress record) {
		return enrollMaterialAddressMapper.insertSelective(record);
	}

	@Override
	public int updateEnrollMaterialAddress(EnrollMaterialAddress record) {
		redisUtil.delete(REDISKEY.ENROLL_MAIL_ONE + record.getTtid());
		return enrollMaterialAddressMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<EnrollMaterialAddress> getEnrollMaterialAddress(
			EnrollMaterialAddressExample example) {
		return enrollMaterialAddressMapper.selectByExample(example);
	}

}
