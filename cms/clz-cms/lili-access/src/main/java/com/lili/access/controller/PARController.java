package com.lili.access.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.finance.model.PARBDTO;
import com.lili.finance.service.ICMSPARService;

@Controller
@Scope("prototype")
@RequestMapping("/par")
public class PARController extends BaseController{
	
	@Autowired
	ICMSPARService cmsPARService;

	@RequestMapping(value="/batch", method= RequestMethod.GET)
    @ResponseBody
    public String batch(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			PARBDTO dto = (PARBDTO) buildObject(request, PARBDTO.class);
			return cmsPARService.findBatch(dto).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
}
