package com.lili.configfile.manager;

import com.lili.common.vo.ReqResult;

public interface ConfigFileManager {

	/**
     * 根据查询条件获取配置文件信息
	 * @param type 
     * @param id
     * @return
     */
	ReqResult getConfigFileInfo(String path, String cityId, String userType, String key, String channel);
}
