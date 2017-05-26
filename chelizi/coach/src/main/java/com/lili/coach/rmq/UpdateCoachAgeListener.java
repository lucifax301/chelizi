package com.lili.coach.rmq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.common.util.SerializableUtil;
import com.lili.event.vo.CourseStatusEventVo;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;

public class UpdateCoachAgeListener implements MessageListenerConcurrently  {
	private Logger log = Logger.getLogger(UpdateCoachAgeListener.class);
	@Autowired
	CoachManager coachManager;
	@Autowired
	OrderService orderService;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		log.debug("UpdateCoachAgeListener got a message!");
		try {
			for(MessageExt msg : msgs){
				CourseStatusEventVo statusEventVo = (CourseStatusEventVo) SerializableUtil.unserialize(msg
						.getBody());
				
				String orderId = statusEventVo.getOrderId();
				if(null != orderId && !"".equals(orderId)){
					List<OrderVo> orders = orderService.queryByOrderId(orderId, new OrderQuery());
					if(null != orders && orders.size() >0){
						OrderVo ov = orders.get(0);
						updateCoachAge(ov);
					}
				}
				List<OrderVo> ovs = statusEventVo.getOrderList();
				if(null != ovs && ovs.size()>0){
					OrderVo ov = ovs.get(0);
					updateCoachAge(ov);
				}
				
			}
		} catch (Exception e) {
			log.error(
					msgs.size() + "UpdateCoachAgeListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	private void updateCoachAge(OrderVo ov) {
		Long coachId = ov.getCoachId();
		Integer diffHours = getDiffHours(ov.getRstart(),ov.getRend());
		Coach c = coachManager.getCoachInfo(coachId);
		c.setCoachAge(diffHours.intValue() + c.getCoachAge().intValue());
		log.debug("UpdateCoachAgeListener got a message! coachId=" +coachId+",orderId="+ov.getOrderId()+",age added hour="+diffHours+"!" );
		coachManager.updateCoach(c);
		
	}

	private Integer getDiffHours(Date rstart, Date rend) {
		//四舍五入
		return (int) Math.round((rend.getTime() - rstart.getTime())/(60 * 1000)/60.0);
	}
	
	public static void main(String[] args) {
		UpdateCoachAgeListener uca = new UpdateCoachAgeListener();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date now = df.parse("2004-03-26 13:01:40");
			Date date =df.parse("2004-03-26 14:32:24");
			System.out.println(uca.getDiffHours(now, date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

}
