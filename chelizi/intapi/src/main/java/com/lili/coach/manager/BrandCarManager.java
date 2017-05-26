package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.BrandCar;

public interface BrandCarManager {

	/**
	 * 获取车型表，cms使用
	 * @param brandCar
	 */
	public List<BrandCar> getBrandCar(BrandCar brandCar);
	
	/**
	 * 车型表修改，cms使用
	 * @param brandCar
	 */
	public int updateBrandCar(BrandCar brandCar);
	
	/**
	 * 车型表新增，cms使用
	 * @param brandCar
	 */
	public int addBrandCar(BrandCar brandCar);
}
