package com.example.algorithm.util;

import java.util.Arrays;

/**
 * 二分查找
 *
 * @author Administrator
 * @date 2021-11-4
 **/
public class DichotomyUtils {
    private int[] a;
    public DichotomyUtils(int[] keys) {

        // defensive copy
        a = new int[keys.length];
        for (int i = 0; i < keys.length; i++)
            a[i] = keys[i];

        // sort the integers
        Arrays.sort(a);

        // check for duplicates
        for (int i = 1; i < a.length; i++)
            if (a[i] == a[i-1])
                throw new IllegalArgumentException("Argument arrays contains duplicate keys.");
    }

    public boolean contains(int key) {
        return rank(key) != -1;
    }

    public int rank(int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }
}
