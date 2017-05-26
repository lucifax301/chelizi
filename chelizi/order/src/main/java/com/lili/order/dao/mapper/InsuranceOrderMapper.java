package com.lili.order.dao.mapper;

import com.lili.order.dto.InsuranceOrder;
import com.lili.order.dto.InsuranceOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface InsuranceOrderMapper {
    int countByExample(InsuranceOrderExample example);

    int deleteByExample(InsuranceOrderExample example);

    int deleteByPrimaryKey(String insuranceId);

    int insert(InsuranceOrder record);

    int insertSelective(InsuranceOrder record);

    List<InsuranceOrder> selectByExampleWithRowbounds(InsuranceOrderExample example, RowBounds rowBounds);

    List<InsuranceOrder> selectByExample(InsuranceOrderExample example);

    InsuranceOrder selectByPrimaryKey(String insuranceId);

    int updateByExampleSelective(@Param("record") InsuranceOrder record, @Param("example") InsuranceOrderExample example);

    int updateByExample(@Param("record") InsuranceOrder record, @Param("example") InsuranceOrderExample example);

    int updateByPrimaryKeySelective(InsuranceOrder record);

    int updateByPrimaryKey(InsuranceOrder record);
}