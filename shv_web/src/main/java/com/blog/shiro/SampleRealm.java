package com.blog.shiro;

import com.blog.domin.User;
import com.blog.enums.UserState;
import com.blog.service.userService.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SampleRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("到我了");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //调用数据库验证数据是否正确
//        User user = userService.login(token.getUsername(),token.getPassword());
//        if(null == user){
//            throw new AccountException("账号或密码不正确!");
//        }else if(UserState.BAN.getIndex() == user.getStatus()){
//            throw new DisabledAccountException("账号已经禁止登陆");
//        }else{
//            user.setLastLoginTime(new Date());
//            //数据库更新用户信息
//            userService.updateByPrimaryKey(user);
//        }
        User user =  new User("wangsen",token.getUsername(),token.getPassword().toString());
        return new SimpleAuthenticationInfo(user,token.getPassword(),getName());
    }
}
