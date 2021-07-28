package com.example.myapplication;

public class Sort {
    /**
     * 冒泡排序
     *
     *  关键：每一轮的排序之后，最后一个是最大的，下一轮循环则不需要再比较
     *  复杂度为o(n^2),空间复杂度为o(1)
     */
    public void BubbleSort(int[] aar){
        int n = aar.length;
        for (int i = 0; i < n;i++){
            for (int j = 1; j < n - i; j++){
                if (aar[j -1] > aar[j]){
                    swap(aar,j -1,j);
                }
            }
        }
    }

    /**
     * 选择排序
     */
    public void SelectSort(int[] aar){
        int n = aar.length;
        for (int i = 0;i < n;i++){
            int minIndex = i;
            for (int j = i+1; j < n; j++){
                if (aar[j] < aar[minIndex]){
                    minIndex = j;
                }
            }
            if (minIndex != i){
                swap(aar,i,minIndex);
            }
        }
    }



    /**
     * 进行交换
     * @param i
     * @param j
     */
    private  void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
