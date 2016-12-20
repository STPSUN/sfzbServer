package com.idg.bfzb.server.utility.cache.redis;

import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @company ND TM
 * @author lsd
 * 
 * 2014-7-28
 */
public interface ListCacheService<K, V> {
	/**
	 * 
	 * <p>说明：根据key左pop</p>
	 * @param key
	 * @return
	 * V
	 */
	V leftPop(K key); 
	/**
	 * 
	 * <p>说明：根据key在指定的时间内左pop</p>
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 * V
	 */
	V leftPop(K key, long timeout, TimeUnit unit);
	/**
	 * 
	 * <p>说明：根据key和value做左push</p>
	 * @param key
	 * @param value
	 * @return
	 * Long
	 */
	Long leftPush(K key, V value); 
	/**
	 * 
	 * <p>说明：根据key和value基于基准线左push</p>
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 * Long
	 */
	Long leftPush(K key, V pivot, V value); 
	/**
	 * 
	 * <p>说明：根据key，start，end获取范围列表（end=-1表示列表的结尾）</p>
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * List<V>
	 */
	List<V>	range(K key, long start, long end); 
	/**
	 * 
	 * <p>说明：根据key右pop</p>
	 * @param key
	 * @return
	 * V
	 */
	V rightPop(K key); 
	/**
	 * 
	 * <p>说明：根据key在指定的时间内右pop</p>
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 * V
	 */
	V rightPop(K key, long timeout, TimeUnit unit);
	/**
	 * 
	 * <p>说明：根据key和value做右push</p>
	 * @param key
	 * @param value
	 * @return
	 * Long
	 */
	Long rightPush(K key, V value); 
	/**
	 * 
	 * <p>说明：根据key和value基于基准线做左push</p>
	 * @param key
	 * @param pivot
	 * @param value
	 * @return
	 * Long
	 */
	Long rightPush(K key, V pivot, V value);
	/**
	 * 
	 * <p>说明：根据key获取列表大小</p>
	 * @param key
	 * @return
	 * Long
	 */
	Long size(K key);
	/**
	 * 
	 * <p>说明：？</p>
	 * @param key
	 * @param i
	 * @param value
	 * @return
	 * Long
	 */
	Long remove(K key, long i, Object value); 
	/**
	 * 
	 * <p>说明：删除列表</p>
	 * @param key
	 * void
	 */
    void delete(K key);
	/**
	 * 
	 * <p>说明：删除多个列表</p>
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
