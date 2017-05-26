package com.lili.student.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.student.manager.CmsTitleContentManager;
import com.lili.student.mapper.dao.common.TitleTypeMapper;
import com.lili.student.mapper.dao.common.TypeContentMapper;
import com.lili.student.model.TitleTypeVo;
import com.lili.student.model.TypeContentVo;

public class CmsTitleContentManagerImpl implements CmsTitleContentManager {
	
	@Autowired
	TitleTypeMapper titleTypeMapper;
	
	@Autowired
	TypeContentMapper typeContentMapper;

	@Override
	public PagedResult<TitleTypeVo> queryTitleList(TitleTypeVo tt) {
		PageUtil.startPage(tt.getPageNo(), tt.getPageSize());
		return BeanUtil.toPagedResult(titleTypeMapper.queryTitleList(tt));
	}

	@Override
	public int addTitle(TitleTypeVo tt) {
		return titleTypeMapper.insertSelective(tt);
	}

	@Override
	public int deleteTitle(TitleTypeVo tt) {
		TypeContentVo tcv = new TypeContentVo();
		tcv.setTypeId(tt.getId());
		typeContentMapper.deleteByTypeId(tcv);
		return titleTypeMapper.deleteByPrimaryKey(tt.getId());
	}

	@Override
	public int updateTitle(TitleTypeVo tt) {
		return titleTypeMapper.updateByPrimaryKeySelective(tt);
	}

	@Override
	public TypeContentVo queryContent(TypeContentVo tc) {
		return typeContentMapper.selectByPrimaryKey(tc.getId());
	}

	@Override
	public PagedResult<TypeContentVo> queryContentList(TypeContentVo tc) {
		PageUtil.startPage(tc.getPageNo(), tc.getPageSize());
		return BeanUtil.toPagedResult(typeContentMapper.queryContentList(tc));
	}

	@Override
	public int addContent(TypeContentVo tc) {
		return typeContentMapper.insertSelective(tc);
	}

	@Override
	public int updateContent(TypeContentVo tc) {
		return typeContentMapper.updateByPrimaryKeySelective(tc);
	}

	@Override
	public int releaseContent(TypeContentVo tc) {
		return typeContentMapper.updateByPrimaryKeySelective(tc);
	}

}
