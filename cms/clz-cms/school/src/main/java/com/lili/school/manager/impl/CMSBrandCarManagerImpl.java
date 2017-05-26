package com.lili.school.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.coach.dto.BrandCar;
import com.lili.school.manager.CMSBrandCarManager;
import com.lili.school.mapper.dao.BrandCarMapper;
import com.lili.school.model.BrandCarBDTO;

public class CMSBrandCarManagerImpl implements CMSBrandCarManager{

	@Autowired
	BrandCarMapper brandCarMapper;
	
	@Override
	public BrandCar findOne(Integer id) throws Exception {
		return brandCarMapper.selectByPrimaryKey(id);
	}

	@Override
	public PagedResult<BrandCar> findBatch(BrandCarBDTO dto) throws Exception {
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult(brandCarMapper.findBatch(dto));
	}

	@Override
	public List<BrandCar> getAllBrandName() throws Exception {
		return brandCarMapper.getAllBrandName();
	}

	@Override
	public List<BrandCar> getCarByBrand(String brandname) throws Exception {
		return brandCarMapper.getCarByBrand(brandname);
	}

}
