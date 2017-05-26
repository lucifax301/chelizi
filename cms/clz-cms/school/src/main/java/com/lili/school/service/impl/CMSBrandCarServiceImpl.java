package com.lili.school.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.dto.BrandCar;
import com.lili.coach.manager.BrandCarManager;
import com.lili.school.manager.CMSBrandCarManager;
import com.lili.school.model.BrandCarBDTO;
import com.lili.school.service.CMSBrandCarService;

public class CMSBrandCarServiceImpl implements CMSBrandCarService{
	

	protected final Logger access = Logger.getLogger(this.getClass());

	
	@Autowired
	CMSBrandCarManager cmsBrandCarManager;
	@Autowired
	BrandCarManager brandCarManager;

	@Override
	public ResponseMessage findOne(Integer id) throws Exception {
		BrandCar brandCar = cmsBrandCarManager.findOne(id);
		return new ResponseMessage().addResult("pageData", brandCar);
	}

	@Override
	public ResponseMessage findBatch(BrandCarBDTO dto) throws Exception {
		PagedResult<BrandCar> batch = cmsBrandCarManager.findBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage insertOne(BrandCar brandCar) throws Exception {
		int count = brandCarManager.addBrandCar(brandCar);
		if(count <= 0){
			return new ResponseMessage("插入失败");
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage updateOne(BrandCar brandCar) throws Exception {
		int count = brandCarManager.updateBrandCar(brandCar);
		if(count <= 0){
			return new ResponseMessage("插入失败");
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage getAllBrandName() throws Exception {
		return new ResponseMessage().addResult("pageData", cmsBrandCarManager.getAllBrandName());
	}

	@Override
	public ResponseMessage getCarByBrand(String brandname) throws Exception {
		return new ResponseMessage().addResult("pageData", cmsBrandCarManager.getCarByBrand(brandname));
	}

}
