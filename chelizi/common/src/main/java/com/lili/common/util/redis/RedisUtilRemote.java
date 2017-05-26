package com.lili.common.util.redis;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.lili.common.util.StringUtil;

public class RedisUtilRemote {
	/*
	 * 注意： 1. 如果运行过程中关闭灰度环境后，为了提升性能，请在正式环境上将下面这个key刷为false即可。
	 *      2. 上述方式关闭后，如果需要再次开启灰度，那么操作两个步骤是：a.删除redis中这个key，2. 重新启动对应程序
	 *      3. 总结上面即：关闭灰度可不重启，但是再度启动灰度环境需要重启，关于再度开启后续需要提供一个接口来替代重启。 
	 */
	private static final String REMOTEREDISSTART = "grayenv.remote.redis.start";
	private static Logger log = Logger.getLogger(RedisUtilRemote.class);

	@Value("${remote.redis.start}")
	private String remoteStart;

	protected RedisTemplate<Serializable, Serializable> redisTemplate;

	private RedisTemplate<Serializable, Serializable> redisTemplateRemote;

	public void delete(final String key) {
		// 任务时候远程业务不能导致对本机的影响，现在只考虑异常，后续要考虑延时
		try {
			// 只有已经开启远程配置，且未在redis中关闭的才执行删除操作
			if (StringUtil.isNotNullAndNotEmpty(remoteStart)
					&& "true".equalsIgnoreCase(remoteStart) && needRemote()) {
				redisTemplateRemote.execute(new RedisCallback<Object>()
				{
		            public Object doInRedis(RedisConnection connection) throws DataAccessException
		            {
		                byte[] keys = redisTemplateRemote.getStringSerializer().serialize(key);
		                if (connection.exists(keys))
		                {
		                    connection.del(keys);
		                }
		                return null;
		            }
		        });			}
		} catch (Exception e) {
			log.error(key + " remote delete Exception:" + e.getMessage(), e);
		}
	}

	public <T> void delete(final Set<byte[]> keySet) {
		// 任务时候远程业务不能导致对本机的影响，现在只考虑异常，后续要考虑延时
		try {
			// 只有已经开启远程配置，且未在redis中关闭的才执行删除操作
			if (StringUtil.isNotNullAndNotEmpty(remoteStart)
					&& Boolean.getBoolean(remoteStart) && needRemote()) {
				redisTemplateRemote.execute(new RedisCallback<T>() {
					public T doInRedis(RedisConnection connection)
							throws DataAccessException {
						try {
							connection.openPipeline();

							Iterator<byte[]> it = keySet.iterator();
							while (it.hasNext()) {
								connection.del(it.next());
							}
							connection.closePipeline();
						} catch (Exception e) {
							log.error(
									keySet
											+ " remote delete set inner Exception:"
											+ e.getMessage(), e);
						} finally {
							try {
								connection.closePipeline();
							} catch (Exception e) {
							}
						}
						return null;
					}
				});
			}
		} catch (Exception e) {
			log.error(
					keySet + " remote delete set Exception:" + e.getMessage(),
					e);
		}
	}

	public boolean needRemote() {
		try {
			String needRemote = getLocal(REMOTEREDISSTART);
			// 1.初次开启
			if (StringUtil.isNullOrEmpty(needRemote)) {
				setLocal(REMOTEREDISSTART, remoteStart, 0);
				return true;
				// 2.持续运行，可以通过刷新redis数据关闭
			} else if (StringUtil.isNotNullAndNotEmpty(needRemote)
					&& "true".equalsIgnoreCase(needRemote)) {
				return true;
				// 3.测试完毕后关闭，注意无法再通过redis开启
			} else {
				remoteStart = "false";
				log.info(needRemote + " of key " + REMOTEREDISSTART
						+ " is not true, so DONOT REMOTE=" + remoteStart);
				return false;
			}
		} catch (Exception e) {
			log.error(remoteStart + " start Exception:" + e.getMessage(), e);
		}
		// 4. 其他情况都执行远程删除处理
		return true;
	}
	public <T> void setRemote(final String key, final T value,final int liveSecond)
    {
		redisTemplateRemote.execute(new RedisCallback<T>()
        {
            @SuppressWarnings("unchecked")
			@Override
            public T doInRedis(RedisConnection connection) throws DataAccessException
            {
                connection.set(redisTemplateRemote.getStringSerializer().serialize(key),
                        ((RedisSerializer<T>)redisTemplateRemote.getValueSerializer()).serialize(value));
                if (liveSecond > 0) {
                    connection.expire(redisTemplateRemote.getStringSerializer().serialize(key), liveSecond);
                }
                return null;
            }
        });
    }
	public RedisTemplate<Serializable, Serializable> getRedisTemplateRemote() {
		return redisTemplateRemote;
	}

	public void setRedisTemplateRemote(
			RedisTemplate<Serializable, Serializable> redisTemplateRemote) {
		this.redisTemplateRemote = redisTemplateRemote;
	}
	
	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(
			RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private <T> T getLocal(final String key) {
		return redisTemplate.execute(new RedisCallback<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] keys = redisTemplate.getStringSerializer()
						.serialize(key);
				byte[] value = connection.get(keys);
				T r = null;
				if (value != null) {
					r = ((RedisSerializer<T>) redisTemplate
							.getValueSerializer()).deserialize(value);
				}
				return r;
			}
		});
	}

	private <T> void setLocal(final String key, final T value,
			final int liveSecond) {
		redisTemplate.execute(new RedisCallback<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public T doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.set(
						redisTemplate.getStringSerializer().serialize(key),
						((RedisSerializer<T>) redisTemplate
								.getValueSerializer()).serialize(value));
				if (liveSecond > 0) {
					connection.expire(redisTemplate.getStringSerializer()
							.serialize(key), liveSecond);
				}
				return null;
			}
		});
	}
}
