package com.lili.exam.manager.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.coach.dto.School;
import com.lili.coach.manager.CoachManager;
import com.lili.exam.dto.ExamPlaceRecharge;
import com.lili.exam.dto.ExamPlaceRechargeGears;
import com.lili.exam.dto.ExamPlaceRechargeSchool;
import com.lili.exam.manager.ExamPlaceRechargeManager;
import com.lili.exam.mapper.ExamPlaceRechargeGearsMapper;
import com.lili.exam.mapper.ExamPlaceRechargeMapper;
import com.lili.exam.mapper.ExamPlaceRechargeSchoolMapper;

public class ExamPlaceRechargeManagerImpl implements ExamPlaceRechargeManager {
	private static Logger log = LoggerFactory.getLogger(ExamPlaceRechargeManagerImpl.class);
	@Autowired
	ExamPlaceRechargeMapper rechargeMapper;
	@Autowired
	ExamPlaceRechargeGearsMapper rechargeGearsMapper;
	@Autowired
	ExamPlaceRechargeSchoolMapper rechargeSchoolMapper;
	@Autowired
	private CoachManager coachManager;
	
	@Override
	public int addRecharge(String name, String gears, String info, String muser) {
		ExamPlaceRecharge record = new ExamPlaceRecharge();
		record.setName(name);
		record.setInfo(info);
		Date nowDate = new Date();
		record.setCtime(nowDate);
		record.setMtime(nowDate);
		record.setCuser(muser);
		record.setMuser(muser);
		rechargeMapper.insertSelective(record);
		int rid = record.getId();
		log.debug("add rid=" + rid);
		return 0;
	}

	@Override
	public Integer addRecharge(String name,
			List<ExamPlaceRechargeGears> rechargeGearList, String info,
			String muser) {
		try {
			ExamPlaceRecharge record = new ExamPlaceRecharge();
			record.setName(name);
			record.setInfo(info);
			Date nowDate = new Date();
			record.setCtime(nowDate);
			record.setMtime(nowDate);
			record.setCuser(muser);
			record.setMuser(muser);
			rechargeMapper.insertSelective(record);
			int rid = record.getId();
			log.debug("add rid=" + rid);
			if(null != rechargeGearList && rechargeGearList.size() > 0){
				for(ExamPlaceRechargeGears gear:rechargeGearList){
					gear.setRid(rid);
					gear.setCtime(nowDate);
					gear.setMtime(nowDate);
					gear.setState((byte) 0); //'状态：0-已激活；1-已停用；2-已删除',
					rechargeGearsMapper.insertSelective(gear);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ExamPlaceRechargeSchool addRechargeSchool(String rid,
			String schoolId, String muser) {
		try {
			School school = coachManager.getSchool(Integer.parseInt(schoolId));
			ExamPlaceRechargeSchool record = new ExamPlaceRechargeSchool();
			record.setRid(Integer.parseInt(rid));
			record.setSchoolId(Integer.parseInt(schoolId));
			record.setSchoolName(school.getName());
			record.setSchoolMobile(school.getPhoneNum());
			record.setSchoolAddr(school.getAddress());
			record.setCoachCount(school.getCoachCount());
			record.setState((byte) 0);//'状态：0-已激活；1-已停用；2-已删除',
			Date dateNow = new Date();
			record.setCtime(dateNow);
			record.setMtime(dateNow);
			try {
				rechargeSchoolMapper.insertSelective(record);
				
				return record;
			} catch (Exception e) {
				//可能主键冲突，代表已添加过
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}






















































