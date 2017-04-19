package com.linle.socket.msg;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linle.common.util.JsonUtil;
import com.linle.socket.msg.model.WebSocketMsg;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class MsgServiceImpl implements MsgService {

	@Autowired
	private ShardedJedisPool shardedJedisPool;

	public ObjectMapper m = new ObjectMapper();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void add(String key, WebSocketMsg webMsg) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		try {
			logger.info("放入的信息是:"+m.writeValueAsString(webMsg));
			shardedJedis.rpush(key, m.writeValueAsString(webMsg));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		shardedJedisPool.returnResource(shardedJedis);
	}

	@Override
	public void del(String key, WebSocketMsg webMsg) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		String value = "";
		try {
			value = m.writeValueAsString(webMsg);
			logger.info("删除的信息是:"+value);
		} catch (JsonProcessingException e) {
			logger.error("删除redis中的信息报错，key=" + key + "|value:" + value);
			e.printStackTrace();
		}
		Long count = shardedJedis.lrem(key, 0, value);
		shardedJedisPool.returnResource(shardedJedis);
	}

	@Override
	public List<WebSocketMsg> getMsgs(String key) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		Long length = shardedJedis.llen(key);
		List<String> str = shardedJedis.lrange(key, 0, length);
		JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, WebSocketMsg.class);
		List<WebSocketMsg> list = JsonUtil.StringToList(str.toString(), javaType);
		shardedJedisPool.returnResource(shardedJedis);
		return list;
	}

	@Override
	public WebSocketMsg getMsg(String key) {
		ShardedJedis shardedJedis = shardedJedisPool.getResource();
		List<String> str = shardedJedis.lrange(key, 0, 1);
		JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, WebSocketMsg.class);
		List<WebSocketMsg> list = JsonUtil.StringToList(str.toString(), javaType);
		shardedJedisPool.returnResource(shardedJedis);
		return list.isEmpty()?null:list.get(0);
	}

}
