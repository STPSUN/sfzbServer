package com.idg.bfzb.server.utility.cache.redis.impl;

import com.idg.bfzb.server.utility.cache.redis.ListCacheService;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class ListCacheServiceImpl<K, V> implements ListCacheService<K, V> {
	
	@Resource(name="redisTemplate")
	private ListOperations<K, V> listOps;

	@Override
	public V leftPop(K key) {
		// TODO Auto-generated method stub
		return listOps.leftPop(key);
	}

	@Override
	public V leftPop(K key, long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		return listOps.leftPop(key, timeout, unit);
	}

	@Override
	public Long leftPush(K key, V value) {
		// TODO Auto-generated method stub
		return listOps.leftPush(key, value);
	}

	@Override
	public Long leftPush(K key, V pivot, V value) {
		// TODO Auto-generated method stub
		return listOps.leftPush(key, pivot, value);
	}

	@Override
	public List<V> range(K key, long start, long end) {
		// TODO Auto-generated method stub
		return listOps.range(key, start, end);
	}

	@Override
	public V rightPop(K key) {
		// TODO Auto-generated method stub
		return listOps.rightPop(key);
	}

	@Override
	public V rightPop(K key, long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		return listOps.rightPop(key, timeout, unit);
	}

	@Override
	public Long rightPush(K key, V value) {
		// TODO Auto-generated method stub
		return listOps.rightPush(key, value);
	}

	@Override
	public Long rightPush(K key, V pivot, V value) {
		// TODO Auto-generated method stub
		return listOps.rightPush(key, pivot, value);
	}

	@Override
	public Long size(K key) {
		// TODO Auto-generated method stub
		return listOps.size(key);
	}

	@Override
	public Long remove(K key, long i, Object value) {
		// TODO Auto-generated method stub
		return listOps.remove(key, i, value);
	}

	@Override
	public void delete(K key) {
		// TODO Auto-generated method stub
		listOps.getOperations().delete(key);
	}

	@Override
	public void delete(Collection<K> key) {
		// TODO Auto-generated method stub
		listOps.getOperations().delete(key);
	}

	@Override
	public RedisOperations<K, V> getOperations() {
		// TODO Auto-generated method stub
		return listOps.getOperations();
	}

	@Override
	public Boolean expire(K key, long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		return listOps.getOperations().expire(key, timeout, unit);
	}

	@Override
	public Boolean expireAt(K key, Date date) {
		// TODO Auto-generated method stub
		return listOps.getOperations().expireAt(key, date);
	}

}
