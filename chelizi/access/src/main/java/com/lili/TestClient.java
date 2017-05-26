/**
 * 
 */
package com.lili;

import java.util.Random;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lili.coach.dto.Coach;
import com.lili.coach.manager.CoachManager;
import com.lili.coach.service.CoachService;
import com.lili.common.vo.ReqConstants;
import com.lili.location.service.LocationService;
import com.lili.location.vo.CoachLoc;
/**
 * @author linbo
 *
 */
public class TestClient
{
    private static ClassPathXmlApplicationContext context = null;

    
    public static ClassPathXmlApplicationContext getDubboContext()
    {
        return context;
    }
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        context =  new ClassPathXmlApplicationContext(
                new String[] { "applicationContext.xml" });
        context.start();
        
        CoachService coachService = TestClient.getDubboContext().getBean(CoachService.class);
        
 
        LocationService locationService = (LocationService) TestClient.getDubboContext().getBean(LocationService.class);
        //ICoachStateManager coachStateManager = (ICoachStateManager) TestClient.getDubboContext().getBean(CoachService.class);
        CoachManager coachManager = TestClient.getDubboContext().getBean(CoachManager.class);
        
        double lge [] = {114.019313, 114.013766, 114.023791, 114.026899, 113.9};
        double lae [] = {22.539865, 22.542089, 22.537445, 22.540199, 22.5};
 
        long baseId = 0;
        //for (int i = 1000005348; i < 1000006353; i++)  99号机
        for (int i = 1000000001; i < 1000000999; i++)
        {
            long coachId = i;
            
            CoachLoc t = new CoachLoc();
            t.setCoachId(coachId);
            
            t.setLge(lge[i % 5] + new Random().nextFloat());
            t.setLae(lae[i % 5] + new Random().nextFloat());
            t.setDir((float) (4.0 + new Random().nextFloat()));
            
            /*t.setLge(lge[i % 5]);
            t.setLae(lae[i % 5]);*/
            
            try
            {
                locationService.saveTeacher(t);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            coachService.setOnline(String.valueOf(coachId), true);
            
            Coach coach = new Coach();
            coach.setCoachId(coachId);
            coach.setWstate(ReqConstants.COACH_STATUS_ON_WORK);
            //设置出车课程
            coach.setCourses("1,2,3,4,5");
            //设置出车id //收车时可用为空
            try {
                coach.setCoachCarId(Integer.parseInt("1"));
            } catch (NumberFormatException e) {
                
            }
            coach.setEventDesc("");
            coachManager.updateCoach(coach);
            
            coachService.doStatus(String.valueOf(coachId), "1", "1,2,3,4,5", "on", "");
            
        }
        System.out.println("success.");
    }
}
