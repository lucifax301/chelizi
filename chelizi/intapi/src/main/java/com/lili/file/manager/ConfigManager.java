package com.lili.file.manager;

import com.lili.common.vo.ReqResult;

public interface ConfigManager {

	ReqResult getConfigs(String userId, String userType, String mtime);

}
