package com.lili.school.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.school.dto.EnrollPackageTemplateWithBLOBs;
import com.lili.school.manager.CMSEPTManager;
import com.lili.school.mapper.dao.EPTMapper;
import com.lili.school.model.EnrollPackageTemplateVo;
import com.lili.school.model.EptwbBDTO;

public class CMSEPTManagerImpl implements CMSEPTManager{

	@Autowired
	EPTMapper eptMapper;
	
	@Override
	public EnrollPackageTemplateVo findOne(Integer ttid)
			throws Exception {
		return eptMapper.selectByPrimaryKey(ttid);
	}

	@Override
	public PagedResult<EnrollPackageTemplateWithBLOBs> findBatch(EptwbBDTO dto)
			throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(eptMapper.findBatch(dto));
	}
	
	
}
