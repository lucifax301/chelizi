/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import com.lili.coach.dto.Trfield;

public interface TrfieldMapper {

	/**
	 * 根据主键删除训练场信息
	 * @param trFieldId
	 * @return
	 */
    int deleteByPrimaryKey(Integer trField);

    /**
     * 新增训练场信息
     * @param trField
     * @return
     */
    int insert(Trfield trField);

    /**
     * 新增训练场信息
     * @param trField
     * @return
     */
    int insertSelective(Trfield trField);

    /**
     * 根据id获取训练场信息
     * @param trFieldId
     * @return
     */
    Trfield selectByPrimaryKey(Integer carId);

    /**
     * 修改训练场信息
     * @param trField
     * @return
     */
    int updateByPrimaryKeySelective(Trfield trField);

    /**
     * 修改训练场信息
     * @param trField
     * @return
     */
    int updateByPrimaryKey(Trfield trField);
    
    /**
     * 获取训练场列表
     * @param trField
     * @return
     */
    List<Trfield> getAll(Trfield trField);
    
    /**
     * 获取训练场数量
     * @return
     */
    int countAll();
    
}
