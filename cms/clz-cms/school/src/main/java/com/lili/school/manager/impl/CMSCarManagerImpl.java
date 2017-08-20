package com.lili.school.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.util.BeanUtil;
import com.lili.cms.util.PageUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.school.manager.CMSCarManager;
import com.lili.school.mapper.dao.CarMapper;
import com.lili.school.model.Car;
import com.lili.school.model.CarBDTO;
import com.lili.school.model.CarCheckVo;
import com.lili.school.model.CarNBDTO;

public class CMSCarManagerImpl implements CMSCarManager{
	
	@Autowired
	CarMapper carMapper;
	@Autowired
	RedisUtil redisUtil;
	

	public Car findOne(long carId)  throws Exception{
		Car car = carMapper.findOne(carId);
		return car;
	}

	/**
	 * 分页查询<p>
	 * PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
	 */
	@Override
	public PagedResult<Car> findBatch(CarBDTO dto) throws Exception{
		PageUtil.startPage(dto.getPageNo(), dto.getPageSize());
		return BeanUtil.toPagedResult((carMapper.findBatch(dto)));
	}

	@Override
	public List<Car> findOneForCoach(CarNBDTO dto)  throws Exception{
		return carMapper.findOneForCoach(dto);
	}

	@Override
	public Long insertOne(Car car)  throws Exception{
		return carMapper.insertOne(car);
	}

	@Override
	public Long updateOne(Car car)  throws Exception{
		redisUtil.delete(REDISKEY.CAR_INFO + car.getCarId().toString());
		List<String> coachIds = carMapper.findCoachIdbyCarId(car.getCarId().intValue());
		if (coachIds != null && !coachIds.isEmpty()){
			for (String coachId : coachIds){
				redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId);
				redisUtil.delete(REDISKEY.COACH_VO + coachId);
			}
		}
		return carMapper.updateOne(car);
	}
	
	@Override
	public Long deleteOne(Car car)  throws Exception{
		redisUtil.delete(REDISKEY.CAR_INFO + car.getCarId().toString());
		
		return carMapper.delete(car);
	}

	@Override
	public Integer findExist(CarNBDTO dto) throws Exception {
		int count = carMapper.findExist(dto);
		return count;
	}

	@Override
	public List<Car> findExportBatch(CarBDTO dto) throws Exception {
		List<Car> carList = carMapper.findExportBatch(dto);
		return carList;
	}

	@Override
	public List<Car> findByStudentId(long studentId) throws Exception {
		return carMapper.findByStudentId(studentId);
	}

	@Override
	public Integer queryTotalCoachCar(Car car) {
		return carMapper.queryTotalCar(car);
	}

	@Override
	public Long insertCarInfo(Car car) {
		Long result = null;
		
		result = carMapper.insertCarInfo(car);
		
		return result;
	}

	@Override
	public List<Car> findCar(CarNBDTO dto) throws Exception {
		return carMapper.findCar(dto);
	}

	@Override
	public Long delCoachCar(CarNBDTO dto) throws Exception {
		return carMapper.delCoachCar(dto);
	}

	@Override
	public Long updateCoachCarId(CarNBDTO dto) throws Exception {
		return carMapper.updateCoachCarId(dto);
	}

	@Override
	public Long insertCoachCar(CarNBDTO dto) throws Exception {
		return carMapper.insertCoachCar(dto);
	}

	@Override
	public CarCheckVo queryRegCarInfo(Integer carId) throws Exception {
		return carMapper.queryRegCarInfo(carId);
	}
	
}
