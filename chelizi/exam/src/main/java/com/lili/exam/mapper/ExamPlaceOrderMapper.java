package com.lili.exam.mapper;

import com.lili.exam.dto.ExamPlaceOrder;
import com.lili.exam.dto.ExamPlaceOrderExample;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExamPlaceOrderMapper {
    int countByExample(ExamPlaceOrderExample example);

    int deleteByExample(ExamPlaceOrderExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(ExamPlaceOrder record);

    int insertSelective(ExamPlaceOrder record);

    List<ExamPlaceOrder> selectByExampleWithRowbounds(ExamPlaceOrderExample example, RowBounds rowBounds);

    List<ExamPlaceOrder> selectByExample(ExamPlaceOrderExample example);

    ExamPlaceOrder selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") ExamPlaceOrder record, @Param("example") ExamPlaceOrderExample example);

    int updateByExample(@Param("record") ExamPlaceOrder record, @Param("example") ExamPlaceOrderExample example);

    int updateByPrimaryKeySelective(ExamPlaceOrder record);

    int updateByPrimaryKey(ExamPlaceOrder record);
    
    List<Integer> selectIncome(Integer placeId);
    
    List<Integer> selectOrderCount(Integer placeId);
    
    ExamPlaceOrder selectCode(ExamPlaceOrder record);
    
    List<ExamPlaceOrder> selectByPayorderid(ExamPlaceOrder record);
}