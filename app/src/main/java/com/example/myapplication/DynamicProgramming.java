package com.example.myapplication;

public class DynamicProgramming {
    /**
     * 动态规划：将待求解问题分解成若干个子问题，先求解子问题，然后从这些子问题的解得到原问题的解。
     * 路径问题
     * 回文串问题
     * 买卖股票问题
     * 子序列问题
     * 打家劫舍问题
     * 其他问题 ：爬楼梯/斐波拉契
     *
     */

    /**
     * 爬楼梯问题：
     * 思路：如果最后一步爬1步，这种情况下有f(n-1)种方法。
     * 如果最后一步爬2级，共有f(n-2)种方法
     * @param n
     * @return
     */
    public int climbStairs(int n){
        if (n == 1){
            return 1;
        }
        if (n == 2){
            return 2;
        }
        return climbStairs(n -1) + climbStairs(n -2);
    }

    public int uniquePaths(int m,int n){
        if (m == 1 && n == 1){
            return 1;
        }
        int[][] dp = new int[m+1][n+1];
        for (int i = 0;i < m;i++){
            dp[i][0] = 1;
        }
        for (int i = 0; i < n;i++){
            dp[0][i] = 1;
        }
        for (int i = 0; i < m;i++){
            for (int j = 0; j < n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

}
