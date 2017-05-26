package com.lili.access.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.school.service.ICmsPosterService;

/**
 * 开机广告
 * @author lzb
 */
@Controller
@Scope("prototype")
@RequestMapping("/poster")
public class PosterController extends BaseController {
	Logger logger = Logger.getLogger(PosterController.class);
	
	@Autowired
	ICmsPosterService posterService;
	

	/**
	 * 获取开机广告
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPoster", method = RequestMethod.GET)
	@ResponseBody
	public Object getPoster(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			String userType = request.getParameter("userType");//1-教练；2-学员
			String status = request.getParameter("status");//状态
			Integer type = 0;
			Integer isDel = 0;
			if(userType != null && userType != ""){
				type = Integer.valueOf(userType);
			}
			if(status != null && status != ""){
				isDel = Integer.valueOf(status);
			}
			resp = posterService.query(type, isDel);
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 获取上传凭证
	 * posterId为null
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getUptoken", method = RequestMethod.POST)
	@ResponseBody
	public Object getUptoken(HttpServletRequest request, HttpServletResponse response) {
		String token = null;
		try {
			//拉取上传文件凭证
			token = posterService.getUptoken();
		} 
		catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return token;
	}
	
	/**
	 * 新增开机广告
	 * posterId为null
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addPoster", method = RequestMethod.POST)
	@ResponseBody
	public Object addPoster(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			String userType = request.getParameter("userType");//1-教练；2-学员
			String imgName = request.getParameter("imgName");//广告名称
			String posterPic = request.getParameter("posterPic");//广告图片名称
			Integer type = 0;
			if(userType != null && userType != ""){
				type = Integer.valueOf(userType);
			}
			
			resp = posterService.addPoster(type, imgName, posterPic).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 更新开机广告，cms使用，激活、停用、修改图片
	 * pos，posterId不为空；激活、停用设置isdel；
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updatePoster", method = RequestMethod.POST)
	@ResponseBody
	public Object updatePoster(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			String userType = request.getParameter("userType");//1-教练；2-学员
			String status = request.getParameter("status");//状态
			String imgName = request.getParameter("imgName");//广告名称，如果是修改图片，改值必传
			String posterPic = request.getParameter("posterPic");//广告图片名称,如果是修改图片，改值必传
			Integer type = 0;
			Integer isDel = 0; //0-未停用；1-停用
			if(userType != null && userType != ""){
				type = Integer.valueOf(userType);
			}
			if(status != null && status != ""){
				isDel = Integer.valueOf(status);
			}
			resp = posterService.updatePoster(type, isDel, imgName, posterPic).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

}
