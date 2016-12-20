package com.idg.bfzb.server.utility.cache.redis.impl;


import com.idg.bfzb.server.utility.cache.redis.CommonCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class CommonCacheServiceImpl<K,V> implements CommonCacheService<K, V> {
	
	@Resource(name="redisTemplate")
	private RedisTemplate<K, V> template;

	@Override
	public RedisTemplate<K, V> getTemplate() {
		// TODO Auto-generated method stub
		 return template;
	}


}
