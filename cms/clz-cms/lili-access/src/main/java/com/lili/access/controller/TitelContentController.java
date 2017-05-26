package com.lili.access.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.constant.Constant;
import com.lili.student.model.TitleTypeVo;
import com.lili.student.model.TypeContentVo;
import com.lili.student.service.CmsTitleContentService;
import com.lili.user.model.User;

/**
 * 帮助中心配置
 * 
 * @author lzb
 *
 */
@Controller
@RequestMapping("/titelContent")
public class TitelContentController extends BaseController {

	@Autowired
	CmsTitleContentService cmsTitleContentService;

	@RequestMapping(value = "/queryTitleList", method = RequestMethod.GET)
	@ResponseBody
	public String queryTitleList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			TitleTypeVo tt = (TitleTypeVo) buildObject(request, TitleTypeVo.class);
			return cmsTitleContentService.queryTitleList(tt).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/addTitle", method = RequestMethod.POST)
	@ResponseBody
	public String addTitle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String creater = user.getAccount(); 
			
			TitleTypeVo tt = (TitleTypeVo) buildObject(request, TitleTypeVo.class);
			tt.setCreater(creater);
			return cmsTitleContentService.addTitle(tt).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/deleteTitle", method = RequestMethod.POST)
	@ResponseBody
	public String deleteTitle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			TitleTypeVo ept = (TitleTypeVo) buildObject(request, TitleTypeVo.class);
			return cmsTitleContentService.deleteTitle(ept).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/updateTitle", method = RequestMethod.POST)
	@ResponseBody
	public String updateTitle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String creater = user.getAccount(); 
			
			TitleTypeVo ttv = (TitleTypeVo) buildObject(request, TitleTypeVo.class);
			ttv.setUpdater(creater);
			return cmsTitleContentService.updateTitle(ttv).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}

	}

	@RequestMapping(value = "/queryContent", method = RequestMethod.GET)
	@ResponseBody
	public String queryContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			TypeContentVo tc = (TypeContentVo) buildObject(request, TypeContentVo.class);
			return cmsTitleContentService.queryContent(tc).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/queryContentList", method = RequestMethod.GET)
	@ResponseBody
	public String queryContentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			TypeContentVo tc = (TypeContentVo) buildObject(request, TypeContentVo.class);
			return cmsTitleContentService.queryContentList(tc).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/addContent", method = RequestMethod.POST)
	@ResponseBody
	public String addContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String creater = user.getAccount(); 
			
			TypeContentVo tc = (TypeContentVo) buildObject(request, TypeContentVo.class);
			tc.setCreater(creater);
			return cmsTitleContentService.addContent(tc).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/updateContent", method = RequestMethod.POST)
	@ResponseBody
	public String updateContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String creater = user.getAccount(); 
			
			TypeContentVo tc = (TypeContentVo) buildObject(request, TypeContentVo.class);
			tc.setUpdater(creater);
			return cmsTitleContentService.updateContent(tc).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/releaseContent", method = RequestMethod.POST)
	@ResponseBody
	public String releaseContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			String creater = user.getAccount(); 
			
			TypeContentVo tc = (TypeContentVo) buildObject(request, TypeContentVo.class);
			tc.setUpdater(creater);
			return cmsTitleContentService.releaseContent(tc).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
}
