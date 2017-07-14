package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.GoodsTypeInfo;

import java.io.Serializable;
import java.util.List;

public class GoodsTypeResult implements Serializable {
    private int code;
    private String message ;
    private List<GoodsTypeInfo> data;

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

    public List<GoodsTypeInfo> getData() {
        return data;
    }

    public void setData(List<GoodsTypeInfo> data) {
        this.data = data;
    }
}
