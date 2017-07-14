package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.AllGoodsInfo;

import java.io.Serializable;
import java.util.List;

public class AllGoodsResult implements Serializable {
    private int code;
    private String message;
    private List<AllGoodsInfo> data;

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

    public List<AllGoodsInfo> getData() {
        return data;
    }

    public void setData(List<AllGoodsInfo> data) {
        this.data = data;
    }
}
