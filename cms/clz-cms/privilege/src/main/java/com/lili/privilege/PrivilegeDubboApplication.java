package com.lili.privilege;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PrivilegeDubboApplication {

	private static Logger log = Logger.getLogger(PrivilegeDubboApplication.class);
	
	public static void main(String[] args) {
		AbstractApplicationContext ctx = null;
		try {
			ctx = new ClassPathXmlApplicationContext(
					"spring-init.xml");
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
