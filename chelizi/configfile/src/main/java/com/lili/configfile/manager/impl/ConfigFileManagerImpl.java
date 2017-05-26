package com.lili.configfile.manager.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.configfile.manager.ConfigFileManager;
import com.lili.configfile.manager.parser.AdFileParser;
import com.lili.configfile.manager.parser.CommonFileParser;
import com.lili.configfile.manager.parser.FileHandle;
import com.lili.configfile.manager.parser.ResourceListener;

public class ConfigFileManagerImpl implements ConfigFileManager {

	private Logger logger = LoggerFactory.getLogger(ConfigFileManagerImpl.class);

	@Autowired
    RedisUtil redisUtil;
	
	/**
	 * 文件名定义: 1/2_******_1/2.txt  头：1-教练；2-学员，尾：1-通用；2-微课
	 *  文件内容格式要求：每个值以|分割，如无值，也要有|线，即||
	 */
	@Override
	public ReqResult getConfigFileInfo(String path, String cityId, String userType, String key, String channel) {
		// 读取内容
		Integer handleType = 1;
		if (channel != null && !"".equals(channel)) {
			handleType = Integer.parseInt(channel);
		}
		String fileName = null;
		fileName = userType + "_" + cityId + "_" + channel + ".txt";
		logger.info("***************************** Path: " + path + ",fileName: " + fileName +  ",key: " + key + ",redisUtil: "+ redisUtil);
		
		ReqResult r = new ReqResult();
		FileHandle fileHandle = null;
		// 1 表示通用处理；2-微课
		switch (handleType) {
			case 1:
				fileHandle = new FileHandle(new CommonFileParser(redisUtil));
				r = fileHandle.parserFile(path, fileName);
				break;
			case 2:
				fileHandle = new FileHandle(new AdFileParser(redisUtil));
				r = fileHandle.parserFile(path, fileName);
				break;
			default:
				break;
		}
		
		//读取完后启动线程，如果线程已启，则不启动
		try {
			logger.info("*************************************** ResourceListener Start!");
			String isRun = null;
			isRun = redisUtil.get(REDISKEY.CONFIG_FILE + "working");
			if(isRun == null){
				logger.info("*********************************************** path :" + path);
				ResourceListener.addListener(path, redisUtil);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return r;
	}

}
