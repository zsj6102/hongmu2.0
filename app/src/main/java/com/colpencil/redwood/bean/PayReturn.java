package com.colpencil.redwood.bean;

import java.io.Serializable;

public class PayReturn implements Serializable{
    private int code;
    private String message;
    private Payinfo data;

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

    public Payinfo getData() {
        return data;
    }

    public void setData(Payinfo data) {
        this.data = data;
    }
}
