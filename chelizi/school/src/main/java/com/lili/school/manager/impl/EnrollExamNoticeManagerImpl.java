package com.lili.school.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.school.dto.EnrollExamNotice;
import com.lili.school.manager.EnrollExamNoticeManager;
import com.lili.school.mapper.EnrollExamNoticeMapper;

public class EnrollExamNoticeManagerImpl implements EnrollExamNoticeManager {
	@Autowired
	private EnrollExamNoticeMapper enrollExamNoticeMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public EnrollExamNotice getNoticeById(int subjectId) {
		EnrollExamNotice een = redisUtil.get(RedisKeys.REDISKEY.ENROLL_NOTICE + subjectId);
		if(null == een){
			een = enrollExamNoticeMapper.selectByPrimaryKey(subjectId);
			if(null != een ){
				redisUtil.set(RedisKeys.REDISKEY.ENROLL_NOTICE + subjectId,een,RedisKeys.EXPIRE.WEEK);
			}
		}
				
		return een;
	}

}
