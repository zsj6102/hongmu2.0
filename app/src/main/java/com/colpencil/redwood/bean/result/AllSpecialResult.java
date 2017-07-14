package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.AllSpecialInfo;

import java.io.Serializable;
import java.util.List;

public class AllSpecialResult implements Serializable {
    private String message;
    private int code;
    private List<AllSpecialInfo> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AllSpecialInfo> getData() {
        return data;
    }

    public void setData(List<AllSpecialInfo> data) {
        this.data = data;
    }
}
