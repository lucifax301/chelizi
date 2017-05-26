package com.lili.share.rmq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.common.constant.MoneyChange;
import com.lili.common.util.SerializableUtil;
import com.lili.common.vo.ReqConstants;
import com.lili.pay.manager.MoneyManager;
import com.lili.share.dao.mapper.ShareMapper;
import com.lili.share.dao.po.SharePo;
import com.lili.student.dto.StudentAccount;
import com.lili.student.manager.StudentManager;
import com.lili.student.vo.StudentMessage;

public class ShareStudentFlowListener implements MessageListenerConcurrently{
	private Logger log = Logger.getLogger(ShareStudentFlowListener.class);
	@Autowired
	ShareMapper shareMapper;
	
    @Autowired
    StudentManager studentManager;
    
    @Autowired
    MoneyManager moneyManager;
    
    private final String SUID = "eed8490442a611e696afd89d672a2800"; //学员推荐分享主键

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		try {
			for(MessageExt msg : msgs){
	            StudentMessage studentMsg = SerializableUtil.unserialize(msg.getBody());
	            
	          /*  SharePo sp = shareMapper.queryReceviePhoneIsExit(studentMsg.getPhoneNum());
	            if( sp != null &&  SUID.equals(sp.getSuid())){
	            	StudentAccount studentAccountIU = new StudentAccount();
	            	StudentAccount studentAccount = studentManager.getStudentMoney(sp.getSendUser());
	            	if (studentAccount != null) {
	            		studentAccountIU.setMoney(studentAccount.getMoney() + 20000);
	            		studentAccountIU.setStudentId(sp.getSendUser());
	            		studentManager.updateStudentAccount(studentAccountIU);
	            		log.info("******************************************* Update 100 For Student Account,StudentId : " + studentMsg.getStudentId());
	            		moneyManager.addMoneyLog(studentMsg.getStudentId(), MoneyManager.UserType.STUDENT, "system", studentAccount.getMoney() + 10000, 10000,  MoneyChange.AWARD, true, true, "推荐奖励", "", "喱喱钱包");
	            	}
	            	else {
	            		studentAccountIU.setMoney(20000);
	            		studentAccountIU.setStudentId(sp.getSendUser());
	            		studentManager.insertStudentAccount(studentAccountIU);
	            		log.info("******************************************* Add 100 For Student Account,StudentId : " + studentMsg.getStudentId());
	            		moneyManager.addMoneyLog(studentMsg.getStudentId(), MoneyManager.UserType.STUDENT, "system", 0, 20000,  MoneyChange.AWARD, true, true, "推荐奖励", "", "喱喱钱包");
	            	}
	            	SharePo spo = new SharePo();
	            	spo.setRecevieUser(studentMsg.getStudentId());
	            	spo.setRecevieState(4);//接受状态：1已关联，2已注册，3已报名，4出流水'
	            	spo.setRegType(ReqConstants.USER_TYPE_STUDENT);
		            shareMapper.updateByReceviePhone(spo, studentMsg.getPhoneNum());
	            }*/
	            log.debug("ShareStudentEnrollPaidListener get a message. MSGTYPE_STUDENT_ENROLLPAID. StudentId: "+studentMsg.getStudentId());
	            
	            
			}
		} catch (Exception e) {
			log.error(
					msgs.size() + "ShareStudentEnrollPaidListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}


}
