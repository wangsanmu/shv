package com.blog.common.shiro.token;

import com.blog.domin.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.http.HttpServletRequest;

public class TokenManager {

    /**
     * 获取当前登录的用户User对象
     * @return
     */
    public static User getToken(){
        User token = (User)SecurityUtils.getSubject().getPrincipal();
        return token ;
    }


    /**
     * 登录
     * @param user
     * @param rememberMe
     * @return
     */
    public static User login(User user, Boolean rememberMe, HttpServletRequest request){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword(),rememberMe,request.getRemoteHost());
        SecurityUtils.getSubject().login(token);
        return getToken();
    }
}
