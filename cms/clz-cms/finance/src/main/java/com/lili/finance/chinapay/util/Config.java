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
		System.out.println("ִ��Config�е�getInstance����!");
		
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}
	
	/**
	 * ��ʼ�������ļ�
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
	 * ����keyֵȡ�ö�Ӧ��valueֵ
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
