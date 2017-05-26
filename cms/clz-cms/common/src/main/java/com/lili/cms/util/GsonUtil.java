package com.lili.cms.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 基于Google  GSON封装的工具类
 * @author Hughes
 *
 */
public class GsonUtil {

	
	/**
	 * 传入对象的空字段值会以 null-field:'' 形式在JSON串显示,而不是在生成JSON串时忽略这个空字段
	 * @param obj
	 * @return
	 */
	public static String serialNulls(Object obj){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(obj==null?"":obj);
	}

	/**
	 * 
	 * @param obj
	 * @param filter 字符串数组,过滤条件,添加对象需要过滤的字段名
	 * @return
	 */
	public static String serialFilterNulls(Object obj,String[] filter){
        Gson gson =  new GsonBuilder().serializeNulls().setExclusionStrategies(new JsonFiledFilter(filter)).create();
        return gson.toJson(obj==null?"":obj);
	}
	
}

class JsonFiledFilter implements ExclusionStrategy {
	String[] keys;

	public JsonFiledFilter(String[] keys) {
		this.keys = keys;
	}

	@Override
	public boolean shouldSkipClass(Class<?> arg0) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes arg0) {
		for (String key : keys) {
			if (key.equals(arg0.getName())) {
				return true;
			}
		}
		return false;
	}

}