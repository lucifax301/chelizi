package com.lili.file.service;

import com.lili.common.vo.ReqResult;

public interface PosterService {
	/**
	 * 获取开机广告，客户端获取最新的一张。
	 * @param userType
	 * @return
	 */
	public ReqResult getPoster(String userType);
	

}
