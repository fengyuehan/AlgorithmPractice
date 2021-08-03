package com.example.myapplication;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 字符串相关
 */
public class Characters {

    /**
     * 替换空格
     * 将一个字符串中的每个空格替换成“%20”。
     * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     */
    /**
     * 在内存足够的时候，可以向前或者向后遍历都可以
     * @param str
     * @return
     */
    public String replaceSpace(String str){
        if (TextUtils.isEmpty(str)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int len = str.length()-1;
        for (int i = len; i >= 0;i--){
            if (str.charAt(i) == ' '){
                sb.append("02%");
            }else {
                sb.append(str.charAt(i));
            }
        }
        return sb.reverse().toString();
    }
    /**
     * 当要求时间复杂度为O（n）的时候
     */
    public char[] replaceSpace2(String str){
        if (TextUtils.isEmpty(str)){
            return new char[]{};
        }
        int count = getBlackNum(str);
        //传入字符串的长度
        int oldLength = str.length();
        //拼接后字符串的长度
        int newLength = str.length() + 2 * count;
        char [] arr = new char[newLength];
        int originIndex = oldLength - 1;
        int newIndex = newLength -1;
        while (originIndex > 0 && oldLength != newIndex){
            if (arr[originIndex] == ' '){
                arr[newIndex--] = '0';
                arr[newIndex--] = '2';
                arr[newIndex--] = '%';
            }else {
                arr[newIndex--] = arr[originIndex];
            }
            originIndex--;
        }
        return arr;
    }

    /**
     * 计算空格数
     * @param str
     * @return
     */
    private int getBlackNum(String str) {
        int count = 0;
        for (int i = 0;i < str.length();i++){
            if (str.charAt(i) == ' '){
                count++;
            }
        }
        return count;
    }


    /**
     * 最长公共前缀
     * 思路：先进行排序，然后拿第一个和最后一个进行比较，例如：ab abc abcd ,如果第一个的第一个字符与最后一个的第一个字符相等，那么中间的也肯定相等。
     */
    public String longestCommonPrefix(String[] strs){
        if (strs == null || strs.length == 0){
            return "";
        }
        Arrays.sort(strs);
        char[] first = strs[0].toCharArray();
        char[] last = strs[strs.length-1].toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        int len = first.length < last.length ? first.length : last.length;
        int i = 0;
        while (i < len){
            if (first[i] == last[i]){
                stringBuilder.append(first[i]);
                i++;
            }else {
                break;
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 统计回文数
     * 直接计算字母出现的次数，因为只有双数才能构成回文
     */
    public int longestPalindrome(String s){
        HashSet<Character> set = new HashSet<>();
        int len = s.length();
        //统计字母出现的次数
        int count = 0;
        if (TextUtils.isEmpty(s)){
            return 0;
        }
        for (int i = 0; i < len; i++){
            if (set.contains(s.charAt(i))){
                set.remove(s.charAt(i));
                count++;
            }else {
                set.add(s.charAt(i));
            }
        }
        return set.isEmpty() ? count * 2:count *2 +1;
    }

    /**
     * 验证回文数
     * 用两个指针首尾比较，如果是回文数，肯定是相等的
     */
    public boolean isPalindrome(String s){
        if (s.length() == 0){
            return true;
        }
        int l = 0;
        int r = s.length() -1;
        while (l < r){
            if (!Character.isLetterOrDigit(s.charAt(l))){
                l++;
            }else if (!Character.isLetterOrDigit(s.charAt(r))){
                r--;
            }else {
                if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))){
                    return false;
                }
                l++;
                r--;
            }
        }
        return true;
    }

    /**
     * 如果没有空格这些的话判断一个字符串是不是回文数
     */
    public boolean isPalindrome2(String s){
        if (TextUtils.isEmpty(s)){
            return false;
        }
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length -1;
        while (left < right){
            if (chars[left++] != chars[right--]){
                return false;
            }
        }
        return true;
    }

    /**
     * 最长回文串
     */

}
