package com.lili.school.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.coach.dto.BrandCar;
import com.lili.school.model.BrandCarBDTO;

public interface CMSBrandCarManager {

	public BrandCar findOne(Integer id)  throws Exception;

	public PagedResult<BrandCar> findBatch(BrandCarBDTO dto)  throws Exception;
	

    
    List<BrandCar> getAllBrandName()  throws Exception;
    
    List<BrandCar> getCarByBrand(String brandname)  throws Exception;
}
