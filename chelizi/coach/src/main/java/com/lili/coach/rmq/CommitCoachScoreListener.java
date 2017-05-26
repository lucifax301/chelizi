package com.lili.coach.rmq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.order.vo.CoachScoreVo;

public class CommitCoachScoreListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(CommitCoachScoreListener.class);
	@Autowired
	CoachManager coachManager;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		try {
			for(MessageExt msg : msgs){
				CoachScoreVo scv = (CoachScoreVo) SerializableUtil.unserialize(msg
						.getBody());
				Coach c = new Coach();
				c.setCoachId(scv.getCoachId());
				if(null ==scv.getOrderTotal() ||scv.getOrderTotal() == 0){
					c.setStarLevel(OrderConstant.COACHSCORE);
				}else {
					c.setStarLevel((int) (scv.getScoreTotal()/scv.getOrderTotal()));
				}
				log.debug("CommitCoachScoreListener got a message! coachId="+scv.getCoachId()+
						" orderTotal="+scv.getOrderTotal()+" scoreTotal="+ scv.getScoreTotal() );
				coachManager.updateCoach(c);
			}
		} catch (Exception e) {
			log.error(
					msgs.size() + "CommitCoachScoreListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
