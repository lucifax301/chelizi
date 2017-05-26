package com.lili.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author devil
 *
 * @param <T>
 */
public class SimpleCache<T> implements LiLiCacheInterface{

	private ConcurrentHashMap<String,Object> cache=new ConcurrentHashMap();

	@Override
	public void put(String key, Object value) {
		cache.put(key, value);
	}

	@Override
	public Object get(String key) {
		return cache.get(key);
	}

	@Override
	public void remove(String key) {
		cache.remove(key);
	}
	
	
}
