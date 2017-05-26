package com.lili.share.manager;

public interface IShareManager {

	
	/**
	 * 根据接收人手机、优惠券模板查询是否已存在领取记录，当>0时表示已领取
	 * @param receviePhone
	 * @param recevieTemplate
	 * @return
	 */
	int queryIsExitShare(String receviePhone, String recevieTemplate);
	
	/**
	 * 更新获得的优惠券
	 * @param receviePhone
	 * @param recevieTemplate
	 * @param recevieCoupon
	 */
	void updateRecevieCoupon(String receviePhone, String recevieTemplate, Long recevieCoupon);
}
