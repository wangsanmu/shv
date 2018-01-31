package com.blog.common.shiro.dao;

import com.blog.common.loger.LoggerUtils;
import com.blog.common.shiro.cache.JedisShiroSessionRepository;
import com.blog.common.shiro.sesson.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;

public class CustomShiroSessionDAO extends AbstractSessionDAO {

    private ShiroSessionRepository shiroSessionRepository;


    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        getShiroSessionRepository().saveSession(session);
        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        return getShiroSessionRepository().getSession(sessionId);
    }

    public void update(Session session) throws UnknownSessionException {
        getShiroSessionRepository().saveSession(session);
    }

    public void delete(Session session) {
        if(session == null){
            LoggerUtils.error(getClass(), "Session 不能为null");
            return;
        }
        Serializable id = session.getId();
        if(id != null){
            getShiroSessionRepository().deleteSession(id);
        }
    }

    public Collection<Session> getActiveSessions() {
        return getShiroSessionRepository().getAllSessions();
    }


    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

}
