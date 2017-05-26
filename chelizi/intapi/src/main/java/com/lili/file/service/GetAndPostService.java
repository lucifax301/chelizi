package com.lili.file.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import com.alibaba.fastjson.JSONObject;

public interface GetAndPostService {
	
	 public JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr);
	
	 public String sendPost(String url, Object param);
	 
	 public String getHttpURL(String path,String method) throws MalformedURLException ,ProtocolException, IOException;
}
