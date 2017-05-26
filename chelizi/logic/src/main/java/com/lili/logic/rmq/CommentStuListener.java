package com.lili.logic.rmq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.common.constant.RedisExpireConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.order.service.CoachScoreService;
import com.lili.order.service.CoachStatisticService;
import com.lili.order.service.StudentStatisticService;
import com.lili.order.vo.StuCommentVo;
import com.lili.order.vo.StudentStatisticQuery;
import com.lili.order.vo.StudentStatisticVo;

public class CommentStuListener implements MessageListenerConcurrently {
	private Logger log = Logger.getLogger(AcceptOrderListener.class);
	@Autowired
	private CoachScoreService coachScoreService;
	@Autowired
	private CoachStatisticService coachStatisticService;
	@Autowired
	private RedisUtil redisUtil;
	@Resource(name="orderProducer")
	private DefaultMQProducer orderProducer;
	@Autowired
	private StudentStatisticService studentStatisticService;
	@SuppressWarnings("unchecked")
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		// 待优化：去重复
		List<StuCommentVo> sc=null;
		try {
			for (MessageExt msg: msgs) {
				// 1.计算当日统计
				sc = (List<StuCommentVo>) SerializableUtil.unserialize(msg.getBody());
				// 0.保证消息不重复执行
				long weiyi=TimeUtil.getDateToMillis(sc.get(0).getCotime());
				String redisKey = "commentstuorder_static_" + weiyi;
				if (sc!=null && !sc.isEmpty() && !redisUtil.isExist(redisKey)) {
					//获取评分
					StudentStatisticQuery sq=new StudentStatisticQuery();
					String idin="and ctid in (";
					int i=0;
					for(;i<sc.size()-1;i++){
						idin+=sc.get(i).getCtid()+",";
					}
					idin+=sc.get(i).getCtid()+")";
					sq.setGroupBy(idin);
					StudentStatisticVo vo=new StudentStatisticVo();
					vo.setStudentId(sc.get(0).getStudentId());
					List<StudentStatisticVo> list=studentStatisticService.queryByObjectAnd(vo, sq);
					Map<Integer,StudentStatisticVo> map=new HashMap<Integer,StudentStatisticVo>();
					for(int j=0;list!=null && j<list.size();j++){
						map.put(list.get(j).getCtid(), list.get(j));
					}
					for(i=0;i<sc.size();i++){
						StuCommentVo one=sc.get(i);
						if(map.containsKey(one.getCtid())){
							vo=map.get(one.getCtid());
							vo.setTotal(vo.getTotal()+1);
							vo.setScore(vo.getScore()+one.getScore());
						} else {
							vo=new StudentStatisticVo();
							vo.setScore(one.getScore());
							vo.setStudentId(one.getStudentId());
							vo.setCtid(one.getCtid());
							vo.setTotal(1);
							list.add(vo);
						}
					}
					// 回存
					studentStatisticService.saveStudentStatisticList(list);
					// N.保证消息不重复执行
					redisUtil.set(redisKey, 1,
							RedisExpireConstant.RMQTIME.RETYSECONDS);
				}
			}
		} catch (Exception e) {
			log.error(
					sc + "CommentStuListener Exception:"
							+ e.getMessage(), e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
