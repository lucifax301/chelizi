package com.lili.school.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.cms.util.StringUtil;
import com.lili.log.model.LogCommon;
import com.lili.school.manager.CMSCarManager;
import com.lili.school.manager.CMSSchoolManager;
import com.lili.school.model.Car;
import com.lili.school.model.CarBDTO;
import com.lili.school.model.CarCheckVo;
import com.lili.school.model.CarNBDTO;
import com.lili.school.service.CMSCarService;

public class CMSCarServiceImpl implements CMSCarService {

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	CMSCarManager cmsCarManager;

	@Autowired
	CMSSchoolManager cmsSchoolManager;

	public ResponseMessage findOne(long carId) throws Exception {
		Car car = cmsCarManager.findOne(carId);
		return new ResponseMessage().addResult("car", car);
	}

	@Override
	public ResponseMessage findBatch(CarBDTO dto) throws Exception {
		PagedResult<Car> batch = cmsCarManager.findBatch(dto);
		return new ResponseMessage().addResult("pageData", batch);
	}

	@Override
	public ResponseMessage insertOne(LogCommon logCommon, Car car) throws Exception {
		CarNBDTO dto = new CarNBDTO();
		dto.setCarId(car.getCarId());
		dto.setCarNo(car.getCarNo());
		dto.setDriveNumber(car.getDriveNumber());
		if (cmsCarManager.findExist(dto) <= 0) {
			if (cmsCarManager.insertOne(car) <= 0) {
				return new ResponseMessage("插入失败");
			} else {
				if (logCommon != null) {
					logCommon.setRelateId(String.valueOf(car.getCarId()));
				}
				return new ResponseMessage();
			}
		}
		return new ResponseMessage("车辆信息已存在");
	}

	@Override
	public ResponseMessage findOneForCoach(CarNBDTO dto) throws Exception {

		List<Car> cars = cmsCarManager.findOneForCoach(dto);
		StringBuffer coachNames = new StringBuffer();
		Car car0 = null;
		if (cars != null && cars.size() > 0) {
			car0 = cars.get(0);
			for (int i = 0; i < cars.size(); i++) {
				if (!StringUtil.isNullString(cars.get(i).getCoachName())) {
					coachNames.append(cars.get(i).getCoachName() + "|");
				}
			}
			if (coachNames.length() > 0) {
				car0.setCoachName(coachNames.substring(0, coachNames.length() - 1));
			}
		}
		return new ResponseMessage().addResult("car", car0);
	}

	@Override
	public ResponseMessage updateOne(LogCommon logCommon, Car car) throws Exception {
		CarNBDTO dto = new CarNBDTO();
		dto.setCarId(car.getCarId());
		dto.setCarNo(car.getCarNo());
		dto.setDriveNumber(car.getDriveNumber());
		if (cmsCarManager.findExist(dto) <= 0) {
			if (cmsCarManager.updateOne(car) <= 0) {
				return new ResponseMessage("更新失败");
			} else {
				if (logCommon != null) {
					logCommon.setRelateId(String.valueOf(car.getCarId()));
				}
				return new ResponseMessage();
			}
		}
		return new ResponseMessage("车辆信息已存在");
	}

	@Override
	public List<Car> getExportSource(CarBDTO dto) throws Exception {
		return cmsCarManager.findExportBatch(dto);
	}

	@Override
	public List<Car> findByStudentId(long studentId) throws Exception {
		return cmsCarManager.findByStudentId(studentId);
	}

	@Override
	public Integer queryTotalCoachCar(Car car) {
		return cmsCarManager.queryTotalCoachCar(car);
	}

	@Override
	public Integer findExist(CarNBDTO dto) throws Exception {
		return cmsCarManager.findExist(dto);
	}

	@Override
	public List<Car> findCar(CarNBDTO dto) throws Exception {
		return cmsCarManager.findCar(dto);
	}

	@Override
	public Long delCoachCar(CarNBDTO dto) throws Exception {
		return cmsCarManager.delCoachCar(dto);
	}

	@Override
	public Long updateCoachCarId(CarNBDTO dto) throws Exception {
		return cmsCarManager.updateCoachCarId(dto);
	}

	@Override
	public Long insertCoachCar(CarNBDTO dto) throws Exception {
		return cmsCarManager.insertCoachCar(dto);
	}

	@Override
	public CarCheckVo queryRegCarInfo(Integer carId) throws Exception {
		return cmsCarManager.queryRegCarInfo(carId);
	}

}
