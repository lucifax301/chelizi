package com.lili.coach.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.manager.CourseSManager;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.course.service.CourseService;

public class CourseServiceImpl implements CourseService {
	
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    CourseSManager courseSManager;

	@Override
	public ReqResult getCourses(String userId, String userType, String tokenId)
			throws Exception {
		//简单接口，无需登录校验。
		ReqResult r = new ReqResult();
		try {
			Object o = courseSManager.getCourseS();
            r.setCode(ResultCode.ERRORCODE.SUCCESS);
            r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
            r.setData(o);
		} catch (Exception e) {
            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r;
	}

}
