package com.lili.student.mapper.dao;

import com.lili.student.dto.StudentAuth;
import com.lili.student.dto.StudentAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentAuthMapper {
    int countByExample(StudentAuthExample example);

    int deleteByExample(StudentAuthExample example);

    int deleteByPrimaryKey(Long id);

    int insert(StudentAuth record);

    int insertSelective(StudentAuth record);

    List<StudentAuth> selectByExampleWithRowbounds(StudentAuthExample example, RowBounds rowBounds);

    List<StudentAuth> selectByExample(StudentAuthExample example);

    StudentAuth selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") StudentAuth record, @Param("example") StudentAuthExample example);

    int updateByExample(@Param("record") StudentAuth record, @Param("example") StudentAuthExample example);

    int updateByPrimaryKeySelective(StudentAuth record);

    int updateByPrimaryKey(StudentAuth record);
}