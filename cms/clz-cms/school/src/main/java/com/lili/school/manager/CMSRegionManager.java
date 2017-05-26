package com.lili.school.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.coach.model.RegionBDTO;
import com.lili.school.model.Region;

public interface CMSRegionManager {

	List<Region> findCityList(Region region);

	public Region findOne(Integer rid) throws Exception;

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
	public  PagedResult<Region> findBatch(RegionBDTO dto) throws Exception;


	public  Long updateOne(Region region) throws Exception;


	public  Long addOne(Region region) throws Exception;
}
