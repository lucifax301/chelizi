package com.lili.configfile.manager.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.vo.ReqResult;
import com.lili.configfile.manager.FileParserManager;
import com.lili.configfile.vo.ConfigFileVo;

/**
 * 通用文件解析
 * @author lzb
 *
 */

public class CommonFileParser implements FileParserManager {
	private Logger logger = LoggerFactory.getLogger(CommonFileParser.class);
	
    RedisUtil redisUtil;
	
	public CommonFileParser(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
	
	@Override
	public ReqResult parserFile(String path, String fileName) {
		ReqResult r = ReqResult.getSuccess();
		File file;
		String encoding = "GBK";
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try {
			logger.info("******************************** CommonFileParser  Start! Path :" + path  + fileName);
			file = new File(path + fileName);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				ConfigFileVo configFileVo = null;
				List<ConfigFileVo> configList = new ArrayList<ConfigFileVo>();
				String[] vList = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					logger.info("****************************** " + lineTxt.trim());
					if (lineTxt.trim() != null && !"".equals(lineTxt.trim())) { //再次过滤空行：防止出现空行情况
						vList = lineTxt.split("\\|");
					}
					if (vList.length > 0) {
						configFileVo = new ConfigFileVo();
						for (int i = 0; i < vList.length; i++) {
							logger.info("******************************* vList[" + i + "]: " + vList[i]);
							if (vList[i] == null) {
								vList[i]  = "";
							}
							if (i == 0) {
								configFileVo.setMemu(vList[i]);
							}
							else if (i == 1) {
								configFileVo.setType(vList[i]);
							}
							else if (i == 2) {
								configFileVo.setFileType(vList[i]);
							}
							else if (i == 3) {
								configFileVo.setFileId(vList[i]);
							}
							else if (i == 4) {
								configFileVo.setId(vList[i]);
							}
							else if (i == 5) {
								configFileVo.setName(vList[i]);
							}
							else if (i == 6) {
								configFileVo.setImg2xUrl(vList[i]);
							}
							else if (i == 7) {
								configFileVo.setImg3xUrl(vList[i]);
							}
							else if (i == 8) {
								configFileVo.setUrl(vList[i]);
							}
							else if (i == 9) {
								configFileVo.setDtime(vList[i]);
							}
							else if (i == 10) {
								configFileVo.setDescription(vList[i]);
							}
						}
						configList.add(configFileVo);
					}
				}
				
				r.setData(configList);
				
				read.close();
				
				//文件名称即为Key值，更新缓存
				fileName = fileName.replace("_", ".");
				String key = REDISKEY.CONFIG_FILE + fileName.substring(0, fileName.lastIndexOf("."));
				logger.info("********************************  Update key Start: " + key + ", configList : " + configList.size()+", redisUtil:  "+ redisUtil);
				redisUtil.set(key, configList);
				logger.info("******************************** Update key  Success End! key : " + key );
				
			} else {
				logger.error("****************************** 找不到指定的文件");
			}
		} catch (FileNotFoundException e) {
			logger.error("****************************** FileNotFoundException :" + e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error("****************************** UnsupportedEncodingException: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("****************************** IOException: " + e.getMessage());
			try {
				if(read != null){
					read.close();
				}
				if(bufferedReader != null){
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return r;
	}

}
