package com.example.algorithm.util;

import java.util.Arrays;

/**
 * @author Toyou-UI
 */
public class QuickSortUtils {

    private static final int[] SOURCE_ARRAY = {1, 5, 9, 6, 4, 3, 2, 1, 7, 0, 5, 8};

    public static void main(String[] args) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(SOURCE_ARRAY, SOURCE_ARRAY.length);

        int[] ints = quickSort(arr, 0, arr.length - 1);
        for (int anInt : ints) {
            System.out.print(anInt + " ");
        }
        System.out.println();
    }

    public static int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    private static int partition(int[] arr, int left, int right) {
        // 设定基准值（pivot）
        int index = left + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[left]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, left, index - 1);
        return index - 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
