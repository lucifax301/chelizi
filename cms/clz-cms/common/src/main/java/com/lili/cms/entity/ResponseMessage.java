package com.lili.cms.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.lili.cms.constant.HttpConstant;
import com.lili.cms.util.DateUtil;
import com.lili.cms.util.GsonUtil;

/**
 * 业务与接入层之间传递的实体
 * 返回给前端信息的封装
 * @author Hughes
 *
 */
public class ResponseMessage<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private Map<String, Object> result = new HashMap<>();
	
	private T data;
	
	/**
	 * 错误码
	 */
	private int code;
	
	/**
	 * 返回给前端的信息
	 */
	private String msg;
	
	
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public ResponseMessage(){
		this.code = HttpConstant.SUCCESS_CODE;
		this.msg = HttpConstant.SUCCESS_MSG;
	}
	
	public ResponseMessage(String msg){
		this.code = HttpConstant.ERROR_CODE;
		this.msg = msg;
	}

	public ResponseMessage(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

	public String getMsg() {
		return msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ResponseMessage addPagedResult(Object value){
		result.put("pageData", GsonUtil.serialNulls(value));
		return this;
	}
	

	public ResponseMessage addPagedStrResult(String value){
		result.put("pageData", value);
		return this;
	}
	
	public ResponseMessage addResult(String key, Object value){
		result.put(key, GsonUtil.serialNulls(value));
		return this;
	}
	

	public ResponseMessage addResult(String key, String value){
		result.put(key, value);
		return this;
	}
	
	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param filter 字符串数组,过滤条件,添加对象需要过滤的字段名
	 * @return
	 */
	public ResponseMessage addResult(String key, Object value,String[] filter){
		result.put(key, GsonUtil.serialFilterNulls(value,filter));
		return this;
	}
	
	public String build(){
        JsonObject innerObject = new JsonObject();
        JsonObject resultObject = new JsonObject();

        for (Map.Entry<String, Object> entry : result.entrySet()) {
        	resultObject.addProperty(entry.getKey(), entry.getValue()==null?"":entry.getValue().toString());
        }
        innerObject.add("result", resultObject);
        innerObject.addProperty("ts", DateUtil.getCurrentDateTime());
        innerObject.addProperty("code", code);
        innerObject.addProperty("msg", msg);
		return GsonUtil.serialNulls(innerObject);
	}
	
	public String buildRsp(String status, String msg, Integer code){
		JsonObject innerObject = new JsonObject();
		JsonObject resultObject = new JsonObject();
		
		for (Map.Entry<String, Object> entry : result.entrySet()) {
			resultObject.addProperty(entry.getKey(), entry.getValue()==null?"":entry.getValue().toString());
		}
		innerObject.add("result", resultObject);
		innerObject.addProperty("ts", DateUtil.getCurrentDateTime());
		innerObject.addProperty("status", status);
		innerObject.addProperty("msg", msg);
		innerObject.addProperty("code", code);
		return build(innerObject);
	}
	
	public String build(Object obj){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(obj);
	}
	
}
