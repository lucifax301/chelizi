package authcode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.lili.authcode.dto.Notice;
import com.lili.authcode.manager.NoticeManager;
import com.lili.authcode.service.AuthcodeService;
import com.lili.common.constant.JpushConstant;
import com.lili.common.constant.NoticeType;
import com.lili.common.util.Page;
import com.lili.common.util.SerializableUtil;
import com.lili.common.vo.JpushMsg;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class NoticeManagerTest {
	@Autowired
	NoticeManager noticeManger;
	
	@Autowired
	AuthcodeService authcodeService;
	@Autowired
	StudentManager studentManager;
	 @Autowired
	  OrderService orderService;
//	@Resource(name = "jpushProducer")
//	static
//    DefaultMQProducer jpushProducer;
//	@Test
//	public void getStudentNotice(){
//		Page<Notice> pages = noticeManger.getNoticesByStudentId(2296059l, null, null, (byte)1,null,1, 100);
//		System.out.println("pages---------->"+pages);
//		System.out.println("pages---------->"+pages.getDataList());
//		
//	}
	
	@Test
	public void getCoachNotice() throws Exception{
		
		List<OrderVo> list = orderService.searchBmClass(202129,1066);
    	System.out.println("22"+list.size());
    	
		//Page<Notice> pages = noticeManger.getNoticesByCoachId(2732l, null, null, (byte)1,null,null,1, 100);
		//(Long coachId,Integer schoolId,Integer cityId,byte type,Boolean isVip,Date etime, int pageNo, int pageSize);
		Notice notice=new Notice();
	//	notice.setSchoolId("1");
	//	notice.setApplyexam("402,0|1,0");
	//	notice.setCityId("100101,103100");
	//	notice.setUserIdStrs(",68828005,");
	//	notice.setUtype("1");
		notice.setTitle("222");
		notice.setUserType((byte) 9);
	//	noticeManger.updateNotice(notice);
	//	noticeManger.sendMessage(notice);
	//	noticeManger.sendMessageTouTiao(notice);
	//	Student student= studentManager.getStudentInfo(49205124);
	//	noticeManger.getNoticeIndex(student);
		System.out.println("pages---------->");
		
	}
	public static void main(String arg[]){
		Map<String, String> extra = new HashMap<>();
	    extra.put("\"orderId\"", "123");
		extra.toString().replace("=", ":");
	        System.out.println(extra);
	        
	}
}
