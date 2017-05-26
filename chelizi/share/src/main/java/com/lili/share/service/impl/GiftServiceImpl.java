package com.lili.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.vo.ReqResult;
import com.lili.share.manager.GiftManager;
import com.lili.share.service.GiftService;

public class GiftServiceImpl implements GiftService {
	@Autowired
	private GiftManager giftManager;
	
	@Override
	public ReqResult getGiftCount() {
		return giftManager.getGiftCount();
	}

	@Override
	public ReqResult getGiftRecent() {
		return giftManager.getGiftRecent();
	}

	@Override
	public ReqResult postGift(String name, String mobile, String couponId,
			String address) {
		return giftManager.saveGift(name,mobile,couponId,address);
	}

}
