package com.colpencil.redwood.bean.result;

import java.io.Serializable;

public class AnnounceResult implements Serializable {

    private String msg;
    private String code;
    private String url;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }
}
