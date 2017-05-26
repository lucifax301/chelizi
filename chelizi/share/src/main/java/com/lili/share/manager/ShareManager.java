/**
 * 
 */
package com.lili.share.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.share.dao.mapper.ShareMapper;
import com.lili.share.dao.po.SharePo;

public class ShareManager implements IShareManager{
	Logger logger = LoggerFactory.getLogger(ShareManager.class);

	@Autowired
	private ShareMapper shareMapper;

	@Override
	public int queryIsExitShare(String receviePhone, String recevieTemplate) {
		logger.info("********************************Query Is Exit Share Start! receviePhone: " + receviePhone + ",recevieTemplate" + recevieTemplate);
		Integer size = 0;
		try {
			size = shareMapper.queryIsExitShare(receviePhone, recevieTemplate);
		} catch (Exception e) {
			logger.info("********************************QueryIsExitShare Error: " + e.getMessage());
		}
		
		return size;
	}

	@Override
	public void updateRecevieCoupon(String receviePhone, String recevieTemplate, Long recevieCoupon) {
		try {
			logger.info("********************************Update RecevieCoupon Start!  receviePhone: " + receviePhone + ",recevieTemplate" + recevieTemplate+ ",recevieCoupon: " + recevieCoupon);
			SharePo search = new SharePo();
			search.setReceviePhone(receviePhone);
			search.setRecevieTemplate(recevieTemplate);
			SharePo sharePo = new SharePo();
			sharePo.setRecevieCoupon(recevieCoupon);
			shareMapper.updateNotNullByObject(sharePo, search);
			logger.info("********************************Update RecevieCoupon Success!");
		} 
		catch (Exception e) {
			logger.error("*******************************Update RecevieCoupon Error: " + e.getMessage());
		}
	}
    
}
