package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;


public class CardWallInfo implements Serializable {

    private int code;
    private String message;
    private List<CardInfo> data;

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

    public List<CardInfo> getData() {
        return data;
    }

    public void setData(List<CardInfo> data) {
        this.data = data;
    }
}
