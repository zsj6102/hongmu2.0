package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 商品详情界面
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/5
 */
public class GoodDetailsReturn implements Serializable {

    private int code;

    private String msg;
    /**
     * 商品信息
     */
    private GoodInfo goodInfo;

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

    public GoodInfo getResult() {
        return goodInfo;
    }

    public void setResult(GoodInfo goodInfo) {
        this.goodInfo = goodInfo;
    }

    @Override
    public String toString() {
        return "GoodDetailsReturn{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + goodInfo +
                '}';
    }
}
