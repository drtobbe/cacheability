package com.mannetroll.cacheability;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author drtobbe
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class CacheabilityApplication extends SpringBootServletInitializer {
    private final static Logger logger = LoggerFactory.getLogger(CacheabilityApplication.class);

    public static void main(String[] args) {
        logger.info("Start");
        SpringApplication.run(CacheabilityApplication.class, args);
        logger.info("SpringApplication done");

        LogParser logParser = new LogParser();
        File file = new File("/Users/drtobbe/Desktop/cacheability/access_findNearestByAddress.log");
        logParser.parse(file);

        logger.info("Done");
    }

}