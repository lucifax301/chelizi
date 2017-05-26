/**
 * 
 */
package com.lili.log.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.log.dto.LogCoupon;
import com.lili.log.dto.LogPay;
import com.lili.log.mapper.dao.LogCouponMapper;
import com.lili.log.mapper.dao.LogPayMapper;
import com.lili.log.vo.PayLogVo;

/**
 * @author linbo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-init.xml"})
public class LogServiceImplTest
{
    @Autowired
    private LogCouponMapper logCouponMappger;
    
    @Autowired
    private LogPayMapper logPayMapper;
    
    @Test
    public void test()
    {
        PayLogVo payLogVo = new PayLogVo();
        payLogVo.setCoachid((long) 101011010);
        payLogVo.setCouponid(101010);
        payLogVo.setCouponmoney(1.0);
        payLogVo.setOrderid("32424234322342");
        payLogVo.setPaymoney(1.1);
        payLogVo.setPaytime(new Date());
        payLogVo.setPayway("weixin");
        payLogVo.setStudentid((long) 33432234);
        payLogVo.setWaternum("234234234234234");
        
        LogPay logPay = new LogPay();
        logPay.setCoachid(payLogVo.getCoachid());
        logPay.setCouponid(payLogVo.getCouponid());
        logPay.setCouponmoney(payLogVo.getCouponmoney());
        logPay.setOrderid(payLogVo.getOrderid());
        logPay.setPaymoney(payLogVo.getPaymoney());
        logPay.setPaytime(payLogVo.getPaytime());
        logPay.setPayway(payLogVo.getPayway());
        logPay.setStudentid(payLogVo.getStudentid());
        logPay.setWaternum(payLogVo.getWaternum());
        
        int a = logPayMapper.insertAndGetId(logPay);
        if (payLogVo.getCouponid() != 0)
        {
            LogCoupon logCoupon = new LogCoupon();
            logCoupon.setCouponid(payLogVo.getCouponid());
            logCoupon.setStudentid(payLogVo.getStudentid());
            logCoupon.setUsetime(payLogVo.getPaytime());
            logCoupon.setPayid(logPay.getPayId());
            logCouponMappger.insert(logCoupon);
        }

        return ;
    }

}
