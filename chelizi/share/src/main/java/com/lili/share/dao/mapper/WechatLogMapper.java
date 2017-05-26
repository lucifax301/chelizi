package com.lili.share.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.lili.share.dto.WechatLog;
import com.lili.share.dto.WechatLogExample;

public interface WechatLogMapper {
    int countByExample(WechatLogExample example);

    int deleteByExample(WechatLogExample example);
    
    /**
     * 按照主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id); 

    int insert(WechatLog record);

    int insertSelective(WechatLog record);

    List<WechatLog> selectByExampleWithRowbounds(WechatLogExample example, RowBounds rowBounds);

    List<WechatLog> selectByExample(WechatLogExample example);

    WechatLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WechatLog record, @Param("example") WechatLogExample example);

    int updateByExample(@Param("record") WechatLog record, @Param("example") WechatLogExample example);

    int updateByPrimaryKeySelective(WechatLog record);

    int updateByPrimaryKey(WechatLog record);
}