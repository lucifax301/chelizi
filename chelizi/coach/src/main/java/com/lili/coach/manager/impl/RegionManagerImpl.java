package com.lili.coach.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Region;
import com.lili.coach.manager.RegionManager;
import com.lili.coach.mapper.dao.RegionMapper;

public class RegionManagerImpl implements RegionManager {

	@Autowired
	private RegionMapper regionMapper;
	
	@Override
	public List<Region> getRegion(Region region) {
		return regionMapper.getAll(region);
	}

	@Override
	public Region getRegionInfo(int rid) {
		return regionMapper.selectByPrimaryKey(rid);
	}

	@Override
	public int getCount() {
		return regionMapper.countAll();
	}

	@Override
	public int addRegion(Region region) {
		return regionMapper.insertSelective(region);
	}

	@Override
	public int updateRegion(Region region) {
		return regionMapper.updateByPrimaryKeySelective(region);
	}

	@Override
	public int deleteRegion(int rid) {
		return regionMapper.deleteByPrimaryKey(rid);
	}

	@Override
	public Integer getRidByName(String cityName) {
		return regionMapper.getRidByName(cityName.trim());
	}

	@Override
	public List<Region> getRegistCity(Region region) {
		return regionMapper.selectRegisterCity(region);
	}
	
	

}
