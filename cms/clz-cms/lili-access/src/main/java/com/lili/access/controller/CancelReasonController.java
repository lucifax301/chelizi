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
import com.lili.order.service.ICmsCancelReasonService;
import com.lili.order.vo.CancelReason;
import com.lili.order.vo.CancelReasonVo;
import com.lili.user.model.User;

/**
 * 取消订单标签
 * @author lzb
 */
@Controller
@Scope("prototype")
@RequestMapping("/cancelReason")
public class CancelReasonController extends BaseController {
	Logger logger = Logger.getLogger(CancelReasonController.class);
	
	@Autowired
	ICmsCancelReasonService cancelReasonService;

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			CancelReason cancelReason = (CancelReason) buildObject(request, CancelReason.class);
			resp = cancelReasonService.query(cancelReason);
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 删除
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
			resp = cancelReasonService.isUseOrDel(checker, remark, id, isdel).build();
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
			Integer checker = Integer.valueOf(user.getAccount()); 
			CancelReasonVo cancelReasonVo = (CancelReasonVo) buildObject(request, CancelReasonVo.class);
			cancelReasonVo.setMuid(checker);
			resp = cancelReasonService.update(cancelReasonVo).build();
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
			Integer checker = Integer.valueOf(user.getAccount()); 
			CancelReasonVo cancelReasonVo = (CancelReasonVo) buildObject(request, CancelReasonVo.class);
			cancelReasonVo.setMuid(checker);
			resp = cancelReasonService.addCourse(cancelReasonVo).build();
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

}
