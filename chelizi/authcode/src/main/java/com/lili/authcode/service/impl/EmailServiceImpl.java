package com.lili.authcode.service.impl;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.authcode.service.EmailService;
import com.lili.common.vo.ReqConstants;

public class EmailServiceImpl implements EmailService {
	private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	@Resource(name="authcodeProp")
	private Properties authcodeProp;
	
	ExecutorService threadPool = Executors.newCachedThreadPool();

	@Override
	public void send(final String subject, final String msg) {
		String mailEnv = authcodeProp.getProperty("mail.env");
		logger.debug("EmailServiceImpl mailEnv: " + mailEnv);
		if(mailEnv.equals(ReqConstants.ENV_TEST)){
			logger.debug("EmailServiceImpl subject: " + subject + ",msg:"+msg);
			return;
		}
		threadPool.execute(new Runnable() {
			public void run() {
				final SimpleEmail email = new SimpleEmail();
				email.setHostName(authcodeProp.getProperty("mail.hostName"));
				email.setAuthentication(authcodeProp.getProperty("mail.user"), authcodeProp.getProperty("mail.pwd"));
				email.setCharset("utf-8");
				try {
					email.setFrom(authcodeProp.getProperty("mail.from"));
					String to = authcodeProp.getProperty("mail.to");
					String[] tos= to.split(";");
					for(int i=0;i<tos.length;i++){
						email.addTo(tos[i]);
					}
			        email.setSubject(subject);
			        email.setMsg(msg);
			        email.send();
				} catch (EmailException e) {
					e.printStackTrace();
				}
			}
		});

	}

}
