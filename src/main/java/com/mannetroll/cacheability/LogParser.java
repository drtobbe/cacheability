package com.mannetroll.cacheability;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogParser {
    private final static Logger logger = LoggerFactory.getLogger(LogParser.class);
    Pattern accessLogPattern = Pattern.compile(getAccessLogRegex(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    public LogParser() {
    }

    /**
    * Structures Apache common access log
    *@return regex
    */
    static String getAccessLogRegex() {
        String clientHost = "^([\\d.]+)"; // Client IP
        String regex2 = " (\\S+)"; // -
        String user = " (\\S+)"; // -
        String date = " \\[([\\w:/]+\\s[+\\-]\\d{4})\\]"; // Date
        String request = " \"(.+?)\""; // request method and url
        String httpStatusCode = " (\\d{3})"; // HTTP code
        String numOfBytes = " (\\d+|(.+?))"; // Number of bytes
        String responseTime = " (\\d+|(.+?))"; // Response times
        return clientHost + regex2 + user + date + request + httpStatusCode + numOfBytes + responseTime;
    }

    /**
    * Converts any formated date to epoch
    *@param dateFormmat : eg. yyy-MM-dd
    *@param date : it should which fits dateFormmat criteria
    *@return long : epoch representation
    *@throws ParseException
    */
    public long convertTimetoEpoch(String dateFormat, String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return (sdf.parse(date)).getTime();
    }

    public void parse(File file) {
        logger.info("file: " + file);

    }

}
