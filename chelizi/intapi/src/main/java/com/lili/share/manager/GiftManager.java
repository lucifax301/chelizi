package com.lili.share.manager;

import com.lili.common.vo.ReqResult;

public interface GiftManager {

	public ReqResult getGiftCount();

	public ReqResult getGiftRecent();

	public ReqResult saveGift(String name, String mobile, String couponId,
			String address);

}
