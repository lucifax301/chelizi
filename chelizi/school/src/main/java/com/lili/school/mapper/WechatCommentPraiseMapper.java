package com.lili.school.mapper;

import com.lili.school.dto.WechatCommentPraise;
import com.lili.school.dto.WechatCommentPraiseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatCommentPraiseMapper {
    int countByExample(WechatCommentPraiseExample example);

    int deleteByExample(WechatCommentPraiseExample example);

    int deleteByPrimaryKey(String praiseId);

    int insert(WechatCommentPraise record);

    int insertSelective(WechatCommentPraise record);

    List<WechatCommentPraise> selectByExampleWithRowbounds(WechatCommentPraiseExample example, RowBounds rowBounds);

    List<WechatCommentPraise> selectByExample(WechatCommentPraiseExample example);

    WechatCommentPraise selectByPrimaryKey(String praiseId);

    int updateByExampleSelective(@Param("record") WechatCommentPraise record, @Param("example") WechatCommentPraiseExample example);

    int updateByExample(@Param("record") WechatCommentPraise record, @Param("example") WechatCommentPraiseExample example);

    int updateByPrimaryKeySelective(WechatCommentPraise record);

    int updateByPrimaryKey(WechatCommentPraise record);
}