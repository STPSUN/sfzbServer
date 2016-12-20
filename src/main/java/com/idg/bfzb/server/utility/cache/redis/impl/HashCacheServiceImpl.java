package com.idg.bfzb.server.utility.cache.redis.impl;

import com.idg.bfzb.server.utility.cache.redis.HashCacheService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class HashCacheServiceImpl<H, HK, HV> implements HashCacheService<H, HK, HV> {
	
	@Resource(name="redisTemplate")
	private HashOperations<H, HK, HV> hashOps;

	@Override
	public void delete(H key, Object... hashKeys) {
		// TODO Auto-generated method stub
		hashOps.delete(key, hashKeys);
	}

	@Override
	public HV get(H key, Object hashKey) {
		// TODO Auto-generated method stub
		return hashOps.get(key, hashKey);
	}

	@Override
	public Set<HK> keys(H key) {
		// TODO Auto-generated method stub
		return hashOps.keys(key);
	}

	@Override
	public List<HV> multiGet(H key, Collection<HK> hashKeys) {
		// TODO Auto-generated method stub
		return hashOps.multiGet(key, hashKeys);
	}

	@Override
	public void put(H key, HK hashKey, HV value) {
		// TODO Auto-generated method stub
		hashOps.put(key, hashKey, value);
	}

	@Override
	public Long size(H key) {
		// TODO Auto-generated method stub
		return hashOps.size(key);
	}

	@Override
	public List<HV> values(H key) {
		// TODO Auto-generated method stub
		return hashOps.values(key);
	}

	@Override
	public RedisOperations<H, ?> getOperations() {
		// TODO Auto-generated method stub
		return hashOps.getOperations();
	}

	@Override
	public Boolean expire(H key, long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		return hashOps.getOperations().expire(key, timeout, unit);
	}

	@Override
	public Boolean expireAt(H key, Date date) {
		// TODO Auto-generated method stub
		return hashOps.getOperations().expireAt(key, date);
	}

}
