package com.example.myapplication;

import android.util.Printer;

public class ThreadDemo {
    private volatile int number = 1;
    private volatile int value = 1;

    public void firstPrint(){
        synchronized (this){
            while (value != 1){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(number);
            number++;
            value = 2;
            notifyAll();
        }
    }

    public void twoPrinter(){
        synchronized (this){
            while (value != 2){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(number);
            number++;
            value = 3;
            notifyAll();
        }
    }

    public void threePrinter(){
        synchronized (this){
            while (value != 3){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(number);
            number++;
            value = 1;
            notifyAll();
        }
    }
}
