/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lili.coach.dto.Trfields;
import com.lili.coach.vo.TrfieldsVo;

public interface TrfieldsMapper {

	/**
	 * 根据主键删除训练场信息
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增训练场信息
     * @param trFields
     * @return
     */
    int insert(Trfields trFields);

    /**
     * 新增训练场信息
     * @param trFields
     * @return
     */
    int insertSelective(Trfields trFields);

    /**
     * 根据id获取训练场信息
     * @param trFieldId
     * @return
     */
    Trfields selectByPrimaryKey(Integer id);

    /**
     * 修改训练场信息
     * @param trFields
     * @return
     */
    int updateByPrimaryKeySelective(Trfields trFields);

    /**
     * 修改训练场信息
     * @param trFields
     * @return
     */
    int updateByPrimaryKey(Trfields trFields);
    
    /**
     * 获取训练场列表
     * @param trFields
     * @return
     */
    List<Trfields> getAll(Trfields trFields);
    
    /**
     * 获取训练场数量
     * @return
     */
    int countAll();

	List<TrfieldsVo> getTrfieldsSpecial(@Param("name") String name, @Param("region") String region, @Param("imei") String imei);
    
}
