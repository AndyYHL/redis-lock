package com.example.algorithm;

import com.example.algorithm.service.IUserService;
import com.example.algorithm.service.MyInterceptor;
import com.example.algorithm.service.UserService;
import com.example.algorithm.service.UserService1;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;

@SpringBootTest
class AlgorithmApplicationTests {

    @Test
    void contextLoads() {
        UserService target = new UserService();
        UserService1 target1 = new UserService1();
        IUserService userService = (UserService) this.getUserService(UserService.class, target);
        IUserService userService1 = (UserService1) this.getUserService(UserService1.class, target1);
        userService.say();
        userService.say2();

        userService1.say();
        userService1.say2();
    }

    public Object getUserService(Class<?> clazz, IUserService o) {
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(clazz);
        // 设置enhancer的回调对象
        enhancer.setCallback(new MyInterceptor(o));
        // 创建代理对象
        return enhancer.create();
    }

}
