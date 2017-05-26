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
import com.lili.order.vo.CommonPriceVo;
import com.lili.school.model.CommonPrice;
import com.lili.school.service.ICmsCommonPriceService;
import com.lili.user.model.User;

/**
 * 课程定价
 * @author lzb
 */
@Controller
@Scope("prototype")
@RequestMapping("/commonPrice")
public class CommonPriceController extends BaseController {
	Logger logger = Logger.getLogger(CommonPriceController.class);
	
	@Autowired
	ICmsCommonPriceService commonPriceService;

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			CommonPrice commonPrice = (CommonPrice) buildObject(request, CommonPrice.class);
			resp = commonPriceService.query(commonPrice);
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
			String id = request.getParameter("idList");
			String isdelStr = request.getParameter("isdel");
			Integer isdel = 0;
			if(isdelStr != null && isdelStr !="") {
				isdel = Integer.valueOf(isdelStr);
			}
			resp = commonPriceService.isUseOrDel(checker,  id, isdel).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Object check(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Constant.USER_SESSION);
			String checker = user.getAccount(); 
			CommonPriceVo commonPriceVo = (CommonPriceVo) buildObject(request, CommonPriceVo.class);
			commonPriceVo.setMuid(Integer.valueOf(checker));
			resp = commonPriceService.check(commonPriceVo).build();
		}
		catch (Exception e) {
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
			CommonPriceVo commonPriceVo = (CommonPriceVo) buildObject(request, CommonPriceVo.class);
			commonPriceVo.setMuid(Integer.valueOf(checker));
			resp = commonPriceService.update(commonPriceVo).build();
		}
		catch (Exception e) {
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
			CommonPriceVo commonPriceVo = (CommonPriceVo) buildObject(request, CommonPriceVo.class);
			commonPriceVo.setCuid(Integer.valueOf(checker));
			resp = commonPriceService.addCourse(commonPriceVo).build();
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

}
