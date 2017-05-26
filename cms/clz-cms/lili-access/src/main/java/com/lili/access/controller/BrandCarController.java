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

import com.lili.coach.dto.BrandCar;
import com.lili.school.model.BrandCarBDTO;
import com.lili.school.service.CMSBrandCarService;


@Controller
@Scope("prototype")
@RequestMapping("/brand-car")
public class BrandCarController extends BaseController{
	@Autowired
	CMSBrandCarService cmsBrandCarService;
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
    @ResponseBody
    public String view(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsBrandCarService.findOne(getParamInt(request, "seqId")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 获取所有品牌
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/all-brand", method=RequestMethod.GET)
    @ResponseBody
    public String allBrand(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsBrandCarService.getAllBrandName().build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 根据品牌获取所有车系
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/car", method=RequestMethod.GET)
    @ResponseBody
    public String car(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			return cmsBrandCarService.getCarByBrand(getParamStr(request, "brandname")).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			BrandCarBDTO dto = (BrandCarBDTO) buildObject(request, BrandCarBDTO.class);
			return cmsBrandCarService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
    @ResponseBody
    public String add(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			BrandCar brandCar = (BrandCar) buildObject(request, BrandCar.class);
			return cmsBrandCarService.insertOne(brandCar).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	

	@RequestMapping(value="/update", method= RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			BrandCar brandCar = (BrandCar) buildObject(request, BrandCar.class);
			return cmsBrandCarService.updateOne(brandCar).build();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("异常:" + e.getMessage());
		}
		
	}
	

}
