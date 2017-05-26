package com.lili.school.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.school.dto.EnrollOrder;
import com.lili.school.dto.EnrollOrderExample;
import com.lili.school.manager.EnrollOrderManager;
import com.lili.school.mapper.EnrollOrderMapper;

public class EnrollOrderManagerImpl implements EnrollOrderManager {
	@Autowired
	private EnrollOrderMapper enrollOrderMapper;
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void updateEnrollOrder(EnrollOrder enrollOrder) {
		enrollOrderMapper.updateByPrimaryKeySelective(enrollOrder);

	}

	@Override
	public EnrollOrder getEnrollOrderByStudentId(long studentId) {
		EnrollOrderExample example = new EnrollOrderExample();
		EnrollOrderExample.Criteria criteria = example.createCriteria();
		criteria.andStudentIdEqualTo(studentId);
		criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
		List<EnrollOrder> eos = enrollOrderMapper.selectByExample(example);
		if (null != eos && eos.size() > 0) {
			String key = RedisKeys.REDISKEY.ENROLL_STUDENT_INFO + studentId;
			redisUtil.set(key, eos.get(0), 3600);
			return eos.get(0);
		}
		return null;
	}

}
