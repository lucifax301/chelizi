package com.lili.coupon.manager.impl;

import com.lili.coupon.dto.Ticket;
import com.lili.student.dto.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * QQTicketManager Tester.
 *
 * @author ZLong
 * @version 1.0
 * @since <pre>五月 17, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class QQTicketManagerTest {

    @Autowired
    private QQTicketManager qqTicketManager;

    @After
    public void after() throws Exception {
    }

    @Before
    public void before() throws Exception {
    }

    /**
     * Method: useTicket(Ticket ticket)
     */
    @Test
    public void testUseTicket() throws Exception {
        //TODO: Test goes here...
        Ticket ticket = qqTicketManager.findQQTicketByCode("222");
        Student student = new Student();
        student.setStudentId(100000004L);
        qqTicketManager.useTicket(ticket, student);
    }
} 
