package com.blog.common.shiro.cache;

import com.blog.common.loger.LoggerUtils;
import com.blog.common.serialize.SerializeUtil;
import com.blog.common.shiro.sesson.CustomSessionManager;
import com.blog.common.shiro.sesson.SessionStatus;
import com.blog.common.shiro.sesson.ShiroSessionRepository;
import org.apache.shiro.session.Session;


import java.io.Serializable;
import java.util.Collection;

public class JedisShiroSessionRepository implements ShiroSessionRepository{

    private static final String  REDIS_SHIRO_SESSION = "sojson-shiro-demo-session:";

    public static final String REDIS_SHIRO_ALL = "*sojson-shiro-demo-session:*";

    private static final int DB_INDEX = 1;

    private  JedisManager jedisManager;

    public void saveSession(Session session) {
        if(session == null || session.getId() == null){
            throw new NullPointerException("session is empty");
        }
        try {
            byte[] key = SerializeUtil.serialize(buildRedisSessionKey(session.getId()));

            if(null == session.getAttribute(CustomSessionManager.SESSION_STATUS)){
                //Session 踢存出自存储。
                SessionStatus sessionStatus = new SessionStatus();
                session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }

            byte[] value = SerializeUtil.serialize(session);
         /*
            直接使用 (int) (session.getTimeout() / 1000) 的话，session失效和redis的TTL 同时生效
         */
            getJedisManager().saveValueByKey(DB_INDEX, key, value, (int) (session.getTimeout() / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            getJedisManager().deleteByKey(DB_INDEX,
                    SerializeUtil.serialize(buildRedisSessionKey(id)));
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]",id);
        }
    }



    @Override
    public Session getSession(Serializable id) {
        if (id == null)
            throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            byte[] value = getJedisManager().getValueByKey(DB_INDEX, SerializeUtil
                    .serialize(buildRedisSessionKey(id)));
            session = SerializeUtil.deserialize(value, Session.class);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取session异常，id:[%s]",id);
        }
        return session;
    }

    public Collection<Session> getAllSessions() {
        Collection<Session> sessions = null;
        try {
            sessions = getJedisManager().AllSession(DB_INDEX,REDIS_SHIRO_SESSION);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取全部session异常");
        }

        return sessions;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
