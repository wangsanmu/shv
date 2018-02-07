package com.blog.common.shiro.listerner;

import com.blog.common.shiro.sesson.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class CustomSessionListener implements SessionListener {

    private ShiroSessionRepository shiroSessionRepository;

    /**
     * 会话开始
     * @param session
     */
    public void onStart(Session session) {
        System.out.println("on Start");
    }

    /**
     * 会话结束
     * @param session
     */
    public void onStop(Session session) {
        System.out.println("on Stop");
    }

    /**
     * 会话过期
     * @param session
     */
    public void onExpiration(Session session) {
        getShiroSessionRepository().deleteSession(session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }
}
