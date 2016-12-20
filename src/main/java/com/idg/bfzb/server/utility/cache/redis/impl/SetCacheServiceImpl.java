package com.idg.bfzb.server.utility.cache.redis.impl;

import com.idg.bfzb.server.utility.cache.redis.SetCacheService;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class SetCacheServiceImpl<K, V> implements SetCacheService<K, V> {
	
	@Resource(name="redisTemplate")
	private SetOperations<K, V> setOps;

	@Override
	public Long add(K key, V... values) {
		// TODO Auto-generated method stub
		return setOps.add(key, values);
	}

	@Override
	public Set<V> difference(K key, Collection<K> otherKeys) {
		// TODO Auto-generated method stub
		return setOps.difference(key, otherKeys);
	}

	@Override
	public Set<V> difference(K key, K otherKey) {
		// TODO Auto-generated method stub
		return setOps.difference(key, otherKey);
	}

	@Override
	public Set<V> intersect(K key, Collection<K> otherKeys) {
		// TODO Auto-generated method stub
		return setOps.intersect(key, otherKeys);
	}

	@Override
	public Set<V> intersect(K key, K otherKey) {
		// TODO Auto-generated method stub
		return setOps.intersect(key, otherKey);
	}

	@Override
	public Set<V> members(K key) {
		// TODO Auto-generated method stub
		return setOps.members(key);
	}

	@Override
	public V pop(K key) {
		// TODO Auto-generated method stub
		return setOps.pop(key);
	}

	@Override
	public List<V> randomMembers(K key, long count) {
		// TODO Auto-generated method stub
		return setOps.randomMembers(key, count);
	}

	@Override
	public Long remove(K key, Object... values) {
		// TODO Auto-generated method stub
		return setOps.remove(key, values);
	}

	@Override
	public Long size(K key) {
		// TODO Auto-generated method stub
		return setOps.size(key);
	}

	@Override
	public Set<V> union(K key, Collection<K> otherKeys) {
		// TODO Auto-generated method stub
		return setOps.union(key, otherKeys);
	}

	@Override
	public Set<V> union(K key, K otherKey) {
		// TODO Auto-generated method stub
		return setOps.difference(key, otherKey);
	}

	@Override
	public void delete(K key) {
		// TODO Auto-generated method stub
		setOps.getOperations().delete(key);
	}

	@Override
	public void delete(Collection<K> key) {
		// TODO Auto-generated method stub
		setOps.getOperations().delete(key);
		
	}

	@Override
	public RedisOperations<K, V> getOperations() {
		// TODO Auto-generated method stub
		return setOps.getOperations();
	}

	@Override
	public Boolean expire(K key, long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		return setOps.getOperations().expire(key, timeout, unit);
	}

	@Override
	public Boolean expireAt(K key, Date date) {
		// TODO Auto-generated method stub
		return setOps.getOperations().expireAt(key, date);
	}

}
