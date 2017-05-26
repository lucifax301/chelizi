package com.lili.log.mapper.dao;

import com.lili.log.dto.LogOperate;
import com.lili.log.dto.LogOperateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface LogOperateMapper {
    int countByExample(LogOperateExample example);

    int deleteByExample(LogOperateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogOperate record);

    int insertSelective(LogOperate record);

    List<LogOperate> selectByExampleWithRowbounds(LogOperateExample example, RowBounds rowBounds);

    List<LogOperate> selectByExample(LogOperateExample example);

    LogOperate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogOperate record, @Param("example") LogOperateExample example);

    int updateByExample(@Param("record") LogOperate record, @Param("example") LogOperateExample example);

    int updateByPrimaryKeySelective(LogOperate record);

    int updateByPrimaryKey(LogOperate record);
}