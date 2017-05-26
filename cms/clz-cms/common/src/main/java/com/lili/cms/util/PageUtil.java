package com.lili.cms.util;

import com.github.pagehelper.PageHelper;

/**
 * 分页工具类
 * @author hughes
 *
 */
public class PageUtil {

	/**
	 * 默认请求页数
	 */
	public static final int DEFAULT_PAGE_NO = 1;
	
	/**
	 * 默认每页数据条数
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	

	/**
	 * 预备分页操作
	 * 该方法只会将之后的第一次查询做分页处理
	 * @param params
	 */
	public static void startPage(int pageNo,int pageSize) {
		//startPage是告诉拦截器说我要开始分页了。分页参数是这两个。
		PageHelper.startPage((pageNo<=0)?DEFAULT_PAGE_NO:pageNo
								,(pageSize<=0)?DEFAULT_PAGE_SIZE:pageSize);  
	}
	
	public static void startPage(int pageNo,int pageSize,String orderBy) {
		PageHelper.orderBy(orderBy);
		//startPage是告诉拦截器说我要开始分页了。分页参数是这两个。
		PageHelper.startPage((pageNo<=0)?DEFAULT_PAGE_NO:pageNo
								,(pageSize<=0)?DEFAULT_PAGE_SIZE:pageSize);  
	}
}
