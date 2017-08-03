package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.OrderDtail;

import java.io.Serializable;

public class OrderPayInfo implements Serializable {
    private int code;
    private String message;
    private OrderDtail data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderDtail getData() {
        return data;
    }

    public void setData(OrderDtail data) {
        this.data = data;
    }
}
