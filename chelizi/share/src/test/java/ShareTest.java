
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.share.service.ShareUserService;
import com.lili.share.vo.ShareUserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class ShareTest {
	private Logger log = Logger.getLogger(ShareTest.class);
	
	@Autowired
	private ShareUserService shareUserService;


	@Before
	public void before() {
		log.info("start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	@After
	public void after() {
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<affter");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addPrice() {
		ShareUserVo s= new ShareUserVo();
		s.setSuid("11");
		s.setUserName("test");
		s.setSendTotal(8000);
		s.setSendType(1);
		s.setRegType(1);
		s.setCheckState(1);
		s.setIsdel(0);
		s.setMtime("2015-10-31 17:55:50");
		try {
			shareUserService.add(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
