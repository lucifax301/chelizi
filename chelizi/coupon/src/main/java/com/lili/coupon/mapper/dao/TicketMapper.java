package com.lili.coupon.mapper.dao;

import com.lili.coupon.dto.Ticket;

/**
 * Created by ZLong on 2016/五月/17.
 */
public interface TicketMapper {
    Ticket getQQTicketByCode(String code);

    int updateByPrimaryKeySelective(Ticket ticket);
}
