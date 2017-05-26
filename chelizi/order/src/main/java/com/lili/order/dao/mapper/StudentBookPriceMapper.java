package com.lili.order.dao.mapper;

import com.lili.order.dto.StudentBookPrice;
import com.lili.order.dto.StudentBookPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentBookPriceMapper {
    int countByExample(StudentBookPriceExample example);

    int deleteByExample(StudentBookPriceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StudentBookPrice record);

    int insertSelective(StudentBookPrice record);

    List<StudentBookPrice> selectByExampleWithRowbounds(StudentBookPriceExample example, RowBounds rowBounds);

    List<StudentBookPrice> selectByExample(StudentBookPriceExample example);

    StudentBookPrice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StudentBookPrice record, @Param("example") StudentBookPriceExample example);

    int updateByExample(@Param("record") StudentBookPrice record, @Param("example") StudentBookPriceExample example);

    int updateByPrimaryKeySelective(StudentBookPrice record);

    int updateByPrimaryKey(StudentBookPrice record);
}