package com.example.myapplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StackType {
    /**
     * 每日温度
     * 请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
     *
     * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
     *
     */
    //暴力解法
    public int[] dailyTemperature(int[] T){
        int length = T.length;
        int[] result = new int[length];

        for (int  i = 0; i < length; i ++){
            for (int j = i + 1; j < length;j++){
                if (T[j] > T[i]){
                    result[i] = j - i;
                    break;
                }
            }
        }
        return result;
    }

    //使用栈
    public int[] dailyTemperatureForStack(int[] T){
        int length = T.length;
        int[] result = new int[length];

        Stack<Integer> stack = new Stack();
        for (int i = 0; i < length;i++){
            int temp = T[i];
            while (!stack.isEmpty() && temp > T[stack.peek()]){
                int preIndex = stack.pop();
                result[preIndex] = i - preIndex;
            }
            stack.push(i);
        }
        return result;
    }

    /**
     * 有效的括号
     * 括号闭合类型不匹配
     * 有剩余的无法抵消的左括号
     * 有无法抵消的右括号/先出现右括号
     */
    public boolean isValid(String s){
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length();i++){
            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{'){
                stack.push(s.charAt(i));
            }else {
                if (stack.isEmpty()){
                    return false;
                }
                if (s.charAt(i) == ')' && stack.pop() != '('){
                    return false;
                }
                if (s.charAt(i) == ']' && stack.pop() != '['){
                    return false;
                }
                if (s.charAt(i) == '}' && stack.pop() != '{'){
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }


}
