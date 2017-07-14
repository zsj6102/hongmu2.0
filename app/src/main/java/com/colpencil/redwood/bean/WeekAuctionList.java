package com.colpencil.redwood.bean;

import java.io.Serializable;

public class WeekAuctionList implements Serializable {

    private int id;
    private String type_name;
    private String goods_name;
    private String img;
    private long begin_time;
    private long end_time;
    private double spot_price;
    private long systemTime;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public double getSpot_price() {
        return spot_price;
    }

    public void setSpot_price(int spot_price) {
        this.spot_price = spot_price;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
