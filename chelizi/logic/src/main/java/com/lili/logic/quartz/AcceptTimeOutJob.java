package com.lili.logic.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.OrderConstant;
import com.lili.common.util.SerializableUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.common.vo.ResultCode;
import com.lili.logic.state.CoachStateManager;
import com.lili.logic.state.StudentStateManager;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderQuery;
import com.lili.order.vo.OrderVo;

public class AcceptTimeOutJob extends DynamicJob {
	
	private static Logger log=Logger.getLogger(AcceptTimeOutJob.class);
	public AcceptTimeOutJob(String orderId){
		super(orderId,DynamicJob.ACCEPTTIMESECOND);
		this.setOrderId(orderId);
	}
	public AcceptTimeOutJob(){
	}
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String orderId=null;
		try {
			orderId=getOrderId(context);
			SchedulerContext schCtx = context.getScheduler().getContext();  
			ApplicationContext appCtx = (ApplicationContext)schCtx.get("applicationContext");  
			DefaultMQProducer jpushProducer=appCtx.getBean("jpushProducer",DefaultMQProducer.class);
			OrderService orderService=appCtx.getBean(OrderService.class);
			StudentStateManager stateManager = appCtx.getBean(StudentStateManager.class);
			CoachStateManager coachStateManager = appCtx.getBean(CoachStateManager.class);
			OrderVo order=orderService.queryOrderById(orderId, new OrderQuery());
			if(order!=null && order.getOrderState()==OrderConstant.ORDERSTATE.GIVEORDER){
				//1.自动取消
				Integer intRetype = OrderConstant.RETYPE.COACHREFUSE;
				Integer intUcancel = OrderConstant.USETYPE.COACH;
				String code = orderService.cancelOrder(orderId, intRetype, "系统自动取消",
						intUcancel, "",0);
				//2.取消成功发送消息
				if(ResultCode.ERRORCODE.SUCCESS.equals(code)){
				    //同学员主动取消订单
				    stateManager.handleCancelOrder(orderId, "0", "系统自动取消", String.valueOf(order.getStudentId()), "2", "", true);
				    coachStateManager.handleCancelOrder(orderId, "0", "系统自动取消", String.valueOf(order.getCoachId()), "1", "", true);
				    //学员消息取消的时候，已经有了，所以就不在发送
//					//学员
//					String content = "很抱歉，教练暂时忙";
//					JpushMsg jmsg = new JpushMsg();
//					jmsg.setAlter(content);
//					jmsg.setUserId(order.getStudentId());
//					jmsg.setOperate(JpushConstant.OPERATE.STUAUTOCANCEL);
//					jmsg.setOrderId(order.getOrderId());
//					Message jpush = new Message();
//					jpush.setKeys(order.getOrderId());
//					jpush.setTopic(jpushProducer.getCreateTopicKey());
//					jpush.setTags(JpushConstant.RMQTAG.JPUSH2STU);
//					jpush.setBody(SerializableUtil.serialize(jmsg));
//					jpushProducer.send(jpush);
					//教练
					String content = "接单超时，系统已经自动取消";
					JpushMsg jmsg = new JpushMsg();
					jmsg.setAlter(content);
					jmsg.setUserId(order.getCoachId());
					jmsg.setOperate(JpushConstant.OPERATE.COACHAUTOCANCEL);
					jmsg.setOrderId(order.getOrderId());
					Message jpush = new Message();
					jpush.setKeys(order.getOrderId());
					jpush.setTopic(jpushProducer.getCreateTopicKey());
					jpush.setTags(JpushConstant.RMQTAG.JPUSH2COACH);
					jpush.setBody(SerializableUtil.serialize(jmsg));
					jpushProducer.send(jpush);
				} else {
					log.debug(code+" auto cancel ERROR="+order);
				}
				
			} else {
				if(log.isDebugEnabled()){
					log.debug(orderId+" neednot to auto cancel because of not givestate="+order);
				}
			}
		}catch(Exception e){
			log.error(orderId+" Exception:"+e.getMessage(),e);
		} finally {
			try {
				Scheduler scheduler=context.getScheduler();
				JobKey jobKey = JobKey.jobKey(getJobName(), getJobGroup());  
				scheduler.deleteJob(jobKey);
			} catch (SchedulerException e) {}
		}
	}
	public String getOrderId(JobExecutionContext context){
		String result=null;
		if(context==null){
			result=String.valueOf(getData("orderId"));
		} else {
			result=context.getJobDetail().getJobDataMap().getString("orderId");
		}
		return result;
	}
	public void setOrderId(String orderId) {
		this.setJobName(orderId);
		setData("orderId",orderId);
	}
	
}
