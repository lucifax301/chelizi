package com.lili.access.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.constant.Constant;
import com.lili.file.dto.Coursenew;
import com.lili.school.model.CourseNewDTO;
import com.lili.school.service.ICmsCourseService;
import com.lili.user.model.User;

/**
 * 课程资源
 * @author lzb
 */
@Controller
@Scope("prototype")
@RequestMapping("/course")
public class CourseController extends BaseController {
	Logger logger = Logger.getLogger(CourseController.class);
	
	@Autowired
	ICmsCourseService courseService;

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			CourseNewDTO courseNew = (CourseNewDTO) buildObject(request, CourseNewDTO.class);
			resp = courseService.query(courseNew);
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 停用、激活
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/isdel", method = RequestMethod.POST)
	@ResponseBody
	public Object isdel(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			String remark  = request.getParameter("checkRemark");
			String id = request.getParameter("idList");
			String isdelStr = request.getParameter("isdel");
			Integer isdel = 0;
			if(isdelStr != null && isdelStr !="") {
				isdel = Integer.valueOf(isdelStr);
			}
			resp = courseService.isUseOrDel(checker, remark, id, isdel).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			Coursenew coursenew = (Coursenew) buildObject(request, Coursenew.class);
			resp = courseService.update(checker, coursenew).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			Coursenew coursenew = (Coursenew) buildObject(request, Coursenew.class);
			resp = courseService.addCourse(checker, coursenew).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

}
