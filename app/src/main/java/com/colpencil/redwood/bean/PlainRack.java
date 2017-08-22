package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * 藏品货架 的普通货架item
 */
public class PlainRack implements Serializable {
    private int store_id; //商家id
    private String store_name;//商家名称
    private int goods_id;//商品id
    private String goods_name;//商品名称
    private String thumbnail;//商品缩略图
    private String level_name;//商品等级名称
    private String level_pic;//商品等级图片
    private double price;//商品当前价格
    private int is_top;//0:不置顶，1：置顶
    private String address;//商家地址

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getLevel_pic() {
        return level_pic;
    }

    public void setLevel_pic(String level_pic) {
        this.level_pic = level_pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
