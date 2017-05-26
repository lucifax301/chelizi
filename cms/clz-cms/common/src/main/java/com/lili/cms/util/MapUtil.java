package com.lili.cms.util;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Map工具类
 * @author Hughes
 *
 */
public class MapUtil {

	/**
	 * 判断map中,对应key的值是否为空
	 * @param params
	 * @param key
	 * @return
	 */
	public static boolean containsNull(Map<String, Object> params,String key){
		if(StringUtil.isNull(params.get(key)))
			return true;
		return false;
	}
	
	public static String getStringVal(Map<String, Object> params,String key){
		if(StringUtil.isNull(params.get(key)))
			return "";
		return params.get(key).toString();
	}

	public static Integer getIntegerVal(Map<String, Object> params,String key){
		if(StringUtil.isNull(params.get(key)))
			return 0;
		return StringUtil.stringToInteger(params.get(key).toString());
	}


	public static Long getLongVal(Map<String, Object> params,String key){
		if(StringUtil.isNull(params.get(key)))
			return 0L;
		return StringUtil.stringToLong(params.get(key).toString());
	}
	

	public static Date getDateVal(Map<String, Object> params,String key) throws ParseException{
		if(StringUtil.isNull(params.get(key)))
			return DateUtil.now();
		return DateUtil.parseDate(params.get(key).toString());
	}
}
