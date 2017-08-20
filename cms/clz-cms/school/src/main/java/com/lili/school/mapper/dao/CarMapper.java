/**
 * 
 */
package com.lili.school.mapper.dao;

import java.util.List;
import java.util.Map;

import com.lili.school.model.Car;
import com.lili.school.model.CarBDTO;
import com.lili.school.model.CarCheckVo;
import com.lili.school.model.CarNBDTO;

public interface CarMapper {

	public Car findOne(long carId) throws Exception;

	/**
	 * 更新教练中的车辆Id
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertCoachCar(CarNBDTO dto) throws Exception;

	/**
	 * 更新教练中的车辆Id
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long updateCoachCarId(CarNBDTO dto) throws Exception;

	/**
	 * 删除车和教练的对应关系
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long delCoachCar(CarNBDTO dto) throws Exception;

	public List<Car> findBatch(CarBDTO dto) throws Exception;

	/**
	 * 主要通过车牌号查询车辆信息
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<Car> findCar(CarNBDTO dto) throws Exception;

	public List<Car> findExportBatch(CarBDTO dto) throws Exception;

	public List<Car> findOneForCoach(CarNBDTO dto) throws Exception;

	public int findExist(CarNBDTO dto) throws Exception;

	public Long insertOne(Car car) throws Exception;

	public Long updateOne(Car car) throws Exception;
	
	public Long delete(Car car) throws Exception;

	public List<Car> findByStudentId(long studentId) throws Exception;

	public int findTotalCar(Map<String, Object> params) throws Exception;

	public Integer queryTotalCar(Car car);

	public Long insertCarInfo(Car car);

	public CarCheckVo queryRegCarInfo(Integer carId);

	public List<String> findCoachIdbyCarId(Integer carId);
}
