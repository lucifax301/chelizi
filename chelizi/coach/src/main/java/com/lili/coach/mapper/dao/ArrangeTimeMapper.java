/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import com.lili.coach.dto.ArrangeTime;

public interface ArrangeTimeMapper {

	/**
	 * 根据主键删除排班时间信息
	 * @param arrangeTimeId
	 * @return
	 */
    int deleteByPrimaryKey(Integer arrangeTime);

    /**
     * 新增排班时间信息
     * @param arrangeTime
     * @return
     */
    int insert(ArrangeTime arrangeTime);

    /**
     * 新增排班时间信息
     * @param arrangeTime
     * @return
     */
    int insertSelective(ArrangeTime arrangeTime);

    /**
     * 根据id获取排班时间信息
     * @param arrangeTimeId
     * @return
     */
    ArrangeTime selectByPrimaryKey(Integer carId);

    /**
     * 修改排班时间信息
     * @param arrangeTime
     * @return
     */
    int updateByPrimaryKeySelective(ArrangeTime arrangeTime);

    /**
     * 修改排班时间信息
     * @param arrangeTime
     * @return
     */
    int updateByPrimaryKey(ArrangeTime arrangeTime);
    
    /**
     * 获取排班时间列表
     * @param arrangeTime
     * @return
     */
    List<ArrangeTime> getAll(ArrangeTime arrangeTime);
    
    /**
     * 获取排班时间数量
     * @return
     */
    int countAll();
    
}
