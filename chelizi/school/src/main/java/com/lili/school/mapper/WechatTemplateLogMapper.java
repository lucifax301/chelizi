package com.lili.school.mapper;

import com.lili.school.vo.WechatTemplateLog;

public interface WechatTemplateLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatTemplateLog record);

    int insertSelective(WechatTemplateLog record);

    WechatTemplateLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatTemplateLog record);

    int updateByPrimaryKey(WechatTemplateLog record);
}