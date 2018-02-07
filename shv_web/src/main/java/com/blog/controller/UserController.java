package com.blog.controller;

import com.blog.common.shiro.token.TokenManager;
import com.blog.domin.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("userLogin")
public class UserController {

    @RequestMapping(value = "submitLogin",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> submitlogin(User user, Boolean remeberme, HttpServletRequest request){
        user = TokenManager.login(user,remeberme,request);
        if(user!= null){
            System.out.println("登陆成功");
        }
        return  null;
    }
}
