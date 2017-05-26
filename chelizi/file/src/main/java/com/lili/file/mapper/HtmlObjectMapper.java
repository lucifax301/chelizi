package com.lili.file.mapper;

import com.lili.file.dto.HtmlObject;
import com.lili.file.dto.HtmlObjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface HtmlObjectMapper {
    int countByExample(HtmlObjectExample example);

    int deleteByExample(HtmlObjectExample example);

    int deleteByPrimaryKey(Integer hid);

    int insert(HtmlObject record);

    int insertSelective(HtmlObject record);

    List<HtmlObject> selectByExampleWithBLOBsWithRowbounds(HtmlObjectExample example, RowBounds rowBounds);

    List<HtmlObject> selectByExampleWithBLOBs(HtmlObjectExample example);

    List<HtmlObject> selectByExampleWithRowbounds(HtmlObjectExample example, RowBounds rowBounds);

    List<HtmlObject> selectByExample(HtmlObjectExample example);

    HtmlObject selectByPrimaryKey(Integer hid);

    int updateByExampleSelective(@Param("record") HtmlObject record, @Param("example") HtmlObjectExample example);

    int updateByExampleWithBLOBs(@Param("record") HtmlObject record, @Param("example") HtmlObjectExample example);

    int updateByExample(@Param("record") HtmlObject record, @Param("example") HtmlObjectExample example);

    int updateByPrimaryKeySelective(HtmlObject record);

    int updateByPrimaryKeyWithBLOBs(HtmlObject record);

    int updateByPrimaryKey(HtmlObject record);
}