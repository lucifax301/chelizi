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
import com.lili.order.vo.CommentTagVo;
import com.lili.school.model.CommentTag;
import com.lili.school.service.ICmsCommentTagService;
import com.lili.user.model.User;

/**
 * 教练服务评价、学员技能标签
 * @author lzb
 */
@Controller
@Scope("prototype")
@RequestMapping("/commentTag")
public class CommentTagController extends BaseController {
	Logger logger = Logger.getLogger(CommentTagController.class);
	
	@Autowired
	ICmsCommentTagService commentTagService;

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			CommentTag commentTag = (CommentTag) buildObject(request, CommentTag.class);
			resp = commentTagService.query(commentTag);
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
			resp = commentTagService.isUseOrDel(checker,  id, isdel).build();
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
			CommentTagVo commentTagVo = (CommentTagVo) buildObject(request, CommentTagVo.class);
			commentTagVo.setMuid(Integer.valueOf(checker));
			resp = commentTagService.update(commentTagVo).build();
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
			CommentTagVo commentTagVo = (CommentTagVo) buildObject(request, CommentTagVo.class);
			commentTagVo.setMuid(Integer.valueOf(checker));
			resp = commentTagService.addCommentTag(commentTagVo).build();
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

}
