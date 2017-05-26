package com.lili.access.controller;

import java.io.IOException;

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
import com.lili.report.service.ICmsStatisticsTotalLiliService;
import com.lili.report.vo.StatisticsCoachSchool;
import com.lili.report.vo.StatisticsStudentSchool;
import com.lili.user.model.User;

/**
 * 教务报表
 * 
 * @author lzb
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/statistics-total")
public class RStatisticsTotalController extends BaseController {
	Logger logger = Logger.getLogger(RStatisticsTotalController.class);

	@Autowired
	ICmsStatisticsTotalLiliService statisticsTotalLiliService;

	@RequestMapping(value = "/listJx", method = RequestMethod.GET)
	@ResponseBody
	public String listJx(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String schoolId  =  String.valueOf(user.getSchoolId());
			resp = statisticsTotalLiliService.listJx(schoolId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 学员统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/studentJx", method = RequestMethod.GET)
	@ResponseBody
	public String studentJx(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			Long schoolId =  user.getSchoolId();
			StatisticsStudentSchool staStudentVo = (StatisticsStudentSchool) buildObject(request, StatisticsStudentSchool.class);
			staStudentVo.setSchoolId(schoolId);
			resp = statisticsTotalLiliService.studentJx(staStudentVo);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 教练统计
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/coachJx", method = RequestMethod.GET)
	@ResponseBody
	public String coachJx(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			Long schoolId =  user.getSchoolId();
			StatisticsCoachSchool statisticsCoachVo = (StatisticsCoachSchool) buildObject(request, StatisticsCoachSchool.class);
			statisticsCoachVo.setSchoolId(schoolId);
			resp = statisticsTotalLiliService.coachJx(statisticsCoachVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
}
