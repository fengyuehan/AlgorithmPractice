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

    /**
     * 一个机器人位于一个 m x n 网格的左上角 ,机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角
     * 机器人走到（i,j）的位置，有两种方式：
     * 一种是从 (i-1, j) 这个位置走一步到达
     * 一种是从(i, j - 1) 这个位置走一步到达
     * 因为是计算所有可能的步骤，所以是把所有可能走的路径都加起来，所以关系式是 dp[i] [j] = dp[i-1] [j] + dp[i] [j-1]。
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m,int n){
        if (m == 1 && n == 1){
            return 1;
        }
        int[][] dp = new int[m+1][n+1];
        for (int i = 0;i < m;i++){
            dp[i][0] = 1;
        }
        for (int j = 0; j < n;j++){
            dp[0][j] = 1;
        }
        for (int i = 0; i < m;i++){
            for (int j = 0; j < n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    /**
     * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     * 跟上面的题目唯一的区别是dp[i] [j] = min(dp[i-1][j]，dp[i][j-1]) + arr[i][j];// arr[i][j] 表示网格种的值  求最小值
     *
     * 初始值的计算
     * 当 dp[i] [j] 中，如果 i 或者 j 有一个为 0，那么还能使用关系式吗？答是不能的，因为这个时候把 i - 1 或者 j - 1，就变成负数了，数组就会出问题了，所以我们的初始值是计算出所有的 dp[0] [0….n-1] 和所有的 dp[0….m-1] [0]。这个还是非常容易计算的，相当于计算机图中的最上面一行和左边一列。因此初始值如下：
     * dp[0] [j] = arr[0] [j] + dp[0] [j-1]; // 相当于最上面一行，机器人只能一直往左走
     * dp[i] [0] = arr[i] [0] + dp[i] [0];  // 相当于最左面一列，机器人只能一直往下走
     *
     * @return
     */
    public int uniquePaths2(int[][] arr){
        int m = arr.length;
        int n = arr[0].length;
        if (m <= 0 || n <= 0){
            return 0;
        }

        int[][] dp = new int[m][n];
        dp[0][0] = arr[0][0];
        for (int i = 0; i < m; i++){
            dp[i][0] = dp[i-1][0] + dp[i][0];
        }
        for (int j = 0; j < n;j++){
            dp[0][j] = dp[0][j -1] + dp[0][j];
        }

        for (int i = 0; i < m;i++){
            for (int j = 0; i < n; j++){
                dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1]) + arr[i][j];
            }
        }
        return dp[m-1][n-1];
    }

    /**
     * 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1,String word2){
        int m = word1.length();
        int n = word2.length();

        /**
         * 始终需要记住的是dp表示次数
         * 例如dp[i][j] 表示将word1的前i个字符变成word2的前j个字符需要的次数
         */
        int dp[][] = new int[m + 1][n + 1];
        //""经过0次变换成为""
        dp[0][0] = 0;

        //base case
        //1.dp的第一列初始化：表示word1经过多少次删除操作成为空串""
        for(int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }

        //1.dp的第一列初始化：表示空串""经过多少次添加操作成为word2
        for(int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }


        //  自底向上求解
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    //dp[i - 1][j] + 1 表示删除word1中i处的字符（因为该字符不在word2的j中），操作次数+1
                    //dp[i][j - 1] + 1 表示word1在i之后添加一个字符匹配word2的j处字符，操作次数+1
                    //dp[i - 1][j - 1] + 1 表示将word1的i处字符替换为word2的j处字符，操作次数+1
                    dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1);
                }
            }
        }

        // 储存着整个 word1 和 word2 的最小编辑距离
        return dp[m][n];
    }

    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    /**
     * 硬币：你有三种硬币，分别面值2元，5元和7元，每种硬币都足够多。买一本书需要27元，如何用最少的硬币组合正好付清
     */
    /**
     * 递归
     */
    public int getMinCoinCount(int x){
        if (x == 0){
            return 0;
        }
        int count = Integer.MAX_VALUE;

        if (x > 2 && getMinCoinCount(x - 2) != Integer.MAX_VALUE){
            count = Math.min(getMinCoinCount(x -2)+1,count);
        }
        if (x > 5 && getMinCoinCount(x - 5) != Integer.MAX_VALUE){
            count = Math.min(getMinCoinCount(x -5)+1,count);
        }
        if (x > 7 && getMinCoinCount(x - 7) != Integer.MAX_VALUE){
            count = Math.min(getMinCoinCount(x -7)+1,count);
        }

        return count;
    }

    /**
     * 动态规划
     */
    public int getMinCoinCount1(int[] arr,int M){
        int[] f = new int[M+1];
        f[0] = 0;
        for (int i  = 0; i < M;i++){
            f[i] = Integer.MAX_VALUE;
            for (int j = 0; j < arr.length;j++){
                if (i >= arr[j] && f[i - arr[j]] != Integer.MAX_VALUE){
                    f[i] = Math.min(f[i - arr[j]],f[i]);
                }
            }
        }
        if (f[M] == Integer.MAX_VALUE){
            f[M] = -1;
        }
        return f[M];
    }

    /**
     * 给出一个非负整数数组，你最初定位在数组的第一个位置。数组中的每个元素代表你在那个位置可以跳跃的最大长度。判断你是否能到达数组的最后一个位置。
     */

    public boolean canjump(int[] A){
        boolean[] f = new boolean[A.length];
        f[0] = true;
        for (int i  = 1; i < A.length;i++){
            f[i] = false;
            for (int j = 0; j < i; j++){
                if (f[j] && j + A[j] >= i){
                    f[i] = true;
                    break;
                }
            }
        }
        return f[A.length -1];
    }

    /**
     * 给你一根长度为n的绳子，请把绳子剪成整数长的m段（m、n都是整数，n>1并且m>1，m<=n），每段绳子的长度记为k[1],…,k[m]。请问k[1]*…*k[m]可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。（2 <= n <= 60）
     *
     */

    public int cutRope(int target){
        if(target == 2){
            return 1;
        }else if(target == 3){
            return 2;
        }

        int[] f = new int[target+1];
        for(int i=1;i<=3;i++){
            f[i] = i;
        }
        for(int i=4;i<=target;i++){
            for(int j=1;j<i;j++){
                f[i] = Math.max(f[i],j*f[i-j]);
            }
        }

        return f[target];
    }

    /**
     * 连续子数组的最大和
     */
    public int FindGreatestSumOfSubArray(int[] array) {
        int[] f = new int[array.length + 1];
        f[0] = 0;
        int ret = array[0];
        for(int i=1;i<=array.length;i++){
            f[i] = Math.max(array[i-1],f[i-1] + array[i-1]);
            ret = Math.max(ret,f[i]);
        }
        return ret;
    }

    public int FindGreatestSumOfSubArray1(int[] array) {
        int max = array[0];
        int sum = array[0];

        for(int i=1;i<=array.length;i++){
            sum = Math.max(sum + array[i],array[i]);
            if (sum > max){
                max = sum;
            }
        }
        return max;
    }

    public int FindGreatestSumOfSubArray2(int[] array) {
        int max = array[0];
        int sum = array[0];

        for(int i=1;i<=array.length;i++){
            sum += array[i];
            if (sum > max){
                max = sum;
            }
        }
        return max;
    }

}
