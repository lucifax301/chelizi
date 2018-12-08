package com.lili.pay.config;

import java.util.HashMap;
import java.util.Map;

public class ExamPayConfig {

	public Map<String,WXPayConfig> configs = new HashMap<String,WXPayConfig>();

	public Map<String, WXPayConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(Map<String, WXPayConfig> configs) {
		this.configs = configs;
	}
	
	public WXPayConfig getConfig(String key){
		return this.configs.get(key);
	}
}
