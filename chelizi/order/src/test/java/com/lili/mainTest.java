package com.lili;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lili.order.service.CoachClassService;
import com.lili.order.service.OrderService;

public class mainTest {
	 public static void main(String []args)  throws Exception {
		 System.out.println(">>>>>>>>>"+System.currentTimeMillis());   
		 	@SuppressWarnings("resource")
			ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-init.xml");
	        OrderService os=ac.getBean(OrderService.class);
	        long now=System.currentTimeMillis(); 
	        CoachClassService os1=ac.getBean(CoachClassService.class);
		 	System.out.println(">>>>>>>>>"+ac.getBeanNamesForType(CoachClassService.class));
		 	CoachClassService os2=(CoachClassService)ac.getBean("coachClassServiceImpl");
		 	
	    	int price=os.getCoachPrice(new Date(now),new Date(now+60000*45), 100, "1", 1, 1);
	    	System.out.println(">>>>>>>>>"+price);
//	        System.out.print(tl);
	    }
	    
}
