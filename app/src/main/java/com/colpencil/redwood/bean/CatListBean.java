package com.colpencil.redwood.bean;

import java.util.List;

public class CatListBean {
    private int code;
    private String message;
    private List<CatBean> data;

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

    public List<CatBean> getData() {
        return data;
    }

    public void setData(List<CatBean> data) {
        this.data = data;
    }
}
