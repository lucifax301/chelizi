package com.lili.school.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.manager.RegionManager;
import com.lili.coach.model.RegionBDTO;
import com.lili.school.manager.CMSRegionManager;
import com.lili.school.model.Region;
import com.lili.school.service.CMSRegionService;

public class CMSRegionServiceImpl implements CMSRegionService{

	protected final Logger access = Logger.getLogger(this.getClass());

	@Autowired
	CMSRegionManager cmsRegionManager;
	@Autowired
	RegionManager regionManager;
	
	@Override
	public ResponseMessage findCityList(Region region) throws Exception {
		return new ResponseMessage().addResult("pageData", cmsRegionManager.findCityList(region));
	}
	
	@Override
	public List<Region> findAllCity(Region region) throws Exception{
		return cmsRegionManager.findCityList(region);
	}

	@Override
	public ResponseMessage findOne(Integer rid) throws Exception {
		Region student = cmsRegionManager.findOne(rid);
		return new ResponseMessage().addResult("student", student);
	}

	@Override
	public ResponseMessage findBatch(RegionBDTO dto) throws Exception {
		return new ResponseMessage().addResult("pageData", cmsRegionManager.findBatch(dto));
	}

	@Override
	public ResponseMessage updateOne(com.lili.coach.dto.Region region) throws Exception {
		int count = regionManager.updateRegion(region);
		if(count <= 0){
			return new ResponseMessage("更新失败");
		}
		return new ResponseMessage();
	}

	@Override
	public ResponseMessage addOne(com.lili.coach.dto.Region region) throws Exception {
		int count = regionManager.addRegion(region);
		if( count <= 0){
			return new ResponseMessage("插入失败");
		}
		return new ResponseMessage();
	}

	@Override
	public Region findByName(String region) throws Exception {
		return cmsRegionManager.findByName(region);
	}

	@Override
	public Region findRegion(Integer rid) throws Exception {
		return cmsRegionManager.findOne(rid);
	}
	
	@Override
	public List<Region> findRegionList(Region region) throws Exception {
		return cmsRegionManager.findCityList(region);
	}
}
