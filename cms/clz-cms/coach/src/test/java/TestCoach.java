import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.coach.service.CMSCoachService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class TestCoach {

	@Autowired
	CMSCoachService cmsCoachService;
	
	@Test
	public void test() {
		try{
		cmsCoachService.findOne(123);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
