package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.Region;


public interface RegionManager {

	/**
	 * 获取城市列表
	 * @param region
	 * @return
	 */
	public List<Region> getRegion(Region region);

	/**
	 * 根据id获取城市信息
	 * @param rid
	 * @return
	 */
	public Region getRegionInfo(int rid);

	/**
	 * 获取城市数量
	 * @return
	 */
	public int getCount();

	/**
	 * 新增城市信息
	 * @param region
	 * @return
	 */
	public int addRegion(Region region);

	/**
	 * 更新城市信息
	 * @param region
	 * @return
	 */
	public int updateRegion(Region region);

	/**
	 * 根据id删除城市信息
	 * @param rid
	 * @return
	 */
	public int deleteRegion(int rid);

	/**
	 * 根据城市名称获取城市id
	 * @param cityName
	 * @return
	 */
	public Integer getRidByName(String cityName);

	/**
	 * 获取注册城市信息
	 * @param registerCity
	 * @return
	 */
	public List<Region> getRegistCity(Region region);
}
