package com.lili.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisUtil
{
    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    public <T> void set(final String key, final T value,final int liveSecond)
    {
        redisTemplate.execute(new RedisCallback<T>()
        {
            @SuppressWarnings("unchecked")
			
            public T doInRedis(RedisConnection connection) throws DataAccessException
            {
                connection.set(redisTemplate.getStringSerializer().serialize(key),
                        ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
                if (liveSecond > 0) {
                    connection.expire(redisTemplate.getStringSerializer().serialize(key), liveSecond);
                }
                return null;
            }
        });
    }
    public <T> void set(final String key, final T value)
    {
        set(key,value,0);
    }
    
    public <T> T get(final String key)
    {
        return redisTemplate.execute(new RedisCallback<T>()
        {
            @SuppressWarnings("unchecked")
			
            public T doInRedis(RedisConnection connection) throws DataAccessException
            {
                byte[] keys = redisTemplate.getStringSerializer().serialize(key);
                byte[] value = connection.get(keys);
                T r=null;
                if (value!=null)
                {
                	r=((RedisSerializer<T>)redisTemplate.getValueSerializer()).deserialize(value);
                }
                return r;
            }
        });
    }
    
    /**
     * 批量设置缓存
     * @param aa
     */
    public <T> void mset(final Map<String,T> cacheMap,final int liveSecond)
    {
        redisTemplate.execute(new RedisCallback<T>()
        {
            @SuppressWarnings("unchecked")
			
            public T doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(null == cacheMap || cacheMap.size()==0){
            		return null;
            	}
            	Map<byte[],byte[]> byteMap = new HashMap<byte[], byte[]>();
            	Iterator<Entry<String, T>> it = cacheMap.entrySet().iterator();
            	while(it.hasNext()){
            		Entry<String, T> entry = it.next();
            		byteMap.put(redisTemplate.getStringSerializer().serialize(entry.getKey()), 
            				((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(entry.getValue()));
            	}
            	if(liveSecond>0){
            		connection.openPipeline();
            		connection.mSet(byteMap);
            		Iterator<byte[]> itKey=byteMap.keySet().iterator(); 
            		while(itKey.hasNext()){
            			connection.expire(itKey.next(), liveSecond);
            		}
            		connection.closePipeline();
            	} else {
            		connection.mSet(byteMap);
            	}
            	return null;
            }
        });
    }
    public <T> void mset(final Map<String,T> cacheMap){
    	this.mset(cacheMap, 0);
    }
    /**
     * 批量查询多个缓存数据
     * @param ks
     * @return
     */
    public <T> List<T> mget(final List<String> keyList)
    {
    	return redisTemplate.execute(new RedisCallback<List<T>>()
    			{
		    		@SuppressWarnings("unchecked")
		    		
		    		public List<T> doInRedis(RedisConnection connection) throws DataAccessException
		    		{
		    			if(keyList==null || keyList.isEmpty()){
		    				return null;
		    			}
		    			byte[][] kk = new byte[keyList.size()][];
		    			for(int i=0;i<keyList.size();i++){
		    				kk[i]= redisTemplate.getStringSerializer().serialize(keyList.get(i));
		    			}
		    			List<byte[]> value = connection.mGet(kk);
		    			List<T> r=new ArrayList<T>();
		    			if (value!=null && value.size()>0)
		    			{
		    				for(int i=0;i<value.size();i++){
		    					T a=((RedisSerializer<T>)redisTemplate.getValueSerializer()).deserialize(value.get(i));
		    					r.add(a);
		    				}
		    			}
		    			return r;
		    		}
    			});
    }

    /**
     * 批量查询多个缓存数据，查询到的放在第一个字段里面，未查询到的key放在第二个字段里面
     * @param ks 待查询的多个key，用空格分隔后拼接的字符串
     * @return
     */
    public <T,S> Map<String,Object> mmget(final String prefix,final List<S> ids, String idField)
    {
    	return redisTemplate.execute(new RedisCallback<Map<String,Object>>()
    			{
		    		@SuppressWarnings("unchecked")
		    		
		    		public Map<String,Object> doInRedis(RedisConnection connection) throws DataAccessException
		    		{
		    			byte[][] kk = new byte[ids.size()][];
		    			for(int i=0;i<ids.size();i++){
		    				String k = prefix+ids.get(i);
		    				byte[] ks = redisTemplate.getStringSerializer().serialize(k);
		    				kk[i]= ks;
		    			}
		    			List<byte[]> value = connection.mGet(kk);
		    			//存放查询成功的数据
		    			List<T> a=new ArrayList<T>();
		    			//存放未查询到数据的key
		    			List<S> b = new ArrayList<S>();
		    			if (value!=null && value.size()>0)
		    			{
		    				for(int i=0;i<value.size();i++){
		    					T c=((RedisSerializer<T>)redisTemplate.getValueSerializer()).deserialize(value.get(i));
		    					if(null != c){
		    						a.add(c);
		    					}else{
		    						b.add(ids.get(i));
		    					}
		    					
		    				}
		    			}
		    			Map<String,Object> data = new HashMap<String, Object>();
		    			data.put("success", a);
		    			data.put("fail", b);
		    			return data;
		    		}
    			});
    }

    public void delete(final String key)
    {
        redisTemplate.execute(new RedisCallback<Object>()
        {
            
            public Object doInRedis(RedisConnection connection) throws DataAccessException
            {
                byte[] keys = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keys))
                {
                    connection.del(keys);
                }
                return null;
            }
        });
    }

    public boolean isExist(final String key, final String value)
    {
        String t = get(key);
        if (t != null && t.equals(value))
        {
            return true;
        }
        return false;
    }

	public boolean isExist(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] keys = redisTemplate.getStringSerializer()
						.serialize(key);
				if (connection.exists(keys)) {
					return "1";
				}
				return "0";
			}
		});
		if ("1".equals(result)) {
			return true;
		}
		return false;
	}
}
