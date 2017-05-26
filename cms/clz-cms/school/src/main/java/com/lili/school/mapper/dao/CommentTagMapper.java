package com.lili.school.mapper.dao;


import java.util.List;

import com.lili.school.model.CommentTag;

public interface CommentTagMapper {
	
    int deleteByPrimaryKey(Integer upid);

    int insert(CommentTag record);

    int insertSelective(CommentTag record);

    CommentTag selectByPrimaryKey(Integer upid);

    int updateByPrimaryKeySelective(CommentTag record);

    int updateByPrimaryKey(CommentTag record);

	List<CommentTag> queryComTagList(String id);

	List<CommentTag> queryCommentTagList(CommentTag commonPrice);
}