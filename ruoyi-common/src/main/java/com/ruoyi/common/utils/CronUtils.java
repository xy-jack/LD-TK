package com.ruoyi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换quartz表达式
 * @author yd
 */
public class CronUtils {

    /**
     * 格式化数据
     */
    private static final String DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    /**
     * 准确的时间点到表达式
     * @param date
     * @return
     */
    public static String dateToCron(final Date date){
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = fmt.format(date);
        }
        return formatTimeStr;
    }
}
