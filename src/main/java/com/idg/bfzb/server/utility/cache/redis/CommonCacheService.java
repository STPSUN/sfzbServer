package com.idg.bfzb.server.utility.cache.redis;

import org.springframework.data.redis.core.RedisTemplate;

public interface CommonCacheService<K, V> {
	
	/**
	 * 
	 * <p>说明：获取RedisOperations</p>
	 * @return
	 * RedisOperations<K,V>
	 */
	RedisTemplate<K, V> getTemplate();

}
