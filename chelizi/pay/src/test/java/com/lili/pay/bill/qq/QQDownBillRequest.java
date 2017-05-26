/**
 * 
 */
package com.lili.pay.bill.qq;

import com.tencent.common.HttpUtil;
import com.tencent.common.Util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

/**
 * @author linbo
 *
 */
public class QQDownBillRequest {
	public String sendPost(String api_url, Map<String, String> params) throws UnrecoverableKeyException, KeyManagementException,
    NoSuchAlgorithmException, KeyStoreException, IOException
	{

		//QQ钱包直接用GET方式
		Util.log("API，POST过去的数据是：");
		Util.log(params);

		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		String response = HttpUtil.doGet(api_url, params, "gb2312");
		return response;
	}
}
