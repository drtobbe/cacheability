package com.mannetroll.cacheability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogParser {
    private final static Logger logger = LoggerFactory.getLogger(LogParser.class);
    private static final String UTF_8 = "UTF-8";
    private static final String SEP = "|";

    /*Log file feilds*/
    String clientHost = null;
    String date = null;
    String clientRequest = null;
    String httpStatusCode = null;
    String numOfBytes = null;
    String responseTime = null;

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
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String line = "";
            long index = 0;

            Pattern accessLogPattern = Pattern.compile(getAccessLogRegex(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher accessLogEntryMatcher;
            while ((line = bufferReader.readLine()) != null) {
                index++;
                accessLogEntryMatcher = accessLogPattern.matcher(line);
                if (!accessLogEntryMatcher.matches()) {
                    System.out.println("" + index + " : couldn't be parsed");
                    continue;
                } else {
                    date = accessLogEntryMatcher.group(4);
                    clientRequest = accessLogEntryMatcher.group(5);
                    httpStatusCode = accessLogEntryMatcher.group(6);
                    numOfBytes = accessLogEntryMatcher.group(7);
                    responseTime = accessLogEntryMatcher.group(8);
                    //
                    String verb = clientRequest.split(" ")[0];
                    String url = clientRequest.split(" ")[1];
                    String[] tmp = url.split("\\?");
                    if (tmp.length > 0) {
                        String query = tmp[1];
                        Map<String, String> splitQuery = splitQuery(query);
                        String uri = tmp[0];
                        String[] segment = uri.split("/");
                        if (segment.length > 0) {
                            String key = segment[segment.length - 1];
                            key = verb + SEP + httpStatusCode + SEP + key;
                            for (String string : splitQuery.keySet()) {
                                key += SEP + string + "=" + splitQuery.get(string);
                            }
                            logger.info("key: " + key);
                        }
                    }
                }
            }
            bufferReader.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {

        }

    }

    public Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new TreeMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String param = URLDecoder.decode(pair.substring(0, idx), UTF_8);
            if (!"consumerId".equals(param)) {
                query_pairs.put(param, URLDecoder.decode(pair.substring(idx + 1), UTF_8));
            }
        }
        return query_pairs;
    }
}
