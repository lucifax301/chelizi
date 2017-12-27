package com.lili.common.util.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisUtil
{
	static int db=0;
	static{
		String redisdb=System.getProperty("redis.db");
		if(redisdb!=null){
			try{
			db=Integer.parseInt(redisdb);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		System.out.println("redisdb---------------"+db);
	}
	
	@Resource(name="redisTemplate")
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

	private RedisUtilRemote redisUtilRemote;
	
    public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}
	public void setRedisTemplate(
			RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	public RedisUtilRemote getRedisUtilRemote() {
		return redisUtilRemote;
	}
	public void setRedisUtilRemote(RedisUtilRemote redisUtilRemote) {
		this.redisUtilRemote = redisUtilRemote;
	}
	
	
	
	public <T> void set(final String key, final T value,final int liveSecond)
    {
        redisTemplate.execute(new RedisCallback<T>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public T doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
                connection.set(redisTemplate.getStringSerializer().serialize(key),
                        ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
                if (liveSecond > 0) {
                    connection.expire(redisTemplate.getStringSerializer().serialize(key), liveSecond);
                }
                if(redisUtilRemote!=null){
                	redisUtilRemote.delete(key);
                }
                return null;
            }
        });
    }
    public <T> void set(final String key, final T value)
    {
        set(key,value,0);
    }
	
    public <T> Boolean setNX(final String key, final T value,final int liveSecond)
    {
        return redisTemplate.execute(new RedisCallback<Boolean>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
                Boolean r =  connection.setNX(redisTemplate.getStringSerializer().serialize(key),
                        ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
                if (liveSecond > 0) {
                    connection.expire(redisTemplate.getStringSerializer().serialize(key), liveSecond);
                }
                if(redisUtilRemote!=null){
                	redisUtilRemote.delete(key);
                }
                return r;
            }
        });
    }
    
    public <T> Boolean setNX(final String key, final T value)
    {
        return redisTemplate.execute(new RedisCallback<Boolean>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
                Boolean r =  connection.setNX(redisTemplate.getStringSerializer().serialize(key),
                        ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
                return r;
            }
        });
    }
    
    public <T> T get(final String key)
    {
        return redisTemplate.execute(new RedisCallback<T>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public T doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
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
    
    public <T> Set<T> keys(final String pattern)
    {
        return redisTemplate.execute(new RedisCallback<Set<T>>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public Set<T> doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
                byte[] patterns = redisTemplate.getStringSerializer().serialize(pattern);
                Set<byte[]> values = connection.keys(patterns);
                Set<T> r = new HashSet<>();
                if (values!=null)
                {
                	for (byte[] value : values)
                		r.add((T) new String(value));
                }
                return r;
            }
        });
    }
    
    /*
     * 获得在线用户id专用
     */
    public <T> Set<T> getOnlineUserId(final String pattern,final String reg)
    {
        return redisTemplate.execute(new RedisCallback<Set<T>>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public Set<T> doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
                byte[] patterns = redisTemplate.getStringSerializer().serialize(pattern);
                Set<byte[]> values = connection.keys(patterns);
                Set<T> r = new HashSet<>();
                if (values!=null)
                {
                	for (byte[] value : values)
                		r.add((T) new String(value).replace(reg, ""));
                }
                return r;
            }
        });
    }
    
    /**
     * 批量设置缓存
     * @param aa
     */
    public <T> void mset(final Map<String,T> aa,final int liveSecond)
    {
        redisTemplate.execute(new RedisCallback<T>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public T doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
            	if(null == aa || aa.size()==0){
            		return null;
            	}
            	Map<byte[],byte[]> bb = new HashMap<byte[], byte[]>();
            	Iterator<Entry<String, T>> it = aa.entrySet().iterator();
            	while(it.hasNext()){
            		Entry<String, T> entry = it.next();
            		String key = entry.getKey();
            		T value = entry.getValue();
            		bb.put(redisTemplate.getStringSerializer().serialize(key), 
            				((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
            	}
            	connection.mSet(bb);
            	 if(redisUtilRemote!=null){
                 	redisUtilRemote.delete(bb.keySet());
                 }
                return null;
            }
        });
    }
    
    /**
     * 批量查询多个缓存数据
     * @param ks
     * @return
     */
    public <T> List<T> mget(final String prefix,final List<Long> ids)
    {
    	return redisTemplate.execute(new RedisCallback<List<T>>()
    			{
		    		@SuppressWarnings("unchecked")
		    		@Override
		    		public List<T> doInRedis(RedisConnection connection) throws DataAccessException
		    		{
		    			if(db>0)
		            		connection.select(db);
		    			byte[][] kk = new byte[ids.size()][];
		    			for(int i=0;i<ids.size();i++){
		    				String k = prefix+ids.get(i);
		    				byte[] ks = redisTemplate.getStringSerializer().serialize(k);
		    				kk[i]= ks;
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
		    		@Override
		    		public Map<String,Object> doInRedis(RedisConnection connection) throws DataAccessException
		    		{
		    			if(db>0)
		            		connection.select(db);
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
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
                byte[] keys = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keys))
                {
                    connection.del(keys);
                }
                if(redisUtilRemote!=null){
                	redisUtilRemote.delete(key);
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
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				if(db>0)
            		connection.select(db);
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
	public <T> void setAll(final String key, final T value,final int liveSecond)
    {
        redisTemplate.execute(new RedisCallback<T>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public T doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
                connection.set(redisTemplate.getStringSerializer().serialize(key),
                        ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
                if (liveSecond > 0) {
                    connection.expire(redisTemplate.getStringSerializer().serialize(key), liveSecond);
                }
                if(redisUtilRemote!=null){
                	redisUtilRemote.setRemote(key, value, liveSecond);
                }
                return null;
            }
        });
    }
	public <T> void setMe(final String key, final T value,final int liveSecond)
    {
        redisTemplate.execute(new RedisCallback<T>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public T doInRedis(RedisConnection connection) throws DataAccessException
            {
            	if(db>0)
            		connection.select(db);
                connection.set(redisTemplate.getStringSerializer().serialize(key),
                        ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
                if (liveSecond > 0) {
                    connection.expire(redisTemplate.getStringSerializer().serialize(key), liveSecond);
                }
                return null;
            }
        });
    }
	
	public <T> void zAdd(final String key,final T value,final double score){
		redisTemplate.execute(new RedisCallback<T>()
		        {
		            @SuppressWarnings("unchecked")
					@Override
		            public T doInRedis(RedisConnection connection) throws DataAccessException
		            {
		            	if(db>0)
		            		connection.select(db);
		            	connection.zAdd(redisTemplate.getStringSerializer().serialize(key), score, ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
		                
		                return null;
		            }
		        });
	}
	
	public <T> void zRemove(final String key,final T value){
		redisTemplate.execute(new RedisCallback<T>()
		        {
		            @SuppressWarnings("unchecked")
					@Override
		            public T doInRedis(RedisConnection connection) throws DataAccessException
		            {
		            	if(db>0)
		            		connection.select(db);
		            	connection.zRem(redisTemplate.getStringSerializer().serialize(key), ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
		            	
		                return null;
		            }
		        });
	}
	
	public <T> void zRemoveByScore(final String key,final double min,final double max){
		redisTemplate.execute(new RedisCallback<T>()
		        {
					@Override
		            public T doInRedis(RedisConnection connection) throws DataAccessException
		            {
						if(db>0)
		            		connection.select(db);
		            	connection.zRemRangeByScore(redisTemplate.getStringSerializer().serialize(key), min, max);
		                return null;
		            }
		        });
	}
	
	public <T> void zRemRange(final String key,final long start,final long end){
		redisTemplate.execute(new RedisCallback<T>()
		{
			@Override
			public T doInRedis(RedisConnection connection) throws DataAccessException
			{
				if(db>0)
            		connection.select(db);
				connection.zRemRange(redisTemplate.getStringSerializer().serialize(key), start, end);
				return null;
			}
		});
	}
	
	/**
	 * 顺序获取结果
	 * @param key
	 * @param offset
	 * @param length
	 * @return
	 */
	public <T> List<T> zGet(final String key,final int offset,final int length)
    {
    	return redisTemplate.execute(new RedisCallback<List<T>>()
    			{
		    		@SuppressWarnings("unchecked")
		    		@Override
		    		public List<T> doInRedis(RedisConnection connection) throws DataAccessException
		    		{
		    			if(db>0)
		            		connection.select(db);
		    			Set<byte[]> set= connection.zRange(redisTemplate.getStringSerializer().serialize(key), offset, length+offset);
		    			List<T> list=new ArrayList<T>();
		    			java.util.Iterator<byte[]> it= set.iterator();
		    			while(it.hasNext()){
		    				T c=((RedisSerializer<T>)redisTemplate.getValueSerializer()).deserialize(it.next());
		    				list.add(c);
		    			}
		    			return list;
		    		}
    			});
    }
	
	/**
	 * 倒序获取结果
	 * @param key
	 * @param offset
	 * @param length
	 * @return
	 */
	public <T> List<T> zRevRange(final String key,final int offset,final int length)
	{
		return redisTemplate.execute(new RedisCallback<List<T>>()
		{
			@SuppressWarnings("unchecked")
			@Override
			public List<T> doInRedis(RedisConnection connection) throws DataAccessException
			{
				if(db>0)
            		connection.select(db);
				Set<byte[]> set= connection.zRevRange(redisTemplate.getStringSerializer().serialize(key), offset, length+offset);
				List<T> list=new ArrayList<T>();
				java.util.Iterator<byte[]> it= set.iterator();
				while(it.hasNext()){
					T c=((RedisSerializer<T>)redisTemplate.getValueSerializer()).deserialize(it.next());
					list.add(c);
				}
				return list;
			}
		});
	}
	
	/**
	 * 获取有序集合的大小
	 * @param key
	 * @return
	 */
	public <T>  Long zCard(final String key)
	{
		return redisTemplate.execute(new RedisCallback<Long>()
		{
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException
			{
				if(db>0)
            		connection.select(db);
				Long size = connection.zCard(redisTemplate.getStringSerializer().serialize(key));
				return size;
			}
		});
	}
	
	public <T>  Long incr(final String key)
	{
		return redisTemplate.execute(new RedisCallback<Long>()
		{
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException
			{
				if(db>0)
            		connection.select(db);
				Long id = connection.incr(redisTemplate.getStringSerializer().serialize(key));
				return id;
			}
		});
	}
	
}
