package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.DynamicInfo;

import java.io.Serializable;
import java.util.List;

public class DynamicResult implements Serializable{
    private int code;
    private String message;
    private List<DynamicInfo> data;

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

    public List<DynamicInfo> getData() {
        return data;
    }

    public void setData(List<DynamicInfo> data) {
        this.data = data;
    }
}
