package com.lili.coupon.condition;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.coach.dto.CourseS;
import com.lili.coach.manager.CourseSManager;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class CoursesManagerTest
{
    @Autowired
    private CourseSManager courseSManager;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testInit()
    {
        
        try
        {
            Map<Integer, CourseS> courMap = courseSManager.getAllCourseMap();
            System.out.println(courMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsConditionTrue()
    {
       
    }

    @Test
    public void testDestory()
    {
        
    }
}
