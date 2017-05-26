package com.lili.school.mapper;

import com.lili.school.dto.EnrollImportRecord;
import com.lili.school.dto.EnrollImportRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EnrollImportRecordMapper {
    int countByExample(EnrollImportRecordExample example);

    int deleteByExample(EnrollImportRecordExample example);

    int deleteByPrimaryKey(String tableNo);

    int insert(EnrollImportRecord record);

    int insertSelective(EnrollImportRecord record);

    List<EnrollImportRecord> selectByExampleWithRowbounds(EnrollImportRecordExample example, RowBounds rowBounds);

    List<EnrollImportRecord> selectByExample(EnrollImportRecordExample example);

    EnrollImportRecord selectByPrimaryKey(String tableNo);

    int updateByExampleSelective(@Param("record") EnrollImportRecord record, @Param("example") EnrollImportRecordExample example);

    int updateByExample(@Param("record") EnrollImportRecord record, @Param("example") EnrollImportRecordExample example);

    int updateByPrimaryKeySelective(EnrollImportRecord record);

    int updateByPrimaryKey(EnrollImportRecord record);
}