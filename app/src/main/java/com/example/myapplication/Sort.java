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
        for (int i = 0; i < n - 1;i++){
            for (int j = 1; j < n - i -1; j++){
                if (aar[j ] > aar[j + 1]){
                    swap(aar,j,j+1);
                }
            }
        }
    }

    /**
     * 优化
     */
    public void BubbleSort1(int[] aar){
        for (int i = aar.length - 1; i>0 ; i--){
            boolean isBreak = false;
            for (int j = 0; j < i; j++){
                if (aar[j] > aar[j+1]){
                    swap(aar,j,j+1);
                    isBreak = true;
                }
            }
            if (!isBreak){
                break;
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

    public static int[] insertSort(int[] aar){
        int cuurent,preIndex;
        for(int i = 1; i < aar.length;i++){
            cuurent = aar[i];
            preIndex = i - 1;
            while (preIndex >= 0 && cuurent < aar[preIndex]){
                aar[preIndex+1] = aar[preIndex];
                preIndex--;
            }
            aar[preIndex + 1] = cuurent;
        }
        return aar;
    }

    public void insert(int[] aar){
        for (int i = 1; i < aar.length;i++){
            int insertIndex = i;
            int insertValue = aar[i];
            while (insertIndex > 0 && insertValue < aar[insertIndex - 1]){
                aar[insertIndex] = aar[insertIndex-1];
                insertIndex--;
            }
            aar[insertIndex-1] = insertValue;
        }
    }

    public static String toString(int[] arr){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length;i++){
            sb.append(arr[i]).append("---");
        }
        return sb.toString();
    }

    public static void main(String[] args){
        int[] a = {3,9,1,7};
        System.out.println(toString(insertSort(a)));
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

    /**
     * 快排
     */
    private void quickSort(int[] arr,int low,int high){
        int pivot = part(arr,low,high);
        quickSort(arr,low,pivot-1);
        quickSort(arr,pivot+1,high);
    }

    private int part(int[] arr,int low,int high){
        int pre = arr[low];
        while (low < high){
            while (low < high && arr[high] >= pre){
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= pre){
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = pre;
        return low;
    }


}
