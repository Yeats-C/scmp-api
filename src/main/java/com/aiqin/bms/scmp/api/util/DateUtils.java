package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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
    public static final String  PATTERN_DATE = "yyyy-MM-dd";
    public static final String  MONTH_TIME = "yyyy-MM";

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

    /**
     * 获取所在天的前一天时间<br>
     */
    public static String yestedayDate(){
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.DATE, -1);
        Date date = ca.getTime();
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(PATTERN_DATE);
    }

    public static int getYear() {
        Calendar sysDate = Calendar.getInstance();
        return sysDate.get(Calendar.YEAR);
    }

    public static Date getDate(String time) {
        Date parse = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATE);
            if(StringUtils.isNotBlank(time)){
                parse = formatter.parse(time);
            }
        }catch (Exception e){
            throw new BizException(ResultCode.DATE_CONVERSION_FAILED);
        }
        return parse;
    }

    /**
     * 获取当前月
     * @return
     */
    public static String getMonths() {
        SimpleDateFormat sdf = new SimpleDateFormat(MONTH_TIME);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //c.add(Calendar.MONTH, -i);
        Date m = c.getTime();
        return sdf.format(m);
    }

    /**
     * @return Stirng like "1928-09"
     */
    public static String getLastMonthString(Date d) {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(df1.format(d));
        Period period = Period.ofMonths(-1);
        LocalDate plus = date.plus(period);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM");
        return plus.format(df);
    }
}
