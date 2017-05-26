package com.lili.student.rmq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.bbs.manager.IBBSManager;
import com.lili.common.util.SerializableUtil;
import com.lili.student.dto.Student;

public class StudentUpdateUserListener implements MessageListenerConcurrently {
	
	private Logger log = Logger.getLogger(StudentUpdateUserListener.class);
	
	@Autowired
	IBBSManager bbsManager;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		Student student = null;
		try {
			log.info("********************************** StudentUpdateUserListener Start");
			for (MessageExt msg : msgs) {
				student = SerializableUtil.unserialize(msg.getBody());
				if (student != null) {
					bbsManager.updateBbsUserInfo(student.getStudentId(), 2, student.getName(), student.getHeadIcon(), student.getPhoneNum());
				}
			}
		} catch (Exception e) {
			log.error( student + " StudentUpdateUserListener Exception:" + e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
