package com.lili.file.mapper;

import com.lili.file.dto.Poster;
import com.lili.file.dto.PosterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PosterMapper {
    int countByExample(PosterExample example);

    int deleteByExample(PosterExample example);

    int deleteByPrimaryKey(Integer posterid);

    int insert(Poster record);

    int insertSelective(Poster record);

    List<Poster> selectByExampleWithRowbounds(PosterExample example, RowBounds rowBounds);

    List<Poster> selectByExample(PosterExample example);

    Poster selectByPrimaryKey(Integer posterid);

    int updateByExampleSelective(@Param("record") Poster record, @Param("example") PosterExample example);

    int updateByExample(@Param("record") Poster record, @Param("example") PosterExample example);

    int updateByPrimaryKeySelective(Poster record);

    int updateByPrimaryKey(Poster record);
}