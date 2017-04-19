package com.linle.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;


public class CustomShiroSessionDAO extends AbstractSessionDAO {

	private ShiroSessionRepository shiroSessionRepository;

	@Override
	public void update(Session session) throws UnknownSessionException {
		shiroSessionRepository.saveSession(session);
	}

	@Override
	public void delete(Session session) {
		shiroSessionRepository.deleteSession(session.getId());
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return shiroSessionRepository.getAllSessions();

	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		getShiroSessionRepository().saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return getShiroSessionRepository().getSession(sessionId);
	}

	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(
			ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}

}
