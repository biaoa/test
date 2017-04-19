package com.linle.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.linle.system.service.RedisManager;


@Component("redisShiroSessionRepository")
public class RedisShiroSessionRepository implements ShiroSessionRepository {

	private final String REDIS_SHIRO_SESSION = "shiro-session:";

	private final static Logger logger = LoggerFactory
			.getLogger(RedisShiroSessionRepository.class);

	@Resource
	private RedisManager redisManager;
	

	@Override
	public void saveSession(Session session) {
		if (session == null || session.getId() == null) {
			logger.error("session或者session id为空");
			return;
		}
		try {
			redisManager.updateCached(getSessionKey(session.getId()), session);
		} catch (Exception e) {
			logger.error("Exception occur when updateCached value with key="
					+ session.getId(), e);
		}
	}

	@Override
	public void deleteSession(Serializable sessionId) {
		try {
			redisManager.deleteCached(getSessionKey(sessionId));
		} catch (Exception e) {
			logger.error("Exception occur when deleteSession value with key="
					+ sessionId, e);
		}

	}

	@Override
	public Collection<Session> getAllSessions() {
		return null;
	}

	@Override
	public Session getSession(Serializable sessionId) {
		try {
			return (Session)redisManager.getCached(getSessionKey(sessionId));
		} catch (Exception e) {
			logger.error("Exception occur when getSession value with key="
					+ sessionId, e);
			return null;
		}
	}

	public String getSessionKey(Serializable sessionId) {
		return sessionId.toString();
	}
}
