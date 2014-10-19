package com.mannetroll.cacheability.util.monitor.statistics;

public class CacheInfoItem {
    private final String key;
    private long lastcall5 = 0;
    private long TTL5 = 301 * 1000;
    private int cachehit5 = 0;
    private int cachemiss5 = 0;
    //
    private long lastcall15 = 0;
    private long TTL15 = 1501 * 1000;
    private int cachehit15 = 0;
    private int cachemiss15 = 0;

    public CacheInfoItem(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void addCacheCall(double timeSlice, int chunk, long now) {
        if (lastcall5 == 0) {
            lastcall5 = now;
            lastcall15 = now;
            //first access call
            return;
        }
        if ((now - lastcall5) < TTL5) {
            cachehit5++;
        } else {
            cachemiss5++;
            lastcall5 = now;
        }
        if ((now - lastcall15) < TTL15) {
            cachehit15++;
        } else {
            cachemiss15++;
            lastcall15 = now;
        }
    }

    public double getCacheability5() {
        double total = cachehit5 + cachemiss5;
        return (total > 0 ? 100d * cachehit5 / total : 0);
    }

    public double getCacheability15() {
        double total = cachehit15 + cachemiss15;
        return (total > 0 ? 100d * cachehit15 / total : 0);
    }
}
