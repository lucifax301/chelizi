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

import com.lili.finance.service.ICmsLiliWalletService;
import com.lili.finance.vo.UserMoneyVo;

/**
 * 喱喱钱包
 * @author lzb
 */
@Controller
@Scope("prototype")
@RequestMapping("/liliWallet")
public class LiLiWalletController extends BaseController{
	Logger logger = Logger.getLogger(LiLiWalletController.class);
	
	@Autowired
	ICmsLiliWalletService cmsLiliWalletService;
	
	/**
	 * 资金统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fundCount", method = RequestMethod.GET)
	@ResponseBody
	public String fundCount(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String resp = null;
		try {
			String month = request.getParameter("month");
			resp =  cmsLiliWalletService.fundCount(month).build();
		} 
		catch (Exception e) {
			logger.error("********************************* fundCount error : " + e.getMessage());
		}
		return resp;
	}
	
	
	/**
	 * 账户余额
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	@ResponseBody
	public String balance(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			return  cmsLiliWalletService.balance().build();
		} 
		catch (Exception e) {
			logger.error("*********************************balance error : " + e.getMessage());
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 余额明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/touchBalance", method = RequestMethod.GET)
	@ResponseBody
	public String touchBalance(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request, UserMoneyVo.class);
			return  cmsLiliWalletService.touchBalance(userMoneyVo).build();
		} 
		catch (Exception e) {
			logger.error("*********************************touchBalance error : " + e.getMessage());
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 收入明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/accountBalance", method = RequestMethod.GET)
	@ResponseBody
	public String accountBalance(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request, UserMoneyVo.class);
			return  cmsLiliWalletService.accountBalance(userMoneyVo).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	/**
	 * 费用明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/costDetail", method = RequestMethod.GET)
	@ResponseBody
	public String costDetail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			UserMoneyVo userMoneyVo = (UserMoneyVo) buildObject(request, UserMoneyVo.class);
			return  cmsLiliWalletService.costDetail(userMoneyVo).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}
	
	
}