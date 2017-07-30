package com.lili.exam.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.exam.dto.ExamPlacePayOrder;
import com.lili.exam.manager.ExamPlaceOrderManager;


public class ExamQuartz {

	private Logger log = Logger.getLogger(ExamQuartz.class);
	
	@Autowired
	private ExamPlaceOrderManager examPlaceOrderManager;
	
	public void doQuartz(){
		List<ExamPlacePayOrder> orders= examPlaceOrderManager.getUnpayOrder();
		
		for(ExamPlacePayOrder order:orders){
			examPlaceOrderManager.expireOrder(order);
		}
	}
}
