package com.example.algorithm.util;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

/**
 * @author ：dulinji
 * @version ：v1
 * @description ：对数组或list对象进行操作获取的公共操作类
 * @date ：Created in 2021/11/2 16:53
 * @modified By：
 */

@Slf4j
public class ArryAndListUtils {

    /**
     * 对传入list 进行倒排
     *
     * @param arryList   参数list集合
     * @param comparator 比较器
     * @return 排序后List
     */
    public static Object arrySortDesc(List<?> arryList, Comparator comparator) {
        arryList.sort(comparator.reversed());
        return arryList;
    }

    /**
     * 对传入的list 返回最大值
     *
     * @param arryList   参数list集合
     * @param comparator 比较器
     * @return 最大值 （对象）
     */
    public static Object getMax(List<?> arryList, Comparator comparator) {
        arryList.sort(comparator);
        return arryList.get(0);
    }


    /**
     * 对传入的list 返回最小值
     *
     * @param arryList   参数list集合
     * @param comparator 比较器
     * @return 最小值 （对象）
     */
    public static Object getMin(List<?> arryList, Comparator comparator) {
        arryList.sort(comparator);
        return arryList.get(arryList.size() - 1);
    }


    /**
     * 对传入的list 返回平均值
     *
     * @param arryList  参数list集合
     * @param collector 收集器
     * @return 平均值 Double
     */
    public static Double getAvg(List<?> arryList, Collector collector) {
        return (Double) arryList.stream().collect(collector);
    }


}
