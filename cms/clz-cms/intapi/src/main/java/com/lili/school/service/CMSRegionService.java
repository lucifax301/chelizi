package com.lili.school.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.model.RegionBDTO;
import com.lili.school.model.Region;

public interface CMSRegionService {


	public ResponseMessage findCityList(Region region) throws Exception;


	public Region findRegion(Integer rid) throws Exception;

	public ResponseMessage findOne(Integer rid) throws Exception;


	/**
	 * 通过区域名
	 * @param region
	 * @return
	 * @throws Exception
	 */
	public Region findByName(String region) throws Exception;
	
	/**
	 * 分页
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public  ResponseMessage findBatch(RegionBDTO dto) throws Exception;


	public  ResponseMessage updateOne(com.lili.coach.dto.Region region) throws Exception;


	public  ResponseMessage addOne(com.lili.coach.dto.Region region) throws Exception;


	public List<Region> findAllCity(Region region) throws Exception;

	public List<Region> findRegionList(Region region) throws Exception;


}
