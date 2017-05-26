package com.lili.coach.rmq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.coach.manager.CoachManager;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.vo.JpushMsg;

public class UpdateCoachStateListener implements MessageListenerConcurrently {

	private Logger log = Logger.getLogger(UpdateCoachStateListener.class);
	@Autowired
	CoachManager coachManager;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		JpushMsg jpushMsg = null;
		try {
			for (MessageExt msg : msgs) {
				jpushMsg = (JpushMsg) SerializableUtil.unserialize(msg
						.getBody());
				log.debug("UpdateCoachStateListener---> " + jpushMsg);
				// 屏蔽无operate码的消息发送
				if (jpushMsg.getOperate() == null) {
					log.error(jpushMsg + " has null operate, so DONOT send.");
					continue;
				} else if (jpushMsg.getOperate().equals(
						JpushConstant.OPERATE.COACHCLASSIN)) {
					if (jpushMsg.getExtras().containsKey(
							JpushConstant.EXTRAFIELD.GENTYPE)
							&& jpushMsg.getExtras()
									.get(JpushConstant.EXTRAFIELD.GENTYPE)
									.equals(JpushConstant.GENTYPE.AUTOGEN)) {
						log.debug("UpdateCoachStateListener--->COACHCLASSIN "
								+ jpushMsg);
						/*Coach c = new Coach();
						c.setCoachId(jpushMsg.getUserId());
						c.setWstate(ReqConstants.COACH_STATUS_ON_CLASS);
						coachManager.updateCoach(c);*/
					}
				} else if (jpushMsg.getOperate().equals(
						JpushConstant.OPERATE.COACHCLASSOUT)) {
					if (jpushMsg.getExtras().containsKey(
							JpushConstant.EXTRAFIELD.GENTYPE)
							&& jpushMsg.getExtras()
									.get(JpushConstant.EXTRAFIELD.GENTYPE)
									.equals(JpushConstant.GENTYPE.AUTOGEN)) {
						log.debug("UpdateCoachStateListener--->COACHCLASSOUT "
								+ jpushMsg);
						/*Coach c = new Coach();
						c.setCoachId(jpushMsg.getUserId());
						c.setWstate(ReqConstants.COACH_STATUS_OFF_CLASS);
						coachManager.updateCoach(c);*/
					}
				}
			}
		} catch (Exception e) {
			log.error(
					jpushMsg + " UpdateCoachStateListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}

		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
