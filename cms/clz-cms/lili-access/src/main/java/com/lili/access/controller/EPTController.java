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

import com.lili.school.dto.EnrollPackageTemplateExample;
import com.lili.school.dto.EnrollPackageTemplateWithBLOBs;
import com.lili.school.model.EptwbBDTO;
import com.lili.school.service.CMSEPTService;

/**
 * 报名包接入层
 * @author hughes
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/ept")
public class EPTController extends BaseController{
	@Autowired
	CMSEPTService cmsEPTService;


	@RequestMapping(value="/token", method=RequestMethod.GET)
    @ResponseBody
    public String token(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsEPTService.getToken().build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsEPTService.findOne(getParamInt(request, "ttid")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}


	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			EptwbBDTO dto = (EptwbBDTO) buildObject(request, EptwbBDTO.class);
			return cmsEPTService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			EnrollPackageTemplateWithBLOBs ept = (EnrollPackageTemplateWithBLOBs) 
					buildObject(request, EnrollPackageTemplateWithBLOBs.class);
			return cmsEPTService.insertOne(ept).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			EnrollPackageTemplateWithBLOBs ept = (EnrollPackageTemplateWithBLOBs) 
					buildObject(request, EnrollPackageTemplateWithBLOBs.class);
			EnrollPackageTemplateExample temp = (EnrollPackageTemplateExample) 
					buildObject(request, EnrollPackageTemplateExample.class);
			return cmsEPTService.updateOne(ept,temp).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}

	@RequestMapping(value="/cancle", method=RequestMethod.GET)
    @ResponseBody
    public String cancle(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsEPTService.cancleOne(getParamInt(request, "ttid")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/active", method=RequestMethod.GET)
    @ResponseBody
    public String active(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsEPTService.activeOne(getParamInt(request, "ttid")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	
	

}
