package com.colpencil.redwood.bean.result;

import java.io.Serializable;

public class CommonResult implements Serializable {

    private String code;
    private String msg;
    private int id;
    private String message;
    private String result;
    private int num;
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", id=" + id +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                ", num=" + num +
                ", url='" + url + '\'' +
                '}';
    }
}
