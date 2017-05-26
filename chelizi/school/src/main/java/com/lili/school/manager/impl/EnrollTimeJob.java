package com.lili.school.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.redis.RedisUtil;
import com.lili.school.dto.EnrollLongtrain;
import com.lili.school.dto.EnrollLongtrainExample;
import com.lili.school.dto.EnrollTheory;
import com.lili.school.dto.EnrollTheoryExample;
import com.lili.school.mapper.EnrollLongtrainMapper;
import com.lili.school.mapper.EnrollTheoryMapper;

public class EnrollTimeJob {
	private static Logger log = LoggerFactory.getLogger(EnrollTimeJob.class);
	@Autowired
	EnrollTheoryMapper enrollTheoryMapper;
    @Autowired
    EnrollLongtrainMapper enrollLongtrainMapper;

	@Autowired
	private RedisUtil redisUtil;
    
	public void checkEnrollTask(){
		try {
			Date nowDate = new Date();
			EnrollTheoryExample example = new EnrollTheoryExample();
			example.createCriteria()
			.andStateEqualTo(1)		//状态：0-未确认；1-确认开课，待上课；2-已上课；3-已取消',
			.andClassDateLessThanOrEqualTo(nowDate);
			List<EnrollTheory> theorys = enrollTheoryMapper.selectByExample(example);
			if(null != theorys && theorys.size()>0){
				for(EnrollTheory theory:theorys){
					String dataStr = new SimpleDateFormat("yyyy-MM-dd").format(theory.getClassDate()) + " " + theory.getClassTime().split("-")[0];
					Date theoryDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dataStr);
					if(nowDate.after(theoryDate)){ //现在已经过了上课时间，则改为上课状态
						theory.setState(2);
						enrollTheoryMapper.updateByPrimaryKeySelective(theory);
					}
				}
			}
			
			EnrollLongtrainExample example2 = new EnrollLongtrainExample();
			example2.createCriteria()
			.andStateEqualTo(1)		//状态：0-未确认；1-确认开课，待上课；2-已上课；3-已取消',
			.andClassDateLessThanOrEqualTo(nowDate);
			List<EnrollLongtrain> trains = enrollLongtrainMapper.selectByExample(example2);
			if(null != trains && trains.size()>0){
				for(EnrollLongtrain train:trains){
					String dataStr = new SimpleDateFormat("yyyy-MM-dd").format(train.getClassDate()) + " " + train.getClassTime().split("-")[0];
					Date theoryDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dataStr);
					if(nowDate.after(theoryDate)){ //现在已经过了上课时间，则改为上课状态
						train.setState(2);
						enrollLongtrainMapper.updateByPrimaryKeySelective(train);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
}





































