package com.lili.cache;

public interface LiLiCacheInterface<T> {
	
	public void put(String key,T value);
	
	public T get(String key);
	
	public void remove(String key);
}
