package com.example.algorithm.util;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test描述
 *
 * @author Administrator
 * @date 2021-11-1
 **/
public class Test {
    @SneakyThrows
    public static void main(String[] args) {
        /*List<String> a = new ArrayList<>();
        List<Integer> statusList = Arrays.asList(1, 2);
        System.out.println(statusList);
        System.out.println(statusList.contains(1));
        System.out.println(statusList.contains(3));
        statusList.set(1,3);
        System.out.println(statusList.contains(3));*/
        List<String> d = strddd("1");
        d.forEach(System.out::println);

        List<String> dc = strddd("2","3","4");
        dc.forEach(System.out::println);
    }

    public static List<String> strddd(String ... chars) {
        return Arrays.asList(chars);
    }
}
