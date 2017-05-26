package com.lili.school.quartz;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.lili.common.util.DateUtil;

public class DynamicQuartz {
	private Logger log = Logger.getLogger(DynamicQuartz.class);
	private Scheduler scheduler;

	public void addJob(DynamicJob dynamicJob){
		try {
			int afterSecond=dynamicJob.getAfterSecond();
			JobDetail jobDetail = JobBuilder.newJob(dynamicJob.getClass()).withIdentity(dynamicJob.getJobName(), dynamicJob.getJobGroup()).usingJobData(dynamicJob.getJobDataMap()).build();
//			SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().(afterSecond).withRepeatCount(1);  
			Trigger simpleTrigger=TriggerBuilder.newTrigger().withIdentity(dynamicJob.getJobName(), dynamicJob.getJobGroup()).startAt(DateUtil.dateAfterMilliSecond(new Date(), afterSecond*1000)).build();  
			scheduler.scheduleJob(jobDetail, simpleTrigger);
		}catch(Exception e){
			log.error(dynamicJob.getJobDataMap()+" add job Exception:"+e.getMessage(),e);
		}
	}
	
	public void pauseJob(DynamicJob dynamicJob){
		try {
			JobKey jobKey = JobKey.jobKey(dynamicJob.getJobName(), dynamicJob.getJobGroup());  
			scheduler.pauseJob(jobKey);
		} catch(Exception e) {
			log.error(dynamicJob.getJobDataMap()+" pause job Exception:"+e.getMessage(),e);
		}
	}
	
	public void removeJob(DynamicJob dynamicJob){
		try {
			JobKey jobKey = JobKey.jobKey(dynamicJob.getJobName(), dynamicJob.getJobGroup());  
			scheduler.deleteJob(jobKey);
		} catch(Exception e) {
			log.error(dynamicJob.getJobDataMap()+" delete job Exception:"+e.getMessage(),e);
		}
	}
	public void resumeJob(DynamicJob dynamicJob){
		try {
			JobKey jobKey = JobKey.jobKey(dynamicJob.getJobName(), dynamicJob.getJobGroup());  
			scheduler.resumeJob(jobKey);
		} catch(Exception e) {
			log.error(dynamicJob.getJobDataMap()+" delete job Exception:"+e.getMessage(),e);
		}
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
}
