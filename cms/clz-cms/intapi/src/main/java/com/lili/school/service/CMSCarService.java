package com.lili.school.service;

import java.util.List;

import com.lili.cms.entity.ResponseMessage;
import com.lili.log.model.LogCommon;
import com.lili.school.model.Car;
import com.lili.school.model.CarBDTO;
import com.lili.school.model.CarCheckVo;
import com.lili.school.model.CarNBDTO;

public interface CMSCarService {

	/**
	 * 
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public ResponseMessage findOne(long carId) throws Exception;

	/**
	 * 插入教练和车的关系
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertCoachCar(CarNBDTO dto) throws Exception;

	/**
	 * 删除车和教练的对应关系
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long delCoachCar(CarNBDTO dto) throws Exception;

	/**
	 * 更新教练中的车辆Id
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long updateCoachCarId(CarNBDTO dto) throws Exception;

	/**
	 * 参数
	 * 
	 * @param params
	 * @return
	 */
	public ResponseMessage findBatch(CarBDTO dto) throws Exception;

	public List<Car> getExportSource(CarBDTO dto) throws Exception;

	public ResponseMessage findOneForCoach(CarNBDTO dto) throws Exception;

	/**
	 * 主要通过车牌号查询车辆信息
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<Car> findCar(CarNBDTO dto) throws Exception;

	/**
	 * 参数
	 * 
	 * @param params
	 * @return
	 */
	public ResponseMessage insertOne(LogCommon token, Car car) throws Exception;

	/**
	 * 参数
	 * 
	 * @param params
	 * @return
	 */
	public ResponseMessage updateOne(LogCommon token, Car car) throws Exception;

	public Integer queryTotalCoachCar(Car car);

	public List<Car> findByStudentId(long studentId) throws Exception;

	public Integer findExist(CarNBDTO carBDTO) throws Exception;

	/**
	 * 获取注册教练车信息
	 * @param carId	
	 * @return
	 */
	public CarCheckVo queryRegCarInfo(Integer carId) throws Exception;
	
	public ResponseMessage deleteOne(LogCommon logCommon, Car car) throws Exception;

}
