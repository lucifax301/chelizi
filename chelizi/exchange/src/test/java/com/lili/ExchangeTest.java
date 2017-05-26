package com.lili;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lili.coach.vo.CoachVo;
import com.lili.common.util.BeanCopy;
import com.lili.exchange.dto.ExSubDTO;
import com.lili.exchange.service.ExchangeService;
import com.lili.exchange.service.impl.ExchangeServiceImpl;
import com.lili.exchange.vo.ExResult;
import com.lili.exchange.vo.ExSubPostVo;
import com.lili.location.service.LocationService;
import com.lili.location.vo.CoachLoc;
import com.lili.order.vo.SearchVo;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-init.xml")
public class ExchangeTest
{
	@Autowired
	private ExchangeService exchangeService;
	
	@Test
	public void test(){
		ExSubPostVo espVo = new ExSubPostVo();
		espVo.setPlatnum("chelizi");
		espVo.setSchoolId("123");
		espVo.setSchoolName("hhh");
		espVo.setFlowCode("123");
		espVo.setStuIdCard("123");
		espVo.setStuName("fff");
		espVo.setCoachIdCard("123");
		espVo.setCoachName("ddd");
		espVo.setVehicleNo("粤B");
		espVo.setRecnum("7");
		espVo.setSubjcode("222");
		espVo.setSubject("1");
		espVo.setStartTime("20160807120000");
		espVo.setEndTime("20160807123000");
		espVo.setDuration("30");
		espVo.setInputUserCode("321");
		espVo.setInputUserName("sss");
		espVo.setInputTime("20160807123000");
		espVo.setTerminalType("WEB");
		ExResult<ExSubDTO> result = exchangeService.postBookInfo(espVo);
		System.out.println(result.getMessage());
		System.out.println(result.getData());
	}
}
