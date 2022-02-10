package com.example.algorithm.util;

import lombok.SneakyThrows;
import org.apache.commons.collections4.map.PassiveExpiringMap;

/**
 * Test描述
 *
 * @author Administrator
 * @date 2021-11-1
 **/
public class Test {
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(60));
        System.out.println(Integer.toBinaryString(30));
        System.out.println(Integer.toBinaryString(60>>1)+"==="+(60>>1));
        System.out.println(Integer.toBinaryString(60>>2)+"==="+(60>>2));
        System.out.println(Integer.toBinaryString(60>>3)+"==="+(60>>3));
        System.out.println(Integer.toBinaryString(60<<1)+"==="+(60<<1));
        System.out.println(Integer.toBinaryString(60<<2)+"==="+(60<<2));
        System.out.println(Integer.toBinaryString(60<<3)+"==="+(60<<3));
        System.out.println(Integer.toBinaryString(2^1)+"==="+(2^1));
    }
}
