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

import com.lili.coach.model.RegionBDTO;
import com.lili.school.service.CMSRegionService;

@Controller
@Scope("prototype")
@RequestMapping("/region")
public class RegionController extends BaseController{

	@Autowired
	CMSRegionService cmsRegionService;
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsRegionService.findOne(getParamInt(request, "rid")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			RegionBDTO dto = (RegionBDTO) buildObject(request, RegionBDTO.class);
			return cmsRegionService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			com.lili.coach.dto.Region region = (com.lili.coach.dto.Region)
					buildObject(request, com.lili.coach.dto.Region.class);
			return cmsRegionService.addOne(region).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			com.lili.coach.dto.Region region = (com.lili.coach.dto.Region) 
					buildObject(request, com.lili.coach.dto.Region.class);
			return cmsRegionService.updateOne(region).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	
}
