package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 售后中心返回值
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/18
 */
public class AfterSalesCenterReturn implements Serializable {
    private int code;
    private String msg;
    /**
     * 售后集合
     */
    private List<AfterSalesCenterItem> data;

    @Override
    public String toString() {
        return "AfterSalesCenterReturn{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AfterSalesCenterItem> getData() {
        return data;
    }

    public void setData(List<AfterSalesCenterItem> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
