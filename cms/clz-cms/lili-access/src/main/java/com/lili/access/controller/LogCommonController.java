package com.lili.access.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.log.model.LogCommonBDTO;
import com.lili.log.service.CMSLogCommonService;


/**
 * 日志统一操作
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/logCommon")
public class LogCommonController extends BaseController{
	
	@Autowired
	CMSLogCommonService cmsLogCommonService;
	
	/**
	 * 获取记录日志
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/batch", method = RequestMethod.GET)
	@ResponseBody
	public String batch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			LogCommonBDTO dto = (LogCommonBDTO) buildObject(request, LogCommonBDTO.class);
			return cmsLogCommonService.findLogList(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}

	
}
