package com.hon.conquer.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Frank on 2018/2/5.
 * E-mail:frank_hon@foxmail.com
 */

public class DateFormatUtil {
    private DateFormatUtil(){
        throw new AssertionError("No construction for constant class");
    }

    public static String formatTimeToString(long time) {
        Date d = new Date(time + 24*60*60*1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);

        return format.format(d);
    }
}
