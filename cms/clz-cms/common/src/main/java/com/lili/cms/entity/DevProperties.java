package com.lili.cms.entity;

/**
 * 不同环境下,不同的系统参数
 * @author hughes
 *
 */
public class DevProperties {

	/**
	 * 是否是本地开发部署,0--是,1--不是
	 */
	private String dev;

	public String getDev() {
		return dev;
	}

	public void setDev(String dev) {
		this.dev = dev;
	}
	
}
