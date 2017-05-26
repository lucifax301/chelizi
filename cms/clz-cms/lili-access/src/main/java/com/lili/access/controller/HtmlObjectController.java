package com.lili.access.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.file.dto.HtmlObject;
import com.lili.student.model.HtmlObjectBDTO;
import com.lili.student.service.CMSHtmlObjectService;

/**
 * 协议条款接口
 * @author hughes
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/html-object")
public class HtmlObjectController extends BaseController{

	@Autowired
	CMSHtmlObjectService cmsHtmlObjectService;
	

	
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsHtmlObjectService.findOne(getParamInt(request, "hid")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			HtmlObjectBDTO dto = (HtmlObjectBDTO) buildObject(request, HtmlObjectBDTO.class);
			return cmsHtmlObjectService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/add", method=RequestMethod.POST)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			HtmlObject ept = (HtmlObject) 
					buildObject(request, HtmlObject.class);
			return cmsHtmlObjectService.insertOne(ept).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			HtmlObject ept = (HtmlObject) 
					buildObject(request, HtmlObject.class);
			return cmsHtmlObjectService.updateOne(ept).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
}
