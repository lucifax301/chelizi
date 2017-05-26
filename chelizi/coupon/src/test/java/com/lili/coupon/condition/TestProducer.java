/**
 * 
 */
package com.lili.coupon.condition;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * @author linbo
 *
 */
public class TestProducer
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("192.168.1.136:9876");
        try
        {
            producer.start();
            for (int i = 0; i < 1000; i++)
            {
                Message msg = new Message("login",
                        String.valueOf(100000023 + i),
                        String.valueOf(100000001 + i),
                        "100000001".getBytes());
                SendResult result = producer.send(msg);
                System.out.println("id:" + result.getMsgId() +
                        " result:" + result.getSendStatus());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            producer.shutdown();
        }
    }

}
