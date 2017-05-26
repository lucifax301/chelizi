package com.lili.coach.mapper.dao;

import java.util.List;

import com.lili.coach.dto.CarCheck;

public interface CarCheckMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(CarCheck record);

    int insertSelective(CarCheck record);

    CarCheck selectByPrimaryKey(Integer id);

    int updateCarCheck(CarCheck record);
    
    int updateByPrimaryKeySelective(CarCheck record);

    int updateByPrimaryKey(CarCheck record);

	List<CarCheck> queryCarCheckByCoachId(CarCheck record);

	CarCheck queryCarCheckInfo(CarCheck carCheck);

	int deleteCoachCar(CarCheck record);
}