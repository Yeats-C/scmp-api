package com.aiqin.bms.scmp.api.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class DayUtil {
    public static String getDayStr(int days){
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.DATE,days);
        Date date = ca.getTime();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(date);
    }

}
