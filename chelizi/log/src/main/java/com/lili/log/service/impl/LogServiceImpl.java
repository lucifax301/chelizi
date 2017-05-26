/**
 * 
 */
package com.lili.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.log.dto.LogComplain;
import com.lili.log.dto.LogCoupon;
import com.lili.log.dto.LogOperate;
import com.lili.log.dto.LogPay;
import com.lili.log.mapper.dao.LogCouponMapper;
import com.lili.log.mapper.dao.LogPayMapper;
import com.lili.log.service.LogComplainManager;
import com.lili.log.service.LogService;
import com.lili.log.vo.PayLogVo;

/**
 * @author linbo
 *
 */
public class LogServiceImpl implements LogService
{
    @Autowired
    private LogCouponMapper logCouponMappger;
    
    @Autowired
    private LogPayMapper logPayMapper;
    @Autowired
    private LogComplainManager logComplainManager;
    
    /* (non-Javadoc)
     * @see com.lili.log.service.LogService#logPay(com.lili.log.vo.PayLogVo)
     */
    @Override
    public int logPay(PayLogVo payLogVo)
    {
        if (payLogVo == null)
        {
            return 0;
        }
        
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
        
        logPayMapper.insertAndGetId(logPay);
        if (payLogVo.getCouponid() != 0)
        {
            LogCoupon logCoupon = new LogCoupon();
            logCoupon.setCouponid(payLogVo.getCouponid());
            logCoupon.setStudentid(payLogVo.getStudentid());
            logCoupon.setUsetime(payLogVo.getPaytime());
            logCoupon.setPayid(logPay.getPayId());
            logCouponMappger.insert(logCoupon);
        }

        return logPay.getPayId();
    }

	@Override
	public int logOperate(LogOperate logOperate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int logCompain(LogComplain logComplain) {
		return logComplainManager.addLogComplain(logComplain);
	}

	@Override
	public int isExitLog(String payOrderId) {
		return logPayMapper.queryIsExitLogByOrderId(payOrderId);
	}
    
    
}
