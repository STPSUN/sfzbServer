package com.idg.bfzb.server.utility.cache.redis;

//import org.springframework.data.redis.core.RedisOperations;

import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface SetCacheService<K,V> {
	/**
	 * 
	 * <p>说明：新增集合</p>
	 * @param key
	 * @param values
	 * @return
	 * Long
	 */
	Long add(K key, V... values); 
	/**
	 * 
	 * <p>说明：取差集 A = {'a', 'b', 'c'}，B = {'a', 'e', 'i', 'o', 'u'} 结果 ={'b', 'c'}</p>
	 * @param key
	 * @param otherKeys
	 * @return
	 * Set<V>
	 */
	Set<V> difference(K key, Collection<K> otherKeys); 
	/**
	 * 
	 * <p>说明：取差集 A = {'a', 'b', 'c'}，B = {'a', 'e', 'i', 'o', 'u'} 结果 ={'b', 'c'}</p>
	 * @param key
	 * @param otherKey
	 * @return
	 * Set<V>
	 */
	Set<V> difference(K key, K otherKey);
	/**
	 * 
	 * <p>说明：取交集</p>
	 * @param key
	 * @param otherKeys
	 * @return
	 * Set<V>
	 */
	Set<V> intersect(K key, Collection<K> otherKeys); 
	/**
	 * 
	 * <p>说明：取交集</p>
	 * @param key
	 * @param otherKey
	 * @return
	 * Set<V>
	 */
	Set<V> intersect(K key, K otherKey); 
	/**
	 * 
	 * <p>说明：获取集合成员</p>
	 * @param key
	 * @return
	 * Set<V>
	 */
	Set<V> members(K key); 
	/**
	 * 
	 * <p>说明：移除并返回集合中的一个随机元素</p>
	 * @param key
	 * @return
	 * V
	 */
	V pop(K key); 
	/**
	 * 
	 * <p>说明：根据key和count获取随机列表数</p>
	 * @param key
	 * @param count
	 * @return
	 * List<V>
	 */
	List<V>	randomMembers(K key, long count); 
	/**
	 * 
	 * <p>说明：移除元素</p>
	 * @param key
	 * @param values
	 * @return
	 * Long
	 */
	Long remove(K key, Object... values); 
	/**
	 * 
	 * <p>说明：获取key大小</p>
	 * @param key
	 * @return
	 * Long
	 */
	Long size(K key); 
	/**
	 * 
	 * <p>说明：取并集</p>
	 * @param key
	 * @param otherKeys
	 * @return
	 * Set<V>
	 */
	Set<V> union(K key, Collection<K> otherKeys); 
	/**
	 * 
	 * <p>说明：取并集</p>
	 * @param key
	 * @param otherKey
	 * @return
	 * Set<V>
	 */
	Set<V> union(K key, K otherKey); 
	/**
	 * 
	 * <p>说明：删除key</p>
	 * @param key
	 * void
	 */
	void delete(K key);
	/**
	 * 
	 * <p>说明：删除key</p>
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
