package com.colpencil.redwood.bean;

import java.io.Serializable;

public class NumReturn implements Serializable {
    private int code;
    private String msg;
    private int num;

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
