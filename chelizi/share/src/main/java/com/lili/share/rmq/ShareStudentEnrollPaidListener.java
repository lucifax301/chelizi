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
import com.lili.pay.manager.MoneyManager;
import com.lili.share.dao.mapper.ShareMapper;
import com.lili.share.dao.po.SharePo;
import com.lili.student.dto.StudentAccount;
import com.lili.student.manager.StudentManager;
import com.lili.student.vo.StudentMessage;

public class ShareStudentEnrollPaidListener implements MessageListenerConcurrently{
	private Logger log = Logger.getLogger(ShareStudentEnrollPaidListener.class);
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
	            String mobile = studentMsg.getPhoneNum();
	            //分享活动记录
	            SharePo sp = new SharePo();
	            sp.setReceviePhone(mobile);
	            sp.setRecevieUser(studentMsg.getStudentId());
	            sp.setRecevieState(3);//接受状态：1已关联，2已注册，3已报名'
	            shareMapper.updateByReceviePhone(sp, mobile);
	            log.debug("ShareStudentEnrollPaidListener get a message. MSGTYPE_STUDENT_ENROLLPAID. StudentId: "+studentMsg.getStudentId());
	            
	            
	           /* SharePo sp2 = shareMapper.queryReceviePhoneIsExit(studentMsg.getPhoneNum());
	            if( sp2 != null &&  SUID.equals(sp2.getSuid())){
	            	StudentAccount studentAccountIU = new StudentAccount();
	            	StudentAccount studentAccount = studentManager.getStudentMoney(sp2.getSendUser());
	            	if (studentAccount != null) {
	            		studentAccountIU.setMoney(studentAccount.getMoney() + 20000);
	            		studentAccountIU.setStudentId(sp2.getSendUser());
	            		studentManager.updateStudentAccount(studentAccountIU);
	            		log.info("******************************************* Update 200 For Student Account,StudentId : " + sp2.getSendUser());
	            		moneyManager.addMoneyLog(sp2.getSendUser(), MoneyManager.UserType.STUDENT, "system", studentAccount.getMoney() + 20000, 20000,  MoneyChange.AWARD, true, true, "推荐奖励", "", "喱喱钱包");
	            	}
	            	else {
	            		studentAccountIU.setMoney(20000);
	            		studentAccountIU.setStudentId(sp2.getSendUser());
	            		studentManager.insertStudentAccount(studentAccountIU);
	            		log.info("******************************************* Add 200 For Student Account,StudentId : " + sp2.getSendUser());
	            		moneyManager.addMoneyLog(sp2.getSendUser(), MoneyManager.UserType.STUDENT, "system", 0, 20000,  MoneyChange.AWARD, true, true, "推荐奖励", "", "喱喱钱包");
	            	}
	            }*/
	            //五月天演唱会活动记录。发短信验证码，送门票
	            //1.时间是否在6.13号之后
	            //2.判断已发放的学员数量是否超过了150张
	            //3.发短信通知学员，短信中带一个领券验证码
	            //4.记录券
	            
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
