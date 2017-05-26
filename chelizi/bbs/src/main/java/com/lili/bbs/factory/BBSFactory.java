package com.lili.bbs.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.bbs.action.BBSAction;
import com.lili.bbs.action.CoachBBSAction;
import com.lili.bbs.action.StudentBBSAction;

/**
 * 工厂处理：学员、教练分流
 * 
 * @author lzb
 *
 */
public class BBSFactory {

	private static Logger logger = LoggerFactory.getLogger(BBSFactory.class);
	
	@Autowired
	StudentBBSAction studentBBSAction;
	
	@Autowired
	CoachBBSAction coachBBSAction;
	
	/**
	 * 拿到发起对应渠道接口
	 * @param userType
	 * @return
	 */
	public BBSAction getBBSAction(Integer userType) {
		BBSAction bbsAction = null;
		try {
			switch (userType) {
			case 1:
				bbsAction = coachBBSAction;
				break;
			case 2:
				bbsAction = studentBBSAction;
				break;
			default:
				break;
			}
			return bbsAction;
		} 
		catch (Exception e) {
			logger.error("******************************* userType : " + userType, e);
		}

		return bbsAction;
	}

}
