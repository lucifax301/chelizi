package com.lili.school.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.lili.authcode.service.AuthcodeService;
import com.lili.common.util.redis.RedisUtil;
import com.lili.student.manager.StudentManager;

public class RemindRegisterJob {
	private static Logger log = LoggerFactory.getLogger(RemindRegisterJob.class);
	@Autowired
	private StudentManager studentManager;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private AuthcodeService authcodeService;
	@Value("${school.remind.register}")
	private String remind_school = "1-深港,2-东莞广仁,4-深圳港安,5-深圳广仁";
	
	private String msgId = "125185"; //
	
	public void remindRegister(){
		try {
			String dateToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			if(!redisUtil.isExist("remindRegister_"+ dateToday)){
				redisUtil.setAll("remindRegister_"+ dateToday,"working",600); //只在一个机器执行即可
				//数据同步的时间在每天早上的4:33:00左右
				String date1 = dateToday + " " + "04:00:00";
				String date2 = dateToday + " " + "05:00:00";
				String[] schools = remind_school.split(",");
				int maxPerTime = 100;
				if(null != schools && schools.length > 0){
					for(int i =0;i<schools.length;i++){
						String[] school = schools[i].split("-");
						Integer schoolId = Integer.parseInt(school[0]);
						String schoolName = school[1];
						List<String> mobiles = studentManager.getNewRegisterMobile(date1, date2, schoolId);
						Map<Integer, String> msgs = new HashMap<Integer, String>();
						msgs.put(1, schoolName);
						if(null != mobiles && mobiles.size()>0){
							if(mobiles.size()>maxPerTime){
								//分批发送
								int group = mobiles.size() / maxPerTime + 1; //213-->3
								for(int j=0;j<group; j++){	// 0 1 2
									List<String> temp = null;
									if(j != group -1){
										temp = mobiles.subList(j*maxPerTime, (j+1)*maxPerTime); //[0,100) [100,200) [200,300)
									}else{
										temp = mobiles.subList(j*maxPerTime, mobiles.size());
									}
									String mobileStrTemp = temp.toString();
									String mobileStr = mobileStrTemp.substring(1, mobileStrTemp.length()-1);
									authcodeService.sendMsgById(msgId, mobileStr, msgs);
								}
							}else {
								//直接发送
								String mobileStrTemp = mobiles.toString();
								String mobileStr = mobileStrTemp.substring(1, mobileStrTemp.length()-1);
								authcodeService.sendMsgById(msgId, mobileStr, msgs);
							}
							
						}
						
					}
				}
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
