package com.lili.coach.rmq;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.coach.service.CoachService;
import com.lili.coach.vo.CoachStatusRecord;
import com.lili.common.util.LogUtil;
import com.lili.common.util.SerializableUtil;
import com.lili.event.vo.CoachCarInOutVo;
import com.lili.order.service.OrderService;

/**
 * 教练出车收车
 * @author devil
 *
 */
public class UpdateCoachStatusListener implements MessageListenerConcurrently  {
	private Logger log = Logger.getLogger(UpdateCoachStatusListener.class);
	@Autowired
	CoachService coachService;
	@Autowired
	OrderService orderService;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		log.debug("UpdateCoachStatusListener got a message!");
		try {
			for(MessageExt msg : msgs){
				CoachCarInOutVo statusEventVo = (CoachCarInOutVo) SerializableUtil.unserialize(msg
						.getBody());
				
				try{
			        CoachStatusRecord record =new CoachStatusRecord();
			        record.setCoachId(statusEventVo.getCoachId());
			        record.setCarId(statusEventVo.getCarId());
			        if("in".equals(statusEventVo.getStatus())){//收车
			        	record.setStatus(0);
			        	coachService.doInOutCarStatus(record);
			        }else if("out".equals(statusEventVo.getStatus())){//出车
			        	record.setStatus(1);
			        	coachService.doInOutCarStatus(record);
			        }
			        
		        }catch(Exception ex){
		        	log.warn("add coach status record error,"+LogUtil.getStackMsg(ex));
		        }
			}
		} catch (Exception e) {
			log.error(
					msgs.size() + "UpdateCoachStatusListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}


}
