package com.lili.cache;

import com.lili.common.util.redis.RedisUtil;

/**
 * 
 * @author devil
 *
 * @param <T>
 */
public class RedisCache<T> implements LiLiCacheInterface {

	
	
    public RedisUtil getRedisUtil() {
		return redisUtil;
	}

	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	protected RedisUtil redisUtil;
	
	@Override
	public void put(String key, Object value) {
		redisUtil.set(key, value);
	}

	@Override
	public Object get(String key) {
		return redisUtil.get(key);
	}

	@Override
	public void remove(String key) {
		redisUtil.delete(key);
	}

	
}
