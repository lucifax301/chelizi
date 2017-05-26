package coach;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.common.util.SerializableUtil;
import com.lili.event.vo.CoachCarInOutVo;
import com.lili.event.vo.CourseStatusEventVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class SendStatusTest {

	@Resource(name="coachProducer")
    DefaultMQProducer coachProducer;
	
	@Test
	public void test() {
		CoachCarInOutVo carInOutVo = new CoachCarInOutVo();
        carInOutVo.setCarId(2568);
        carInOutVo.setCoachId(3870);
        carInOutVo.setTime(new Date());
        carInOutVo.setStatus("out");
        CourseStatusEventVo v2=new CourseStatusEventVo();
        v2.setOrderId("1233");
        Message message = new Message(coachProducer.getCreateTopicKey(), "out", SerializableUtil.serialize(carInOutVo));
        try
        {
        	System.out.println("----------------------------------------------------------");
        	SendResult r=coachProducer.send(message);
        	System.out.println("CoachServiceImpl----->sendStatus:"+r);
            System.out.println("CoachServiceImpl----->sendStatus:"+message);
            System.out.println("----------------------------------------------------------");
        }
        catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e)
        {
            e.printStackTrace();
        }
	}

}
