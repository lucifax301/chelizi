package com.lili.student.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.file.dto.HtmlObject;
import com.lili.student.manager.CMSHtmlObjectManager;
import com.lili.student.mapper.dao.common.HtmlObjectMapper;
import com.lili.student.model.HtmlObjectBDTO;

public class CMSHtmlObjectManagerImpl implements CMSHtmlObjectManager{

	@Autowired
	HtmlObjectMapper htmlObjectMapper;
	
	@Override
	public HtmlObject findOne(Integer id) {
		return htmlObjectMapper.selectByPrimaryKey(id);
	}

	@Override
	public PagedResult<HtmlObject> findBatch(HtmlObjectBDTO dto) {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((htmlObjectMapper.findBatch(dto)));
	}

	
}
