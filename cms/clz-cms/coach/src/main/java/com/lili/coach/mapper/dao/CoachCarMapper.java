/**
 * 
 */
package com.lili.coach.mapper.dao;

import java.util.List;

import com.lili.coach.model.CoachCar;

public interface CoachCarMapper {


    /**
     * 根据id获取教练、车辆信息
     * @param coachId
     * @return
     */
    CoachCar selectByPrimaryKey(Long coachId);

	List<CoachCar> queryCoachCarList(Long coachId);

}
