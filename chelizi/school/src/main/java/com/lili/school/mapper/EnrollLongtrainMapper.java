package com.lili.school.mapper;

import com.lili.school.dto.EnrollLongtrain;
import com.lili.school.dto.EnrollLongtrainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EnrollLongtrainMapper {
    int countByExample(EnrollLongtrainExample example);

    int deleteByExample(EnrollLongtrainExample example);

    int deleteByPrimaryKey(Integer ltId);

    int insert(EnrollLongtrain record);

    int insertSelective(EnrollLongtrain record);

    List<EnrollLongtrain> selectByExampleWithRowbounds(EnrollLongtrainExample example, RowBounds rowBounds);

    List<EnrollLongtrain> selectByExample(EnrollLongtrainExample example);

    EnrollLongtrain selectByPrimaryKey(Integer ltId);

    int updateByExampleSelective(@Param("record") EnrollLongtrain record, @Param("example") EnrollLongtrainExample example);

    int updateByExample(@Param("record") EnrollLongtrain record, @Param("example") EnrollLongtrainExample example);

    int updateByPrimaryKeySelective(EnrollLongtrain record);

    int updateByPrimaryKey(EnrollLongtrain record);
}