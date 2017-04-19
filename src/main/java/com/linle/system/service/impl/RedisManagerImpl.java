package com.linle.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.util.SerializeUtil;
import com.linle.system.service.RedisManager;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component("redisManager")
public class RedisManagerImpl<K, V> implements RedisManager<K, V>{
	
	@Autowired
	@Qualifier("shardedJedisPool")
	private ShardedJedisPool shardedJedisPool;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private  Logger _logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void deleteCached(final String sessionId) throws Exception {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		shardedJedis.del(sessionId);
		shardedJedisPool.returnResource(shardedJedis);
	}

	@Override
	public void updateCached(final String key, final Object session)
			throws Exception {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		shardedJedis.set(key.getBytes(), SerializeUtil.serialize(session));
		shardedJedisPool.returnResource(shardedJedis);
	}

	@Override
	public V getCached(final String sessionId) throws Exception {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		V sid = (V)SerializeUtil.unserialize(shardedJedis.get(sessionId.getBytes()));
		shardedJedisPool.returnResource(shardedJedis);
		return sid;
	}

	@Override
	public void deleteCached(String namespace, String key) throws Exception {
	}

	@Override
	public V getCached(String namespace, String key) throws Exception {
		return null;
	}

	@Override
	public void updateCached(String namespace, String key, Object value)
			throws Exception {
	}

	@Override
	public void rpush(String key, Object value) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		shardedJedis.rpush(key.getBytes(),SerializeUtil.serialize(value));
		shardedJedisPool.returnResource(shardedJedis);
	}

	@Override
	public void lrem(String key, int count, Object value) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		shardedJedis.lrem(key.getBytes(),(long)count,SerializeUtil.serialize(value));
		shardedJedisPool.returnResource(shardedJedis);
	}

	@Override
	public Long sAdd(String key, Object value) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		Long count = (long) 0;
		try {
			count = shardedJedis.sadd(key,mapper.writeValueAsString(value));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			_logger.error("redis存入set数据报错，key="+key+"。错误原因:"+e);
		}finally {
			shardedJedisPool.returnResource(shardedJedis);
		}
		return count;
	}

	@Override
	public Long sRemove(String key, String value) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		Long count = shardedJedis.srem(key,value);
		shardedJedisPool.returnResource(shardedJedis);
		return count;
	}

	@Override
	public V sMembers(String key) {
		ShardedJedis shardedJedis = null;
		V set = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			set = (V) shardedJedis.smembers(key);
		} catch (Exception e) {
			_logger.error("sMembers出错:"+e);
		}finally {
			shardedJedisPool.returnResource(shardedJedis);
		}
		return set;
	}
	
	
}