package com.lili.school.mapper;

import com.lili.school.dto.WechatComment;
import com.lili.school.dto.WechatCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatCommentMapper {
    int countByExample(WechatCommentExample example);

    int deleteByExample(WechatCommentExample example);

    int deleteByPrimaryKey(String commentId);

    int insert(WechatComment record);

    int insertSelective(WechatComment record);

    List<WechatComment> selectByExampleWithRowbounds(WechatCommentExample example, RowBounds rowBounds);

    List<WechatComment> selectByExample(WechatCommentExample example);

    WechatComment selectByPrimaryKey(String commentId);

    int updateByExampleSelective(@Param("record") WechatComment record, @Param("example") WechatCommentExample example);

    int updateByExample(@Param("record") WechatComment record, @Param("example") WechatCommentExample example);

    int updateByPrimaryKeySelective(WechatComment record);

    int updateByPrimaryKey(WechatComment record);
}