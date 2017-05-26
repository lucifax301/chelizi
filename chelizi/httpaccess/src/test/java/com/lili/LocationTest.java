package com.lili;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.logic.service.OrderLogic;
import com.lili.order.service.OrderService;
import com.lili.order.vo.OrderVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class LocationTest
{
    @Autowired
    private OrderLogic orderLogic;
	@Autowired
	private OrderService orderService;
    @Before
    public void before() {
    
    }
    @Test
    public void findtest() throws Exception {
    	List<OrderVo> list = orderService.searchBmClass(202129,1066);
    	System.out.println("22"+list.size());
    }

    public static void main(String []args)  throws Exception {
       String comment="fsdfs你的号呀";
       String encode=URLEncoder.encode(comment, "utf-8");
       String decod=URLDecoder.decode(encode, "utf-8");
       System.out.println(decod);
    }
    
}
