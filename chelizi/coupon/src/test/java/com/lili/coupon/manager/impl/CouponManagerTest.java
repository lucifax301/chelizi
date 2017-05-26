package com.lili.coupon.manager.impl;

import com.lili.coupon.dto.StudentCoupon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * CouponManagerImpl Tester.
 *
 * @author ZLong
 * @version 1.0
 * @since <pre>五月 4, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class CouponManagerTest {

    @Autowired
    private CouponManager couponManager;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getStudentAllCouponByPage(long studentId, int page, boolean isExist)
     */
    @Test
    public void testGetStudentAllCouponByPage() throws Exception {
        //TODO: Test goes here...
        List<StudentCoupon> data = couponManager.getStudentAllCouponByPage(100115198, 1, true, true);
        System.out.println(data);
    }
}
