package com.lili.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import com.lili.share.service.BaseService;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRealTime
{
	@Resource(name="redisTemplate")
    protected RedisTemplate<Serializable, Serializable> redisTemplate;
	@Resource(name="redisTemplateString")
    protected RedisTemplate<String, String> redisTemplateString;
	
    public static final String KEYKEYPREFIX="KEYLIST_";
    public static final String TIMEKEYPREFIX="TIME_";
    public static final String OBJECTPREFIX="OBJECT_";
    public static final String COMPLEXPREFIX="COMPLEX_";
    public static final String PRIMARYKEYPREFIX="DBPK_";
    public static final String SPELKEY="cache";
    public static int CACHESECOND=172800;
    
    
    public <T> String getObjectKey(final T t,final BaseService baseService) throws Exception {
    	String key=OBJECTPREFIX+baseService.getProjectVoKey()+"_"+BeanCopy.getSortKeyValue(t);
    	return key;
    }
    
    public Set<byte[]> getTimeKey(String keyPrefix,List<String> spelKey,Object ... object){
    	Set<byte []> result=new LinkedHashSet<byte []>();
    	if(null==spelKey|| spelKey.isEmpty() || null==object) {
    		return result;
    	}
    	ExpressionParser parser = new SpelExpressionParser();  
	 	EvaluationContext context = new StandardEvaluationContext();
	 	for(int i=0;i<object.length;i++){
	 		context.setVariable(SPELKEY, object[i]);
	 		for(int j=0;j<spelKey.size();j++){
	 			String key=keyPrefix+spelKey.get(j);
	 			if(!keyPrefix.contains("\"")){
	 				key="\""+keyPrefix+spelKey.get(j);
	 			}
	 			String spelValue = parser.parseExpression(key).getValue(context,String.class);
	 			result.add(redisTemplate.getStringSerializer().serialize(spelValue));
	 		}
	 	}
	 	return result;
    }
    
    public String getComplexKey(String spelKey,Object ... object){
    	String key="\""+COMPLEXPREFIX+spelKey;
    	ExpressionParser parser = new SpelExpressionParser();  
	 	EvaluationContext context = new StandardEvaluationContext();
	 	for(int i=0;object!=null && i<object.length;i++) {
	 		context.setVariable("param"+(i+1), object[i]);
	 	}
	 	String spelValue = parser.parseExpression(key).getValue(context,String.class);
	 	return spelValue;
    }
    
    public List<String> getParamKey(String[] spelKey,Object ... object){
    	if(spelKey==null){
    		return null;
    	}
    	List<String> paramKey=new ArrayList<String>();
    	ExpressionParser parser = new SpelExpressionParser();  
	 	EvaluationContext context = new StandardEvaluationContext();
	 	for(int i=0;object!=null && i<object.length;i++) {
	 		context.setVariable("param"+(i+1), object[i]);
	 	}
    	for(int i=0;i<spelKey.length;i++){
    		String key="\""+TIMEKEYPREFIX+spelKey[i];
    		String spelValue = parser.parseExpression(key).getValue(context,String.class);
    		paramKey.add(spelValue);
    	}
	 	return paramKey;
    }
    
    public Set<byte[]> getTimeKey(String keyPrefix,String spelKey,Object ... object){
    	Set<byte []> result=new LinkedHashSet<byte []>();
    	if(null==spelKey|| spelKey.isEmpty() || null==object) {
    		return result;
    	}
    	ExpressionParser parser = new SpelExpressionParser();  
	 	EvaluationContext context = new StandardEvaluationContext();
	 	for(int i=0;i<object.length;i++){
	 		context.setVariable(SPELKEY, object[i]);
	 		String key=keyPrefix+spelKey;
 			if(!keyPrefix.contains("\"")){
 				key="\""+keyPrefix+spelKey;
 			}
	 		String spelValue = parser.parseExpression(key).getValue(context,String.class);
		 	result.add(redisTemplate.getStringSerializer().serialize(spelValue));
	 	}
	 	return result;
    }
    public String  getVoKey(Object pkValue,final BaseService baseService){
    	String voKey=OBJECTPREFIX+baseService.getProjectVoKey()+"_"+baseService.getPkFieldName()+"-"+pkValue;
    	return voKey;
    }
    public List<String> getVoKeyList(final List<Object> pkValue,final BaseService baseService){
    	List<String> keyList=new ArrayList<String>();
    	for(Object one:pkValue){
    		String voKey=OBJECTPREFIX+baseService.getProjectVoKey()+"_"+baseService.getPkFieldName()+"-"+one;
    		keyList.add(voKey);
    	}
    	return keyList;
    }
    
    @SuppressWarnings("unchecked")
	public <T,S> T getVo(final Object s,final BaseService baseService){
    	List<S> tt=new ArrayList<S>();
		if(!(s instanceof List)) {
			tt.add((S)s);
		} else {
			tt=(List<S>)s;
		}
		final List<S> pkValue=tt;
    	List<String> keyList=new ArrayList<String>();
    	for(S one:pkValue){
    		String voKey=OBJECTPREFIX+baseService.getProjectVoKey()+"_"+baseService.getPkFieldName()+"-"+one;
    		keyList.add(voKey);
    	}
    	List<Object> r=mget(keyList);
    	if(s instanceof List) {
			return (T)r;
		} else if(r!=null && !r.isEmpty()) {
			return (T)r.get(0);
		} else {
			return null;
		}
    }
    
    @SuppressWarnings("unchecked")
	public <T,S> void add(final S s,final BaseService baseService)
    {
    	List<T> tt=new ArrayList<T>();
		if(!(s instanceof List)) {
			tt.add((T)s);
		} else {
			tt=(List<T>)s;
		}
		final List<T> t=tt;
		String keyKey=baseService.getProjectVoKey();
		List<String> spelKey=smember(KEYKEYPREFIX+keyKey);
		//整表更新
		spelKey.add(keyKey+"\"");
		//id更新
		String spelPk=keyKey+"_"+baseService.getPkFieldName()+"-\"+#"+SPELKEY+"."+baseService.getPkFieldName();
		spelKey.add(spelPk);
		final Set<byte[]> timeKey=this.getTimeKey(TIMEKEYPREFIX,spelKey, t.toArray());
		final Set<byte[]> pkKey=getTimeKey(OBJECTPREFIX,spelPk, t.toArray());
		final int cacheSecond=CACHESECOND;
		redisTemplate.execute(new RedisCallback<T>(){
			public T doInRedis(RedisConnection connection) throws DataAccessException
			{
				Map<byte[],byte[]> byteMap=new HashMap<byte[],byte[]>();
				byte[] curr=((RedisSerializer<Long>)redisTemplate.getValueSerializer()).serialize(System.currentTimeMillis());
				Iterator<byte[]> it=timeKey.iterator();
				while(it.hasNext()){
					byte[] key=it.next();
					byteMap.put(key, curr);
				}
				it=pkKey.iterator();
				int i=0;
				while(it.hasNext()){
					byte[] key=it.next();
					byteMap.put(key, ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(t.get(i++)));
				}
				if(cacheSecond>0){
					connection.openPipeline();
            		connection.mSet(byteMap);
            		it=timeKey.iterator(); 
            		while(it.hasNext()){
            			connection.expire(it.next(), cacheSecond);
            		}
            		it=pkKey.iterator();
    				while(it.hasNext()){
    					connection.expire(it.next(), cacheSecond);
    				}
            		connection.closePipeline();
				} else {
					connection.mSet(byteMap);
				}
				return null;
			}
		});
    }
   
    @SuppressWarnings("unchecked")
	public <T,S> void reaload(final S s,final BaseService baseService)
    {
    	List<T> tt=new ArrayList<T>();
		if(!(s instanceof List)) {
			tt.add((T)s);
		} else {
			tt=(List<T>)s;
		}
		final List<T> t=tt;
		String keyKey=baseService.getProjectVoKey();
		List<String> spelKey=smember(KEYKEYPREFIX+keyKey);
		//整表更新
		spelKey.add(keyKey+"\"");
		//id更新
		String spelPk=keyKey+"_"+baseService.getPkFieldName()+"-\"+#"+SPELKEY+"."+baseService.getPkFieldName();
		spelKey.add(spelPk);
		final Set<byte[]> timeKeySrc=this.getTimeKey(TIMEKEYPREFIX,spelKey, t.toArray());
		final Set<byte[]> pkKeySrc=getTimeKey(OBJECTPREFIX,spelPk, t.toArray());
		final int cacheSecond=CACHESECOND;
		redisTemplate.execute(new RedisCallback<T>(){
			public T doInRedis(RedisConnection connection) throws DataAccessException
			{
				boolean update=false;
				Set<byte[]> timeKey=new HashSet<byte[]>();
				Set<byte[]> pkKey=new HashSet<byte[]>();
				connection.openPipeline();
				Iterator<byte[]> it=timeKeySrc.iterator();
				while(it.hasNext()){
					byte[] key=it.next();
					if(connection.exists(key)){
						timeKey.add(key);
					}
				}
				it=pkKeySrc.iterator();
				while(it.hasNext()){
					byte[] key=it.next();
					if(connection.exists(key)){
						pkKey.add(key);
					}
				}
				connection.closePipeline();
				
				Map<byte[],byte[]> byteMap=new HashMap<byte[],byte[]>();
				byte[] curr=((RedisSerializer<Long>)redisTemplate.getValueSerializer()).serialize(System.currentTimeMillis());
				it=timeKey.iterator();
				while(it.hasNext()){
					byte[] key=it.next();
					byteMap.put(key, curr);
					update=true;
				}
				it=pkKey.iterator();
				int i=0;
				while(it.hasNext()){
					byte[] key=it.next();
					byteMap.put(key, ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(t.get(i++)));
					update=true;
				}
				if(cacheSecond>0 && update ){
					connection.openPipeline();
            		connection.mSet(byteMap);
            		it=timeKey.iterator(); 
            		while(it.hasNext()){
            			connection.expire(it.next(), cacheSecond);
            		}
            		it=pkKey.iterator();
    				while(it.hasNext()){
    					connection.expire(it.next(), cacheSecond);
    				}
            		connection.closePipeline();
				} else if(update){
					connection.mSet(byteMap);
				}
				return null;
			}
		});
    }
    @SuppressWarnings("unchecked")
	public <T,S> void delete(final S s,final BaseService baseService)
    {
    	List<T> tt=new ArrayList<T>();
		if(!(s instanceof List)) {
			tt.add((T)s);
		} else {
			tt=(List<T>)s;
		}
		final List<T> t=tt;
		String keyKey=baseService.getProjectVoKey();
		List<String> spelKey=smember(KEYKEYPREFIX+keyKey);
		//整表更新
		spelKey.add(keyKey+"\"");
		//id更新
		String spelPk=keyKey+"."+baseService.getPkFieldName()+".\"#"+SPELKEY+"."+baseService.getPkFieldName();
		spelKey.add(spelPk);
		final Set<byte[]> timeKey=this.getTimeKey(TIMEKEYPREFIX,spelKey, t.toArray());
		final Set<byte[]> pkKey=getTimeKey(OBJECTPREFIX,spelPk, t.toArray());
		final int cacheSecond=CACHESECOND;
		redisTemplate.execute(new RedisCallback<T>(){
			public T doInRedis(RedisConnection connection) throws DataAccessException
			{
				Map<byte[],byte[]> byteMap=new HashMap<byte[],byte[]>();
				long curr=System.currentTimeMillis();
				Iterator<byte[]> it=timeKey.iterator();
				while(it.hasNext()){
					byte[] key=it.next();
					byteMap.put(key, ((RedisSerializer<Long>)redisTemplate.getValueSerializer()).serialize(curr));
				}
				connection.openPipeline();
            	connection.mSet(byteMap);
            	it=timeKey.iterator();
            	if(cacheSecond>0){
	            	while(it.hasNext()){
	            		connection.expire(it.next(), cacheSecond);
	            	}
            	}
            	it=pkKey.iterator();
	    		while(it.hasNext()){
	    			connection.del(it.next());
	    		}
            	connection.closePipeline();
				return null;
			}
		});
    }
    public <T> void addComplex(String timeKey,String key,T t){
    	if(StringUtil.isNullOrEmpty(timeKey) || StringUtil.isNullOrEmpty(key) || t==null){
    		return ;
    	}
    	List<String> timeKeyList=new ArrayList<String>();
    	timeKeyList.add(timeKey);
    	addComplex(timeKeyList, key, t);
    }
    public <T> void addComplex(final List<String> timeKey,final String key,final T t)
    {
    	if(timeKey==null || StringUtil.isNullOrEmpty(key) || t==null){
    		return ;
    	}
    	RealTimeCache<T> cache=new RealTimeCache<T>();
    	List<Long> valueList=mget(timeKey);
    	cache.setObject(t);
    	cache.setKeyList(timeKey);
    	cache.setVaueList(valueList);
    	set(key, cache, CACHESECOND);
    }
    public <T> void sadd(String key,T value){
    	List<T> list=new ArrayList<T>();
    	list.add(value);
    	sadd(key,list);
    }
    @SuppressWarnings("unchecked")
	public <T> void sadd(String key,List<T> values){
    	if(StringUtil.isNullOrEmpty(key) || values==null || values.isEmpty()){
    		return;
    	}
    	final byte[] byteKey=redisTemplate.getStringSerializer().serialize(key);
    	final byte[][] byteValues=new byte[values.size()][]; 
    	for (int i=0;i<values.size();i++) {
    		byteValues[i]=((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(values.get(i));
    	}
    	redisTemplate.execute(new RedisCallback<Object>(){
			
			public Object doInRedis(RedisConnection connection) throws DataAccessException
			{
				return connection.sAdd(byteKey, byteValues);
			}
    	});
    }
    
    @SuppressWarnings("unchecked")
	public <T> List<T> smember(String key){
    	final List<T> result=new ArrayList<T>();
    	if(StringUtil.isNullOrEmpty(key)){
    		return result;
    	}
    	final byte[] byteKey=redisTemplate.getStringSerializer().serialize(key);
    	return redisTemplate.execute(new RedisCallback<List<T>>(){
			
			public List<T> doInRedis(RedisConnection connection) throws DataAccessException
			{
				Set<byte[]> set=connection.sMembers(byteKey);
				if(set!=null) {
					Iterator<byte[]> it=set.iterator();
					while (it.hasNext()) {
						result.add(((RedisSerializer<T>)redisTemplate.getValueSerializer()).deserialize(it.next()));
					}
				}
				return result;
			}
    	});
    }
    public <T> void  setNotEixst(final String key, final T value,final int liveSecond){
    	   redisTemplate.execute(new RedisCallback<T>()
    	   {
    	            @SuppressWarnings("unchecked")
    				
    	            public T doInRedis(RedisConnection connection) throws DataAccessException
    	            {
    	                connection.setNX(redisTemplate.getStringSerializer().serialize(key),
    	                        ((RedisSerializer<T>)redisTemplate.getValueSerializer()).serialize(value));
    	                if (liveSecond > 0) {
    	                    connection.expire(redisTemplate.getStringSerializer().serialize(key), liveSecond);
    	                }
    	                return null;
    	            }
    	   });
    }
	public  void  setNotEixst(final String key, final String value){
    	redisTemplateString.opsForValue().set(key, value);
	}
    public  Long  incrBy(final String key, final int number){
 	   return redisTemplate.execute(new RedisCallback<Long>()
 	   {
 				
 	            public Long doInRedis(RedisConnection connection) throws DataAccessException
 	            {
 	                return connection.incrBy(redisTemplate.getStringSerializer().serialize(key),number);
 	            }
 	   });
   }
	public <T> RealTimeCache<T> getComplexCache(String key)
    {
    	RealTimeCache<T> cache=get(key);
    	if(cache!=null){
    		List<String> keyList=cache.getKeyList();
    		boolean modify=false;
    		if(keyList!=null && !keyList.isEmpty()){
    			List<Long> oldList=cache.getVaueList();
        		List<Long> modList=mget(keyList);
        		for(int i=0;keyList!=null && i<keyList.size();i++){
        			Long mod=modList.get(i);
        			Long old=oldList.get(i);
        			if((old!=null && mod==null) || (old==null && mod!=null) || (old!=null && mod!=null && old.longValue()!=mod)){
        				modify=true;
        				break;
        			} 
        		}
    		} 
    		//如果已经修改，那么直接失效
    		if(modify){
    			cache=null;
    			delete(key);
    		}
    	}
    	return cache;
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
	private ConcurrentHashMap<String,Boolean> pk2Redis=new ConcurrentHashMap<String,Boolean>();
	
	@SuppressWarnings("unchecked")
	public <T> T getPrimaryKey(BaseService baseService,int number) {
		//如果没有初始化 且 redis中不存在初始值，则从数据库取得初始值
		String pkKey=PRIMARYKEYPREFIX+baseService.getProjectVoKey();
	    Boolean pkInit=pk2Redis.get(pkKey);
		boolean hasInit=(pkInit!=null && pkInit);
		Long r=null;
		T t=null;
		if(!hasInit && !isExist(pkKey)) {
			//1.从数据库中获取初值
			t=baseService.getMaxPk();
			//2.保存至redis中
			if(t==null ){
				setNotEixst(pkKey,  "1");
			} else {
				setNotEixst(pkKey,  String.valueOf(t));
			}
			//3.从redis中获取当前需要的值
			r=incrBy(pkKey,number)-(number-1);
			pk2Redis.put(pkKey, true);
		} else {
			r=incrBy(pkKey,number)-(number-1);
		}
		if (!(t instanceof Long)) {
			return (T)Integer.valueOf(String.valueOf(r));
		}
		return (T)r;
	}
}
