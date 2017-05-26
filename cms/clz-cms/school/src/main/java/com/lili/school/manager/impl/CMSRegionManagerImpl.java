package com.lili.school.manager.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.coach.model.RegionBDTO;
import com.lili.school.manager.CMSRegionManager;
import com.lili.school.mapper.dao.RegionMapper;
import com.lili.school.model.Region;

public class CMSRegionManagerImpl implements CMSRegionManager{

	@Autowired
	RegionMapper regionMapper;
	
	@Override
	public List<Region> findCityList(Region region) {
		return regionMapper.findCityList(region);
	}

	@Override
	public Region findOne(Integer rid) throws Exception {
		return regionMapper.findOne(rid);
	}

	@Override
	public PagedResult<Region> findBatch(RegionBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(regionMapper.findBatch(dto));
	}

	@Override
	public Long updateOne(Region region) throws Exception {
		return regionMapper.updateOne(region);
	}

	@Override
	public Long addOne(Region region) throws Exception {
		return regionMapper.addOne(region);
	}

	@Override
	public Region findByName(String region) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("region", region);
		return regionMapper.findByName(params);
	}
	
	
}
