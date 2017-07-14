package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/11 15 30
 */
public class BrowsingPostDate implements Serializable {
    /**
     * 时间
     */
    private long date;
    /**
     * 浏览记录列表
     */
    private List<PostItem> dateList;

    @Override
    public String toString() {
        return "BrowsingPostDate{" +
                "date=" + date +
                ", dateList=" + dateList +
                '}';
    }

    public List<PostItem> getDateList() {
        return dateList;
    }

    public void setDateList(List<PostItem> dateList) {
        this.dateList = dateList;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


}
