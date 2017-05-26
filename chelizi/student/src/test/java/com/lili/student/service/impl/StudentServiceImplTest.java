package com.lili.student.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.student.service.StudentService;
import com.lili.student.vo.MycoachesVo;

/**
 * StudentServiceImpl Tester.
 *
 * @author ZLong
 * @version 1.0
 * @since <pre>六月 13, 2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-init.xml"})
public class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }
    
    @Test
    public void getStudent(){
//    	ReqResult r=studentService.getUserInfo("1","11");
//    	Student student=(Student)r.getResult().get("data");
//    	System.out.println("222");
    	MycoachesVo v1=new MycoachesVo();v1.setCoachId(1L);
    	List a=new ArrayList();a.add("1");a.add("2");
    	v1.setClasses(a);
    	
    	MycoachesVo v2=new MycoachesVo();v2.setCoachId(2L);
    	List b=new ArrayList();b.add("1");b.add("2");b.add("3");
    	v2.setClasses(b);
    	
    	MycoachesVo v3=new MycoachesVo();v3.setCoachId(3L);
    	
    	MycoachesVo v4=new MycoachesVo();v4.setCoachId(4L);
    	List<MycoachesVo> mc=new ArrayList();
    	mc.add(v1);
    	mc.add(v2);
    	mc.add(v3);
    	mc.add(v4);
    	
    	Collections.sort(mc,new Comparator<MycoachesVo>(){

			@Override
			public int compare(MycoachesVo arg0, MycoachesVo arg1) {
				if(arg0.getClasses()!=null){
					if(arg1.getClasses()!=null){
						if(arg0.getClasses().size()>arg1.getClasses().size()){
							return -1;
						}else{
							return 1;
						}
					}else{
						return -1;
					}
				}else{
					if(arg1.getClasses()!=null){
						return 1;
					}else{
						return 0;
					}
				}
				
			}

			
        	
        });
    	
    	for(int i=0;i<mc.size();i++){
    		System.out.println("============================="+mc.get(i).getCoachId()+" "+mc.get(i).getName());
    	}
    }
    
    
} 
