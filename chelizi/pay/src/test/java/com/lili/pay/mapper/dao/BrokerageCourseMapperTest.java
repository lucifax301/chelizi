package com.lili.pay.mapper.dao;

import com.lili.pay.dto.BrokerageCourse;
import com.lili.pay.mapper.dao.BrokerageCourseMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ZLong on 2016/五月/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class BrokerageCourseMapperTest {

    @Autowired
    private BrokerageCourseMapper brokerageCourseMapper;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSelectRightBrokerage() {
        BrokerageCourse bc = new BrokerageCourse();
        bc.setRegionId(100100);
        bc.setCourseTmpId(1);
        bc.setDltype((byte) 1);
        bc.setDateRule("3");
        BrokerageCourse result = brokerageCourseMapper.selectRightBrokerage(bc);
        System.out.println(result);
    }

}
