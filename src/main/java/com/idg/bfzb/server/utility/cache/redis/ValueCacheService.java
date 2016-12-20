package com.idg.bfzb.server.utility.cache.redis;

import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface ValueCacheService<K, V> {
	 /**
	  * 
	  * <p>说明：新增</p>
	  * @param key
	  * @param value
	  * void
	  */
	 void set(K key, V value);
	 /**
	  * 
	  * <p>说明：新增并设置超时时间</p>
	  * @param key key值
	  * @param value redis值
	  * @param timeout TTL时间
	  * @param timeUnit 时间单位
	  * void
	  */
	 void set(K key, V value, long timeout, TimeUnit timeUnit);
	 /**
	  * 
	  * <p>说明：查询</p>
	  * @param key
	  * @return
	  * V
	  */
	 V get(K key);
	 /**
	  * 
	  * <p>说明：查询并且修改</p>
	  * @param key
	  * @param value
	  * @return
	  * V
	  */
	 V getAndSet(K key, V value);
	 /**
	  * 
	  * <p>说明：根据多个key获取列表</p>
	  * @param keys
	  * @return
	  * List<V>
	  */
	 List<V> multiGet(Collection<K> keys);
	 /**
	  * 
	  * <p>说明：删除</p>
	  * @param key
	  * void
	  */
	 void delete(K key);
	 /**
	  * 
	  * <p>说明：删除</p>
	  * @param key
	  * void
	  */
	 void delete(Collection<K> key);
	 /**
	  * 
	  * <p>说明：获取RedisOperations</p>
	  * @return
	  * RedisOperations<K,V>
	  */
	 
	 RedisOperations<K, V> getOperations();
	 
	 /**
	  * 
	  * <p>说明：自增</p>
	  * @param k
	  * @param delta
	  * @return
	  * V
	  */
	 Long increment(K k, long delta);
	 /**
		 * 
		 * <p>说明：设置过期时间</p>
		 * @param key
		 * @param timeout
		 * @param unit
		 * @return
		 * Boolean
		 */
		Boolean expire(K key, long timeout, TimeUnit unit);
		/**
		 * 
		 * <p>说明：设置过期日期</p>
		 * @param key
		 * @param date
		 * @return
		 * Boolean
		 */
		Boolean expireAt(K key, Date date);

}
