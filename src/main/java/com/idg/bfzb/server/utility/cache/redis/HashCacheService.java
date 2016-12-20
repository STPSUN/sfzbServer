package com.idg.bfzb.server.utility.cache.redis;

import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Hash缓存接口
 * @company ND TM
 * @author lsd
 * 
 * 2014-7-28
 */
public interface HashCacheService<H,HK,HV> {
	/**
	 * 
	 * <p>说明：根据key+hashKeys(可选)删除值</p>
	 * @param key
	 * @param hashKeys
	 * void
	 */
	void delete(H key, Object... hashKeys); 
	/**
	 * 
	 * <p>说明：根据key+hashKey(域)获取hash value</p>
	 * @param key
	 * @param hashKey
	 * @return
	 * HV
	 */
	HV get(H key, Object hashKey);  
	/**
	 * 
	 * <p>说明：根据key获取hash key(域)集合</p>
	 * @param key
	 * @return
	 * Set<HK>
	 */
	Set<HK>	keys(H key); 
	/**
	 * 
	 * <p>说明：根据key和hashkeys获取hash值列表</p>
	 * @param key
	 * @param hashKeys
	 * @return
	 * List<HV>
	 */
	List<HV> multiGet(H key, Collection<HK> hashKeys); 
	/**
	 * 
	 * <p>说明：新增</p>
	 * @param key
	 * @param hashKey
	 * @param value
	 * void
	 */
	void put(H key, HK hashKey, HV value); 
	/**
	 * 
	 * <p>说明：根据key获取大小</p>
	 * @param key
	 * @return
	 * Long
	 */
	Long size(H key); 
	/**
	 * 
	 * <p>说明：根据key获取hash值列表</p>
	 * @param key
	 * @return
	 * List<HV>
	 */
	List<HV> values(H key);
	
	/**
	 * 
	 * <p>说明：获取redis operations</p>
	 * @return
	 * RedisOperations<H,?>
	 */
	RedisOperations<H, ?> getOperations();
	/**
	 * 
	 * <p>说明：设置过期时间</p>
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 * Boolean
	 */
	Boolean expire(H key, long timeout, TimeUnit unit);
	/**
	 * 
	 * <p>说明：设置过期日期</p>
	 * @param key
	 * @param date
	 * @return
	 * Boolean
	 */
	Boolean expireAt(H key, Date date);

}
