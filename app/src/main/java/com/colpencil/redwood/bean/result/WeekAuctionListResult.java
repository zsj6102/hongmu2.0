package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.WeekAuctionList;

import java.io.Serializable;
import java.util.List;

public class WeekAuctionListResult implements Serializable {

    private String code;
    private String msg;
    private List<WeekAuctionList> result;

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

    public List<WeekAuctionList> getResult() {
        return result;
    }

    public void setResult(List<WeekAuctionList> result) {
        this.result = result;
    }
}
