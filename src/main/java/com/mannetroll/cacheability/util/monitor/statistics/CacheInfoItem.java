package com.mannetroll.cacheability.util.monitor.statistics;

public class CacheInfoItem {
    private final String key;
    private long lastcall;
    private long TTL = 300 * 1000;
    private int cachehit = 0;
    private int cachemiss = 0;

    public CacheInfoItem(String key, long now) {
        this.key = key;
        lastcall = now;
    }

    public void addCacheCall(double timeSlice, int chunk, long now) {
        long age = now - lastcall;
        if (age < TTL) {
            cachehit++;
            System.out.println("cachehit: " + cachehit + " - " + key);
        } else {
            cachemiss++;
            System.out.println("cachemiss: " + cachemiss + " - " + key);
            lastcall = now;
        }
    }

}
