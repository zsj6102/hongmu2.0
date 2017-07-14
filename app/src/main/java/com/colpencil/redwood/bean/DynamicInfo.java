package com.colpencil.redwood.bean;

import java.io.Serializable;


public class DynamicInfo implements Serializable {

    private int goods_id;
    private double price;
    private String thumbnail;
    private String name;
    private String store_city;
    private String store_name;
    private String level_pic;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore_city() {
        return store_city;
    }

    public void setStore_city(String store_city) {
        this.store_city = store_city;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getLevel_pic() {
        return level_pic;
    }

    public void setLevel_pic(String level_pic) {
        this.level_pic = level_pic;
    }
}
