/**
 * 
 */
package com.lili.common.util.jpush;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.common.util.SerializableUtil;
import com.lili.common.vo.JpushMsg;

/**
 * @author linbo
 *
 */
@Component
public class PushComponent
{
    private static final Logger logger = LoggerFactory.getLogger(PushComponent.class);
    
    @Autowired
    private static DefaultMQProducer jpushProducer;

//    @PreDestroy
//    public void destroy()
//    {
//        jpushProducer.shutdown();
//    }
    
    public static SendResult pushMsg(JpushMsg pushMsg, String tag)
    {
        Message msg = new Message(jpushProducer.getCreateTopicKey(), tag, SerializableUtil.serialize(pushMsg));
        try
        {
            SendResult result = jpushProducer.send(msg);
            return result;
        }
        catch (MQClientException e)
        {
            logger.error("pushMsg", e);
        }
        catch (RemotingException e)
        {
            logger.error("pushMsg", e);
        }
        catch (MQBrokerException e)
        {
            logger.error("pushMsg", e);
        }
        catch (InterruptedException e)
        {
            logger.error("pushMsg", e);
        }
        return null;
    }
    
    
    
}
