package com.lili.exam.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.lili.exam.dto.ExamPlacePayOrder;

public interface ExamPayMapper {
     int count(ExamPlacePayOrder examPlacePayOrder);

     int insert(ExamPlacePayOrder examPlacePayOrder);

     List<ExamPlacePayOrder> select(ExamPlacePayOrder examPlacePayOrder);
     
     List<ExamPlacePayOrder> selectunpay(ExamPlacePayOrder examPlacePayOrder);
     
     List<ExamPlacePayOrder> list(ExamPlacePayOrder examPlacePayOrder,RowBounds rowBounds);
     
     List<ExamPlacePayOrder> listone(ExamPlacePayOrder examPlacePayOrder,RowBounds rowBounds);
     
     int countone(ExamPlacePayOrder examPlacePayOrder);
    
     ExamPlacePayOrder selectByPrimaryKey(ExamPlacePayOrder examPlacePayOrder);

     int update(ExamPlacePayOrder examPlacePayOrder);
     
     int expire(ExamPlacePayOrder examPlacePayOrder);
}