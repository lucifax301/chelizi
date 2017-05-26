package com.lili.school.mapper;

import com.lili.school.dto.EnrollOrder;
import com.lili.school.dto.EnrollOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EnrollOrderMapper {
    int countByExample(EnrollOrderExample example);

    int deleteByExample(EnrollOrderExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(EnrollOrder record);

    int insertSelective(EnrollOrder record);

    List<EnrollOrder> selectByExampleWithBLOBsWithRowbounds(EnrollOrderExample example, RowBounds rowBounds);

    List<EnrollOrder> selectByExampleWithBLOBs(EnrollOrderExample example);

    List<EnrollOrder> selectByExampleWithRowbounds(EnrollOrderExample example, RowBounds rowBounds);

    List<EnrollOrder> selectByExample(EnrollOrderExample example);

    EnrollOrder selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") EnrollOrder record, @Param("example") EnrollOrderExample example);

    int updateByExampleWithBLOBs(@Param("record") EnrollOrder record, @Param("example") EnrollOrderExample example);

    int updateByExample(@Param("record") EnrollOrder record, @Param("example") EnrollOrderExample example);

    int updateByPrimaryKeySelective(EnrollOrder record);

    int updateByPrimaryKeyWithBLOBs(EnrollOrder record);

    int updateByPrimaryKey(EnrollOrder record);
}