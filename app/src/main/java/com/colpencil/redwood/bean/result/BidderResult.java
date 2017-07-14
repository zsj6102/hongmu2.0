package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.BidderInfoVo;

import java.io.Serializable;
import java.util.List;

public class BidderResult implements Serializable {

    private String code;
    private String msg;
    private List<BidderInfoVo> result;

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

    public List<BidderInfoVo> getResult() {
        return result;
    }

    public void setResult(List<BidderInfoVo> result) {
        this.result = result;
    }
}
