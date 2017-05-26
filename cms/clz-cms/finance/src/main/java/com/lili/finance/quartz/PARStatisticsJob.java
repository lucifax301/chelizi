package com.lili.finance.quartz;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.finance.service.ICMSPARService;

public class PARStatisticsJob {

	Logger access = Logger.getLogger(this.getClass());
	 
	@Autowired
	private ICMSPARService cmsPARService;

	protected void work() {
		long count = cmsPARService.addOne();
		access.info("par job insert  " + count + " data" );
	}
	
}
