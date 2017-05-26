package com.lili.share.service;

import com.lili.common.vo.ReqResult;

/**
 * 分享接口
 * 
 * @author lzb
 *
 */
public interface IShareService {

	/**
	 * 查看自己的分享记录
	 * @param userId
	 * @param userType
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	ReqResult getMineShareList(String userId, String userType, String pageSize, String pageIndex);
	
}
