package com.lili.share.rmq;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.coupon.service.CouponService;
import com.lili.pay.manager.MoneyManager;
import com.lili.share.dao.mapper.ShareMapper;
import com.lili.share.dao.mapper.ShareUserMapper;
import com.lili.share.dao.po.SharePo;
import com.lili.share.dao.po.ShareUserPo;
import com.lili.student.manager.StudentManager;
import com.lili.student.vo.StudentMessage;

public class ShareStudentRegisterListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(ShareStudentRegisterListener.class);
	@Autowired
	ShareMapper shareMapper;
	
	@Autowired
	ShareUserMapper shareUserMapper;
	
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
	
    @Autowired
    RedisUtil redisUtil;
    
    @Autowired
    StudentManager studentManager;
    
    @Autowired
    MoneyManager moneyManager;
    
    @Autowired
    CouponService couponService;
    
    private final String SUID = "eed8490442a611e696afd89d672a2800"; //学员推荐分享主键
    
    private final String COUPONTMPID = "68f6c9f2ca83492faa6a591cf24a3403";//100元报名券

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		try {
			for(MessageExt msg : msgs){
	            StudentMessage studentMsg = SerializableUtil.unserialize(msg.getBody());
	            String mobile = studentMsg.getPhoneNum();
	            SharePo sp = shareMapper.queryReceviePhoneIsExit(mobile);
	            if(null != sp){
	            	String suid = sp.getSuid();
	            	ShareUserPo sup = shareUserMapper.queryById(suid);
	            	sp.setSendTotal(sup.getSendTotal());//设置金额
		            sp.setReceviePhone(mobile);
		            sp.setRecevieUser(studentMsg.getStudentId());
		            sp.setRecevieState(2);//接受状态：1已关联，2已注册，3已报名'
		            sp.setRegType(ReqConstants.USER_TYPE_STUDENT);
		            shareMapper.updateByReceviePhone(sp, mobile);
		            log.debug("ShareStudentRegisterListener get a message. MSGTYPE_STUDENT_REGISTER. StudentId: "+studentMsg.getStudentId());
		            
		            //学员推荐注册后B获取100元礼金，可用于报名，课时等
		            if(SUID.equals(sp.getSuid())){ //分享主键为学员分享
		            	//送钱改为送报名券
		            	/*ReqResult r =  couponService.obtainCouponByPhone(sp.getReceviePhone(), COUPONTMPID);
		            	log.info("******************************************* Send Coupon to Student Account,phone : " + sp.getReceviePhone() + ", Result : " + r.getMsgInfo());*/
		            	
		            	/*StudentAccount studentAccountIU = new StudentAccount();
		            	StudentAccount studentAccount = studentManager.getStudentMoney(studentMsg.getStudentId());
		            	if (studentAccount != null) {
		            		studentAccountIU.setMoney(studentAccount.getMoney() + 10000);
		            		studentAccountIU.setStudentId(studentMsg.getStudentId());
		            		studentManager.updateStudentAccount(studentAccountIU);
		            		log.info("******************************************* Update 100 For Student Account,StudentId : " + studentMsg.getStudentId());
		            		moneyManager.addMoneyLog(studentMsg.getStudentId(), MoneyManager.UserType.STUDENT, "system", studentAccount.getMoney() + 10000, 10000,  MoneyChange.AWARD, true, true, "分享奖励", "", "喱喱钱包");
		            	}
		            	else {
		            		studentAccountIU.setMoney(10000);
		            		studentAccountIU.setStudentId(studentMsg.getStudentId());
		            		studentManager.insertStudentAccount(studentAccountIU);
		            		log.info("******************************************* Add 100 For Student Account,StudentId : " + studentMsg.getStudentId());
		            		moneyManager.addMoneyLog(studentMsg.getStudentId(), MoneyManager.UserType.STUDENT, "system", 0, 10000,  MoneyChange.AWARD, true, true, "分享奖励", "", "喱喱钱包");
		            	}*/
		            	
		            }
		            
		            /*String redisKey = "QuartzJPush_stuSharer_" + sp.getReceviePhone();
            		if (!redisUtil.isExist(redisKey)) {
            			JpushMsg jmsg = new JpushMsg();
            			jmsg.setAlter("您推荐的手机号为：" + sp.getReceviePhone() +"的用户已经注册，该用户报名后200元奖励将送达您的个人账户");
            			jmsg.setUserId(sp.getSendUser());
            			jmsg.setOperate(JpushConstant.OPERATE.STUSHARE);
            			Message jpush = new Message();
            			jpush.setTopic(jpushProducer.getCreateTopicKey());
            			jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
            			jpush.setBody(SerializableUtil.serialize(jmsg));
            			jpushProducer.send(jpush);
            			redisUtil.set(redisKey, 1, 60);
            		}*/
            		
            		
	            }else {
	            	log.error("ShareStudentRegisterListener get a message, but no such share people. MSGTYPE_STUDENT_REGISTER. mobile: "+mobile);
	            }
			}
		} catch (Exception e) {
			log.error(
					msgs.size() + "ShareStudentRegisterListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
