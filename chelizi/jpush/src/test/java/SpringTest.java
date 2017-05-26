import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lili.jpush.rmq.StuJPushListener;


public class SpringTest {
	 public static void main(String []args)  throws Exception {
			ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-init.xml");
			StuJPushListener sp=ac.getBean(StuJPushListener.class);
	        long now=System.currentTimeMillis(); 	
	    	sp.test();
	    	System.out.println(">>>>>>>>>");
//	        System.out.print(tl);
	    }
}
