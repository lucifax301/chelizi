/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.coach.dto.Region;

public interface RegionMapper {

	/**
	 * 根据主键删除城市信息
	 * @param rid
	 * @return
	 */
    int deleteByPrimaryKey(Integer rid);

    /**
     * 新增城市信息
     * @param region
     * @return
     */
    int insert(Region region);

    /**
     * 新增城市信息
     * @param region
     * @return
     */
    int insertSelective(Region region);

    /**
     * 根据id获取城市信息
     * @param rid
     * @return
     */
    Region selectByPrimaryKey(Integer rid);

    /**
     * 修改城市信息
     * @param region
     * @return
     */
    int updateByPrimaryKeySelective(Region region);

    /**
     * 修改城市信息
     * @param region
     * @return
     */
    int updateByPrimaryKey(Region region);
    
    /**
     * 获取城市列表
     * @param region
     * @return
     */
    List<Region> getAll(Region region);
    
    /**
     * 获取城市数量
     * @return
     */
    int countAll();

	/**
	 * 根据城市名称获取城市id
	 * @param cityName
	 * @return
	 */
	Integer getRidByName(@Param("region") String region);

	/**
	 * 获取注册城市
	 * @param region
	 * @return
	 */
	List<Region> selectRegisterCity(Region region);
    
}
