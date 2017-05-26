package com.lili.school.mapper;


import java.util.List;

import com.lili.school.dto.WechatCoachClassDto;
import com.lili.school.vo.WechatCoachClass;

public interface WechatCoachClassMapper {
    int deleteByPrimaryKey(Integer ccid);

    int insert(WechatCoachClass record);

    int insertSelective(WechatCoachClass record);

    WechatCoachClass selectByPrimaryKey(Integer ccid);

    int updateByPrimaryKeySelective(WechatCoachClass record);

    int updateByPrimaryKey(WechatCoachClass record);

	List<WechatCoachClass> queryWechatCoachClassList2(WechatCoachClass wechatCoachClass);
	
	List<WechatCoachClass> queryWechatCoachClassList(WechatCoachClass wechatCoachClass);

	WechatCoachClass queryNearWechatCoachClass(WechatCoachClass recordClass);

	WechatCoachClass queryWechatCoachClass(WechatCoachClass recordClass);

	List<WechatCoachClass> queryCoachClassByCcidIn(WechatCoachClassDto coachClassDto);
}