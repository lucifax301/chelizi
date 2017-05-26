/**
 * 
 */
package com.lili.pay.bill.qq;

import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.qqpay.config.QQPayBussiness;
import com.lili.qqpay.config.QQPayConfig;
import com.lili.qqpay.config.QQPayInitReq;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Date;

/**
 * @author linbo
 *
 */
public class TestQQPay
{
    /**
     * @param args
     */
    @SuppressWarnings("resource")
    public static void main(String[] args)
    {
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-init.xml");
        QQPayConfig qqPayConfig = ac.getBean(QQPayConfig.class);
        String timeStart = TimeUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
        String timeEnd = TimeUtil.getDateFormat(TimeUtil.addDate(new Date(), 600000), "yyyyMMddHHmmss");
        QQPayInitReq qqPayInitReq = new QQPayInitReq(qqPayConfig.getMchID(), "测试QQ钱包", StringUtil.getOrderId(), 1, qqPayConfig.getCallBackUrl(), "attach", timeStart, timeEnd, qqPayConfig.getKey());
        ReqResult reqResult = QQPayBussiness.doQQPayInit(qqPayInitReq,qqPayConfig.getInitUrl());
        System.out.print(reqResult.getResult().get(ResultCode.RESULTKEY.DATA));
    }
}
