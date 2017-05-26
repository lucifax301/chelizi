package coach;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.coach.manager.CoachManager;
import com.lili.coach.vo.CoachStatusRecord;

@ContextConfiguration(locations = {"classpath:spring-init.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestStatus {

	@Autowired
    CoachManager coachManager;
	
	@Test
	public void test() {
		System.out.println("**************************************");
		CoachStatusRecord record =new CoachStatusRecord();
        record.setCoachId(1000L);
        record.setStatus(0);
        coachManager.addCoachStatusRecord(record);
	}

}
