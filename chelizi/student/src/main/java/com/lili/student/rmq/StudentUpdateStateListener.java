package com.lili.student.rmq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.student.manager.StudentManager;

public class StudentUpdateStateListener implements MessageListenerConcurrently {
	
	private Logger log = Logger.getLogger(StudentUpdateStateListener.class);
	
	@Autowired
	StudentManager studentManager;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		JpushMsg jpushMsg=null;
		try {
			for (MessageExt msg : msgs) {
				jpushMsg = SerializableUtil.unserialize(msg
						.getBody());
				//屏蔽无operate码的消息发送
				if(jpushMsg.getOperate()==null){
					log.error(jpushMsg+" has null operate, so DONOT send.");
					continue;
				}
				else if(jpushMsg.getOperate().equals(JpushConstant.OPERATE.STUCLASSIN)){
					if (jpushMsg.getExtras().containsKey(
							JpushConstant.EXTRAFIELD.GENTYPE)
							&& jpushMsg.getExtras()
									.get(JpushConstant.EXTRAFIELD.GENTYPE)
									.equals(JpushConstant.GENTYPE.AUTOGEN)) {
						log.debug("StudentUpdateStateListener--->STUCLASSIN "+jpushMsg);
						/*Student s = new Student();
						s.setStudentId(jpushMsg.getUserId());
						s.setEventState(ReqConstants.STUDENT_STATUS_ON_CLASS);
						s.setEventDesc(jpushMsg.getExtras().get("orderId"));
						studentManager.updateStudent(s);*/
					}
					
				}
				else if(jpushMsg.getOperate().equals(JpushConstant.OPERATE.STUCLASSOUT)){
					if (jpushMsg.getExtras().containsKey(
							JpushConstant.EXTRAFIELD.GENTYPE)
							&& jpushMsg.getExtras()
									.get(JpushConstant.EXTRAFIELD.GENTYPE)
									.equals(JpushConstant.GENTYPE.AUTOGEN)) {
						log.debug("StudentUpdateStateListener--->STUCLASSOUT "+jpushMsg);
						/*Student s = new Student();
						s.setStudentId(jpushMsg.getUserId());
						s.setEventState(ReqConstants.STUDENT_STATUS_OFF_CLASS);
						s.setEventDesc("");
						studentManager.updateStudent(s);*/
					}
				}

			}
		} catch (Exception e) {
			log.error(
					jpushMsg + " StudentUpdateStateListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
