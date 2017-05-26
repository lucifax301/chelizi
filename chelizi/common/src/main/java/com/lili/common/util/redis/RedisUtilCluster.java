package com.lili.common.util.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;

import redis.clients.jedis.BinaryJedisCluster;
import redis.clients.jedis.JedisCluster;

import com.lili.common.util.BeanCopy;
import com.lili.common.util.StringUtil;

@SuppressWarnings("rawtypes")
public class RedisUtilCluster
{
	private Logger log=Logger.getLogger(RedisUtilCluster.class);
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private BinaryJedisCluster binaryJedisCluster;
    
	@Resource(name="keySerializer")
    private RedisSerializer keySerializer;
    @Resource(name="valueSerializer")
    private RedisSerializer valueSerializer;
	
    @SuppressWarnings("unchecked")
	public <T> void set(final String key, final T value,final int liveSecond)
    {
    	try {
	    	if(StringUtil.isNullOrEmpty(key)|| value==null){
	    		return;
	    	}
	    	byte[] byteKey=keySerializer.serialize(key);
	    	byte[] byteValue=valueSerializer.serialize(value);
			binaryJedisCluster.set(byteKey, byteValue);
			if(liveSecond>0){
				binaryJedisCluster.expire(byteKey, liveSecond);
			}
    	} catch(Exception e){
       	  log.error(key + " set "+value+" Exception:"+e.getMessage(),e);   
        }	
    }
    public <T> void set(final String key, final T value)
    {
        set(key,value,0);
    }
    @SuppressWarnings("unchecked")
    public <T> T get(final String key)
    {
       T t=null;
       try {
	       if(StringUtil.isNullOrEmpty(key)) {
	    	   return null;
	       }
	       byte[] byteKey=keySerializer.serialize(key);
	       byte[] value=binaryJedisCluster.get(byteKey);
	       
	       if(value!=null) {
	    	   t=(T)valueSerializer.deserialize(value);
	       }
       } catch(Exception e){
    	 log.error(key + " get Exception:"+e.getMessage(),e);   
       }
       return t;
    }
    @SuppressWarnings("unchecked")
	public <T> void mset(final Map<String,T> mMap,final int liveSecond) {
    	try {
	    	if(mMap==null||mMap.isEmpty()){
	    		return;
	    	}
	    	Set<String> keySet=mMap.keySet();
	    	byte[][] keysvalues=new byte[keySet.size()*2][];
	    	Iterator<String> it=keySet.iterator();
	    	int i=0;
	    	while (it.hasNext()){
	    		String key=it.next();
	    		keysvalues[i++]=keySerializer.serialize(key);
	    		keysvalues[i++]=valueSerializer.serialize(mMap.get(key));
	    	}
	    	binaryJedisCluster.mset(keysvalues);
    	} catch (Exception e){
    		log.error(mMap + " mset Exception:"+e.getMessage(),e);
    	}
    }
    @SuppressWarnings("unchecked")
	public <T> List<T> mget(List<String> keyList){
    	List<T> list=new ArrayList<T>();
    	try {
	    	if(keyList==null || keyList.isEmpty()){
	    		return null;
	    	}
	    	byte[][] byteKey=new byte[keyList.size()][];
	    	for(int i=0;i<keyList.size();i++){
	    		byteKey[i]=keySerializer.serialize(keyList.get(i));
	    	}
	    	List<byte[]> byteValue=binaryJedisCluster.mget(byteKey);
	    	if(byteValue==null || byteValue.isEmpty()){
	    		return null;
	    	}
	    	for(int i=0;i<byteValue.size();i++){
	    		list.add((T)(valueSerializer.deserialize(byteValue.get(i))));
	    	}
	    } catch(Exception e){
	       	 log.error(keyList + " mget Exception:"+e.getMessage(),e);   
        }
    	return list;
    }

	public <T, S> List<T> mget(final String prefix, final List<S> ids) {
		if (ids == null || ids.isEmpty()) {
			log.error(ids + " is empty,so mget null.");
			return null;
		}
		List<String> keys = new ArrayList<String>();
		for (int i = 0; i < ids.size(); i++) {
			keys.add(prefix + ids.get(i));
		}
		return mget(keys);
	}

	public <T, S> Map<String, Object> mmget(final String prefix,
			final List<S> ids, String idField) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<T> list = mget(prefix, ids);
			if (list == null || list.isEmpty()) {
				result.put("success", new ArrayList<T>());
				result.put("fail", ids);
				return result;
			}
			List<S> succList = BeanCopy.getFieldList(list, idField);
			List<S> failList = new ArrayList<S>();
			for (S one : ids) {
				if (!succList.contains(one)) {
					failList.add(one);
				}
			}
			result.put("success", list);
			result.put("fail", failList);
		} catch (Exception e) {
			result.put("success", null);
			result.put("fail", ids);
		}
		return result;
	}
    
    @SuppressWarnings("unchecked")
	public void delete(final String key) {
    	if(StringUtil.isNullOrEmpty(key)){
    		return;
    	}
    	byte[] byteKey=keySerializer.serialize(key);
    	binaryJedisCluster.del(byteKey);
    }
    public boolean isExist(final String key, final String value){
    	Object redisValue=this.get(key);
    	if(redisValue!=null && redisValue.equals(value)){
    		return true;
    	}
    	return false;
    }
    
	public boolean isExist(final String key) {
		if(StringUtil.isNullOrEmpty(key)){
    		return false;
    	}
    	@SuppressWarnings("unchecked")
		byte[] byteKey=keySerializer.serialize(key);
    	return binaryJedisCluster.exists(byteKey);
	}
	public void expire(Set<String> keys,int seconds){
		
	}
}
