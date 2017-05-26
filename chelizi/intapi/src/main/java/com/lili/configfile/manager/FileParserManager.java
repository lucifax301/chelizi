package com.lili.configfile.manager;

import com.lili.common.vo.ReqResult;

public interface FileParserManager {
	
	ReqResult parserFile(String path, String fileName);
}
