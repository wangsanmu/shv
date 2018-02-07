package com.blog.controller;

import com.blog.domin.User;
import com.blog.service.userService.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
@Transactional
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private MockHttpSession mockHttpSession;


    @Autowired
    private DefaultWebSecurityManager securityManager;


    @Before
    public void setup() {
        // init applicationContext
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void submitlogin() throws Exception {
        User user = new User();
        user.setEmail("466348638@qq.com");
        user.setPassword("lovesen");
        System.out.println("立刻解决了的快速减肥");
        String responseString = mockMvc.perform(MockMvcRequestBuilders.get("/userLogin/submitLogin").param("email","466348638@qq.com")
                        .param("password","lovesen").param("remeberme","true").session(mockHttpSession)//请求的url,请求的方法是get
                .contentType(MediaType.APPLICATION_JSON) //数据的格式//添加参数
                ).andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
    }

}