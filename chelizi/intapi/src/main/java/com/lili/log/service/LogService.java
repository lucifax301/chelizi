/**
 * 
 */
package com.lili.log.service;

import com.lili.log.dto.LogComplain;
import com.lili.log.dto.LogOperate;
import com.lili.log.vo.PayLogVo;

/**
 * @author linbo
 *
 */
public interface LogService
{
    public int logPay(PayLogVo payLogVo);
    
    public int logOperate(LogOperate logOperate);
    
    public int logCompain(LogComplain logComplain);

	public int isExitLog(String payOrderId);
}
