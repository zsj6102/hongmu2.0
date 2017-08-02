package com.colpencil.redwood.bean.result;
import java.io.Serializable;
import java.util.List;

public class AllCartList implements Serializable {
    private int code;
    private String message;
    private List<CartList> data;

    public List<CartList> getData() {
        return data;
    }

    public void setData(List<CartList> data) {
        this.data = data;
    }
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




}
