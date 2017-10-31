package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 汪 亮
 * @Description:时间处理工具
 * @Email DramaScript@outlook.com
 * @date 16/6/23
 */
public class TimeUtil {

    public static String longToStringYear(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = sdf.format(date);  //现在的时间
        return result;
    }
    public static String getDateBySplit(String str) {
        String[] strings = str.split(" ");
        return strings[0];
    }

    public static String timeFormat(long time, String type) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        return sdf.format(date);
    }

    public static int getDay(long beginTime, long endTime) {
        if (endTime < beginTime) {
            return 0;
        }
        return (int) ((endTime - beginTime) / (1000 * 24 * 60 * 60));
    }

    public static int getDay(long time) {
        if (time <= 0) {
            return 0;
        }
        return (int) (time / (1000 * 24 * 60 * 60));
    }

    public static int getHourse(long beginTime, long endTime) {
        if (endTime < beginTime) {
            return 0;
        }
        return (int) ((endTime - beginTime) % (1000 * 24 * 60 * 60) / (1000 * 60 * 60));
    }

    public static int getHourse(long time) {
        if (time <= 0) {
            return 00;
        }
        return (int) (time % (1000 * 24 * 60 * 60) / (1000 * 60 * 60));
    }

    public static int getMinute(long beginTime, long endTime) {
        if (endTime < beginTime) {
            return 0;
        }
        return (int) ((endTime - beginTime) % (1000 * 24 * 60 * 60) % (1000 * 60 * 60) / (1000 * 60));
    }

    public static int getMinute(long time) {
        if (time <= 0) {
            return 00;
        }
        return (int) (time % (1000 * 24 * 60 * 60) % (1000 * 60 * 60) / (1000 * 60));
    }

    public static int getSecond(long beginTime, long endTime) {
        if (endTime < beginTime) {
            return 0;
        }
        return (int) ((endTime - beginTime) % (1000 * 24 * 60 * 60) % (1000 * 60 * 60) % (1000 * 60) / 1000);
    }

    public static int getSecond(long time) {
        if (time <= 0) {
            return 00;
        }
        return (int) (time % (1000 * 24 * 60 * 60) % (1000 * 60 * 60) % (1000 * 60) / 1000);
    }

    public static String getTimeDiffDay(long postTime, long nowTime) {
        if (postTime > nowTime) {
            return "时间错误";
        }
        long diff = nowTime - postTime;
        long day = diff / (24 * 60 * 60 * 1000);
        long hour = diff / (60 * 60 * 1000) - day * 24;
        long minute = diff / (60 * 1000) - day * 24 * 60 - hour * 60;
        if (isToday(postTime)) {
            if (day < 1) {
                if (hour < 1) {
                    if (minute < 1) {
                        return "刚刚";
                    } else {
                        return minute + "分钟前";
                    }
                } else {
                    return hour + "小时前";
                }
            } else {
                return timeFormat(postTime, "yy-MM-dd HH:mm");
            }
        } else {
            return timeFormat(postTime, "yy-MM-dd HH:mm");
        }
    }

    public static boolean isToday(long time) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = sdf.format(date);  //现在的时间
        String createTime = timeFormat(time, "yyyy-MM-dd");
        if (nowTime.equals(createTime)) {
            return true;
        } else {
            return false;
        }
    }
}
