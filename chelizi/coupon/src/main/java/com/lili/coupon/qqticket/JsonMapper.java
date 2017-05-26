package com.lili.coupon.qqticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class JsonMapper {
	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	public static ObjectMapper getInstance() {
		return mapper;
	}

	public static ObjectNode createObjectNode() {
		return mapper.createObjectNode();
	}

	public static ArrayNode createArrayNode() {
		return mapper.createArrayNode();
	}

	public static <T> T parseJson(String jsonText, Class<T> clazz) throws IOException {
		return mapper.readValue(jsonText, clazz);

	}

	public static String toJsonText(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}
}
