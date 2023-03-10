package com.example.algorithm.service;

/**
 * UserService描述
 *
 * @author Administrator-YHL
 * @date 2023-3-10
 **/
public class UserService1 implements IUserService {
    @Override
    public void say() {
        System.out.println("hello UserService1");
    }

    @Override
    public void say2() {
        System.out.println("hello1 UserService1");
    }
}
