package com.colpencil.redwood.function.tools;

import java.util.Date;


public class RelativeDataFormat {
    private final static long second = 1000; //1秒钟
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    public static String format(Date dateNow,Date date){
        if( date == null || dateNow == null  ){
            return null;
        }
        long diff = dateNow.getTime() - date.getTime();
        long r = 0;
        if(diff > year){
            r = (diff/year);
            return r+"年前";
        }
        if(diff>month){
            r = (diff/month);
            return r+"个月前";
        }
        if(diff > day){
            r = (diff / day);
            return r+"天前";
        }
        if(diff > hour){
            r = (diff/hour);
            return r+"小时前";
        }
        if(diff > minute){
            r = (diff/minute);
            return r+"分钟前";
        }
        if(diff > second){
            r = (diff/second);
            return r+"秒前";
        }
        return "刚刚";
    }
}
