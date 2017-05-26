package com.lili.coach.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lili.coach.dto.BrandCar;
import com.lili.coach.dto.Coach;
import com.lili.coach.dto.CoachAccount;
import com.lili.coach.dto.CoachCar;
import com.lili.coach.dto.CourseS;
import com.lili.coach.dto.School;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.manager.CourseSManager;
import com.lili.coach.manager.SchoolManager;
import com.lili.coach.mapper.dao.BrandCarMapper;
import com.lili.coach.mapper.dao.CoachCarMapper;
import com.lili.coach.mapper.dao.CoachClassTempMapper;
import com.lili.coach.mapper.dao.CoachMapper;
import com.lili.coach.vo.CoachInfoVo;
import com.lili.coach.vo.CoachStatusRecord;
import com.lili.coach.vo.CoachVo;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.order.vo.CoachClassTemp;
import com.lili.order.vo.CoachClassTempNameVo;
import com.lili.order.vo.CoachClassTempQuery;
import com.lili.order.vo.CoachClassTempVo;
import com.lili.pay.manager.MoneyManager;

public class CoachManagerImpl implements CoachManager {

    private static Logger logger = LoggerFactory.getLogger(CoachManagerImpl.class);

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CoachMapper coachMapper;
	@Autowired
	private CoachCarMapper coachcarMapper;
	@Autowired
	private CourseSManager courseSManager;
	@Autowired
	private BrandCarMapper brandCarMapper;

    @Autowired
    private MoneyManager moneyManager;
    @Autowired
    private SchoolManager schoolManager;

    @Autowired
    private CoachClassTempMapper coachClassTempMapper;
    
    ExecutorService threadPool = Executors.newCachedThreadPool();

	@Override
	public List<Coach> getCoach() {
		return coachMapper.getAll(null);
	}
	
	@Override
	public List<Coach> getCoach(Coach coach) {
		return coachMapper.getAll(coach);
	}

	@Override
	public Coach getCoachInfo(long id) {
	    Coach coach = redisUtil.get(REDISKEY.COACH + id);
	    if (coach != null)
        {
            return coach;
        }
	    else
	    {
	        coach = coachMapper.selectByPrimaryKey(id);
	        if (coach != null)
            {
                //redisUtil.set(REDISKEY.COACH + id, coach,3600*24);
	        	redisUtil.set(REDISKEY.COACH + id, coach,1800);
            }
	        return coach;
        }
	}
	
	
	@Override
	public CoachInfoVo getCoachInfoVo(long id) {
	    Coach coach = getCoachInfo(id) ;
	    try
        {
	        if (coach != null)
            {
	            CoachInfoVo coachInfoVo = BeanCopy.copyNotNull(coach, CoachInfoVo.class);
	            return coachInfoVo;
            }
        }
        catch (Exception e)
        {
            logger.error("coachId: " + id, e);
        }
		return coachMapper.selectCoachInfoVo(id);
	}

	@Override
	public Coach getCoachByPhoneNum(String mobile) {
		return coachMapper.selectByPhoneNum(mobile);
	}

	@Override
	public long getCount(Coach coach) {
		return coachMapper.countAll(coach);
	}

	@Override
	public long addCoach(Coach coach) {
		//return coachMapper.insertAndGetCoachId(coach);
		return coachMapper.insertSelective(coach);
	}

	@Override
	public long updateCoach(Coach coach) {
		long result=-1;
	    if (coach != null)
        {
            long id = coach.getCoachId();
/*            if (id != 0)
            {
                Coach coachTemp = redisUtil.get(REDISKEY.COACH + id);
                if (coachTemp != null)
                {
                    try
                    {
                        coachTemp = BeanCopy.copyNotNull(coach, coachTemp);
                        redisUtil.set(REDISKEY.COACH + id, coachTemp,3600*24);
                    }
                    catch (Exception e)
                    {
                        logger.error("coachId: " + id, e);
                    }
                }
            }*/
            //先更新后删除，防止更新前删除后获取异常
            result=coachMapper.updateByPrimaryKeySelective(coach);
            
            //更新教练时删除缓存中的dto,vo
            redisUtil.delete(REDISKEY.COACH + id);
            redisUtil.delete(REDISKEY.COACH_VO + id);
        }
		return result;
	}

	@Override
	public long deleteCoach(long id) {
	    redisUtil.delete(REDISKEY.COACH + id);
		
		//删除教练
		coachMapper.deleteByPrimaryKey(id);
		
		//根据教练id获取该教练的车辆信息
		CoachCar coachcar1 = new CoachCar();
		coachcar1.setCoachId(id);
/*		List<CoachCar> coachCar = coachcarMapper.getAll(coachcar1);
		
        
		//批量删除车辆信息
        if(coachCar.size() > 0){
        	
        	//车辆id集合
        	List<Integer> carIdList = new ArrayList<Integer>();
        	for (int i = 0; i < coachCar.size(); i++) {
        		carIdList.add(coachCar.get(i).getCarId());
			} 
        	
        	//删除教练下所属的车辆信息
        	carMapper.delCarByCarId(carIdList);
        }*/
		
		//return coachcarMapper.deleteByCoachId(id);
        //逻辑删除教练、车辆关系
		coachcar1.setIsExist(0);
		return coachcarMapper.updateByPrimaryKeySelective(coachcar1);
	}

	@Override
	public boolean isExist(String phoneNum) {
		
		Coach coach = new Coach();
		coach.setPhoneNum(phoneNum);
		
		long count = coachMapper.countAll(coach);
		
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Coach> getCoachesByStudentId(long sid,byte type) {
		return coachMapper.getCoachesByStudentId(sid,type);
	}

	@Override
	public CoachAccount getCoachMoney(Long coachId) {
		return coachMapper.getCoachMoney(coachId);
	}

    /* (non-Javadoc)
     * @see com.lili.coach.manager.CoachManager#updateCoachAccount(com.lili.coach.dto.CoachAccount)
     */
    @Override
    public void updateCoachAccount(CoachAccount coachAccount)
    {
        coachMapper.updateCoachAccount(coachAccount);
    }

	@Override
	public List<CourseS> getCourseSById(long coachId) {
		
		//根据教练id获取教练信息
		Coach coach = getCoachInfo(coachId);
		
		//课程集合
		List<CourseS> list = new ArrayList<CourseS>();
		
		if(null != coach){
			if(StringUtils.isNotEmpty(coach.getCourses())){
				
				//获取教练表里面存储的课程id集合
				String[] courseIds = coach.getCourses().split(",");
				
				for (int i = 0; i < courseIds.length; i++) {
					
					//根据课程id获取课程信息
					CourseS courseS = courseSManager.getCourseSById(Integer.parseInt(courseIds[i]));
					
					//将课程信息添加到课程集合
					list.add(courseS);
				}
			}
		}
		
		return list;
	}

    /* (non-Javadoc)
     * @see com.lili.coach.manager.CoachManager#addCoachAccount(com.lili.coach.dto.CoachAccount)
     */
    @Override
    public void addCoachAccount(CoachAccount c)
    {
        coachMapper.insertCoachAccount(c);
    }

	@Override
	public List<CoachVo> getCoachesByIds(List<Long> coachIds) {
		if(null == coachIds || coachIds.size()==0){
			return null;
		}
		//从缓存中批量获取
		Map<String, Object> aa =redisUtil.mmget(RedisKeys.REDISKEY.COACH_VO, coachIds,"coachId");
		@SuppressWarnings("unchecked")
		List<CoachVo> bb = (List<CoachVo>) aa.get("success");
		@SuppressWarnings("unchecked")
		List<Long> cc = (List<Long>) aa.get("fail");
		if(null != cc && cc.size()>0){
			//未从缓存中获取到的从数据库中获取
			List<CoachVo> dd = coachMapper.getCoachesByIds(cc);
			bb.addAll(dd);
			//mset批量添加到缓存中			
			Map<String,CoachVo> ee = new HashMap<String, CoachVo>();
			for(int i=0;i<dd.size();i++){
				ee.put(RedisKeys.REDISKEY.COACH_VO+dd.get(i).getCoachId(), dd.get(i));
			}
			//redisUtil.mset(ee,0);
			redisUtil.mset(ee,3600);
		}
		return bb;
	}

	@Override
	public long checkCoachCorrectPw(String pw, Long coachId) {
		return coachMapper.checkCoachCorrectPw(pw, coachId);
	}

	@Override
	public void updateCoachPassword(String passwd, Long coachId) {
			coachMapper.updateCoachPassword(passwd, coachId);
	}

	@Override
	public List<BrandCar> getBrandCar(String isCommon, String seqNum) {
		BrandCar brandCar = new BrandCar();
		brandCar.setIscommon(new Byte(isCommon));
		brandCar.setSeqnum(Integer.parseInt(seqNum));
		return brandCarMapper.getAll(brandCar);
	}

	@Override
	public long addCoachList(List<Coach> coaches) {
		int rs = 0;
		for (int i = 0; i < coaches.size(); i++){
			rs += coachMapper.insertSelective(coaches.get(i));
		}
		return rs;
	}

	@Override
	public boolean payCoachesBonuses(CoachAccount coachAccount) {
        try {
        	//20160712给教练发奖金的同时，车厘子余额要减去；并增加流水记录。
            moneyManager.handlePayCoachBonus(coachAccount.getCoachId(), 0, coachAccount.getMoney());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

	@Override
	public boolean payCoachesBonuses(final List<CoachAccount> coachAccounts) {
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
		        try {
		            for (CoachAccount c : coachAccounts) {
		            	//20160712给教练发奖金的同时，车厘子余额要减去；并增加流水记录。
		                moneyManager.handlePayCoachBonus(c.getCoachId(), 0, c.getMoney());
		            }
		        } catch (Exception e) {
		        	e.printStackTrace();
		        }
				
			}
		});
        return true;
    }

	@Override
	public List<CoachClassTempQuery> queryTemplate(CoachClassTemp coachClassTemp) {
		List<CoachClassTempQuery> classTempList = new ArrayList<CoachClassTempQuery>();
		try {
			classTempList = coachClassTempMapper.queryTemplate(coachClassTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classTempList;
	}

	@Override
	public boolean saveClassTemp(List<CoachClassTempVo> classTempList) {
		try {
			coachClassTempMapper.saveClassTemp(classTempList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateClassTemp(List<CoachClassTempVo> classTempList) {
		try {
			for (CoachClassTempVo coachClassTempVo :classTempList) {
				coachClassTempMapper.updateByPrimaryKeySelective(coachClassTempVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean closeClassTemp(Integer tempId) {
		try {
			coachClassTempMapper.deleteByTempId(tempId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<CoachClassTempNameVo> queryTemplateName(CoachClassTemp coachClassTempVo) {
		List<CoachClassTempNameVo> classTempList = new ArrayList<CoachClassTempNameVo>();
		try {
			classTempList = coachClassTempMapper.queryTemplateName(coachClassTempVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classTempList;
	}

	@Override
	public School getSchool(int schoolId){
		return schoolManager.getSchoolInfo(schoolId);
	}

	@Override
	public Integer isExitClassTempName(CoachClassTempVo coachClassTempVo) {
		try {
			return coachClassTempMapper.queryIsExitClassTempName(coachClassTempVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<CoachVo> getPushCoachList(Integer pushType) {
		List<CoachVo> coachVoList = new ArrayList<CoachVo>();
		try {
			if( pushType == 2) {
				coachVoList = coachMapper.queryByCoachIsNotOff();
			}
			else if (pushType ==1) {
				coachVoList = coachMapper.queryByCoachIsNotOn();
			}
			else {
				logger.error("******************************** CoachManagerImpl getPushCoachList pushType is Error : " + pushType);
			}
		} catch (Exception e) {
			logger.error("******************************** CoachManagerImpl getPushCoachList  Error : " + e.getMessage());
			e.printStackTrace();
		}
		return coachVoList;
	}

	@Override
	public List<Coach> getTYCoaches() {
		List<Coach> dd = null;
		try {
			String key = RedisKeys.REDISKEY.COACH +"TEYUE_COACH_ALL"; //从缓存中获取特约教练，特约教练一般固定，目前有20个
			dd=redisUtil.get(key);//从缓存里取
			if (dd == null) {
				dd = coachMapper.queryTYCoaches();
				redisUtil.set(key, dd);
			}
			return dd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dd;
	}
	
	@Override
	public void addCoachStatusRecord(CoachStatusRecord record) {
		coachMapper.insertStatusRecord(record);
	}

	@Override
	public void addLoginCount(Coach coach) {
		//add by devil 添加登录次数
        coachMapper.addLoginCount(coach);
	}

	@Override
	public void addCoachInCount(CoachStatusRecord record) {
		coachMapper.addCoachInCount(record);
	}

	@Override
	public void addCoachOutCount(CoachStatusRecord record) {
		coachMapper.addCoachOutCount(record);
	}

	@Override
	public CoachStatusRecord getLatestStatusRecord(CoachStatusRecord record) {
		return coachMapper.getLatestStatusRecord(record);
	}
	
	
}
