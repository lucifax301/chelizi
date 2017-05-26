package com.lili.school.manager.impl;

import com.lili.coach.dto.Trfield;
import com.lili.common.util.BeanCopy;
import com.lili.common.util.StringUtil;
import com.lili.common.util.redis.RedisKeys;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.common.vo.ResultCode;
import com.lili.school.dto.School;
import com.lili.school.dto.WechatComment;
import com.lili.school.dto.WechatEnrollOrder;
import com.lili.school.dto.WechatEnrollPackage;
import com.lili.school.manager.SchoolManager;
import com.lili.school.service.SchoolService;
import com.lili.school.vo.EnrollOrderVo;
import com.lili.student.dto.Student;
import com.lili.student.manager.StudentManager;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * SchoolManagerImpl Tester.
 *
 * @author ZLong
 * @version 1.0
 * @since <pre>六月 6, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class SchoolManagerImplTest {
    @Autowired
    private SchoolManager schoolManager;
    @Autowired
    private SchoolService schoolService;
    @Autowired
	private RedisUtil redisUtil;
    

	@Autowired
	private StudentManager studentManager;
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }
    

	@Test
	public void test() {
		try {
				
			schoolService.getDriveSchoolListNew(1.1, 1.1, "5", "100100");
			System.out.println("22");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]){
	    
        int array[] = { 1, 2, 3, 4, 5 };
        
        array=Arrays.copyOf(array, array.length+1);
        array[array.length-1]=6;
        
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i ++) {
        	sb.append(array[i] + ", ");
        }
        System.out.println(sb);
        int temp = 0; // 记录临时中间值   
        int size = array.length; // 数组大小   
        
        for (int i = 0; i < size - 1; i++) {   
        	for (int j = i + 1; j < size; j++) {   
        		if (array[i] < array[j]) { // 交换两数的位置   
        			temp = array[i];   
        			array[i] = array[j];   
        			array[j] = temp;   
        		}   
        	}   
        }   
        System.out.println("****************** temp :" + temp);
	}
	
	
}
