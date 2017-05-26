package com.lili.student.service.impl;

import com.lili.common.util.Page;
import com.lili.common.vo.ReqResult;
import com.lili.student.service.RechargeService;
import com.lili.student.service.StudentVipService;
import com.lili.student.vo.RechargeRecordVo;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * StudentVipServiceImpl Tester.
 *
 * @author ZLong
 * @version 1.0
 * @since <pre>六月 14, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class StudentVipServiceImplTest {

    @Autowired
    private StudentVipService studentVipService;
    
    @Autowired
    private RechargeService rechargeService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getStudentVip(int vipId)
     */
    @Test
    public void getStudentVipByStudentId() throws Exception {
    	 System.out.println("22");
    	 
    //	 Page<RechargeRecordVo> count=rechargeService.countRechargeRecord(null, null, null, null, null, null, null,null, 1, 10, false,2);	
    //	List<RechargeRecordVo>  p=rechargeService.getRechargeRecord(null, null, null, null, null, null, null, null,null, 1, 10, false,2);	
    	 List<Integer> rrids=new ArrayList<>();
    	 rrids.add(206);
    	 rrids.add(210);
    	 
    	 rechargeService.passManualRechargeRecord(rrids,22);
        System.out.println("22");
    }

    /**
     * Method: getChargeDiscountListStudentId(long studentId)
     */
//    @Test
//    public void testGetChargeDiscountListStudentId() throws Exception {
//        ReqResult r = studentVipService.getChargeDiscountListStudentId(100080053L);
//        System.out.println(r);
//    }
//
//    /**
//     * Method: updateVipStudentVipPackageId(String mobile, String vipPackageId)
//     */
//    @Test
//    public void testUpdateVipStudentVipPackageId() throws Exception {
//        ReqResult r = studentVipService.updateVipStudentVipPackageId("18813687076", "4d6e7c3da6fd424c845b729e0149a533");
//        System.out.println(r);
//    }
}
