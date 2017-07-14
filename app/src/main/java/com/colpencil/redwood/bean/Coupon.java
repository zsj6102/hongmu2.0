package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 优惠券
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class Coupon implements Serializable {

    private int id;
    private String cpns_sn;
    private String cpns_img;
    private int cpns_id;
    private String cpns_name;
    private int point;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpns_sn() {
        return cpns_sn;
    }

    public void setCpns_sn(String cpns_sn) {
        this.cpns_sn = cpns_sn;
    }

    public String getCpns_img() {
        return cpns_img;
    }

    public void setCpns_img(String cpns_img) {
        this.cpns_img = cpns_img;
    }

    public int getCpns_id() {
        return cpns_id;
    }

    public void setCpns_id(int cpns_id) {
        this.cpns_id = cpns_id;
    }

    public String getCpns_name() {
        return cpns_name;
    }

    public void setCpns_name(String cpns_name) {
        this.cpns_name = cpns_name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
