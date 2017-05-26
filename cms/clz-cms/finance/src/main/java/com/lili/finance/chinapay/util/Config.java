package com.lili.finance.chinapay.util;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author gaoyan
 *
 */
public class Config {

	private static Config instance = null;
	
	private Properties properties = null;
	
	private Config() {
		init();
	}
	
	public static Config getInstance() {
		System.out.println("执行Config中的getInstance方法!");
		
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}
	
	/**
	 * 初始化配置文件
	 */
	public void init(){
		
		InputStream is = null;
		try{
			is = Config.class.getResourceAsStream("/res/cp_config.properties");
			properties = new Properties();
			properties.load(is);
			
			is.close();
		}catch (Exception e){
			throw new RuntimeException("Failed to get properties!");
		}
		finally{
			try {
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据key值取得对应的value值
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
}
