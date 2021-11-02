package com.example.algorithm.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 大顶排（由小到大排序）
 * 小顶排（由大到小排序）
 * 顶排处理数据排序工具
 *
 * @author Toyou-UI
 */
public class MaxAndMinHeapSortUtils {

    private static final int CONSTANT = 2;

    public static void main(String[] args) {
        /*
         * 原始数据
         * ,88,54,58,96,98,78,48,58,52,35,45,78,14,25,36,65,45,82,32,54,22,26,59,71,27
         */
        int[] originalData = {7, 3, 8, 5, 1, 2, 9, 4, 6, 3, 8, 8, 9, 7, 5, 6, 2, 4, 5, 8, 7, 4, 5};
        //获取数据层数
        System.out.println("数据层数:" + getTierNum(originalData));
        //打印顶排前的树形图
        printNodeMap(originalData);
        int[] maxHeap = maxHeap(originalData);
        //打印顶排后的树形图
        printNodeMap(maxHeap);
        //获取指定层数的数据
        List<Integer> integers = assignTierData(maxHeap, 4);
        System.out.println(integers);
        for (int originalDatum : originalData) {
            System.out.print(originalDatum + " ");
        }
        System.out.println();
        System.out.println("大顶排结束------------");

        int[] minHeap = minHeap(originalData);
        //打印顶排后的树形图
        printNodeMap(minHeap);
        //获取指定层数的数据
        List<Integer> data = assignTierData(minHeap, 4);
        System.out.println(data);
        for (int originalDatum : originalData) {
            System.out.print(originalDatum + " ");
        }
        System.out.println();
    }

    /**
     * 大顶排处理（由小到大排序）
     *
     * @param originalData 原始数组数据
     * @return 排序后的数据
     */
    public static int[] maxHeap(int[] originalData) {
        for (int i = originalData.length; i > 0; i--) {
            buildMaxHeap(originalData, i);
            swap(originalData, i);
        }
        return originalData;
    }

    /**
     * 大顶排数据处理
     *
     * @param heap 原数组数据
     * @param len  除去已排序节点的最后一个节点（未排序的最后一个节点）
     */
    private static void buildMaxHeap(int[] heap, int len) {
        for (int i = len / CONSTANT - 1; i >= 0; i--) {
            //根节点大于左子树
            if ((2 * i + 1) < len && heap[i] < heap[2 * i + 1]) {
                heap[i] = heap[i] ^ heap[2 * i + 1];
                heap[2 * i + 1] = heap[i] ^ heap[2 * i + 1];
                heap[i] = heap[i] ^ heap[2 * i + 1];
                //检查交换后的左子树是否满足大顶堆性质 如果不满足 则重新调整子树结构
                boolean flag1 = 2 * (2 * i + 1) + 1 < len && heap[2 * i + 1] < heap[2 * (2 * i + 1) + 1];
                boolean flag2 = 2 * (2 * i + 1) + 2 < len && heap[2 * i + 1] < heap[2 * (2 * i + 1) + 2];
                if (flag1 || flag2) {
                    buildMaxHeap(heap, len);
                }
            }
            //根节点大于右子树
            if ((2 * i + 2) < len && heap[i] < heap[2 * i + 2]) {
                heap[i] = heap[i] ^ heap[2 * i + 2];
                heap[2 * i + 2] = heap[i] ^ heap[2 * i + 2];
                heap[i] = heap[i] ^ heap[2 * i + 2];
                //检查交换后的右子树是否满足大顶堆性质 如果不满足 则重新调整子树结构
                boolean flag3 = 2 * (2 * i + 2) + 1 < len && heap[2 * i + 2] < heap[2 * (2 * i + 2) + 1];
                boolean flag4 = 2 * (2 * i + 2) + 2 < len && heap[2 * i + 2] < heap[2 * (2 * i + 2) + 2];
                if (flag3 || flag4) {
                    buildMaxHeap(heap, len);
                }
            }
        }
    }

    /**
     * 小顶排处理（由大到小排序）
     *
     * @param originalData 原始数组数据
     * @return 排序后的数据
     */
    public static int[] minHeap(int[] originalData) {
        for (int i = originalData.length; i > 0; i--) {
            buildMinHeap(originalData, i);
            swap(originalData, i);
        }
        return originalData;
    }

    /**
     * 小顶排数据处理
     *
     * @param heap 原数组数据
     * @param len  除去已排序节点的最后一个节点（未排序的最后一个节点）
     */
    private static void buildMinHeap(int[] heap, int len) {
        for (int i = len / CONSTANT - 1; i >= 0; i--) {
            //根节点小于左子树
            if ((2 * i + 1) < len && heap[i] > heap[2 * i + 1]) {
                heap[i] = heap[i] ^ heap[2 * i + 1];
                heap[2 * i + 1] = heap[i] ^ heap[2 * i + 1];
                heap[i] = heap[i] ^ heap[2 * i + 1];
                //检查交换后的左子树是否满足大顶堆性质 如果不满足 则重新调整子树结构
                boolean flag1 = 2 * (2 * i + 1) + 1 < len && heap[2 * i + 1] > heap[2 * (2 * i + 1) + 1];
                boolean flag2 = 2 * (2 * i + 1) + 2 < len && heap[2 * i + 1] > heap[2 * (2 * i + 1) + 2];
                if (flag1 || flag2) {
                    buildMinHeap(heap, len);
                }
            }
            //根节点小于右子树
            if ((2 * i + 2) < len && heap[i] > heap[2 * i + 2]) {
                heap[i] = heap[i] ^ heap[2 * i + 2];
                heap[2 * i + 2] = heap[i] ^ heap[2 * i + 2];
                heap[i] = heap[i] ^ heap[2 * i + 2];
                //检查交换后的右子树是否满足大顶堆性质 如果不满足 则重新调整子树结构
                boolean flag3 = 2 * (2 * i + 2) + 1 < len && heap[2 * i + 2] > heap[2 * (2 * i + 2) + 1];
                boolean flag4 = 2 * (2 * i + 2) + 2 < len && heap[2 * i + 2] > heap[2 * (2 * i + 2) + 2];
                if (flag3 || flag4) {
                    buildMinHeap(heap, len);
                }
            }
        }
    }

    /**
     * 移动元素值（交换根节点和数组末尾元素的值,把根节点的最大值和最后一个节点交换）
     *
     * @param heap 数组
     * @param len  下标
     */
    private static void swap(int[] heap, int len) {
        if (heap[0] != heap[len - 1]) {
            heap[0] = heap[0] ^ heap[len - 1];
            heap[len - 1] = heap[0] ^ heap[len - 1];
            heap[0] = heap[0] ^ heap[len - 1];
        }
    }

    /**
     * 获取数组所映射的层数
     *
     * @param heap 数组数据
     * @return 该数组所映射的层数
     */
    public static int getTierNum(int[] heap) {
        return (int) (Math.log(heap.length) / Math.log(2)) + 1;
    }

    /**
     * 获取指定层数据
     *
     * @param heap    排好序的数组
     * @param tierNum 层数
     * @return 指定层数据组
     */
    public static List<Integer> assignTierData(int[] heap, int tierNum) {
        int tier = (int) (Math.log(heap.length) / Math.log(2)) + 1;
        if (tierNum <= 0 || tierNum > tier) {
            throw new RuntimeException("获取层数tierNum：" + tierNum + "，数据总层数：" + tier + "，获取层数tierNum错误！");
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < heap.length; i++) {
            if (i >= Math.pow(2, tierNum - 1) - 1 && i < Math.pow(2, tierNum) - 1) {
                list.add(heap[i]);
            }
        }
        return list;
    }

    /**
     * 打印数组映射的树形图
     *
     * @param heap 数组
     */
    public static void printNodeMap(int[] heap) {
        int length = heap.length;
        int tier = (int) (Math.log(length) / Math.log(2)) + 1;
        for (int i = 0; i < tier; i++) {
            System.out.print(getString((int) Math.pow(2, tier - i - 1) - 1));
            for (int j = 0; j < length; j++) {
                if (j >= Math.pow(2, i) - 1 && j < Math.pow(2, i + 1) - 1) {
                    System.out.print(heap[j]);
                    System.out.print(getString((int) Math.pow(2, tier - i) - 1));
                }
            }
            System.out.println();
        }
        System.out.println("-------- -------- --------");
    }

    private static String getString(int num) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

}
