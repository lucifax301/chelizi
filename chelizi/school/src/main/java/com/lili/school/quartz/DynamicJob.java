package com.lili.school.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;

public abstract class DynamicJob implements Job {
	public static final int ACCEPTTIMESECOND=120;
	
	
	private String jobGroup;
	private String jobName;
	private int afterSecond;
	private JobDataMap jobDataMap=new JobDataMap();
	public DynamicJob(String jobName,int afterSecond) {
		jobGroup=this.getClass().getSimpleName();
		this.jobName=jobName;
		this.afterSecond=afterSecond;
	}
	public DynamicJob(){
	}
	
	public String getJobGroup() {
		if(jobGroup==null){
			return this.getClass().getSimpleName();
		} else {
			return jobGroup;
		}
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getAfterSecond() {
		return afterSecond;
	}

	public void setAfterSecond(int afterSecond) {
		this.afterSecond = afterSecond;
	}
	public void setData(String key,Object value){
		jobDataMap.put(key, value);
	}
	public Object getData(String key){
		return jobDataMap.get(key);
	}
	public JobDataMap getJobDataMap(){
		return jobDataMap;
	}
	public void setJobDataMap(JobDataMap jobDataMap){
		this.jobDataMap=jobDataMap;
	}
}
