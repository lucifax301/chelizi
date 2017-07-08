package com.lili.coach.manager;

import java.util.List;

import com.lili.coach.dto.Car;
import com.lili.coach.dto.CarCheck;
import com.lili.coach.dto.CarInfo;
import com.lili.coach.dto.CoachCar;

public interface CarManager {

	/**
	 * 根据id获取车辆信息
	 * @param id
	 * @return
	 */
	public Car getCarInfo(int id);
	
	public CarInfo getCarInfoByNo(String carNo);

	/**
	 * 新增车辆信息
	 * @param car
	 * @return
	 */
	public int addCar(Car car, Long coachId);

	/**
	 * 更新车辆信息
	 * @param car
	 * @return
	 */
	public int updateCar(Car car, Long coachId);

	/**
	 * 根据id删除车辆信息
	 * @param id
	 * @return
	 */
	public int deleteCar(int id);
	
	/**
	 * 根据教练id获取汽车等级
	 * @param coachId
	 * @return
	 */
	Car getCarLevelByCoachId(Long coachId);
	
	/**
	 * 根据教练id获取教练车辆信息
	 * @param coachId
	 * @return
	 */
	List<Car> getCarByCoachId(Long coachId);

	public int getCountByDrivenumber(String drivenumber);
	
	/**
	 * 批量导入车辆信息，cms使用
	 * @param List<Car>
	 * @return
	 */
	public int addCarList(List<Car> cars);

	public int deleteCoachCarAndCheck(Car car, Long coachId);

	/**
	 * 更新注册教练车信息
	 * @param carCheck
	 */
	public void updateCarCheck(CarCheck carCheck);

	/**
	 * 添加、更新教练审核信息
	 * @param carCheck
	 * @param coachId
	 */
	public void addRegisterCoachCar(CarCheck carCheck, Long coachId, boolean isAddCarCoach);

	/**
	 * 查询注册教练审核信息
	 * @param coachId
	 * @return
	 */
	public List<CarCheck> getCarCheckByCoachId(CarCheck carCheck);

	
	public CarCheck getCarCheckByCarId(int carId);

	public int addCarInfo(Car car);

	public void updateCoachCarAndCheck(CarCheck carCheck, String carIdOld, Long coachId);

	public CarCheck getCarCheckInfo(CarCheck carCheckOld);

	public void deleteCarCheck(CarCheck record);

	public int deleteCoachCar(CoachCar coachCar);
	
	public List<Car> getCarBySchoolId(Integer schoolId);

}
