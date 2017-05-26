package com.lili.school.mapper;

import java.util.List;

import com.lili.school.dto.WechatPlantClassDto;
import com.lili.school.vo.WechatPlantClass;

public interface WechatPlantClassMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(WechatPlantClass record);

    int insertSelective(WechatPlantClass record);

    WechatPlantClass selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(WechatPlantClass record);

    int updateByPrimaryKey(WechatPlantClass record);

	Integer countOnClassNum(WechatPlantClass wechatPlantClass);

	List<WechatPlantClass> queryWechatPlantClassDtoList(WechatPlantClassDto record);

	WechatPlantClass queryWechatPlantClass(WechatPlantClass record);

	List<WechatPlantClass> queryWechatPlantClassList(WechatPlantClass record);

	int updateWechatPlantClass(WechatPlantClass plantClass);
}