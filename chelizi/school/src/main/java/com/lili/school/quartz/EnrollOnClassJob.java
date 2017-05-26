package com.lili.school.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.school.dto.EnrollLongtrain;
import com.lili.school.dto.EnrollTheory;
import com.lili.school.mapper.EnrollLongtrainMapper;
import com.lili.school.mapper.EnrollTheoryMapper;


public class EnrollOnClassJob extends DynamicJob {
	private static Logger log=Logger.getLogger(EnrollOnClassJob.class);
	
	private int type; //1-理论课上课时间到；2-长考课上课时间到
	
	public EnrollOnClassJob(){
		
	}
	public EnrollOnClassJob(String orderId,int afterSecond){
		super(orderId, afterSecond);
		this.setOrderId(orderId);
	}
	
	public EnrollOnClassJob(String orderId,int afterSecond,int type){
		super(orderId, afterSecond);
		this.setOrderId(orderId);
		this.setType(type);
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String orderId=null;
		int type = 1;
		try {
			orderId=getOrderId(context);
			type = getType(context);
			SchedulerContext schCtx = context.getScheduler().getContext();  
			ApplicationContext appCtx = (ApplicationContext)schCtx.get("applicationContext");  
			RedisUtil redisUtil = appCtx.getBean(RedisUtil.class);  
			if(type == 1){
				EnrollTheoryMapper enrollTheoryMapper = appCtx.getBean(EnrollTheoryMapper.class);
				EnrollTheory theory = enrollTheoryMapper.selectByPrimaryKey(Integer.parseInt(orderId));
				if(null != theory && theory.getState()== ReqConstants.THEORY_CLASS_OPEN){
					theory.setState(ReqConstants.THEORY_CLASS_ON);
					enrollTheoryMapper.updateByPrimaryKeySelective(theory);
					redisUtil.delete(RedisKeys.REDISKEY.ENROLL_THEORY+ orderId);
					log.info("EnrollOnClassJob do update:"+ orderId +" !!!");
				}else {
					log.error("EnrollTheory class has been closed or dose not contain id:"+ orderId +" !!!");
				}
			}else if(type == 2){
				EnrollLongtrainMapper enrollLongtrainMapper = appCtx.getBean(EnrollLongtrainMapper.class);
				EnrollLongtrain longtrain = enrollLongtrainMapper.selectByPrimaryKey(Integer.parseInt(orderId));
				if(null != longtrain && longtrain.getState()== ReqConstants.THEORY_CLASS_OPEN ){
					longtrain.setState(ReqConstants.THEORY_CLASS_ON);
					enrollLongtrainMapper.updateByPrimaryKeySelective(longtrain);
					redisUtil.delete(RedisKeys.REDISKEY.ENROLL_LONGTRAIN+ orderId);
					log.info("EnrollOnClassJob do update:"+ orderId +" !!!");
				}else {
					log.error("EnrollLongtrain class has been closed or dose not contain id:"+ orderId +" !!!");
				}
			}else {
				log.error("EnrollOnClassJob type error:"+ type +" !!!");
			}

			
		}catch(Exception e){
			log.error(orderId+" Exception:"+e.getMessage(),e);
		} finally {
			try {
				Scheduler scheduler=context.getScheduler();
				JobKey jobKey = JobKey.jobKey(getJobName(), getJobGroup());  
				scheduler.deleteJob(jobKey);
			} catch (SchedulerException e) {}
		}
		
	}
	public int getType(JobExecutionContext context) {
		int result=1;
		if(context==null){
			result=(int) getData("type");
		} else {
			result=context.getJobDetail().getJobDataMap().getInt("type");
		}
		return result;
	}
	public void setType(int type) {
		this.type = type;
		setData("type",type);
	}
	
	public String getOrderId(JobExecutionContext context){
		String result=null;
		if(context==null){
			result=String.valueOf(getData("orderId"));
		} else {
			result=context.getJobDetail().getJobDataMap().getString("orderId");
		}
		return result;
	}
	public void setOrderId(String orderId) {
		this.setJobName(orderId);
		setData("orderId",orderId);
	}

}
