package com.lili.school.mapper;


import java.util.List;

import com.lili.school.dto.WechatMycoachesDto;
import com.lili.school.vo.WechatMycoaches;

public interface WechatMycoachesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatMycoaches record);

    int insertSelective(WechatMycoaches record);

    WechatMycoaches selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatMycoaches record);

    int updateByPrimaryKey(WechatMycoaches record);

	WechatMycoaches queryByMycoach(WechatMycoaches record);

	int updateWechatMycoaches(WechatMycoaches wechatMycoaches);

	Integer queryNewStudentSum(WechatMycoachesDto mycoach);

	List<WechatMycoaches> queryWechatMycoachesList(WechatMycoachesDto myCoachDto);

	Integer countMyStudentNum(WechatMycoaches myCoachDto);

	WechatMycoaches queryMyWechatBoundCoach(WechatMycoachesDto wechatMycoachesDto);
}