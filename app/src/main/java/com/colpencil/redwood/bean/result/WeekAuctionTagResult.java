package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.WeekAuctionTag;

import java.io.Serializable;
import java.util.List;

public class WeekAuctionTagResult implements Serializable {

    private int code;
    private String msg;
    private List<WeekAuctionTag> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<WeekAuctionTag> getResult() {
        return result;
    }

    public void setResult(List<WeekAuctionTag> result) {
        this.result = result;
    }
}
