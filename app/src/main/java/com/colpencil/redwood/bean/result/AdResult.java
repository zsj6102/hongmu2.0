package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.BannerVo;

import java.io.Serializable;
import java.util.List;

public class AdResult implements Serializable {
    private int code;
    private String message;
    private List<BannerVo> data;

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

    public List<BannerVo> getData() {
        return data;
    }

    public void setData(List<BannerVo> data) {
        this.data = data;
    }
}
