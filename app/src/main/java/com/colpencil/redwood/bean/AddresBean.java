package com.colpencil.redwood.bean;

import com.colpencil.redwood.bean.result.ProvinModel;

import java.io.Serializable;
import java.util.List;

public class AddresBean implements Serializable {
   private int code;
    private List<ProvinModel> data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ProvinModel> getData() {
        return data;
    }

    public void setData(List<ProvinModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
