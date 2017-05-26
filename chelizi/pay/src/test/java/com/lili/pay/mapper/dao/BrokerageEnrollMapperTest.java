package com.lili.pay.mapper.dao;

import com.lili.pay.dto.BrokerageEnroll;
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
public class BrokerageEnrollMapperTest {

    @Autowired
    private BrokerageEnrollMapper brokerageEnrollMapper;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testSelectRightBrokerage() {
        BrokerageEnroll be = new BrokerageEnroll();
        be.setRegionId(100100);
        be.setSchoolId(1);
        BrokerageEnroll result = brokerageEnrollMapper.selectRightBrokerage(be);
        System.out.println(result);
    }
}
