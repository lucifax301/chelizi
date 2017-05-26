package com.lili.school.mapper;

import com.lili.school.dto.EnrollTheoryStudent;
import com.lili.school.dto.EnrollTheoryStudentExample;
import com.lili.school.dto.EnrollTheoryStudentKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EnrollTheoryStudentMapper {
    int countByExample(EnrollTheoryStudentExample example);

    int deleteByExample(EnrollTheoryStudentExample example);

    int deleteByPrimaryKey(EnrollTheoryStudentKey key);

    int insert(EnrollTheoryStudent record);

    int insertSelective(EnrollTheoryStudent record);

    List<EnrollTheoryStudent> selectByExampleWithRowbounds(EnrollTheoryStudentExample example, RowBounds rowBounds);

    List<EnrollTheoryStudent> selectByExample(EnrollTheoryStudentExample example);

    EnrollTheoryStudent selectByPrimaryKey(EnrollTheoryStudentKey key);

    int updateByExampleSelective(@Param("record") EnrollTheoryStudent record, @Param("example") EnrollTheoryStudentExample example);

    int updateByExample(@Param("record") EnrollTheoryStudent record, @Param("example") EnrollTheoryStudentExample example);

    int updateByPrimaryKeySelective(EnrollTheoryStudent record);

    int updateByPrimaryKey(EnrollTheoryStudent record);
}