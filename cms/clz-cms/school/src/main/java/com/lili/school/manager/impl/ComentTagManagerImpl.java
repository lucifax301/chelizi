package com.lili.school.manager.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.school.manager.ICommentTagManager;
import com.lili.school.mapper.dao.CommentTagMapper;
import com.lili.school.model.CommentTag;

public class ComentTagManagerImpl implements ICommentTagManager {
	
	Logger logger = Logger.getLogger(ComentTagManagerImpl.class);
	
	@Autowired
	CommentTagMapper commentTagMapper;

	@Override
	public PagedResult<CommentTag> queryCommentTagList(CommentTag commentTag) {
		PageUtil.startPage(commentTag.getPageNo(), commentTag.getPageSize());
		return BeanUtil.toPagedResult(commentTagMapper.queryCommentTagList(commentTag));
	}

	@Override
	public List<CommentTag> queryComTagList(String id) {
		List<CommentTag> comPriceList = null;
		try {
			comPriceList = commentTagMapper.queryComTagList(id);
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return comPriceList;
	}

}
