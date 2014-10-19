package com.mannetroll.cacheability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mannetroll.cacheability.util.monitor.statistics.AbstractTimerInfoStats;
import com.mannetroll.cacheability.util.monitor.statistics.TimerInfoStats;

/**
 * @author drtobbe
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class CacheabilityApplication extends SpringBootServletInitializer {
    private final static Logger logger = LoggerFactory.getLogger(CacheabilityApplication.class);
    private final static AbstractTimerInfoStats statistics = TimerInfoStats.getInstance();

    public static void main(String[] args) {
        logger.info("Start");
        SpringApplication.run(CacheabilityApplication.class, args);
        logger.info("SpringApplication done");
        
        
        //statistics.addCall(key, responsetime_ms, chunk, now);
        //statistics.addTotalTime(responsetime_ms, chunk, now);

        
        logger.info("Done");
    }

}