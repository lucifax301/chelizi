/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import com.lili.coach.dto.CoachCar;

public interface CoachCarMapper {

	/**
	 * 根据教练id删除教练、车辆关联信息
	 * @param coachId
	 * @return
	 */
    int deleteByCoachId(Long coachId);
    
	/**
	 * 根据车辆id删除教练、车辆关联信息
	 * @param carId
	 * @return
	 */
    int deleteByCarId(Integer carId);

    /**
     * 新增教练、车辆关联信息
     * @param coachCar
     * @return
     */
    int insert(CoachCar coachCar);

    /**
     * 新增教练、车辆关联信息
     * @param coachCar
     * @return
     */
    int insertSelective(CoachCar coachCar);

    /**
     * 根据id获取教练、车辆信息
     * @param coachId
     * @return
     */
    CoachCar selectByPrimaryKey(Long coachId);

    /**
     * 修改教练、车辆信息
     * @param coachCar
     * @return
     */
    int updateByPrimaryKeySelective(CoachCar coachCar);

    /**
     * 修改教练、车辆信息
     * @param coachCar
     * @return
     */
    int updateByPrimaryKey(CoachCar coachCar);
    
    /**
     * 获取教练、车辆列表
     * @return
     */
    List<CoachCar> getAll(CoachCar coachCar);
    
    /**
     * 获取教练、车辆数量
     * @return
     */
    int countAll();

    int deleteCoachCar(CoachCar record);
    
}
