package com.lili.course.service;

import com.lili.common.vo.ReqResult;

public interface CourseService {
	public ReqResult getCourses(String userId,String userType,String tokenId) throws Exception;
}
