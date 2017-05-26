package com.lili.thirdparty.service;

import com.lili.common.vo.ReqResult;

public interface ThirdpartyService {

	public ReqResult getWeixinToken(String noncestr,String timestamp,String url);
}
