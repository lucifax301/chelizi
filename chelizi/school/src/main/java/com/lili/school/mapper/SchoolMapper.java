package com.lili.school.mapper;

import com.lili.school.dto.School;
import com.lili.school.dto.SchoolExample;
import com.lili.school.dto.SchoolWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SchoolMapper {
    int countByExample(SchoolExample example);

    int deleteByExample(SchoolExample example);

    int deleteByPrimaryKey(Integer schoolId);

    int insert(SchoolWithBLOBs record);

    int insertSelective(SchoolWithBLOBs record);

    List<SchoolWithBLOBs> selectByExampleWithBLOBsWithRowbounds(SchoolExample example, RowBounds rowBounds);

    List<SchoolWithBLOBs> selectByExampleWithBLOBs(SchoolExample example);

    List<School> selectByExampleWithRowbounds(SchoolExample example, RowBounds rowBounds);

    List<School> selectByExample(SchoolExample example);

    SchoolWithBLOBs selectByPrimaryKey(Integer schoolId);

    int updateByExampleSelective(@Param("record") SchoolWithBLOBs record, @Param("example") SchoolExample example);

    int updateByExampleWithBLOBs(@Param("record") SchoolWithBLOBs record, @Param("example") SchoolExample example);

    int updateByExample(@Param("record") School record, @Param("example") SchoolExample example);

    int updateByPrimaryKeySelective(SchoolWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SchoolWithBLOBs record);

    int updateByPrimaryKey(School record);
}