package com.lili.school.mapper;

import java.util.List;

import com.lili.school.vo.WechatEnrollClass;
import com.lili.school.vo.WechatEnrollClassVo;

public interface WechatEnrollClassMapper {
    int deleteByPrimaryKey(Integer classId);

    int insert(WechatEnrollClass record);

    int insertSelective(WechatEnrollClass record);

    WechatEnrollClass selectByPrimaryKey(Integer classId);

    int updateByPrimaryKeySelective(WechatEnrollClass record);

    int updateByPrimaryKey(WechatEnrollClass record);

	int insertEnrollClassList(List<WechatEnrollClass> enrollJsonList);

	List<WechatEnrollClass> queryWechatEnrollClassList(WechatEnrollClass wechatEnrollClass);

	WechatEnrollClass queryWechatEnrollClass(WechatEnrollClass recordClass);

	List<WechatEnrollClass> queryClassGroupByCoachList(WechatEnrollClass wechatEnrollClass);

	WechatEnrollClass getMinPrice(WechatEnrollClassVo enrollClass);
}