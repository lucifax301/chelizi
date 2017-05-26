package com.lili.pay.purpose;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.constant.MoneyChange;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.common.vo.ResultCode.ERRORCODE;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.manager.MoneyManager.UserType;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PayWayType;
import com.lili.school.manager.WechatSchoolManager;
import com.lili.school.service.WechatSchoolService;
import com.lili.school.vo.WechatEnrollStudent;

public class WxCoachPurpose implements IPayPurpose {
	@Autowired
	private LogService logService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private WechatSchoolManager wechatSchoolManager;
	@Autowired
	private WechatSchoolService wechatSchoolService;
	@Autowired
	private MoneyManager moneyManager;

	@Override
	public ReqResult doPurpose(PayVo payVo, Date payTime, String waterNum, int totalFee) {
		ReqResult reqResult = ReqResult.getFailed();

		// 验证金额
		if (payVo.getPayValue() != totalFee) {
			reqResult.setCode(ResultCode.ERRORCODE.ORDER_MONEY_MOTIFY);
			return reqResult;
		}

		payVo.setRemark(PayWayType.getChinaName(payVo.getPayWay()) + "|" + MoneyChange.SIGNUP_FEE.getDesc());

		// 记录流水
		PayLogVo payLogVo = new PayLogVo();
		payLogVo.setCouponid(payVo.getCouponId());
		payLogVo.setCouponmoney(0.0);
		payLogVo.setOrderid(payVo.getPayOrderId());
		payLogVo.setPaymoney((double) payVo.getPayValue());
		payLogVo.setPaytime(payTime);
		payLogVo.setPayway(payVo.getPayWay());
		payLogVo.setWaternum(waterNum);
		payLogVo.setStudentid(payVo.getUserId());
		logService.logPay(payLogVo);

		// 记录资金流水--喱喱账户
		moneyManager.addSchoolMoney(0, payVo.getPayValue());
		//记录学员报名流水
		moneyManager.addMoneyLog(payVo.getUserId(),  UserType.WXCOACHSTU, payVo.getPayWay(), 0, payVo.getPayValue(),  MoneyChange.SIGNUP_WXCOACH_FEE,	ReqConstants.MONEY_FLOW_WAIT,
				ReqConstants.MONEY_ACCOUNT_BALANCE, payVo.getRemark(), payVo.getPayOrderId(), UserType.WXCOACHSTU.getDesc() + payVo.getUserId());

		try {
			// 修改报名费支付状态：0代表未支付，1代表已提交,100代表支付成功，101代表支付失败'
			WechatEnrollStudent updateEnrollStudent = new WechatEnrollStudent();
			updateEnrollStudent.setOrderId(payVo.getPayOrderId());
			updateEnrollStudent.setPayState(100);
			updateEnrollStudent.setPayTime(payTime);
			wechatSchoolManager.updateWechatEnrollStudent(updateEnrollStudent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 // 发送微信通知
		WechatEnrollStudent record = new WechatEnrollStudent();
		record.setOrderId(payVo.getPayOrderId());
        WechatEnrollStudent enrollStudent = wechatSchoolManager.queryEnrollStudentInfo(record);
		String  firstStr = "亲爱的" + enrollStudent.getStudentName() + "，您的报名订单已支付成功！";
		wechatSchoolService.sendTemplateEnrollMsg(enrollStudent, firstStr);

		// 去掉标记
		redisUtil.delete(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId());

		return ReqResult.getSuccess();
	}

	@Override
	public Object purposeAdvance(PayVo payVo, ReqResult reqResult) {
		if (payVo.getUserType() != OrderConstant.USETYPE.STUDENT) {
			reqResult.setCode(ERRORCODE.PAY_SIGNUP_IS_NOT_STUDENT);
			return null;
		}

		if (payVo.getPayValue() <= 0) {
			reqResult.setCode(ResultCode.ERRORCODE.PAY_VALUE_IS_NOT_CORRECT);
			return null;
		}

		WechatEnrollStudent recordStudent = new WechatEnrollStudent();
		recordStudent.setOrderId(payVo.getPayOrderId());
		// 如果已经付过款
		WechatEnrollStudent wechatEnrollStudent = wechatSchoolManager.queryWechatEnrollStudent(recordStudent);
		if (wechatEnrollStudent == null) { // 订单不存在
			reqResult.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOTEXIST);
			return null;
		} else if (wechatEnrollStudent != null && wechatEnrollStudent.getPayState() == 100) { // 订单已支付
			reqResult.setCode(ResultCode.ERRORCODE.PAY_ORDER_HAVE_PAY);
			return null;
		} else if (wechatEnrollStudent != null && wechatEnrollStudent.getIsdel() == ReqConstants.OPERATE_DELETE) { // 订单已删除
			reqResult.setCode(ResultCode.ERRORCODE.ORDER_NOTEXIST);
			reqResult.setMsgInfo(ResultCode.ERRORINFO.ORDER_NOTEXIST);
			return null;
		}
		return 0;
	}
}
