package com.mannetroll.cacheability.util.monitor.statistics;

public class CacheInfoItem {
    private final String key;
    private long lastcall5;
    private long TTL5 = 301 * 1000;
    private int cachehit5 = 0;
    private int cachemiss5 = 0;

    public CacheInfoItem(String key, long now) {
        this.key = key;
        lastcall5 = now;
    }

    public void addCacheCall(double timeSlice, int chunk, long now) {
        if ((now - lastcall5) < TTL5) {
            cachehit5++;
        } else {
            cachemiss5++;
            lastcall5 = now;
        }
    }

    public String getKey() {
        return key;
    }

    public int getCachehit5() {
        return cachehit5;
    }

    public int getCachemiss5() {
        return cachemiss5;
    }

}
