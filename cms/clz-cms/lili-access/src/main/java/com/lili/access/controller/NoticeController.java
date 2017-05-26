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

import com.lili.authcode.dto.Notice;
import com.lili.coach.service.ICmsNoticeService;
@Controller
@Scope("prototype")
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	Logger logger = Logger.getLogger(NoticeController.class);
	
	@Autowired
	ICmsNoticeService cmsNoticeService;

	/**
	 * 获取总数--测试
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getNotice", method = RequestMethod.POST)
	@ResponseBody
	public String getNotice(HttpServletRequest request,Notice notice, HttpServletResponse response) throws Exception {
		try {
			int pageNo=Integer.parseInt(request.getParameter("pageNo"));
			int pageSize=Integer.parseInt(request.getParameter("pageSize"));
			return cmsNoticeService.getNotice(notice,pageNo,pageSize).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response,Notice notice) throws Exception{
		try {
		//	Notice notice = (Notice) buildObject(request, Notice.class);
			return cmsNoticeService.addNotice(notice).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response,Notice notice) throws Exception{
		try {
		//	Notice notice = (Notice) buildObject(request, Notice.class);
			if("".equals(notice.getCityId())){
				notice.setCityId(null);
			}
		    if("".equals(notice.getUtype())){
		    	notice.setUtype(null);
		    }
		    if("".equals(notice.getSchoolId())){
		    	notice.setSchoolId(null);
		    }
		    if("".equals(notice.getUserIdStrs())){
		    	notice.setUserIdStrs(null);
		    }
		    if("".equals(notice.getApplyexam())){
		    	notice.setApplyexam(null);
		    }
			return cmsNoticeService.updateNotice(notice).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getNoticeById", method = RequestMethod.GET)
	@ResponseBody
	public String getNoticeById(HttpServletRequest request,String noticeId, HttpServletResponse response) throws Exception {
		try {
			return cmsNoticeService.getNoticeById(noticeId).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value = "/updateState", method = RequestMethod.GET)
	@ResponseBody
	public String updateState(HttpServletRequest request,String noticeId,String isdel, HttpServletResponse response) throws Exception {
		try {
			String type=request.getParameter("type");
			return cmsNoticeService.updateState(noticeId,isdel,type).build();
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
			throw new Exception("异常:" + e.getMessage());
		}
	}



}
