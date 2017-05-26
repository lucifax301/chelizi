package com.lili.httpaccess.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.coach.dto.ArrangeTime;
import com.lili.coach.manager.ArrangeTimeManager;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.course.service.CourseService;
import com.lili.file.service.FileService;

@Controller
@RequestMapping("/v1/courses")
public class CourseController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private ArrangeTimeManager arrangeTimeManager;
	@Autowired
	private FileService fileService;
	
	private Logger log = Logger.getLogger(CourseController.class);

	/**
	 * 获取课程信息
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object getCourses(@RequestParam String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();

		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			r = courseService.getCourses(userId, userType, tokenId);
		} catch (Exception e) {
			log.error("controller: course get courses failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 获取课程信息
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/coursenew", method = RequestMethod.GET)
	@ResponseBody
	public Object getCoursenew(@RequestParam(required=false) String userId,
			@RequestParam(required=false) String userType, @RequestParam(required =false) String cityId,
			@RequestParam(required=false) String v,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		
		try {
			r = fileService.getCoursenew(userId, userType,cityId,v);
		} catch (Exception e) {
			log.error("controller: course get coursesnew failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 学员获取有权限课程信息
	 * @param userId
	 * @param userType
	 * @param cityId
	 * @param v
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/coursenew/valid", method = RequestMethod.GET)
	@ResponseBody
	public Object getCoursenewValid(@RequestParam String userId,
			@RequestParam String userType, @RequestParam(required =false) String cityId,
			@RequestParam(required =false) String v,
			@RequestParam String timestamp, @RequestParam String sign) {
		ReqResult r = new ReqResult();
		
		try {
			r = fileService.getCoursenewValid(userId, userType,cityId,v);
		} catch (Exception e) {
			log.error("controller: course get coursesnew failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	@RequestMapping(value = "/coursenewTree", method = RequestMethod.GET)
	@ResponseBody
	public Object getCoursenewTree(@RequestParam String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign) {
		ReqResult r = new ReqResult();
		
		try {
			r = fileService.getCoursenewTree(userId, userType);
		} catch (Exception e) {
			log.error("controller: course get getCoursenewTree failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 获取排班时间表
	 * @param userId
	 * @param userType
	 * @param timestamp
	 * @param sign
	 * @param headers
	 * @return
	 */
	@RequestMapping(value = "/arrangeTime", method = RequestMethod.GET)
	@ResponseBody
	public Object getArrangeTime(@RequestParam String userId,
			@RequestParam String userType, @RequestParam String timestamp,
			@RequestParam String sign, @RequestHeader HttpHeaders headers) {
		ReqResult r = new ReqResult();
		try {
			String tokenId = headers.getFirst(ReqConstants.TOKEN);
			Object o = arrangeTimeManager.getArrangeTime(new ArrangeTime());
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(o);
		} catch (Exception e) {
			log.error("controller: coach get timestamp failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}



}
