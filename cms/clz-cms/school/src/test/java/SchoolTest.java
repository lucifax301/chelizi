import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.cms.entity.PagedResult;
import com.lili.school.manager.CMSSchoolManager;
import com.lili.school.service.CMSSchoolService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class SchoolTest {

	@Autowired
	CMSSchoolManager cmsSchoolManager;
//	@Autowired
//	CMSSchoolService cmsSchoolService;


	@Test
	public void test() throws Exception{

	}
	
}
