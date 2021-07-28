package com.example.myapplication;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1() {
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.2 / 100);
    }

    @Test
    public void test2(){
        Sort sort = new Sort();
        int[] aar = {5,2,6,3,9,4};
        //sort.BubbleSort(aar);
        sort.SelectSort(aar);
        for (Integer i:aar){
            System.out.println(i);
        }

    }

}