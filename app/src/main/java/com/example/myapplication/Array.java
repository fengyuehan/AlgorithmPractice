package com.example.myapplication;

/**
 * 数组相关
 */
public class Array {
    /**
     * 原地删除数组中的重复元素
     */
    /**
     * 双指针法,针对排序好的
     */
    public int removeSame(int[] nums){
        if (nums.length == 0){
            return 0;
        }
        int i = 0;
        /**
         * 不相等则将赋值，相等则进行下一次循环，调过这次
         */
        for (int j = 1; j < nums.length; j++){
            if (nums[j] != nums[i]){
                i++;
                nums[i] = nums[j];
            }
        }
        return i+1;
    }
}
