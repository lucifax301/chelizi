package com.lili.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExamPlaceDubboApplication {
	private static Logger log = LoggerFactory.getLogger(ExamPlaceDubboApplication.class);
	public static void main(String[] args) {
		AbstractApplicationContext ctx = null;
		try {
			ctx = new ClassPathXmlApplicationContext("spring-init.xml");
			log.info("服务器启动成功***********************************");
			synchronized (ctx) {
				ctx.wait();
			}
		} catch (Exception ex) {
			log.error("服务器启动失败*********************************", ex);
			if (ctx != null) {
				ctx.destroy();
			}
			System.exit(1);
		}
	}

}
