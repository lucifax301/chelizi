import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.authcode.manager.NoticeManager;
import com.lili.cms.entity.ResponseMessage;
import com.lili.common.util.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class Notice {
	
	//	private Logger log = Logger.getLogger(BonusTest.class);
	//	
		@Autowired
		NoticeManager noticeManager;


		@Test
		public void test() {
			try {
				System.out.println("222");
				Page<com.lili.authcode.dto.Notice> page=noticeManager.getNotice(new com.lili.authcode.dto.Notice(), 1, 10);
				ResponseMessage r=new ResponseMessage().addResult("pageData", page.getDataList());
				String string=r.build();
				System.out.println(page.getTotal());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
}
