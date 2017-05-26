package com.lili.coupon.manager.impl;

import com.lili.common.util.StringUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.coupon.dto.Ticket;
import com.lili.coupon.mapper.dao.TicketMapper;
import com.lili.coupon.qqticket.QQTicketConfig;
import com.lili.coupon.qqticket.SDKUtil;
import com.lili.coupon.service.CouponService;
import com.lili.student.dto.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ZLong on 2016/五月/5.
 */
@Service("qqTicketManager")
public class QQTicketManager {
    private Logger logger = LoggerFactory.getLogger(QQTicketManager.class);


    @Autowired
    private QQTicketConfig qqTicketConfig;
    @Autowired
    private TicketMapper ticketMapper;
    @Autowired
    private CouponService couponService;

    public Ticket findQQTicketByCode(String code) {
        return ticketMapper.getQQTicketByCode(code);
    }

    /**
     * @param ticket  不能为null
     * @param student
     * @return
     */
    public ReqResult useTicket(Ticket ticket, Student student) {
        ReqResult r = ReqResult.getFailed();

        try {
            String cardId = ticket.getCardid();
            String code = ticket.getCode();

            if (StringUtil.isNotNullAndNotEmpty(cardId) || StringUtil.isNotNullAndNotEmpty(code)) {
            	// 20160823在原来基础上增加系统券判断
                if (code.startsWith("11tt") || code.startsWith("10")) {//FIXME 这里是测试代码,需要注释掉
                    r = ReqResult.getSuccess();
                } else {
                    //到QQ卡券平台将当前code消费掉
                    r = SDKUtil.useCard(qqTicketConfig.getAppid(), cardId, code);
                }
                if (r.isSuccess()) {

                    ticket.setStatus(1);//将卡券状态设置为已使用状态
                    ticket.setUseTime(new Date());
                    ticket.setStudentId(student.getStudentId());

                    //在数据库中修改当前卡券的状态
                    if (ticketMapper.updateByPrimaryKeySelective(ticket) > 0) {
                        r = ReqResult.getSuccess();
                    }
                }
            } else {
                r = ReqResult.getParamError();
            }
        } catch (Exception e) {
            logger.error("QQ ticket use card error! cardid:{},code:{}", ticket.getCardid(), ticket.getCode(), e);

            r.setCode(ResultCode.ERRORCODE.EXCEPTION);
            r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
        }
        return r;
    }
}
