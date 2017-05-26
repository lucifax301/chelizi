package com.lili.school.service;

import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.dto.BrandCar;
import com.lili.school.model.BrandCarBDTO;

public interface CMSBrandCarService {

	public  ResponseMessage findOne(Integer id) throws Exception;

	public  ResponseMessage findBatch(BrandCarBDTO dto) throws Exception;

	public  ResponseMessage insertOne(BrandCar brandCar) throws Exception;

	public  ResponseMessage updateOne(BrandCar brandCar) throws Exception;
	
    public  ResponseMessage getAllBrandName()  throws Exception;
    
    public  ResponseMessage getCarByBrand(String brandname)  throws Exception;

}
