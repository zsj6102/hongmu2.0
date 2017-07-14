package com.colpencil.redwood.bean.result;

import java.io.Serializable;
import java.util.List;

public class HotResult implements Serializable {

    private String code;
    private String msg;
    private List<String> result;

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

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
