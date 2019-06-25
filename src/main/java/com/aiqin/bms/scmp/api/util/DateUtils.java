package com.aiqin.bms.scmp.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author knight.xie
 * @version 1.0
 * @className DateUtils
 * @date 2019/6/25 10:53
 * @description TODO
 */
public class DateUtils {

    public static final String  YEAR_FORMAT = "yy";
    public static final String  FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static String getCurrentDateTime(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }

}
