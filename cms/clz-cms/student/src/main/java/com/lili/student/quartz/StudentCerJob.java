package com.lili.student.quartz;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.student.manager.CMSStudentManager;

/**
 * 定时统计 学员的证件过期状态
 * @author Hughes
 *
 */
public class StudentCerJob {

	Logger access = Logger.getLogger(this.getClass());
	 
	@Autowired
	private CMSStudentManager cmsStudentManager;

	protected void work() {
		long count = 0;
		try {
			count = cmsStudentManager.updateLinState();
		} catch (Exception e) {
			access.error("|||exception happended when update student licence state,e : " + e.getMessage());
		}
		access.info("student job update  " + count + "  licence expire " );
	}
	
}
