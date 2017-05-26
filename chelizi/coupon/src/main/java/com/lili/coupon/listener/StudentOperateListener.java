/**
 * 
 */
package com.lili.coupon.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lili.common.util.SerializableUtil;
import com.lili.coupon.dto.CStock;
import com.lili.coupon.manager.impl.CouponManager;
import com.lili.coupon.service.CouponService;
import com.lili.student.vo.StudentMessage;

/**
 * @author linbo
 * 学员操作事件监听
 */
public class StudentOperateListener implements MessageListenerConcurrently
{
    @Autowired
    private CouponService couponService;
    
    @Autowired
    private CouponManager couponManager;
    
    /* (non-Javadoc)
     * @see com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently#consumeMessage(java.util.List, com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext)
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context)
    {
        for (MessageExt msg : msgs) {
            String tag = msg.getTags();
           
            StudentMessage studentMsg = SerializableUtil.unserialize(msg.getBody());
            long studentId = studentMsg.getStudentId();
            //根据tag取到stock
            List<CStock> cStocks = couponManager.getAllSuitableStock(tag);
            if (cStocks != null)
            {
                for (CStock cStock : cStocks)
                {
                    couponService.genStudentCouponAndNotify(cStock, studentId);
                }
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
