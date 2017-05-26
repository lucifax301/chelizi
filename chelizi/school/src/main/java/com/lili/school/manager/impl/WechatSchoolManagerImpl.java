package com.lili.school.manager.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.school.dto.EnrollPurpose;
import com.lili.school.dto.WechatCoachClassDto;
import com.lili.school.dto.WechatEnrollStudentDto;
import com.lili.school.dto.WechatMycoachesDto;
import com.lili.school.dto.WechatPlantClassDto;
import com.lili.school.manager.WechatSchoolManager;
import com.lili.school.mapper.EnrollPurposeMapper;
import com.lili.school.mapper.WechatCoachClassMapper;
import com.lili.school.mapper.WechatEnrollClassMapper;
import com.lili.school.mapper.WechatEnrollStudentMapper;
import com.lili.school.mapper.WechatMycoachesMapper;
import com.lili.school.mapper.WechatOrderMapper;
import com.lili.school.mapper.WechatPlantClassMapper;
import com.lili.school.mapper.WechatStudentMapper;
import com.lili.school.mapper.WechatTemplateLogMapper;
import com.lili.school.mapper.WechatTemplateMapper;
import com.lili.school.vo.WechatCoachClass;
import com.lili.school.vo.WechatEnrollClass;
import com.lili.school.vo.WechatEnrollClassVo;
import com.lili.school.vo.WechatEnrollStudent;
import com.lili.school.vo.WechatMycoaches;
import com.lili.school.vo.WechatOrder;
import com.lili.school.vo.WechatPlantClass;
import com.lili.school.vo.WechatStudent;
import com.lili.school.vo.WechatTemplate;
import com.lili.school.vo.WechatTemplateLog;

public class WechatSchoolManagerImpl implements WechatSchoolManager {
	private static Logger logger = LoggerFactory.getLogger(WechatSchoolManagerImpl.class);
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	WechatCoachClassMapper wechatCoachClassMapper;
	
	@Autowired
	WechatEnrollClassMapper wechatEnrollClassMapper;

	@Autowired
	WechatEnrollStudentMapper wechatEnrollStudentMapper;
	
	@Autowired
	WechatPlantClassMapper wechatPlantClassMapper;
	
	@Autowired
	WechatTemplateLogMapper wechatTemplateLogMapper;
	
	@Autowired
	WechatTemplateMapper wechatTemplateMapper;
	
	@Autowired
	WechatMycoachesMapper wechatMycoachesMapper;
	
	@Autowired
	WechatStudentMapper wechatStudentMapper;
	
	@Autowired
	WechatOrderMapper wechatOrderMapper;
	
	@Autowired
	EnrollPurposeMapper enrollPurposeMapper;
	
	@Override
	public ReqResult addEnrollVisCard(List<WechatEnrollClass> enrollJsonList) {
		ReqResult r = ReqResult.getSuccess();
			try {
				if (enrollJsonList != null && enrollJsonList.size() > 0) { 
					for (WechatEnrollClass wechatEnrollClass : enrollJsonList) {
						if (wechatEnrollClass.getType() != null) {
							if (wechatEnrollClass.getType() ==1) { //添加
								wechatEnrollClassMapper.insertSelective(wechatEnrollClass);
							}
							else if (wechatEnrollClass.getType() ==2) {//修改
								if (wechatEnrollClass.getClassId() != null && !"".equals(wechatEnrollClass.getClassId())) {
									wechatEnrollClassMapper.updateByPrimaryKeySelective(wechatEnrollClass);
								}
							}
							else if (wechatEnrollClass.getType() ==3) {//删除
								if (wechatEnrollClass.getClassId() != null && !"".equals(wechatEnrollClass.getClassId())) {
									WechatEnrollClass record = new WechatEnrollClass();
									record.setIsDel(1);
									record.setClassId(wechatEnrollClass.getClassId());
									wechatEnrollClassMapper.updateByPrimaryKeySelective(record);
								}
							}
						}
					}
				}
				//wechatEnrollClassMapper.insertEnrollClassList(enrollJsonList);
			} catch (Exception e) {
				logger.error("**************************************Error addEnrollVisCard Exception: " + e.getMessage());
				r.setCode(ResultCode.ERRORCODE.EXCEPTION);
				r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
				e.printStackTrace();
			}
		
		return r;
	}

	@Override
	public List<WechatEnrollClass> queryWechatEnrollClassList(WechatEnrollClass wechatEnrollClass) {
		List<WechatEnrollClass> wechatEnrollClassList = null;
		try {
			wechatEnrollClassList = wechatEnrollClassMapper.queryWechatEnrollClassList(wechatEnrollClass);
		} catch (Exception e) {
			logger.error("**************************************Error getEnrollVisCard Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return wechatEnrollClassList;
	}

	@Override
	public ReqResult inportStudent(Long coachId, List<WechatMycoaches> weStuJsonList) {
		ReqResult r = ReqResult.getSuccess();
		try {
			Long studentId = null;
			for (WechatMycoaches myCoaches : weStuJsonList) {
				//首先判断学员是否存在
				WechatStudent wechatStudent = new WechatStudent();
				wechatStudent.setPhone(myCoaches.getPhone());
				WechatStudent isExitStudent = wechatStudentMapper.queryWechatStudent(wechatStudent);
				if (isExitStudent == null && myCoaches.getPhone() != null && !"".equals(myCoaches.getPhone())) { //不存在才插入
					wechatStudentMapper.insertSelective(wechatStudent);
					WechatStudent weStudentInfo = wechatStudentMapper.queryWechatStudent(wechatStudent);
					studentId = weStudentInfo.getStudentId();
				}
				else {
					studentId = isExitStudent.getStudentId();
				}
				
				WechatMycoaches myCoachRecord = new WechatMycoaches();
				myCoachRecord.setCoachId(coachId);
				myCoachRecord.setStudentId(studentId);
				//判断是否已存在绑定关系
				WechatMycoaches isExitMyCoach = wechatMycoachesMapper.queryByMycoach(myCoachRecord);
				if (isExitMyCoach == null) { //插入绑定关系
					myCoaches.setStudentId(studentId);
					myCoaches.setCoachId(coachId);
					myCoaches.setChannel(1);
					wechatMycoachesMapper.insertSelective(myCoaches);
				}
				else if (isExitMyCoach != null && isExitMyCoach.getIsdel() == 1) { //更新绑定关系
					myCoaches.setIsdel(0);
					myCoaches.setChannel(1);
					myCoaches.setStudentId(studentId);
					myCoaches.setCoachId(coachId);
					wechatMycoachesMapper.updateWechatMycoaches(myCoaches);
				}
			}
			
		} catch (Exception e) {
			logger.error("**************************************Error inportStudent Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		
		return r;
	}

	@Override
	public ReqResult inportStudentOne(WechatMycoaches mycoaches) {
		ReqResult r = ReqResult.getSuccess();
		try {
			WechatMycoaches record = new WechatMycoaches();
			record.setCoachId(mycoaches.getCoachId());
			record.setStudentId(mycoaches.getStudentId());
			//判断是否已存在绑定关系，如存在，则跳过
			WechatMycoaches isExitMyCoach = wechatMycoachesMapper.queryByMycoach(record);
			if (isExitMyCoach == null) {
				//插入绑定关系
				wechatMycoachesMapper.insertSelective(mycoaches);
			}
			else if (isExitMyCoach != null && isExitMyCoach.getIsdel() == 1) { //更新绑定关系
				mycoaches.setIsdel(0);
				mycoaches.setState(0);
				wechatMycoachesMapper.updateWechatMycoaches(mycoaches);
			}
			else if (isExitMyCoach != null && isExitMyCoach.getIsdel() == 0) { //已存在绑定关系
				r.setCode(ResultCode.ERRORCODE.IS_EXIT_BOUND);
				r.setMsgInfo(ResultCode.ERRORINFO.IS_EXIT_BOUND);
				return r;
			}
		}
		catch (Exception e) {
			logger.error("**************************************Error inportStudentOne Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public List<WechatCoachClass> queryIsExitClass(WechatCoachClass wechatCoachClass) {
		List<WechatCoachClass>  wechatCoachClassList = null;
		try {
			wechatCoachClassList = wechatCoachClassMapper.queryWechatCoachClassList2(wechatCoachClass);
		} catch (Exception e) {
			logger.error("**************************************Error queryIsExitClass Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return wechatCoachClassList;
	}

	@Override
	public ReqResult addWechatClass(Long coachId, WechatCoachClass wechatCoachClass) {
		ReqResult r = ReqResult.getSuccess();
		try {
			wechatCoachClassMapper.insertSelective(wechatCoachClass);
		} catch (Exception e) {
			logger.error("**************************************Error addWechatClass Exception: " + e.getMessage());
			r.setCode(ResultCode.ERRORCODE.EXCEPTION);
			r.setMsgInfo(ResultCode.ERRORINFO.EXCEPTION);
			e.printStackTrace();
		}
		return r;
	}


	@Override
	public List<WechatEnrollStudent> queryWechatEnroll(WechatEnrollStudentDto wechatEnrollStudentDto) {
		List<WechatEnrollStudent> wechatEnrollStudentList = null;
		try {
			wechatEnrollStudentList = wechatEnrollStudentMapper.queryEnrollStudentList(wechatEnrollStudentDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wechatEnrollStudentList;
	}

	@Override
	public WechatStudent queryWechatStudent(WechatStudent wechatStudent) {
		
		WechatStudent wechatStudentInfo = null;
		try {
			if (wechatStudent.getStudentId() != null && !"".equals(wechatStudent.getStudentId())) {
				wechatStudentInfo = redisUtil.get(REDISKEY.LILI_COACH_STUDENT + wechatStudent.getStudentId());
			}
			if (wechatStudentInfo == null) {
				wechatStudentInfo = wechatStudentMapper.queryWechatStudent(wechatStudent);
				if (wechatStudentInfo != null) {
					redisUtil.set(REDISKEY.LILI_COACH_STUDENT + wechatStudentInfo.getStudentId(), wechatStudentInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wechatStudentInfo;
	}

	@Override
	public Integer queryOnClassNum(WechatPlantClass wechatPlantClass) {
		return wechatPlantClassMapper.countOnClassNum(wechatPlantClass);
	}

	@Override
	public List<WechatCoachClass> queryWechatClass(WechatCoachClass wechatCoachClass) {
		List<WechatCoachClass>  wechatCoachClassList = null;
		try {
			wechatCoachClassList = wechatCoachClassMapper.queryWechatCoachClassList(wechatCoachClass);
		} catch (Exception e) {
			logger.error("**************************************Error queryIsExitClass Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return wechatCoachClassList;
	}

	@Override
	public WechatMycoaches queryMyWechatCoach(WechatMycoaches wechatMycoaches) {
		return wechatMycoachesMapper.queryByMycoach(wechatMycoaches);
	}

	@Override
	public WechatEnrollStudent queryEnrollStudentInfo(WechatEnrollStudent record) {
		return wechatEnrollStudentMapper.queryEnrollStudentInfo(record);
	}

	@Override
	public WechatEnrollClass queryWechatEnrollClass(WechatEnrollClass recordClass) {
		return wechatEnrollClassMapper.queryWechatEnrollClass(recordClass);
	}

	@Override
	public WechatEnrollStudent queryWechatEnrollStudent(WechatEnrollStudent recordStudent) {
		return wechatEnrollStudentMapper.queryNewEnrollStudent(recordStudent);
	}

	@Override
	public WechatCoachClass queryNearWechatCoachClass(WechatCoachClass recordClass) {
		return wechatCoachClassMapper.queryNearWechatCoachClass(recordClass);
	}

	@Override
	public Integer subEnrollPurpose(WechatEnrollStudent wechatEnrollStudent) {
		return wechatEnrollStudentMapper.insertSelective(wechatEnrollStudent);
	}

	@Override
	public int handClass(WechatPlantClass wechatPlantClass) {
		return wechatPlantClassMapper.updateByPrimaryKeySelective(wechatPlantClass);
	}

	@Override
	public int updateWechatMyCoach(WechatMycoaches wechatMycoaches) {
		return wechatMycoachesMapper.updateWechatMycoaches(wechatMycoaches);
	}

	@Override
	public void insertWxTemplateLog(WechatTemplateLog wechatTemplateLog) {
		try {
			wechatTemplateLogMapper.insertSelective(wechatTemplateLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int insertWechatStudent(WechatStudent wechatStudentInfo) {
		return wechatStudentMapper.insertSelective(wechatStudentInfo);
	}

	@Override
	public WechatTemplate queryWechatTemplate(WechatTemplate record) {
		WechatTemplate wechatTemplate = redisUtil.get(REDISKEY.LILI_COACH_TEMPLATE + record.getTemplateName());
		    if (wechatTemplate == null) {
		    	wechatTemplate = wechatTemplateMapper.queryWechatTemplate(record);
		        if (wechatTemplate != null) {
		            redisUtil.set(REDISKEY.LILI_COACH_TEMPLATE + wechatTemplate.getTemplateName(), wechatTemplate,3600*24);
	            }
	        }
			return wechatTemplate;
	}

	@Override
	public WechatCoachClass queryWechatCoachClass(WechatCoachClass recordClass) {
		return wechatCoachClassMapper.queryWechatCoachClass(recordClass);
	}

	@Override
	public int insertWechatPlantClass(WechatPlantClass plantClass) {
		return wechatPlantClassMapper.insertSelective(plantClass);
	}

	@Override
	public int inserWechatOrder(WechatOrder wechatOrder) {
		return wechatOrderMapper.insertSelective(wechatOrder);
	}

	@Override
	public List<WechatPlantClass> queryWechatPlantClassDtoList(WechatPlantClassDto record) {
		return wechatPlantClassMapper.queryWechatPlantClassDtoList(record);
	}

	@Override
	public WechatPlantClass queryWechatPlantClass(WechatPlantClass record) {
		return wechatPlantClassMapper.queryWechatPlantClass(record);
	}

	@Override
	public ReqResult updateWechatClass(WechatOrder wechatOrder, WechatCoachClass wechatCoachClass) {
		wechatCoachClassMapper.updateByPrimaryKeySelective(wechatCoachClass);
		wechatOrderMapper.updateByWechatOrder(wechatOrder);
		return null;
	}

	@Override
	public List<WechatPlantClass> queryWechatPlantClassList(WechatPlantClass record) {
		return wechatPlantClassMapper.queryWechatPlantClassList(record);
	}

	@Override
	public int updateWechatCoachClass(WechatCoachClass recordClass) {
		return wechatCoachClassMapper.updateByPrimaryKeySelective(recordClass);
	}

	@Override
	public int updateWechatOrder(WechatOrder order) {
		return wechatOrderMapper.updateByWechatOrder(order);
	}

	@Override
	public int updateWechatPlantClass(WechatPlantClass plantClass) {
		return wechatPlantClassMapper.updateWechatPlantClass(plantClass);
	}

	@Override
	public WechatOrder queryWechatOrder(WechatOrder orderInfo) {
		return wechatOrderMapper.queryWechatOrder(orderInfo);
	}


	@Override
	public int insertWechatMycoaches(WechatMycoaches myCoach) {
		return wechatMycoachesMapper.insertSelective(myCoach);
	}

	@Override
	public Integer countMyStudentNum(WechatMycoaches myCoachDto) {
		return wechatMycoachesMapper.countMyStudentNum(myCoachDto);
	}

	@Override
	public int updateWechatStudent(WechatStudent wechatStudent) {
		int result = 0;
		if (wechatStudent.getStudentId() !=null && !"".equals(wechatStudent.getStudentId())) {
			redisUtil.delete(REDISKEY.LILI_COACH_STUDENT + wechatStudent.getStudentId());
			result = wechatStudentMapper.updateByPrimaryKeySelective(wechatStudent);
			
			WechatStudent wechatStudentInfo = wechatStudentMapper.queryWechatStudent(wechatStudent);
			redisUtil.set(REDISKEY.LILI_COACH_STUDENT + wechatStudent.getStudentId(), wechatStudentInfo);
		}
		else {
			result = wechatStudentMapper.updateByPrimaryKeySelective(wechatStudent);
			
			WechatStudent studentInfo = wechatStudentMapper.queryWechatStudent(wechatStudent);
			redisUtil.delete(REDISKEY.LILI_COACH_STUDENT + studentInfo.getStudentId());
			redisUtil.set(REDISKEY.LILI_COACH_STUDENT + wechatStudent.getStudentId(), studentInfo);
		}
		
		return result;
	}

	@Override
	public List<WechatCoachClass> queryCoachClassByCcidIn(WechatCoachClassDto coachClassDto) {
		return wechatCoachClassMapper.queryCoachClassByCcidIn(coachClassDto);
	}

	@Override
	public Integer countNewStudent(WechatMycoachesDto mycoach) {
		return wechatMycoachesMapper.queryNewStudentSum(mycoach);
	}

	@Override
	public int updateWechatEnrollStudent(WechatEnrollStudent updateEnrollStudent) {
		return wechatEnrollStudentMapper.updateWechatEnrollStudent(updateEnrollStudent);
		
	}

	@Override
	public List<WechatMycoaches> queryWechatMycoachesList(WechatMycoachesDto myCoachDto) {
		return wechatMycoachesMapper.queryWechatMycoachesList(myCoachDto);
	}

	@Override
	public int updateWechatMycoaches(WechatMycoaches mycoach) {
		return wechatMycoachesMapper.updateWechatMycoaches(mycoach);
	}

	@Override
	public List<WechatOrder> queryWechatOrderList(WechatOrder order) {
		return wechatOrderMapper.queryWechatOrderList(order);
	}

	@Override
	public int deleteWechatStudent(WechatStudent exitOpenIdStu) {
		redisUtil.delete(REDISKEY.LILI_COACH_STUDENT + exitOpenIdStu.getStudentId());
		return wechatStudentMapper.deleteByPrimaryKey(exitOpenIdStu.getStudentId());
	}

	@Override
	public WechatMycoaches queryMyWechatBoundCoach(WechatMycoachesDto wechatMycoachesDto) {
		return wechatMycoachesMapper.queryMyWechatBoundCoach(wechatMycoachesDto);
	}
	
	@Override
	public List<WechatEnrollClass> queryClassGroupByCoachList(WechatEnrollClass wechatEnrollClass) {
		List<WechatEnrollClass> wechatEnrollClassList = null;
		try {
			wechatEnrollClassList = wechatEnrollClassMapper.queryClassGroupByCoachList(wechatEnrollClass);
		} catch (Exception e) {
			logger.error("**************************************Error queryClassGroupByCoachList Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return wechatEnrollClassList;
	}

	@Override
	public ReqResult subEnrollPurposeNew(EnrollPurpose enrollPurpose) {
		ReqResult r = ReqResult.getSuccess();
			enrollPurpose.setCtime(new Date());
			enrollPurposeMapper.insertSelective(enrollPurpose);
		return r;
	}

	@Override
	public EnrollPurpose getEnrollPurpose(EnrollPurpose enrollPurpose) {
		return enrollPurposeMapper.getEnrollPurpose(enrollPurpose);
	}

	@Override
	public WechatEnrollClass getMinPrice(WechatEnrollClassVo enrollClass) {
		return wechatEnrollClassMapper.getMinPrice(enrollClass);
	}

}
