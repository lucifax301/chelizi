package com.lili.order.dao.mapper;

import com.lili.order.dto.StudentClassPool;
import com.lili.order.dto.StudentClassPoolExample;
import com.lili.order.dto.StudentClassPoolKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentClassPoolMapper {
    int countByExample(StudentClassPoolExample example);

    int deleteByExample(StudentClassPoolExample example);

    int deleteByPrimaryKey(StudentClassPoolKey key);

    int insert(StudentClassPool record);

    int insertSelective(StudentClassPool record);

    List<StudentClassPool> selectByExampleWithRowbounds(StudentClassPoolExample example, RowBounds rowBounds);

    List<StudentClassPool> selectByExample(StudentClassPoolExample example);

    StudentClassPool selectByPrimaryKey(StudentClassPoolKey key);

    int updateByExampleSelective(@Param("record") StudentClassPool record, @Param("example") StudentClassPoolExample example);

    int updateByExample(@Param("record") StudentClassPool record, @Param("example") StudentClassPoolExample example);

    int updateByPrimaryKeySelective(StudentClassPool record);

    int updateByPrimaryKey(StudentClassPool record);
}