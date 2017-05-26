package com.lili.school.mapper;


import com.lili.school.vo.WechatTemplate;

public interface WechatTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatTemplate record);

    int insertSelective(WechatTemplate record);

    WechatTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatTemplate record);

    int updateByPrimaryKey(WechatTemplate record);

	WechatTemplate queryWechatTemplate(WechatTemplate record);
}