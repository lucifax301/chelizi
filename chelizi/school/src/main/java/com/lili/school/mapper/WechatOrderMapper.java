package com.lili.school.mapper;


import java.util.List;

import com.lili.school.vo.WechatOrder;

public interface WechatOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(WechatOrder record);

    int insertSelective(WechatOrder record);

    WechatOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(WechatOrder record);

    int updateByPrimaryKey(WechatOrder record);

	int updateByWechatOrder(WechatOrder wechatOrder);

	WechatOrder queryWechatOrder(WechatOrder orderInfo);

	List<WechatOrder> queryWechatOrderList(WechatOrder order);
}