package com.lili.report.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.constant.Constant;
import com.lili.cms.entity.PagedResult;
import com.lili.cms.entity.ResponseMessage;
import com.lili.coach.model.CoachBDTO;
import com.lili.coach.service.CMSCoachService;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.EXPIRE;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.order.model.OrderBDTO;
import com.lili.order.service.CMSOrderService;
import com.lili.report.manager.IStaTotalLiliManager;
import com.lili.report.service.ICmsStatisticsTotalLiliService;
import com.lili.report.vo.StatisticsCoachSchool;
import com.lili.report.vo.StatisticsCoachVo;
import com.lili.report.vo.StatisticsOrderVo;
import com.lili.report.vo.StatisticsStudentProgress;
import com.lili.report.vo.StatisticsStudentSchool;
import com.lili.report.vo.StatisticsStudentVo;
import com.lili.report.vo.StatisticsTotalLiliVo;
import com.lili.school.model.Car;
import com.lili.school.model.FieldBDTO;
import com.lili.school.service.CMSCarService;
import com.lili.school.service.CMSFieldService;
import com.lili.student.model.StudentBDTO;
import com.lili.student.service.CMSStudentService;

/**
 * 奖金
 * 
 * @author lzb
 * 
 */
public class CmsStaTotalLiliServiceImpl implements ICmsStatisticsTotalLiliService {

	Logger logger = Logger.getLogger(CmsStaTotalLiliServiceImpl.class);
	
	@Autowired
	IStaTotalLiliManager staticsTotalLiliVoManager;
	
	@Autowired
	CMSCoachService coachService;
	
	@Autowired
	CMSStudentService studentService;
	
	@Autowired
	CMSOrderService orderService;
	
	@Autowired
	CMSCarService carService;
	
	@Autowired
	CMSFieldService fieldService;
	
	@Autowired
	RedisUtil redisUtil;
	
	ExecutorService threadPool = Executors.newCachedThreadPool();

	@Override
	public String list(final String schoolId) throws Exception {
		String resp = redisUtil.get(REDISKEY.CMS_STATISTICS_TOTAL + schoolId);
		if (resp == null){	
			ResponseMessage rm = new ResponseMessage();
			rm.setMsg("正在处理数据");
			resp = rm.build();
			redisUtil.set(REDISKEY.CMS_STATISTICS_TOTAL + schoolId, resp, EXPIRE.HOUR * 4);
			threadPool.execute(new Runnable() {
				public void run() {
					try {
						Long schoolIdL = Long.valueOf(schoolId);
						Map<String, Object> params = new HashMap<String, Object>();
						//学员总人数
						StudentBDTO studto = new StudentBDTO();
						studto.setSchoolId(schoolIdL);
						studto.setCmsChannel(1);
						 Integer studentSum = studentService.queryTotalStudent(studto);
						
						// 教练总人数	 
						CoachBDTO coadto = new CoachBDTO();
						coadto.setSchoolId(schoolIdL);
						coadto.setCmsChannel(1);
						 Integer coachSum = coachService.queryTotalCoach(coadto);
						
						// 订单总数
						 OrderBDTO orddto = new OrderBDTO();
						 orddto.setSchoolId(schoolIdL);
						 orddto.setCmsChannel(1);
						 Integer orderSum = orderService.queryTotalOrder(orddto);
						
						// 教练车总数
						 Car car = new Car();
						 car.setSchoolId(schoolIdL);
						 car.setCmsChannel(1);
						 Integer carSum = carService.queryTotalCoachCar(car);
						
						// 训练场总数
						 FieldBDTO fielddto = new FieldBDTO();
						 fielddto.setSchoolId(schoolIdL);
						 fielddto.setCmsChannel(1);
						 Integer fieldSum = fieldService.queryTotaField(fielddto);
						
						 //查询7天内统计信息
						 List<StatisticsTotalLiliVo> servenList = staticsTotalLiliVoManager.getStatisticsTotalLiliInfo(params);
						 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
						 logger.info("************************************ servenList : " + servenList.size());
						 for (int i = 0; i < servenList.size(); i ++) {
							 servenList.get(i).setDateStr(dateFormat.format(servenList.get(i).getDtime()));
							 servenList.get(i).setLessThreeRatio(servenList.get(i).getLessThreeRatio()+ "%");
							 servenList.get(i).setFullClass(servenList.get(i).getFullClass() + "%");
						 }
						 
						 //查询一周内统计信息
						 StatisticsTotalLiliVo weekSum = staticsTotalLiliVoManager.queryWeekInfo(params);
						 if (weekSum != null) {
							 weekSum.setLessThreeRatio(weekSum.getLessThreeRatio() + "%");
							 weekSum.setFullClass(weekSum.getFullClass() + "%");
							 weekSum.setDateStr(Constant.WEEK_SUM);
						 }
						 
						 //查询一个月内统计信息
						 StatisticsTotalLiliVo monthSum = staticsTotalLiliVoManager.queryMonthInfo(params);
						 if (monthSum != null) {
							 monthSum.setLessThreeRatio(monthSum.getLessThreeRatio() + "%");
							 monthSum.setFullClass(monthSum.getFullClass()+ "%");
							 monthSum.setDateStr(Constant.MONTH_SUM);
						 }
						 
						 //组合数据
						 List<StatisticsTotalLiliVo> dateList = new ArrayList<StatisticsTotalLiliVo>(); 
						 dateList.addAll(servenList);
						 dateList.add(weekSum);
						 dateList.add(monthSum);
						 
						String resp = new ResponseMessage()
												.addResult("studentSum", studentSum)
												.addResult("coachSum", coachSum)
												.addResult("orderSum", orderSum)
												.addResult("carSum", carSum)
												.addResult("fieldSum", fieldSum)
												.addResult("dateList", dateList)
												.build();
						redisUtil.set(REDISKEY.CMS_STATISTICS_TOTAL + schoolId, resp, EXPIRE.HOUR * 4);
					} catch (Exception e) {
						redisUtil.delete(REDISKEY.CMS_STATISTICS_TOTAL + schoolId);
						logger.error("*********************************** error: " + e.getMessage());
						e.printStackTrace();
					}
				}
			});
		}
		return resp;
	}

	@Override
	public String order(StatisticsOrderVo statisticsOrderVo) {
		 String resp =null;
		try {
			PagedResult<StatisticsOrderVo>  orderList = staticsTotalLiliVoManager.queryOrderList(statisticsOrderVo);
			
			StatisticsOrderVo orderSum = staticsTotalLiliVoManager.queryOrderSum(statisticsOrderVo);
			
			resp = new ResponseMessage()
					.addResult("pageData",orderList)
					.addResult("classOrderSum", orderSum.getClassOrderSum())
					.addResult("singupOrderSum", orderSum.getSingupOrderSum())
					.addResult("rClassOrderSum", orderSum.getrClassOrderSum())
					.addResult("rSignupOrderSum", orderSum.getrSignupOrderSum())
					.addResult("classRRSum", orderSum.getClassRRSum())
					.addResult("orderRRSum", orderSum.getOrderRRSum())
					.buildRsp("success","操作成功", 0);
			
		} catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		 return resp;
	}

	@Override
	public String student(StatisticsStudentVo statisticsStudentVo) {
		String resp =null;
		try {
			PagedResult<StatisticsStudentVo>  studentList = staticsTotalLiliVoManager.queryStudentList(statisticsStudentVo);
			
			StatisticsStudentVo studentSum = staticsTotalLiliVoManager.queryStudentSum(statisticsStudentVo);
			
			resp = new ResponseMessage()
					.addResult("code", 0)
					.addResult("pageData", studentList)
					.addResult("hadClassNumSum", studentSum.getHadClassNumSum())
					.addResult("classTimeSum", studentSum.getClassTimeSum())
					.addResult("rClassTimeSum", studentSum.getrClassTimeSum())
					.addResult("registerNumSum", studentSum.getRegisterNumSum())
					.addResult("loginNumSum", studentSum.getLoginNumSum())
					.addResult("signUpNumSum", studentSum.getSignUpNumSum())
					.build();
		} 
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public String coach(StatisticsCoachVo statisticsCoachVo) {
		String resp = null;
		try {
			
			PagedResult<StatisticsCoachVo>  orderList = staticsTotalLiliVoManager.queryCoachList(statisticsCoachVo);
			
			StatisticsCoachVo orderSum = staticsTotalLiliVoManager.queryCoachSum(statisticsCoachVo);
			
			resp = new ResponseMessage()
					.addResult("code", 0)
					.addResult("pageData", orderList)
					.addResult("classNumSum", orderSum.getClassNumSum())
					.addResult("maxStuNumSum",orderSum.getMaxStuNumSum())
					.addResult("hadClassNumSum", orderSum.getHadClassNumSum())
					.addResult("classTimeSum",orderSum.getClassTimeSum())
					.addResult("rClassTImeSum", orderSum.getrClassTImeSum())
					.addResult("loginNumSum", orderSum.getLoginNumSum())
					.addResult("classRRSum", orderSum.getClassRRSum())
					.build();
		} 
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public String listJx(final String schoolId) {
		String resp = redisUtil.get(REDISKEY.CMS_STATISTICS_TOTAL + schoolId);
		if (resp == null){	
			ResponseMessage rm = new ResponseMessage();
			rm.setMsg("正在处理数据");
			resp = rm.build();
			redisUtil.set(REDISKEY.CMS_STATISTICS_TOTAL + schoolId, resp, EXPIRE.HOUR * 4);
			threadPool.execute(new Runnable() {
				public void run() {
					try {
						 Long schoolIdL = Long.valueOf(schoolId);
						 //学员总人数
						StudentBDTO studto = new StudentBDTO();
						studto.setSchoolId(schoolIdL);
						studto.setCmsChannel(2);
						Integer studentSum = studentService.queryTotalStudent(studto);
						
						// 教练总人数	 
						CoachBDTO coadto = new CoachBDTO();
						coadto.setSchoolId(schoolIdL);
						coadto.setCmsChannel(2);
						 Integer coachSum = coachService.queryTotalCoach(coadto);
						 
						// 订单总数
						 OrderBDTO orddto = new OrderBDTO();
						 orddto.setSchoolId(schoolIdL);
						 orddto.setCmsChannel(2);
						 Integer orderSum = orderService.queryTotalOrder(orddto);
						 
						// 教练车总数
						 Car car = new Car();
						 car.setSchoolId(schoolIdL);
						 car.setCmsChannel(2);
						 Integer carSum = carService.queryTotalCoachCar(car);
						 
						// 训练场总数
						 FieldBDTO fielddto = new FieldBDTO();
						 fielddto.setSchoolId(schoolIdL);
						 fielddto.setCmsChannel(2);
						 Integer fieldSum = fieldService.queryTotaField(fielddto);
						 
						String resp = new ResponseMessage()
												.addResult("studentSum", studentSum)
												.addResult("coachSum", coachSum)
												.addResult("orderSum", orderSum)
												.addResult("carSum", carSum)
												.addResult("fieldSum", fieldSum)
												.build();
						redisUtil.set(REDISKEY.CMS_STATISTICS_TOTAL + schoolId, resp, EXPIRE.HOUR * 4);
					} catch (Exception e) {
						redisUtil.delete(REDISKEY.CMS_STATISTICS_TOTAL + schoolId);
						logger.error("*********************************** error: " + e.getMessage());
						e.printStackTrace();
					}
				}
			});
		}
		return resp;
	}
	
	@Override
	public String studentJx(StatisticsStudentSchool statisticsStudentSchool) {
		String resp =null;
		try {
			PagedResult<StatisticsStudentSchool>  studentList = staticsTotalLiliVoManager.queryStudentJxList(statisticsStudentSchool);
			StatisticsStudentSchool studentSum = staticsTotalLiliVoManager.queryStudentJxSum(statisticsStudentSchool);
			
			if (studentSum == null) {
				resp = new ResponseMessage()
						.addResult("code", 0)
						.addResult("pageData", studentList)
						.addResult("hadClassNumSum", 0)
						.addResult("classTimeSum", 0)
						.addResult("rClassTimeSum", 0)
						.addResult("commentNumSum", 0)
						.addResult("loginNumSum", 0)
						.build();
			}
			else {
				resp = new ResponseMessage()
						.addResult("code", 0)
						.addResult("pageData", studentList)
						.addResult("hadClassNumSum", studentSum.getHadClassNumSum())
						.addResult("classTimeSum", studentSum.getClassTimeSum())
						.addResult("rClassTimeSum", studentSum.getrClassTimeSum())
						.addResult("commentNumSum", studentSum.getCommentNumSum())
						.addResult("loginNumSum", studentSum.getLoginNumSum())
						.build();
			}
		} 
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public String coachJx(StatisticsCoachSchool statisticsCoachSchool) {
		String resp = null;
		try {
			PagedResult<StatisticsCoachSchool>  orderList = staticsTotalLiliVoManager.queryCoachJxList(statisticsCoachSchool);
			StatisticsCoachSchool orderSum = staticsTotalLiliVoManager.queryCoachJxSum(statisticsCoachSchool);
			
			if (orderSum == null) {
				resp = new ResponseMessage()
						.addResult("code", 0)
						.addResult("pageData", orderList)
						.addResult("classNumSum", 0)
						.addResult("commentNumSum",0)
						.addResult("classTimeSum",0)
						.addResult("rClassTImeSum", 0)
						.addResult("loginNumSum", 0)
						.build();
			}
			else {
				resp = new ResponseMessage()
						.addResult("code", 0)
						.addResult("pageData", orderList)
						.addResult("classNumSum", orderSum.getClassNumSum())
						.addResult("commentNumSum",orderSum.getCommentNumSum())
						.addResult("classTimeSum",orderSum.getClassTimeSum())
						.addResult("rClassTImeSum", orderSum.getrClassTImeSum())
						.addResult("loginNumSum", orderSum.getLoginNumSum())
						.build();
			}
		} 
		catch (Exception e) {
			logger.error("*********************************** error: " + e.getMessage());
			e.printStackTrace();
		}
		return resp;
	}

	

	
}
