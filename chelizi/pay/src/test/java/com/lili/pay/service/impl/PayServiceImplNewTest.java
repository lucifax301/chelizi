package com.lili.pay.service.impl;

import com.lili.common.vo.ReqConstants;
import com.lili.order.service.OrderService;
import com.lili.pay.service.PayServiceNew;
import com.lili.pay.vo.PayVo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * PayServiceImplNew Tester.
 *
 * @author ZLong
 * @version 1.0
 * @since <pre>五月 21, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class PayServiceImplNewTest {

    @Autowired
    private PayServiceNew payServiceNew;
    @Autowired
    private OrderService orderService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }
    /**
     * Method: pay(PayVo pay)
     */
    @Test
    public void testPay() throws Exception {
    //	PayVo pvo = new PayVo(1,2,50,"weixin","1",6,0,0,"");
   // 	payServiceNew.pay(pvo);
    	orderService.updatePayState("1",ReqConstants.STAGE_STATE_SUCC,"zhifubao");
    	System.out.println("ww");
    }


} 
