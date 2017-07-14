package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/11 16 13
 */
public class BrowsingGoodDate implements Serializable {
    /**
     * 时间
     */
    private long date;
    /**
     * 一天中浏览记录列表
     */
    private List<GoodsItem> dateList;

    @Override
    public String toString() {
        return "BrowsingGoodDate{" +
                "date=" + date +
                ", dateList=" + dateList +
                '}';
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<GoodsItem> getDateList() {
        return dateList;
    }

    public void setDateList(List<GoodsItem> dateList) {
        this.dateList = dateList;
    }
}
