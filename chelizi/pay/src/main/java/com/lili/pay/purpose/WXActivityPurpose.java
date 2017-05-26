/**
 * 
 */
package com.lili.pay.purpose;

import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.pay.vo.PayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author linbo
 *
 */
public class WXActivityPurpose implements IPayPurpose
{
    private static Logger logger = LoggerFactory.getLogger(WXActivityPurpose.class);
    
    @Autowired
    protected RedisUtil redisUtil;
    
    @Override
    public Object purposeAdvance(PayVo payVo, ReqResult reqResult) throws Exception
    {
        return 0;
    }

    @Override
    public ReqResult doPurpose(PayVo payVo, Date endTime, String waterNum, int totalFee)
    {
        logger.error("wxChargeSuccess...");
        // 去掉标记
        redisUtil.delete(REDISKEY.PRE_PAY_ORDER + payVo.getPayOrderId());
        return ReqResult.getSuccess();
    }
}
