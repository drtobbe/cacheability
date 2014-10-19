package com.mannetroll.cacheability;

import java.io.File;

import org.junit.Test;

public class LogParserTest {

    @Test
    public void test() {
        LogParser logParser = new LogParser();
        File file = new File("src/test/resources/test.log");
        logParser.parse(file);
    }

}
