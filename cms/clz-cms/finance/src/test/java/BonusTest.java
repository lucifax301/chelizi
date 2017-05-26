


import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//import com.lili.finance.service.impl.cms.BonusServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class BonusTest {
	private Logger log = Logger.getLogger(BonusTest.class);
//	
//	@Autowired
//	private BonusServiceImpl bonusServiceImpl;


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
	public void test() {
		try {
			//com.lili.common.ReqResult reqResult  =bonusServiceImpl.query(request,	response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
