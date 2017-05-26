package com.lili.school.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.school.manager.IPosterManager;
import com.lili.school.mapper.dao.PosterMapper;
import com.lili.school.model.PosterDTO;

public class PosterManagerImpl  implements  IPosterManager{
	
	@Autowired
	PosterMapper posterMapper;

	@Override
	public PagedResult<PosterDTO> queryList(PosterDTO poster) {
		PageUtil.startPage(poster.getPageNo(), poster.getPageSize());
		return BeanUtil.toPagedResult(posterMapper.queryPosterList(poster));
	}

}
