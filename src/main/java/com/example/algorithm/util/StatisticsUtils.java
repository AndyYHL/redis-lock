package com.example.algorithm.util;

import org.springframework.stereotype.Component;

/**
 * @author Toyou-UI
 */
@Component
public class StatisticsUtils {

    public static void main(String[] args) {
        double[] originalData = {73, 72, 71, 69, 68, 67};
        double standardVariance = getStandardVariance(originalData);
        System.out.println(standardVariance);
        double sampleVariance = getSampleVariance(originalData);
        System.out.println(sampleVariance);
    }

    /**
     * 获取一组数据的标准差（总体标准差）
     *
     * @param originalData 原始数组数据
     * @return 总体标准差
     */
    public static double getStandardVariance(double[] originalData) {
        return Math.sqrt(getVariance(originalData));
    }

    /**
     * 获取一组数据的标准差（样本标准差）
     *
     * @param originalData 原始数组数据
     * @return 样本标准差
     */
    public static double getSampleVariance(double[] originalData) {
        double sampleVariance = getVarianceCount(originalData) / (originalData.length - 1);
        return Math.sqrt(sampleVariance);
    }

    /**
     * 获取一组数据的方差
     *
     * @param originalData 原始数组数据
     * @return 方差
     */
    public static double getVariance(double[] originalData) {
        return getVarianceCount(originalData) / originalData.length;
    }

    private static double getVarianceCount(double[] originalData) {
        //获取平均值
        double countNum = 0;
        for (double originalDatum : originalData) {
            countNum += originalDatum;
        }
        double avg = countNum / originalData.length;
        //获取差值平方数和
        double variance = 0;
        for (double originalDatum : originalData) {
            variance += Math.pow((originalDatum - avg), 2);
        }
        return variance;
    }


}
