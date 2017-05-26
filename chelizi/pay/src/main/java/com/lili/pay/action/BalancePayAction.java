/**
 * 
 */
package com.lili.pay.action;

import com.lili.authcode.service.EmailService;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.common.vo.ResultCode.ERRORCODE;
import com.lili.pay.manager.MoneyManager;
import com.lili.pay.vo.PayVo;
import com.lili.pay.vo.PurposeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author linbo
 * 余额支付
 */
public class BalancePayAction extends PayAction
{
    private static Logger logger = LoggerFactory.getLogger(BalancePayAction.class);
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private MoneyManager moneyManager;
    @Autowired
    private EmailService emailService;

    @Override
    public void doPayAction(PayVo payVo, final ReqResult reqResult) {
        try {

            //帐户余额只能是学员用来支付，教练只能用在提现
        	// 20160913 教练约考场功能，教练也可以用余额支付
/*            if (payVo.getUserType() != OrderConstant.USETYPE.STUDENT) {
                return;
            }*/

            //学员余额不能用来充值
            if (payVo.getPayPurpose() == PurposeType.CHARGE) {
                return;
            }

            // 支付目的预处理
            Object discountAdv = getPayPurpose(payVo.getPayPurpose())
                    .purposeAdvance(payVo, reqResult);
            if (discountAdv == null) {
                return;
            }

            //验证密码是否正确
            String userType = redisUtil.get(REDISKEY.USER_CHECK_PW + String.valueOf(payVo.getUserId()));
            if (userType == null || !userType.equals(String.valueOf(payVo.getUserType()))) {
                reqResult.setCode(ERRORCODE.PAY_IS_NOT_SET_PW);
                return;
            }
            redisUtil.delete(REDISKEY.USER_CHECK_PW + String.valueOf(payVo.getUserId()));

            //判断余额是否足够。如果如果足够支付，则在余额中减去相应金额。t_u_money
            int leftMoney = 0;
            if(payVo.getUserType() == OrderConstant.USETYPE.STUDENT){
            	leftMoney = moneyManager.addStudentMoney(payVo.getUserId(), -1 * payVo.getPayValue());
            }else {
            	leftMoney = moneyManager.addCoachMoney(payVo.getUserId(), -1 * payVo.getPayValue());
            }

            // 余额不足
            if (leftMoney == Integer.MIN_VALUE) {
                reqResult.setCode(ERRORCODE.PAY_MONEY_IS_NOT_ENOUGH);
                return;
            }

            ReqResult req = getPayPurpose(payVo.getPayPurpose())
                    .doPurpose(payVo, new Date(), "", payVo.getPayValue());

            reqResult.setCode((String) req.getResult().get(ResultCode.RESULTKEY.CODE));
            reqResult.setMsgInfo((String) req.getResult().get(ResultCode.RESULTKEY.MSGINFO));
            // 如果失败了，则把余额恢复 
            if(!req.isSuccess()){
                if(payVo.getUserType() == OrderConstant.USETYPE.STUDENT){
                	leftMoney = moneyManager.addStudentMoney(payVo.getUserId(), payVo.getPayValue());
                }else {
                	leftMoney = moneyManager.addCoachMoney(payVo.getUserId(), payVo.getPayValue());
                }
            }

        } catch (Exception e) {
            logger.error("payVo: " + payVo, e);
            emailService.send("【系统】[用户支付异常啦，请抓紧时间处理！！]", "BalancePayAction-->doPayAction|"+payVo+"|Exception:"+e);
        }
    }

    @Override
    public ReqResult payCallBack(Object... callbackParam) {
        //余额支付没有第三方支付的回调流程
        return null;
    }

}
