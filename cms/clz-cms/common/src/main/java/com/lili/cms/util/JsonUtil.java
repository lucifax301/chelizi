package com.lili.cms.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * JSON封装处理
 * @author lzb
 *
 */
public class JsonUtil {

	/**
	 * 将Map转成JSON
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map<String, Object> map) {
		Set<String> keys = map.keySet();
		String key = "";
		Object value = "";
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = (String) it.next();
			value = map.get(key);
			jsonBuffer.append("\"" + key + "\":" + "\"" + value + "\"");
			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}");
		return jsonBuffer.toString();
	}


}
