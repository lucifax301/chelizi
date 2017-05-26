package com.lili.share.service;

import com.lili.common.vo.ReqResult;

public interface GiftService {

	ReqResult getGiftCount();
	
	ReqResult getGiftRecent();

	ReqResult postGift(String name, String mobile, String couponId,
			String address);


}
