package com.example.myapplication;

import java.util.LinkedHashMap;

public class LRU {
    /**
     * LinkedHashMap的实现
     */
    private LinkedHashMap<Integer,Integer> map;
    public LRU(int capacity){
        map = new LinkedHashMap<Integer,Integer>(capacity, 0.75F,true){
            @Override
            protected boolean removeEldestEntry(Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }


    public int get(int key){
        return map.containsKey(key) ? map.get(key):-1;
    }

    public void put(int key,int value){
        map.put(key,value);
    }
}
