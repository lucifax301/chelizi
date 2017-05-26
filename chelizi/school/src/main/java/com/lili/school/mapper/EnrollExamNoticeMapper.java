package com.lili.school.mapper;

import com.lili.school.dto.EnrollExamNotice;
import com.lili.school.dto.EnrollExamNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EnrollExamNoticeMapper {
    int countByExample(EnrollExamNoticeExample example);

    int deleteByExample(EnrollExamNoticeExample example);

    int deleteByPrimaryKey(Integer subjectId);

    int insert(EnrollExamNotice record);

    int insertSelective(EnrollExamNotice record);

    List<EnrollExamNotice> selectByExampleWithBLOBsWithRowbounds(EnrollExamNoticeExample example, RowBounds rowBounds);

    List<EnrollExamNotice> selectByExampleWithBLOBs(EnrollExamNoticeExample example);

    List<EnrollExamNotice> selectByExampleWithRowbounds(EnrollExamNoticeExample example, RowBounds rowBounds);

    List<EnrollExamNotice> selectByExample(EnrollExamNoticeExample example);

    EnrollExamNotice selectByPrimaryKey(Integer subjectId);

    int updateByExampleSelective(@Param("record") EnrollExamNotice record, @Param("example") EnrollExamNoticeExample example);

    int updateByExampleWithBLOBs(@Param("record") EnrollExamNotice record, @Param("example") EnrollExamNoticeExample example);

    int updateByExample(@Param("record") EnrollExamNotice record, @Param("example") EnrollExamNoticeExample example);

    int updateByPrimaryKeySelective(EnrollExamNotice record);

    int updateByPrimaryKeyWithBLOBs(EnrollExamNotice record);

    int updateByPrimaryKey(EnrollExamNotice record);
}