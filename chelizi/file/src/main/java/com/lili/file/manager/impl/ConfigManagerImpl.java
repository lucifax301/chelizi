package com.lili.file.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lili.common.util.BeanCopy;
import com.lili.common.util.redis.RedisUtil;
import com.lili.common.util.redis.RedisKeys.REDISKEY;
import com.lili.common.vo.ReqConstants;
import com.lili.common.vo.ReqResult;
import com.lili.file.dto.Config;
import com.lili.file.dto.ConfigExample;
import com.lili.file.manager.ConfigManager;
import com.lili.file.mapper.ConfigMapper;
import com.lili.file.vo.ConfigVo;

public class ConfigManagerImpl implements ConfigManager {
	private static Logger logger = LoggerFactory.getLogger(ConfigManagerImpl.class);
	@Autowired
	ConfigMapper configMapper;
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public ReqResult getConfigs(String userId, String userType, String mtime) {
		ReqResult r = ReqResult.getSuccess();
		Integer uType = Integer.parseInt(userType.trim());
		List<ConfigVo> cfgsVo = new ArrayList<ConfigVo>();
/*		if(uType == ReqConstants.USER_TYPE_COACH){
			cfgsVo = redisUtil.get(REDISKEY.CONFIGS_FOR_COACH);
		}else if(uType == ReqConstants.USER_TYPE_STUDENT){
			cfgsVo = redisUtil.get(REDISKEY.CONFIGS_FOR_STUDENT);
		}else {
			return ReqResult.getFailed();
		}*/
		
/*		if(null == cfgsVo){*/
			ConfigExample example = new ConfigExample();
			ConfigExample.Criteria criteria = example.createCriteria();
			List<Integer> types = new ArrayList<Integer>();
			types.add(0);//'0-所有用户；1-教练；2-学员'
			types.add(uType);
			criteria.andTypeIn(types);
			criteria.andIsdelEqualTo(ReqConstants.OPERATE_DELETE_NO);
			if(null != mtime && !"".equals(mtime)){
				Date da = new Date(Long.parseLong(mtime));
				criteria.andMtimeGreaterThan(da);
			}
			List<Config> cfgs = configMapper.selectByExample(example);
			if(null != cfgs && cfgs.size() >0){
				try {
					cfgsVo = BeanCopy.copyList(cfgs, ConfigVo.class, BeanCopy.COPYNOTNULL);
				} catch (Exception e) {
					logger.error("ConfigManagerImpl--getConfigs--"+ e);
				}
/*				if(uType == ReqConstants.USER_TYPE_COACH){
					redisUtil.set(REDISKEY.CONFIGS_FOR_COACH,cfgsVo);
				}else if(uType == ReqConstants.USER_TYPE_STUDENT){
					redisUtil.set(REDISKEY.CONFIGS_FOR_STUDENT,cfgsVo);
				}*/
			}
/*		}*/

		r.setData(cfgsVo);
		return r;
	}

}
