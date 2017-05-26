package com.lili.coupon.condition;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class ConditionManagerTest
{
    @Autowired
    //private StudentCouponMapper cConditionMapper;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    @Test
    public void testInit()
    {
        
        try
        {
//            CouponPage couponPage = new CouponPage(1, 10, 100000028, true);
//            List<StudentCoupon> allStudentCoupons = cConditionMapper.selectByPageCoupons(couponPage);
//            System.out.println(allStudentCoupons);
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
