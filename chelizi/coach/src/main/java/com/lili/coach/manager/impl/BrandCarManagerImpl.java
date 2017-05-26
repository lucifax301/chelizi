package com.lili.coach.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.BrandCar;
import com.lili.coach.manager.BrandCarManager;
import com.lili.coach.mapper.dao.BrandCarMapper;

public class BrandCarManagerImpl implements BrandCarManager{
	
	@Autowired
	private BrandCarMapper brandCarMapper;

	@Override
	public List<BrandCar> getBrandCar(BrandCar brandCar) {
		return brandCarMapper.getAll(brandCar);
	}

	@Override
	public int updateBrandCar(BrandCar brandCar) {
		return brandCarMapper.updateByPrimaryKeySelective(brandCar);
	}

	@Override
	public int addBrandCar(BrandCar brandCar) {
		return brandCarMapper.insertSelective(brandCar);
	}

}
