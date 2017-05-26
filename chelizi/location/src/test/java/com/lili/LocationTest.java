package com.lili;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.coach.vo.CoachVo;
import com.lili.common.util.BeanCopy;
import com.lili.location.service.LocationService;
import com.lili.location.vo.CoachLoc;
import com.lili.order.vo.SearchVo;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class LocationTest
{
    @Autowired
    private LocationService locationService;
    
    @Before
    public void before() {
    
    }
    @Test
    public void addtest() throws Exception {
    	for(int i=0;i<50;i++) {
        CoachLoc t=new CoachLoc();
        t.setLge(114.37d+(double)i/100);
        t.setLae(22.27d+(double)i/100);
        t.setCoachId(1000004404+i);
        locationService.saveTeacher(t);
    	}
    	for(int i=50;i<100;i++) {
            CoachLoc t=new CoachLoc();
            t.setLge(114.37d+(double)i/100);
            t.setLae(22.27d);
            t.setCoachId(1000004404+i);
            locationService.saveTeacher(t);
        	}
    	for(int i=100;i<150;i++) {
            CoachLoc t=new CoachLoc();
            t.setLge(114.37d);
            t.setLae(22.27d+(double)i/100);
            t.setCoachId(1000004404+i);
            locationService.saveTeacher(t);
        	}
    }
    @Test
    public void getNearBy()  throws Exception {
        SearchVo s=new SearchVo();
        s.setLge(113.949528d);
        s.setLae(22.556215d);
        s.setPageSkip(0);
        s.setPageLimit(10);
        List<CoachVo> tl=locationService.getNearBy(s);
        System.out.print(tl+"<<");
        System.out.print(BeanCopy.getFieldList(tl, "distance")+"<<");
    }
    
    @Test
    public void getNearBy2()  throws Exception {
		SearchVo search = new SearchVo();
		search.setLae(22.556223999913662);
		search.setLge(113.94952099999998);
		search.setPageSkip(0);
		search.setPageLimit(10*5);//分页大小*查询次数
		search.setDistance(7d);//教练查询的最大距离
    	List<CoachVo> tl=locationService.getNearBy(search);
    	System.out.print(tl+"<<");
    	System.out.print(BeanCopy.getFieldList(tl, "distance")+"<<");
    }
    
    
    
    
    public static void main(String []args)  throws Exception {
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:spring-init.xml");
        LocationService locationService=ac.getBean(LocationService.class);
        SearchVo s=new SearchVo();
        s.setLge(114.37d);
        s.setLae(22.27d);
        s.setPageSkip(10);
        s.setPageLimit(200);
        s.setDistance(50d);
        List<CoachVo> tl=locationService.getNearBy(s);
        List<Object> tlid=BeanCopy.getFieldList(tl, "coachId");
        System.out.print(tlid);
    }
}
