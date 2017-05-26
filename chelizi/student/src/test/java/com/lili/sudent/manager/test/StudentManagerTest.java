package com.lili.sudent.manager.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.common.util.Page;
import com.lili.student.dto.Student;
import com.lili.student.manager.MycoachesManager;
import com.lili.student.manager.StudentManager;
import com.lili.student.service.RechargeService;
import com.lili.student.vo.RechargeGearVo;
import com.lili.student.vo.RechargePlanVo;
import com.lili.student.vo.RechargeRecordVo;
import com.lili.student.vo.VipCompanyVo;
import com.lili.student.vo.VipCustomVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-init.xml"})
public class StudentManagerTest {

	@Autowired
	private StudentManager studentManager;
	@Autowired
	private MycoachesManager mycoachesManager;

	@Test
	public void test() {
		int num = mycoachesManager.countById(2752543, 3902);
	    System.out.println(num);
	
	}

}
