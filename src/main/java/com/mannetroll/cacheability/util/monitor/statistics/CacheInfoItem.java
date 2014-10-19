package com.mannetroll.cacheability.util.monitor.statistics;

public class CacheInfoItem {
    private final String key;
    private long lastcall;

    public CacheInfoItem(String key, long now) {
        this.key = key;
        lastcall = now;
    }

    public void addCall(double timeSlice, int chunk, long now) {
        long age = lastcall - now;
        System.out.println("age: " + age + " - " + key);
        lastcall = now;
    }

}
