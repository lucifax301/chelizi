package com.lili.share.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.lili.share.dto.WechatChannel;
import com.lili.share.dto.WechatChannelExample;

public interface WechatChannelMapper {
    int countByExample(WechatChannelExample example);

    int deleteByExample(WechatChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WechatChannel record);

    int insertSelective(WechatChannel record);

    List<WechatChannel> selectByExampleWithRowbounds(WechatChannelExample example, RowBounds rowBounds);

    List<WechatChannel> selectByExample(WechatChannelExample example);

    WechatChannel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WechatChannel record, @Param("example") WechatChannelExample example);

    int updateByExample(@Param("record") WechatChannel record, @Param("example") WechatChannelExample example);

    int updateByPrimaryKeySelective(WechatChannel record);

    int updateByPrimaryKey(WechatChannel record);
}