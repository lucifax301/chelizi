package com.lili.student.mapper.dao;

import com.lili.student.dto.StudentVip;
import com.lili.student.dto.StudentVipExample;
import com.lili.student.dto.StudentVipWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentVipMapper {
    int countByExample(StudentVipExample example);

    int deleteByExample(StudentVipExample example);

    int deleteByPrimaryKey(Integer vipId);

    int insert(StudentVipWithBLOBs record);

    int insertSelective(StudentVipWithBLOBs record);

    List<StudentVipWithBLOBs> selectByExampleWithBLOBsWithRowbounds(StudentVipExample example, RowBounds rowBounds);

    List<StudentVipWithBLOBs> selectByExampleWithBLOBs(StudentVipExample example);

    List<StudentVip> selectByExampleWithRowbounds(StudentVipExample example, RowBounds rowBounds);

    List<StudentVip> selectByExample(StudentVipExample example);

    StudentVipWithBLOBs selectByPrimaryKey(Integer vipId);

    int updateByExampleSelective(@Param("record") StudentVipWithBLOBs record, @Param("example") StudentVipExample example);

    int updateByExampleWithBLOBs(@Param("record") StudentVipWithBLOBs record, @Param("example") StudentVipExample example);

    int updateByExample(@Param("record") StudentVip record, @Param("example") StudentVipExample example);

    int updateByPrimaryKeySelective(StudentVipWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(StudentVipWithBLOBs record);

    int updateByPrimaryKey(StudentVip record);
}