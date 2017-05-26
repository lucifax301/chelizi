package com.lili.coach.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.Car;
import com.lili.coach.dto.CarCheck;
import com.lili.coach.dto.CarExample;
import com.lili.coach.dto.CarInfo;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.CoachCar;
import com.lili.coach.manager.CarManager;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.mapper.dao.CarCheckMapper;
import com.lili.coach.mapper.dao.CarMapper;
import com.lili.coach.mapper.dao.CoachCarMapper;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;

public class CarManagerImpl implements CarManager {

	@Autowired
	private CarMapper carMapper;
	@Autowired
	private CoachCarMapper coachCarMapper;
	@Autowired
	private CarCheckMapper carCheckMapper;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private CoachManager coachManager;

	@Override
	public Car getCarInfo(int id) {
	    
	    Car car = redisUtil.get(REDISKEY.CAR_INFO + id);
	    if (car == null)
        {
	        car = carMapper.selectByPrimaryKey(id);
            redisUtil.set(REDISKEY.CAR_INFO + id, car,3600*24);
        }
		return car;
	}
	
	

	@Override
	public CarInfo getCarInfoByNo(String carNo) {
		CarExample example = new CarExample();
		CarExample.Criteria criteria = example.createCriteria();
		criteria.andCarNoEqualTo(carNo.trim());
		List<Car> cars = carMapper.selectByExample(example);
		if(null != cars && cars.size() >0){
			CarInfo ci = new CarInfo();
			Car c = cars.get(0);
			ci.setCarId(c.getCarId());
			ci.setCarNo(c.getCarNo());
			ci.setCarType(c.getCarType());
			ci.setDriveType(c.getDriveType());
			
			return ci;
		}

		return null;
	}



	@Override
	public int getCountByDrivenumber(String  drivenumber) {
		CarExample example = new CarExample();
		CarExample.Criteria criteria = example.createCriteria();
		criteria.andDriveNumberEqualTo(drivenumber);
		return carMapper.countByExample(example);
	}

	@Override
	public int addCar(Car car, Long coachId) {
		
		carMapper.insertSelective(car);
		
		//教练、车辆信息新增
		CoachCar coachcar = new CoachCar();
		
		coachcar.setCarId(car.getCarId());
		coachcar.setCoachId(coachId);
		coachcar.setAddTime(new Date());
		coachcar.setIsExist(ReqConstants.OPERATE_DELETE_NO +0);
		int r = coachCarMapper.insert(coachcar);
		
		int id = car.getCarId();        // 获取车辆id

		Coach coachInfo = coachManager.getCoachInfo(coachId);
        // 保存教练车辆id
		if (coachInfo.getIsImport() != 0) { //注册教练我的车辆新加不可出车
			Coach coach = new Coach();
			if (coachId != null && coachId != 0)
			{
				coach.setCoachId(coachId);   // 教练ID
			}
			coach.setCoachCarId(id);                     // 教练车辆ID
			
			// 更新教练车辆id
			coachManager.updateCoach(coach);
		}
        
        redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId.toString());
		return r;
	}

	@Override
	public int updateCar(Car car, Long coachId) {
		redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId.toString());
		return carMapper.updateByPrimaryKeySelective(car);
	}

	@Override
	public int deleteCar(int id) {
		
		//删除车辆信息
		carMapper.deleteByPrimaryKey(id);
		
		//删除教练、车辆信息关系
		return coachCarMapper.deleteByCarId(id);
	}

	@Override
	public Car getCarLevelByCoachId(Long coachId) {
		
		if(null == coachId){
			return null;
		}else{
		    Coach coach = redisUtil.get(REDISKEY.COACH + coachId);
            if (coach == null)
            {
            	coach = coachManager.getCoachInfo(coachId);
            }
            
            return getCarInfo(coach.getCoachCarId());
		}
	}

	@Override
	public List<Car> getCarByCoachId(Long coachId) {
		
		if(null == coachId){
			return null;
		}else{
			List<Car> list=redisUtil.get(REDISKEY.USER_CAR_LIST + coachId);
			if(list!=null){
				return list;
			}
//		    if (redisUtil.isExist(REDISKEY.USER_CAR_LIST + coachId))
//            {
//		    	
//                return redisUtil.get(REDISKEY.USER_CAR_LIST + coachId);
//            }
		    else
		    {
		    	CoachCar cc = new CoachCar();
		    	cc.setCoachId(coachId);
		    	List<CoachCar> ccs = coachCarMapper.getAll(cc);
		    	if(null != ccs && ccs.size() >0){
		    		List<Integer> carids = new ArrayList<Integer>();
		    		for(int i=0;i<ccs.size();i++){
		    			carids.add(ccs.get(i).getCarId());
		    		}
					CarExample example = new CarExample();
					CarExample.Criteria criteria = example.createCriteria();
					criteria.andCarIdIn(carids);
			        List<Car> cars = carMapper.selectByExample(example);
			        redisUtil.set(REDISKEY.USER_CAR_LIST + coachId, cars,3600*24);
			        return cars;
		    	}else {
		    		return null;
		    	}

            }
		}
	}

	@Override
	public int addCarList(List<Car> cars) {
		int rs = 0;
		for (int i = 0; i < cars.size(); i++){
			rs += carMapper.insertSelective(cars.get(i));
		}
		return rs;
	}



	@Override
	public int deleteCoachCarAndCheck(Car car, Long coachId) {
		//教练、车辆信息解绑
		CoachCar coachCar =new CoachCar();
		coachCar.setCarId(car.getCarId());
		coachCar.setCoachId(coachId);
		int r = coachCarMapper.deleteCoachCar(coachCar);
		
		//删除注册教练审核信息
		CarCheck record = new CarCheck();
		record.setCarId(car.getCarId());
		record.setCoachId(coachId);
		carCheckMapper.deleteCoachCar(record);
		
        redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId.toString());
		return r;
	}
	

	@Override
	public void updateCarCheck(CarCheck carCheck) {
		carCheckMapper.updateCarCheck(carCheck);
	}
	

	@Override
	public void addRegisterCoachCar(CarCheck carCheck, Long coachId, boolean isAddCarCoach) {
		
		try {
			Car record = new Car();
			record.setCarNo(carCheck.getCarNo());
			Integer carId = null;
			Car car = carMapper.queryByCarNo(record);//根据车牌判断车辆是否存在
			if (car == null) { //不存在车辆信息
				car = new Car();
				car.setCarNo(carCheck.getCarNo());
				if (carCheck.getDriveType() != null && !"".equals(carCheck.getDriveType())) {
					car.setDriveType(carCheck.getDriveType().byteValue());
				}
				car.setDrivePhoto(carCheck.getDrivePhoto());
				car.setCarImg(carCheck.getCarImg());
				car.setSchoolId(0);//注册添加的车
				carMapper.insertSelective(car);
				Car insertCar = carMapper.queryByCarNo(record);
				carId = insertCar.getCarId();
			}
			else { //已存在车辆信息
				carId = car.getCarId();
			}
			
			if (isAddCarCoach) {
				CoachCar coachcar = new CoachCar();
				coachcar.setCarId(carId);
				coachcar.setCoachId(coachId);
				coachcar.setAddTime(new Date());
				coachcar.setIsExist(ReqConstants.OPERATE_DELETE_NO +0);
				coachCarMapper.insert(coachcar);
			}
			
			//2.增加教练车的审核信息 
			carCheck.setCarId(carId);
			carCheck.setCoachId(coachId);
			carCheckMapper.insertSelective(carCheck);
			
			redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	public List<CarCheck> getCarCheckByCoachId(CarCheck carCheck) {
		List<CarCheck> carCheckList = null;
		try {
			carCheckList = carCheckMapper.queryCarCheckByCoachId(carCheck);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return carCheckList;
	}



	@Override
	public CarCheck getCarCheckByCarId(int carId) {
		CarCheck carCheck= new CarCheck();
		carCheck.setCarId(carId);
		CarCheck carCheckInfo = carCheckMapper.queryCarCheckInfo(carCheck);
		return carCheckInfo;
	}



	@Override
	public int addCarInfo(Car car) {
		return carMapper.insertSelective(car);
	}


	@Override
	public void updateCoachCarAndCheck(CarCheck carCheck, String carIdOld, Long coachId) {
		//删除之前的绑定信息；
		CoachCar record = new CoachCar();
		record.setCoachId(coachId);
		record.setCarId(Integer.parseInt(carIdOld));
		coachCarMapper.deleteCoachCar(record);
		
		//重新插入绑定信息；
		CoachCar coachCar = new CoachCar();
		coachCar.setCoachId(coachId);
		coachCar.setCarId(carCheck.getCarId());
		coachCarMapper.insertSelective(coachCar);
		
		//更新绑定审核信息
		carCheckMapper.updateByPrimaryKeySelective(carCheck);
		
		redisUtil.delete(REDISKEY.USER_CAR_LIST + coachId);
	}


	@Override
	public CarCheck getCarCheckInfo(CarCheck carCheckOld) {
		return carCheckMapper.queryCarCheckInfo(carCheckOld);
	}


	@Override
	public void deleteCarCheck(CarCheck record) {
		carCheckMapper.deleteCoachCar(record);
	}


	@Override
	public int deleteCoachCar(CoachCar coachCar) {
		int r = coachCarMapper.deleteCoachCar(coachCar);
		redisUtil.delete(REDISKEY.USER_CAR_LIST + coachCar.getCoachId());
		return r;
	}


}
