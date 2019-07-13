package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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
    public static Date addDay(Integer day){
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DATE,day);
        return instance.getTime();
    }

    public static Date toDate(String productDate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(productDate);
        } catch (ParseException e) {
            throw new BizException(ResultCode.DATE_CONVERSION_FAILED);
        }
    }
}
