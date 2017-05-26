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

import com.lili.finance.service.ICmsUserMoneyService;
import com.lili.finance.vo.UserMoneyVo;

/**
 * 充值记录查询
 * @author lzb
 */
@Controller
@Scope("prototype")
@RequestMapping("/payHistory")
public class PayHistoryController extends BaseController {
	Logger logger = Logger.getLogger(PayHistoryController.class);
	
	@Autowired
	ICmsUserMoneyService userMoneyService;

	/**
	 * 查询充值记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		String resp = null;
		try {
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request, UserMoneyVo.class);
			userMoneyVo.setOperateType(0);//0为充值
			resp = userMoneyService.queryPayHistoryList(userMoneyVo);
		} catch (Exception e) {
			logger.error("********************************* error："+e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

}
