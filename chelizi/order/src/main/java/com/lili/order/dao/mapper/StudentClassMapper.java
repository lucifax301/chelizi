package com.lili.order.dao.mapper;

import com.lili.order.dto.StudentClass;
import com.lili.order.dto.StudentClassExample;
import com.lili.order.dto.StudentClassVo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface StudentClassMapper {
    int countByExample(StudentClassExample example);

    int deleteByExample(StudentClassExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StudentClass record);

    int insertSelective(StudentClass record);

    List<StudentClass> selectByExampleWithRowbounds(StudentClassExample example, RowBounds rowBounds);

    List<StudentClass> selectByExample(StudentClassExample example);
    
    List<StudentClass> selectPageByExample(StudentClassExample example);
    

    StudentClass selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StudentClass record, @Param("example") StudentClassExample example);

    int updateByExample(@Param("record") StudentClass record, @Param("example") StudentClassExample example);

    int updateByPrimaryKeySelective(StudentClass record);

    int updateByPrimaryKey(StudentClass record);

    /**
     * 手动添加修改部分，包括StudentClassVo,StudentClassExample 	
     * @param example
     * @param rowBounds
     * @return
     */
	List<StudentClassVo> selectVoByExample(StudentClassExample example, RowBounds rowBounds);
	int countVoByExample(StudentClassExample example);

}