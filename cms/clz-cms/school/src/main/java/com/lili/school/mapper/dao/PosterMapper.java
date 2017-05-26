package com.lili.school.mapper.dao;


import java.util.List;

import com.lili.school.model.PosterDTO;

public interface PosterMapper {
    int deleteByPrimaryKey(Integer posterid);

    int insert(PosterDTO record);

    int insertSelective(PosterDTO record);

    PosterDTO selectByPrimaryKey(Integer posterid);

    int updateByPrimaryKeySelective(PosterDTO record);

    int updateByPrimaryKey(PosterDTO record);

	List<PosterDTO> queryPosterList(PosterDTO poster);
}