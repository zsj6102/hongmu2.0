package com.colpencil.redwood.bean.result;

import java.io.Serializable;

public class MemberCoupon implements Serializable {
    private int id;
    private String cpns_img;
    private String discount_price;
    private String cpns_name;
    private String goods_price;
    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpns_img() {
        return cpns_img;
    }

    public void setCpns_img(String cpns_img) {
        this.cpns_img = cpns_img;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getCpns_name() {
        return cpns_name;
    }

    public void setCpns_name(String cpns_name) {
        this.cpns_name = cpns_name;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }
}
