package com.lili.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadTruck {

	private static ThreadLocal<ThreadTruck> th = createThreadTruck();
	
	private Map<Object,Object> map = new ConcurrentHashMap<Object,Object>();
	
	public static ThreadTruck current(){
		return th.get();
	}
	
	public static void clear(){
		th.remove();
	}
	
	public static void put(Object key,Object value){
		current().map.put(key, value);
	}
	
	public static void remove(Object key){
		current().map.remove(key);
	}
	
	public static boolean exists(Object key){
		return current().map.containsKey(key);
	}
	
	public static Object get(Object key){
		return current().map.get(key);
	}
	
	private static ThreadLocal<ThreadTruck> createThreadTruck(){
		return new ThreadLocal<ThreadTruck>(){
			protected ThreadTruck initialValue(){
				return new ThreadTruck();
			}
		};
	}
}