package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.ZcCardInfo;

import java.io.Serializable;
import java.util.List;

public class ZcAllCardInfo implements Serializable{
    private int code;
    private String message;
    private List<ZcCardInfo> data;

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

    public List<ZcCardInfo> getData() {
        return data;
    }

    public void setData(List<ZcCardInfo> data) {
        this.data = data;
    }
}
