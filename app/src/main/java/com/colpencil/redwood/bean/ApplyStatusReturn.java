package com.colpencil.redwood.bean;

import java.io.Serializable;

public class ApplyStatusReturn implements Serializable {
    private int code;
    private StatusData data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public StatusData getData() {
        return data;
    }

    public void setData(StatusData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
