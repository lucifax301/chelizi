package com.lili.school.mapper;

import com.lili.school.dto.WechatEnrollOrder;
import com.lili.school.dto.WechatEnrollOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatEnrollOrderMapper {
    int countByExample(WechatEnrollOrderExample example);

    int deleteByExample(WechatEnrollOrderExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(WechatEnrollOrder record);

    int insertSelective(WechatEnrollOrder record);

    List<WechatEnrollOrder> selectByExampleWithBLOBsWithRowbounds(WechatEnrollOrderExample example, RowBounds rowBounds);

    List<WechatEnrollOrder> selectByExampleWithBLOBs(WechatEnrollOrderExample example);

    List<WechatEnrollOrder> selectByExampleWithRowbounds(WechatEnrollOrderExample example, RowBounds rowBounds);

    List<WechatEnrollOrder> selectByExample(WechatEnrollOrderExample example);

    WechatEnrollOrder selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") WechatEnrollOrder record, @Param("example") WechatEnrollOrderExample example);

    int updateByExampleWithBLOBs(@Param("record") WechatEnrollOrder record, @Param("example") WechatEnrollOrderExample example);

    int updateByExample(@Param("record") WechatEnrollOrder record, @Param("example") WechatEnrollOrderExample example);

    int updateByPrimaryKeySelective(WechatEnrollOrder record);

    int updateByPrimaryKeyWithBLOBs(WechatEnrollOrder record);

    int updateByPrimaryKey(WechatEnrollOrder record);
}