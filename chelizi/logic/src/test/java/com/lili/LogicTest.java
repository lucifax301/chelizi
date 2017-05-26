package com.lili;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.lili.common.constant.JpushConstant;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.SerializableUtil;
import com.lili.common.util.StringUtil;
import com.lili.common.util.TimeUtil;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ReqResult;
import com.lili.logic.service.OrderLogic;
import com.lili.order.service.CoachClassService;
import com.lili.order.vo.CoachClassVo;
import com.lili.order.vo.OrderVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class LogicTest
{
    @Autowired
    private OrderLogic orderLogic;
    @Autowired
    private DefaultMQProducer orderProducer;
	@Resource(name = "jpushProducer")
	private DefaultMQProducer jpushProducer;
    @Autowired
    private DefaultMQPushConsumer acceptOrderConsumer;
    
    @Autowired
	private RedisUtil redisUtil;
    @Autowired
    private CoachClassService coachClassService;  
    
    
    @Before
    public void before() {
    	System.out.println("start>>>>");
    }
    @After
    public void after(){
    	System.out.println("<<<<affter");
    	try {
			Thread.sleep(3000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Test 
    public void testInclass(){
    	String content = "您的课程已经开始";
		JpushMsg jmsg = new JpushMsg();
		jmsg.setAlter(content);
		jmsg.setUserId(123l);
		jmsg.setOperate(JpushConstant.OPERATE.STUCLASSIN);
		jmsg.setOrderId("321order");
		jmsg.getExtras().put("payState", String.valueOf(0));
		Message jpush = new Message();
		jpush.setKeys("321order");
		jpush.setTopic(jpushProducer.getCreateTopicKey());
		jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
		jpush.setBody(SerializableUtil.serialize(jmsg));
		try {
			SendResult sr=jpushProducer.send(jpush);
			System.out.println(sr);
		} catch (MQClientException | RemotingException | MQBrokerException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Test
    public void testRedisSet() throws Exception {
    	String key="CoachClassServiceImpl_2015-11-27_1000004654_0_3";
    	List<CoachClassVo> list =redisUtil.get(key);
    	Date start=new Date();
    	Date end=new Date();
    		boolean idle=true;
     			for(CoachClassVo one:list){
    				long woda=TimeUtil.calcDistanceMillis(one.getCend(),start);
    				//提前下课以实际下课时间为准
    				if(one.getRend()!=null){
    					woda=TimeUtil.calcDistanceMillis(one.getRend(),start);
    				}
    				long tada=TimeUtil.calcDistanceMillis(end,one.getCstart());
    				//提前上课以实际上课时间为准
    				if(one.getRstart()!=null){
    					tada=TimeUtil.calcDistanceMillis(end,one.getRstart());
    				}
    				if(woda<=0&&tada<=0){
    					idle=false;
    					break;
    				}
    			}
 
    	System.out.println(idle);
    }
    @Test
    public void testRedisget() throws Exception {
    	String key="order_12345678";
//    	OrderVo o=redisUtil.get(key);
    	System.out.println(">>"+redisUtil.get(key));
    }
    
    
    @Test
    public void sendMessage() throws Exception {
    	try {
    	long now=System.currentTimeMillis();
    	OrderVo o=new OrderVo();
    	o.setOrderId("accept_1"+now);
    	Message msg=new Message();
    	msg.setKeys("accept_1");
    	msg.setTags("accept_order");
    	msg.setTopic(orderProducer.getCreateTopicKey());
    	msg.setBody(SerializableUtil.serialize(o));
    	orderProducer.send(msg);
    	
    	o=new OrderVo();
    	o.setOrderId("accept_2"+now);
    	msg=new Message();
    	msg.setKeys("accept_2");
    	msg.setTags("accept_order");
    	msg.setTopic(orderProducer.getCreateTopicKey());
    	msg.setBody(SerializableUtil.serialize(o));
    	orderProducer.send(msg);
    	
    	o=new OrderVo();
    	o.setOrderId("cancel_1"+now);
    	msg=new Message();
    	msg.setKeys("cancel_1"+now);
    	msg.setTags("cancel_order");
    	msg.setTopic(orderProducer.getCreateTopicKey());
    	msg.setBody(SerializableUtil.serialize(o));
    	orderProducer.send(msg);
    	o=new OrderVo();
    	o.setOrderId("cancel_2");
    	msg=new Message();
    	msg.setKeys("cancel_2");
    	msg.setTags("cancel_order");
    	msg.setTopic(orderProducer.getCreateTopicKey());
    	msg.setBody(SerializableUtil.serialize(o));
    	orderProducer.send(msg);
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    }
    @Test
    public void findtest() throws Exception {
    	try {
    	String lge1="113.949617";
    	String lae1="22.556323";
    	
    	String lge2="113.9494441561931";
    	String lae2="22.55647394587167";
    	String lge3="113.949583";
        String lae3="22.556628";
    	
    	ReqResult list=orderLogic.reservation("100000021", lge3, lae3, "1", "1","1", "1", "1", "20", "tokenId");
    	System.out.println(list.getResult().get("code")+">>>>>>>>>>>>>>>>>>>>>>"+list);
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+BeanCopy.getFieldList(list.getResult().get("data"),"coachId"));
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+BeanCopy.getFieldList(list.getResult().get("data"),"lge"));
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+BeanCopy.getFieldList(list.getResult().get("data"),"lae"));
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+BeanCopy.getFieldList(list.getResult().get("data"),"starLevel"));
    	
    	} catch(Exception e) {
    		System.out.println(">>>>>>>>>>>>>>>>>>>>>>ERROR");
    		e.printStackTrace();
    		System.out.println(e.getMessage());
    	}
    }
    @Test
    public void getCoachStatistc() throws Exception {
    
    	ReqResult result=orderLogic.getCoachStatistc("1000004567", null, "tokenId");
	    System.out.println(result.getResult().get("data"));
    }
    @Test
    public void addOrdertest() throws Exception {
    	try {
    		OrderVo order=new OrderVo();
        	order.setCoachId(1000005022l);
        	order.setCourseId("1");
        	order.setCstart(new Date());
        	order.setCend(new Date());
        	order.setLearnAddr("learnaddr");
        	order.setOrderState(1);
        	order.setOrderId(StringUtil.getOrderId());
        	order.setPstart(new Date());
        	order.setPend(new Date());
        	order.setStudentId(100000904l);
        	order.setStuAddr("stuaddr");
        	order.setPrice(3);
        	order.setPayState(1);
        	order.setLge(114.3);
        	order.setLae(22.2);
        	order.setOtype(1);
        	order.setOrderState(1);
        	order.setClzNum(2);
        	order.setCarId(123);
        	order.setOrderId("3214321432324234212");
    		ReqResult r=orderLogic.addOrder(order,"token");
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    }
    @Test
    public void acceptOrdertest() throws Exception {
    	ReqResult r=orderLogic.acceptOrder("ll72c263e303054c51aa8aa7c44ae53a9d", "1000004404", "8", "tokenId");
    	System.out.println("code="+r.getResult().get("code"));
    	
    }

    @Test
    public void cancelOrdertest() throws Exception {
    	ReqResult r=orderLogic.cancelOrder("lla6642ccb47dc4204a9266bc06d7a66bb", "1", "reseaon", "100030","2", "tokenId");
    	System.out.println("code="+r.getResult().get("code"));
    	
    }
    @Test
    public void refuseOrdertest() throws Exception {
    	ReqResult r=orderLogic.refuseOrder("llda481910bb264248b236185b9544b511", "100030", "reseaon", "tokenId");
    	System.out.println("code="+r.getResult().get("code"));
    	
    }
    @Test
    public void continuePrice() throws Exception {
    	ReqResult r=orderLogic.continuePrice("ll72c263e303054c51aa8aa7c44ae53a9d", "100030","2", "tokenId");
    	System.out.println("code="+r.getResult().get("code"));
    	System.out.println("price="+r.getResult().get("data"));
    	
    }
    @Test
    public void continueOrder() throws Exception {
    	ReqResult r=orderLogic.continueOrder("ll72c263e303054c51aa8aa7c44ae53a9d", "100030","2", "36000","tokenId");
    	System.out.println("code="+r.getResult().get("code"));
    	
    }
    
    @Test
    public void bookOrder() throws Exception {
    	try {
    		OrderVo vo=new OrderVo();
    		vo.setCoachId(1000004716l);
    		vo.setStudentId(100000186l);
    		vo.setCourseId("1");
    		vo.setCarId(1);
    		vo.setCcid(5);
    		vo.setClzNum(2);
    		vo.setPrice(150000);
    		vo.setLge(114.37);
    		vo.setLae(22.22);
    		vo.setStuAddr("stuAddr");
    		ReqResult r=orderLogic.addOrder(vo,"token");
    	} catch(Exception e){
    		e.printStackTrace();
    	}
//    	System.out.println("code="+r.getResult().get("code"));
    	
    }
/*    @Test
    public void stuCommentCoach() throws Exception {
    	try {
    	orderLogic.stuCommentCoach("1000000030", "99", "orderId", "5", "9", "oneWord", "tokenId");
    	orderLogic.stuCommentCoach("1000000030", "99", "orderId", "5", "10", "oneWord", "tokenId");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }*/
    @Test
    public void coachCommentStu() throws Exception {
    try {
    	Map<Integer,Integer> scores=new HashMap<Integer,Integer> (); 
    	scores.put(29, 50);
    	orderLogic.coachCommentStu("1000005022", "100000904", "818e6bfb3bc044d9ab1f467256a63658", scores, "oneWord", "tokenId");
    	Thread.sleep(500);
    	scores.clear();
    	scores.put(29, 50);
    	orderLogic.coachCommentStu("1000005022", "100000904", "818e6bfb3bc044d9ab1f467256a63658", scores, "oneWord", "tokenId");
    	Thread.sleep(500);
    	scores.clear();
    	scores.put(29, 80);
    	orderLogic.coachCommentStu("1000005022", "100000904", "818e6bfb3bc044d9ab1f467256a63658", scores, "oneWord", "tokenId");
    }catch(Exception e){
    	e.printStackTrace();
    }
    }
    @Test
    public void getStudentScore() throws Exception {
    	/*try {
    	ReqResult r=orderLogic.getStudentScore("999", "3", "1",null,1);
    	System.out.println(r.getResult().get("data"));
    	 }catch(Exception e){
    	    	e.printStackTrace();
    	 }*/
    }
    @Test
    public void getCommenttag() throws Exception {
    	ReqResult r=orderLogic.getCommentTag("1", "5", "tokenId");
    	System.out.print("coach5fen="+r.getResult().get("data"));
    	r=orderLogic.getCommentTag("2", "9", "tokenId");
    	System.out.println("stu9="+r.getResult().get("data"));
    	r=orderLogic.getCommentTag("2", null, "tokenId");
    	System.out.println("stu9all="+r.getResult().get("data"));
    }
    @Test
    public void getCoachStatistic() throws Exception {
    	ReqResult r=orderLogic.getCoachStatistc("1000000030", null, "tokenId");
    	System.out.println(r.getResult().get("data"));
    }

    public static void main(String []args)  throws Exception {
        @SuppressWarnings("resource")
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-init.xml");
        OrderLogic ol=ac.getBean(OrderLogic.class);
        ReqResult list=ol.reservation("1", "114.37", "22.27", "1", "1", "2","9", "1", "20", "tokenId");
    	System.out.println(list.getResult().get("code")+">>>>>>>>>>>>>>>>>>>>>>"+list);
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+BeanCopy.getFieldList(list.getResult().get("data"),"coachId"));
//        System.out.print(tl);
    }
    
}
