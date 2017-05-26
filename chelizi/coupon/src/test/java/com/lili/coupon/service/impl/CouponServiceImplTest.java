package com.lili.coupon.service.impl;

import com.lili.common.vo.ReqResult;
import com.lili.coupon.dto.CCondition;
import com.lili.coupon.service.CouponService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CouponServiceImpl Tester.
 *
 * @author ZLong
 * @version 1.0
 * @since <pre>五月 11, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class CouponServiceImplTest {

    @Autowired
    private CouponService couponService;

    @After
    public void after() throws Exception {
    }

    @Before
    public void before() throws Exception {
    }

    @Test
    public void testExchangeCouponByCdKey() {
        ReqResult result = couponService.exchangeCouponByCdKey(100000004L, "111000000011", 1);
    }

    @Test
    public void testAddCondition() {
        CCondition condition = new CCondition();
        condition.setType((byte) 3);
        condition.setParam1("1");
        condition.setParam2("2");
        condition.setDescri("测试用的");
        ReqResult result = couponService.addCondition(condition);
        System.out.println(result);
    }

    /**
     * Method: obtainCouponByPhone(String phoneNum, String couponTmpId)
     */
    @Test
    public void testObtainCouponByPhone() throws Exception {
        ReqResult result = couponService.obtainCouponByPhone("13560752595", "930b9ebb74d541439fcbbebc4f76f54f");
        System.out.println(result);
    }
} 
