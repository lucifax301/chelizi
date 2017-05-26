package com.lili.common.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.cms.util.DateUtil;
import com.lili.cms.util.StringUtil;
import com.lili.log.model.LogCommon;
import com.lili.log.service.CMSLogCommonService;

/**
 * 记录数据数数据操作的动作到t_log_common中
 * @author hughes
 *
 */
public class LogDBHandler {

	@Autowired
	CMSLogCommonService logCommonService;

	protected  final Logger access = Logger.getLogger(this.getClass());
	
	public void logAfter(JoinPoint jp){
		if(jp.getSignature().getName().startsWith("insert") || jp.getSignature().getName().startsWith("update") || jp.getSignature().getName().startsWith("del")
				|| jp.getSignature().getName().startsWith("add")|| jp.getSignature().getName().startsWith("new")){
		
		try {
			Object[] args = jp.getArgs();
			LogCommon logCommon = (LogCommon)args[0];
			if(logCommon == null || logCommon.getChannel() == null || StringUtil.isNullString(logCommon.getRelateId())){
				return;
			}
			
			if(StringUtil.isNullString(logCommon.getRemark())){
				String operateTime = DateUtil.formatDatetime(logCommon.getOperateTime());
				
				if(logCommon.getAction() == 1){
					logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 插入了当前记录相关数据 ");
				}else if (logCommon.getAction() == 3) {
					logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 删除了当前记录相关数据 ");
				}else {
					logCommon.setRemark(logCommon.getUserName() + " " + operateTime + " 更新了当前记录相关数据 ");
				}
			}
			
			logCommonService.insertOne(logCommon);
			access.info("|||insert log into db,remark : " + logCommon.getRemark() + " detail:"+logCommon.getDetail()+ " logId : " + logCommon.getId()
					+ "|||Method : " + jp.getTarget().getClass() + "." + jp.getSignature().getName());
		} catch (Exception e) {
			return;
		}
		}
	}
	
}
