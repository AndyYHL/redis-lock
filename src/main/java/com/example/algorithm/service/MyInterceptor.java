package com.example.algorithm.service;

import lombok.Data;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * MyInterceptor描述
 *
 * @author Administrator-YHL
 * @date 2023-3-10
 **/
@Data
public class MyInterceptor implements MethodInterceptor {
    private IUserService userService;

    public MyInterceptor(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //System.out.println("cglib before");
        // 调用代理类FastClass对象
        Object result =  methodProxy.invokeSuper(o, objects);
//        Object result = methodProxy.invoke(target, objects);
        //System.out.println("cglib after");
        return result;
    }
}
