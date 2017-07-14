package com.colpencil.redwood.bean.result;

import java.io.Serializable;

public class CustomGoodResult implements Serializable {

    private String msg;
    private String code;
    private String after_img;
    private float final_price;
    private float prepaid_price;
    private String name;
    private String before_img;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAfter_img(String after_img) {
        this.after_img = after_img;
    }

    public void setFinal_price(float final_price) {
        this.final_price = final_price;
    }

    public void setPrepaid_price(float prepaid_price) {
        this.prepaid_price = prepaid_price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBefore_img(String before_img) {
        this.before_img = before_img;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public String getAfter_img() {
        return after_img;
    }

    public float getFinal_price() {
        return final_price;
    }

    public float getPrepaid_price() {
        return prepaid_price;
    }

    public String getName() {
        return name;
    }

    public String getBefore_img() {
        return before_img;
    }
}
