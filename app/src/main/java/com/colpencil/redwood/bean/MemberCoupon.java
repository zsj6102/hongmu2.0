package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 陈 宝
 * @Description:优惠券和代金券
 * @Email 1041121352@qq.com
 * @date 2016/10/17
 */
public class MemberCoupon implements Serializable {

    private int id;
    private String cpns_img;
    private float discount_price;
    private boolean isChoose;

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

    public float getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(float discount_price) {
        this.discount_price = discount_price;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
