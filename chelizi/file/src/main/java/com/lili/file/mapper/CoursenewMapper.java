package com.lili.file.mapper;

import com.lili.file.dto.Coursenew;
import com.lili.file.dto.CoursenewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CoursenewMapper {
    int countByExample(CoursenewExample example);

    int deleteByExample(CoursenewExample example);

    int deleteByPrimaryKey(String coursenewno);

    int insert(Coursenew record);

    int insertSelective(Coursenew record);

    List<Coursenew> selectByExampleWithRowbounds(CoursenewExample example, RowBounds rowBounds);

    List<Coursenew> selectByExample(CoursenewExample example);

    Coursenew selectByPrimaryKey(String coursenewno);

    int updateByExampleSelective(@Param("record") Coursenew record, @Param("example") CoursenewExample example);

    int updateByExample(@Param("record") Coursenew record, @Param("example") CoursenewExample example);

    int updateByPrimaryKeySelective(Coursenew record);

    int updateByPrimaryKey(Coursenew record);
}