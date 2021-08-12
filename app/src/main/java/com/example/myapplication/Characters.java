package com.example.myapplication;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
     * 有一个文本串S，和一个模式串P，现在要查找P在S中的位置，怎么查找呢？
     * 暴力算法
     */
    public int longestPalindrome(String strA,String strB){
        //长串的长度
        char[] charsA = strA.toCharArray();
        char[] charsB = strB.toCharArray();
        int lengthA = charsA.length;
        //短串的长度
        int lengthB = charsB.length;
        int i = 0;
        int j = 0;
        while (i < lengthA && j < lengthB){
            if (charsA[i] == charsB[j]){
                i++;
                j++;
            }else {
                i = i - j + 1;
                j = 0;
            }
        }
        if (j == lengthB){
            return i - j;
        }else {
            return -1;
        }
    }

    /**
     * 利用KPM算法
     * 需要先找出最长前后缀公共因子数组
     */
    public void kmp_search(String text,String pattern){
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(pattern)){
            return;
        }
        int n = pattern.length();
        char[] p = pattern.toCharArray();
        int[] prefix = new int[pattern.length()];
        prefix_tab(p,prefix,n);
        move_prefix_table(prefix,n);
        char[] textChars = text.toCharArray();
        //分别对应text和pattern查询的位置
        int i = 0,j = 0;
        //text的长度
        int m = textChars.length;
        while (i < m){
            if (j == n -1 && textChars[i] == p[j]){
                j = prefix[j];
            }
            if (textChars[i] < p[j]){
                i++;
                j++;
            }else {
                j = prefix[j];
                if (j == -1){
                    i++;
                    j++;
                }
            }
        }
    }

    /**
     *
     * @param pattern
     * @param prefix
     * @param n
     */
    private void prefix_tab(char[] pattern,int[] prefix,int n){
        prefix[0] = 0;
        //最长前后缀公共因子
        int len   = 0;
        //因为在最开始已经把数组的第一位置为0，所以需要从第二位开始比较，也就是下标1
        int i = 1;
        while (i < n){
            /**
             *         0   1   2   3    4    5    6    7    8
             *         A   B   A   B    C    A    B    A    A
             * prefix[]0   0   1   2    0    1
             *
             *  现在我们已经知道A下面的为1，现在需要求B下面的为几，A下面的为1，表明这段字符串的前面有一个最大前后缀公共因子
             *  那如果现在A后面 一个与数组1处位置的字符相等，我们就可以推算出B下面的数字为2
             */
            if (pattern[i] == pattern[len]){
                len++;
                prefix[i] = len;
                i++;
            }else {
                /**
                 *
                 */
                if (len > 0){
                    len = prefix[len -1];
                }else {
                    prefix[i] = len;
                    i++;
                }
            }
        }
    }

    private void move_prefix_table(int[] prefix,int n){
         int i;
         for (i = n -1;i > 0;i --){
             prefix[i] = prefix[i -1];
         }
         prefix[0] = -1;
    }

    /**
     * 反转字符串
     */
    public String reverseString(String s){
        if (TextUtils.isEmpty(s)){
            return "";
        }
        if (s.length() < 2){
            return s;
        }
        int l = 0,r = s.length() -1;
        char[] chars = s.toCharArray();
        while (l < r){
            char temp = chars[l];
            chars[l] = chars[r];
            chars[r] = temp;
            l++;
            r--;
        }
        return new String(chars);
    }

    /**
     * 第一个只出现一次的字符,全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1.
     * 这里需要返回它的位置，如果不是返回它的位置，则直接用迭代器遍历会减少次数。
     *
     */
    public int FirstNotRepeatingChar(String str){
        if (TextUtils.isEmpty(str)){
            return -1;
        }
        HashMap<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < str.length();i++){
            if (map.containsKey(str.charAt(i))){
                map.put(str.charAt(i),map.get(str.charAt(i))+ 1);
            }else {
                map.put(str.charAt(i),1);
            }
        }
        for(int i = 0; i < str.length(); i++){
            if(map.get(str.charAt(i)) == 1)
                return i;
        }
        return -1;
    }

    /**
     *  翻转单词顺序列
     *  有两种方式：
     *  1：先将每一个单词进行分割，然后倒序输出
     *  2： 先旋转每个单词，再旋转整个字符串
     */
    public String reverseWords1(String s){
        String result = new String();
        if (s == null || s.length() == 0){
            return result;
        }
        String[] strarray = s.trim().split(" ");
        if(strarray==null || strarray.length==0){
            return s;
        }
        for(int i =strarray.length-1;i>0;i--) {
            result += strarray[i] + " ";
        }
        result += strarray[0];
        return result;
    }

    public String reverseWords2(String str){
        int n = str.length();
        char[] chars = str.toCharArray();
        int i = 0, j = 0;
        while (j <= n) {
            if (j == n || chars[j] == ' ') {
                reverse(chars, i, j - 1);
                i = j + 1;
            }
            j++;
        }
        reverse(chars, 0, n - 1);
        return new String(chars);
    }

    private void reverse(char[] c, int i, int j) {
        while (i < j)
            swap(c, i++, j--);
    }

    private void swap(char[] c, int i, int j) {
        char t = c[i];
        c[i] = c[j];
        c[j] = t;
    }

    /**
     * 左旋转字符串
     * 1:直接用字符串的截取方法，然后再拼接就好
     * 2：分别对0-n-1，n - length -1的进行翻转，然后对0 - length -1进行翻转
     * @param str
     * @param n
     * @return
     */
    public String LeftRotateString1(String str,int n){
        if (str == null || str.length() < n){
            return "";
        }
        String s1 = str.substring(0,n);
        String s2 = str.substring(n);
        return s2 + s1;
    }

    public String LeftRotateString2(String str,int n){
        if (str == null || str.length() < n){
            return "";
        }
        char[] chars = str.toCharArray();
        reverse(chars,0,n);
        reverse(chars,n,str.length() );
        reverse(chars,0,str.length() );
        return new String(chars);
    }

    /**
     *  旋转字符串
     *  1：每次旋转与目标串比较即可
     *  2：无论它怎样旋转，最终的 A + A包含了所有可以通过旋转操作从 A 得到的字符串
     *  3：KMP
     * @param A
     * @param B
     * @return
     */

    public boolean rotateString1(String A,String B){
        if (A.equals("") && B.equals("")){
            return true;
        }
        int len = A.length();
        for (int i = 0; i < len; i++){
            String first = A.substring(0,1);
            String last = A.substring(1,len);
            A = last + first;
            if (A.equals(B)){
                return true;
            }
        }
        return false;
    }

    public boolean rotateString2(String A,String B){
        return A.length() == B.length() && (A + A).contains(B);
    }


    /**
     * 字符流中第一个不重复的字符
     */
    Map<Character,Integer> map = new HashMap<>();
    List<Character> list = new ArrayList<>();
    public void Insert(char ch)
    {
        if(map.containsKey(ch)){
            int value=map.get(ch);
            map.put(ch,++value);
        }else{
            map.put(ch,1);
        }
        list.add(ch);
    }

    public char FirstAppearingOnce(){
        if(list.isEmpty())
            return '#';for(Character c:list){
            if(map.get(c)==1)
                return c;
        }
        return '#';
    }

    /**
     * 最长回文子串
     *
     * 回温子串只要保证字符串的相对位置即可，与回文子序列还是有差别的
     *
     * 方案：
     *    1、暴力破解 时间复杂度为o(n^2)  空间复杂度为O（1）
     *    2、动态规划  P(i, j) = (P(i + 1, j - 1) && s[i] == s[j])
     *    3、中心扩展法
     */

    public String longestPalindromeDemo(String s){
        String ans = "";
        int max = 0;
        int len = s.length();
        for (int i = 0; i < len; i++){
            for (int j = i+1;j < len;j++){
                String temp = s.substring(i,j);
                if (isPalindrome2(temp) && temp.length() > max){
                    ans = temp;
                    max = j - i;
                }
            }
        }
        return ans;
    }

    public static String longestPalindrome2(String s) {
        int sLen = s.length();
        String ans = "";
        boolean[] P = new boolean[sLen];

        for (int i = sLen - 1; i >= 0; i--) {
            for (int j = sLen - 1; j >= i; j--) {
                P[j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || P[j - 1]);
                if (P[j] && j - i + 1 > ans.length()) {
                    ans = s.substring(i, j + 1);
                }
            }
        }

        return ans;
    }


    public  String longestPalindrome3(String s) {
        if (s == null || s.length() < 1){
            return "";
        }
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++){
            int len1 = expandAroundCenter(s,i,i);
            int len2 = expandAroundCenter(s,i,i+1);
            int len = Math.max(len1,len2);
            if (len > end - start){
                start = i - (len -1)/2;
                end = i + len /2;
            }
        }
        return s.substring(start,end+1);
    }

    public int expandAroundCenter(String s,int left,int right){
        int l = left;
        int r = right;
        while (l > 0 && r < s.length() && s.charAt(l) == s.charAt(r)){
            l--;
            r++;
        }
        return (r -1) - (l +1) + 1;
    }

}
