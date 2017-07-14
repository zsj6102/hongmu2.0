package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.Region;

import java.io.Serializable;
import java.util.List;

public class ResultRegion implements Serializable {
    private int code;
    private List<Region> data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Region> getData() {
        return data;
    }

    public void setData(List<Region> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
