package com.lili.access.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.cms.constant.Constant;
import com.lili.cms.constant.MessageCode;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.BankInfo;
import com.lili.school.service.CMSSchoolService;
import com.lili.user.model.User;

/**
 * 驾校
 * 
 * @author lzb
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/authcodes")
public class AuthcodesController extends BaseController {

	@Autowired
	CMSSchoolService cmsSchoolService;

	/**
	 * 获取短信
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/one", method = RequestMethod.GET)
	@ResponseBody
	public String one(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String mobile = request.getParameter("mobile");
			String reqType = request.getParameter("reqType");
			return cmsSchoolService.one(mobile, reqType).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	/**
	 * 校验短信
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	@ResponseBody
	public String verify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String mobile = request.getParameter("mobile");
			String codeInput = request.getParameter("codeInput");
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION);
			Long schoolId = user.getSchoolId();

			return cmsSchoolService.verify(mobile, codeInput, schoolId).build();
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
	}

	@RequestMapping(value = "/bankName", method = RequestMethod.GET)
	@ResponseBody
	public String bankName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String bankName = "";
		try {
			String publicAccount = request.getParameter("publicAccount");
			// 校验APP请求内容
			if (publicAccount == null || "".equals(publicAccount)) {
				return new ResponseMessage(601, MessageCode.BANKCARD_IS_ERROR).build();
			}
			if (publicAccount.length() > 19 || publicAccount.length() < 15) {
				return new ResponseMessage(601, MessageCode.BANKCARD_IS_ERROR).build();
			}

			if (publicAccount.startsWith("62")) {
				Boolean isRightCard = BankInfo.checkBankCard(publicAccount);
				if (isRightCard) {
					bankName = BankInfo.getNameOfBank(publicAccount); // 获取银行卡的信息
					if ("".equals(bankName)) {
						return new ResponseMessage(601, MessageCode.BANKCARD_IS_ERROR).build();
					}
					bankName = bankName.substring(0, bankName.indexOf("·"));
				} else {
					return new ResponseMessage(601, MessageCode.BANKCARD_IS_ERROR).build();
				}
			} else {
				return new ResponseMessage(601, MessageCode.BANKCARD_IS_ERROR).build();
			}
		} catch (Exception e) {
			throw new Exception("异常:" + e.getMessage());
		}
		return  new ResponseMessage().addResult("pageData", bankName).build();
	}

}