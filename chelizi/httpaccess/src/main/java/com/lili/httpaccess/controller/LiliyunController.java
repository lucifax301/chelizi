package com.lili.httpaccess.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.pay.manager.MoneyManager;

@Controller
@RequestMapping("/v1/yun")
public class LiliyunController {
    @Autowired
    private MoneyManager moneyManager;

	private Logger log = Logger.getLogger(LiliyunController.class);

	/**
	 * 接口测试
	 * @return 系统时间戳
	 */
	@RequestMapping(value = "/timestamp", method = RequestMethod.GET)
	@ResponseBody
	public Object getTime() {
		ReqResult r = new ReqResult();
		
		try {
			Object o =System.currentTimeMillis()/1000L;
			r.setCode(ResultCode.ERRORCODE.SUCCESS);
			r.setMsgInfo(ResultCode.ERRORINFO.SUCCESS);
			r.setData(o);
		} catch (Exception e) {
			log.error("controller: authcode get timestamp failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	/**
	 * 驾校充值
	 * @param userId	驾校id
	 * @param userType	用户类型-驾校传5
	 * @param price		充值金额，单位分
	 * @param remark	备注
	 * @param payWay	充值方式
	 * @param orderId	充值订单
	 * @param timestamp	时间戳
	 * @param sign		签名
	 * @return	余额信息
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	@ResponseBody
	public Object recharge(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String price, @RequestParam String remark,
			@RequestParam String payWay, @RequestParam String orderId,
			@RequestParam String timestamp, @RequestParam String sign
			) {
		ReqResult r = new ReqResult();

		try {
			Integer data = moneyManager.handleSchoolRecharge(userId, userType, price, payWay, orderId, remark);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: recharge failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}

		return r.getResult();
	}
	
	/**
	 * 驾校设置约考场消费限额
	 * @param userId	驾校id
	 * @param userType	用户类型-驾校传5
	 * @param price		限额大小，单位分
	 * @param timestamp	时间戳
	 * @param sign		签名
	 * @return	
	 */
	@RequestMapping(value = "/examPlaceMax", method = RequestMethod.POST)
	@ResponseBody
	public Object setExamPlaceMax(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String price, 
			@RequestParam String timestamp, @RequestParam String sign
			) {
		ReqResult r = new ReqResult();
		
		try {
			Integer data = moneyManager.setExamPlaceMax(userId, userType, price);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: setExamPlaceMax failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}
	
	
	/**
	 * 驾校获取约考场消费限额
	 * @param userId	驾校id
	 * @param userType	用户类型-驾校传5
	 * @param timestamp	时间戳
	 * @param sign		签名
	 * @return	限额，单位分
	 */
	@RequestMapping(value = "/examPlaceMax", method = RequestMethod.GET)
	@ResponseBody
	public Object getExamPlaceMax(@RequestParam String userId,@RequestParam String userType,
			@RequestParam String timestamp, @RequestParam String sign
			) {
		ReqResult r = new ReqResult();
		
		try {
			Integer data = moneyManager.getExamPlaceMax(userId, userType);
			r.setData(data);
		} catch (Exception e) {
			log.error("controller: getExamPlaceMax failed=" + e.getMessage(), e);
			e.printStackTrace();
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
		}
		
		return r.getResult();
	}

}






















