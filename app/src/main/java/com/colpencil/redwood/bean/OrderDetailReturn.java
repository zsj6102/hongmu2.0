package com.colpencil.redwood.bean;

import java.io.Serializable;

public class OrderDetailReturn implements Serializable {
    private int code;

    private String msg;
    private OrderDetailInfo data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OrderDetailInfo getData() {
        return data;
    }

    public void setData(OrderDetailInfo data) {
        this.data = data;
    }
}
