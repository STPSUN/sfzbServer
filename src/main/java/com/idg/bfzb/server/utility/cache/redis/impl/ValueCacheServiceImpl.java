package com.idg.bfzb.server.utility.cache.redis.impl;

import com.idg.bfzb.server.utility.cache.redis.ValueCacheService;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ValueCacheServiceImpl<K,V> implements ValueCacheService<K,V> {
	
	@Resource(name="redisTemplate")
	private ValueOperations<K, V> valOps;


	@Override
	public void set(K key, V value) {
		// TODO Auto-generated method stub
		valOps.set(key, value);
		
	}

	@Override
	public void set(K key, V value, long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		valOps.set(key, value, timeout, unit);
		
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return valOps.get(key);
	}

	@Override
	public V getAndSet(K key, V value) {
		// TODO Auto-generated method stub
		return valOps.getAndSet(key, value);
	}

	@Override
	public List<V> multiGet(Collection<K> keys) {
		// TODO Auto-generated method stub
		return valOps.multiGet(keys);
	}

	@Override
	public void delete(K key) {
		// TODO Auto-generated method stub
		valOps.getOperations().delete(key);
	}

	@Override
	public void delete(Collection<K> key) {
		// TODO Auto-generated method stub
		valOps.getOperations().delete(key);
	}

	@Override
	public RedisOperations<K, V> getOperations() {
		// TODO Auto-generated method stub
		return valOps.getOperations();
	}

	@Override
	public Long increment(K k, long delta) {
		// TODO Auto-generated method stub
		return valOps.increment(k, delta);
	}

	@Override
	public Boolean expire(K key, long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		return valOps.getOperations().expire(key, timeout, unit);
	}

	@Override
	public Boolean expireAt(K key, Date date) {
		// TODO Auto-generated method stub
		return valOps.getOperations().expireAt(key, date);
	}
	
	
}
