package com.example.myapplication;

/**
 * 统计素数的多少
 */
public class Prime {
    /**
     * 暴力算法
     * @param n
     * @return
     */
    public int bf(int n){
        int k = 0;
        for (int i = 2; i < n;i++){
            if (isPrime(i)){
                k++;
            }
        }
        return k;
    }

    /**
     * 判断是否时素数，只需要遍历sqat(i)即可
     * @param i
     * @return
     */
    private boolean isPrime(int i) {
        for (int j = 2; j * j < i;j++){
            if (i % j == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 埃筛法
     */
    public int findPrime(int n){
        boolean[] isPrime = new boolean[n];
        int count = 0;
        for (int i = 2; i < n; i++){
            if (!isPrime[i]){
                count++;
            }
            for (int j = i * i; j  < n; j+= i){
                isPrime[j] = true;
            }
        }
        return count;
    }
}
