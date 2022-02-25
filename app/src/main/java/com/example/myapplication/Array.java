package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 数组相关
 */
public class Array {
    /**
     * 原地删除数组中的重复元素
     */
    /**
     * 双指针法,针对排序好的
     *
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

    /**
     * 寻找数组的中心下标
     *
     * 中心坐标左侧的所有元素的值与右边左右的值相等
     *
     * 采用双指针，一个指针从前面累加，一个指针从末尾相减，看能不能找到一个位置，使两个指针重合
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int centerInde(int[] nums){
        int sum = Arrays.stream(nums).sum();
        int total = 0;
        for (int i = 0; i < nums.length; i++){
            total += nums[i];
            if (total == sum){
                return i;
            }
            sum = sum - nums[i];
        }
        return -1;
    }

    /**
     * x的平方根
     *
     * 二分法（在一个区见寻找一个数）
     * 牛顿迭代(n^2 = x  n = x / n  一直求 n 和 x/n 的平均值，就会无限接近)
     */
    public int binarySearch(int x){
        int low = 0;
        int high = x;
        while (low < high){
            int middle = low + (high - low) /2;
            if (middle * middle == x){
                return middle;
            }else if (middle * middle < x){
                low = middle + 1;
            }else {
                high = middle -1;
            }
        }
        return -1;
    }

    public double sqrt(double i, int x){
        double res = (i + x / i)/2;
        if (res == i){
            return i;
        }else {
            return sqrt(res,x);
        }
    }

    /**
     * 三个数的最大乘积
     * 先排序，然后取绝对值最大的三个数
     * 线性扫描
     */

    public int getMax1(int[] nums){
        Arrays.sort(nums);
        int n = nums.length;
        return Math.max(nums[0] * nums[1]*nums[n-1],nums[n-1] * nums[n -2] * nums[n -3]);
    }

    public int getMax2(int[] nums){
        int min1 = Integer.MAX_VALUE,min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE,max2 = Integer.MAX_VALUE,max3 = Integer.MIN_VALUE;
        for (int x:nums){
            if (x < min1){
                min2 = min1;
                min1 = x;
            }else if (x < min2){
                min2 = x;
            }
            if (x > max1){
                max3 = max2;
                max2 = max1;
                max1 = x;
            }else if (x > max2){
                max3 = max2;
                max2 = x;
            }else if (x > max3){
                max3 = x;
            }
        }
        return Math.max(min1 * min2 * max1 ,max1 * max2 * max3);
    }

    /**
     * 两个数之和
     * 乱序
     * 1、暴力解决   复杂度  O（n^2）
     * 2、map   复杂度  O（N）  空间复杂度   O（n）
     * （针对有序的数组）
     * 3、二分查找法    复杂度  O（n*log(n)）
     * 4、双指针  复杂度为o(n)
     */
    public int[] sum(int[] nums,int target){
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            if (map.containsKey(target  - nums[i])){
                return new int[]{map.get(target - nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return new int[]{0};
    }
    /**
     * 升序排列数组
     */
    public int[] sum2(int[] nums,int taget){
        for (int i = 0; i < nums.length; i++){
            int low = i,high = nums.length - 1;
            while (low <= high){
                int middle = low + (high - low)/2;
                if (nums[middle] == taget){
                    return new int[]{i,middle};
                }else if (nums[middle] > taget){
                    high = middle -1;
                }else if (nums[middle] < taget){
                    low = middle + 1;
                }
            }
        }
        return new int[]{};
    }

    public int[] twoPoint(int[] nums,int target){
        int low = 0,high = nums.length -1;
        while (low <= high){
            int sum = nums[low] + nums[high];
            if (sum == target){
                return new int[]{low,high};
            }else if (sum < target){
                low ++;
            }else if (sum > target){
                high--;
            }
        }

        return new int[]{};
    }

    /**
     * 排列硬币
     * 1、迭代方法
     * 2、二分查找
     */
    public int arrangeCoin(int n){
        for (int i = 1; i <= n; i++){
            n = n - i;
            if (n <= i){
                return i;
            }
        }
        return 0;
    }

    public int arrangeCoin2(int n){
        int low = 0,high = n;
        while (low <= high){
            int mid = low + (high - low) /2;
            int sum = ((mid + 1) * mid) /2;
            if (sum == n){
                return mid;
            }else if (sum < n){
                low++;
            }else {
                high--;
            }
        }
        return high;
    }

    /**
     * 合并两个有序数组，合并后继续成为一个有序数组
     * 1、暴力解决，先将两个数组合并，然后排序 时间复杂度(m+n)log(m+n)
     * 2、双指针  空间换时间 时间复杂度  m +n 这是从前往后作比较
     * 3、双指针  从后往前作比较  这样就不需要增加空间复杂度
     */
    public int[] merge(int[] nums1,int m,int nums2,int n){
        //m为两个数组长度之和
        System.arraycopy(nums2,0,nums1,m,n);
        Arrays.sort(nums1);
        return nums1;
    }

    public int[] merge2(int[] nums1,int m,int[] nums2,int n){
        //先将num保留一个副本
        int[] num_copy = new int[m];
        System.arraycopy(nums1,0,num_copy,0,m);

        int p1 = 0;//指向num_copy
        int p2 = 0;//指向num2
        int p = 0; //指向num1

        while (p1 < m && p2 < n){
            nums1[p++] = num_copy[p1] < nums2[p2] ? num_copy[p1++]:nums2[p2++];
        }
        if (p1 < m){
            System.arraycopy(num_copy,p1,nums1,p1 + p2,m - p1 -p2);
        }
        if (p2 < n){
            System.arraycopy(num_copy,p2,nums1,p1 + p2,m - p1 -p2);
        }
        return nums1;
    }

    public int[] merge3(int[] nums1,int m,int[] nums2,int n){
        int p1 = m -1;
        int p2 = n -1;
        int p = m + n -1;
        while (p1 >= 0 && p2 >= 0){
            nums1[p--] = nums1[p1] < nums2[p2] ? nums2[p2--] : nums1[p--];
        }
        System.arraycopy(nums2,0,nums1,0,p2 + 1);
        return nums1;
    }

    /**
     * 子数组最大平均数
     * 利用滑动窗口
     */
    public double findMaxAverage(int[] num,int k){
        int sum = 0;
        if (k > num.length){
            for (int i = 0; i < num.length; i++){
                sum += num[i];
            }
            return 1.0 * sum / k;
        }
        for (int i = 0; i < k; i++){
            sum += num[i];
        }
        int max = sum;
        for (int i = k; i < num.length; i++){
            sum = sum - num[i - k] + num[k];
            max = Math.max(sum,max);
        }
        return 1.0 * max / k;
    }

    /**
     * 寻找最长连续递增序列
     * 贪心算法：局部求最优解不影响全局求最优解
     */
    public int findMaxLength(int[] nums){
        int start = 0;
        int max = 0;
        for (int i = 1; i < nums.length; i++){
            if (nums[i] < nums[i -1]){
                start = i;
            }
            max = Math.max(max,i - start + 1);
        }
        return max;
    }

    /**
     * 柠檬水找零
     */
    public boolean change(int[] nums){
        int five = 0;
        int ten = 0;
        for (int num:nums){
            if (num == 5){
                five++;
            }else if (num == 10){
                if (five == 0){
                    return false;
                }
                five--;
                ten++;
            }else {
                if (five > 0 && ten < 0){
                    five--;
                    ten--;
                }else if (five > 3){
                    five -= 3;
                }else {
                    return false;
                }
            }
        }
        return true;
    }


}
