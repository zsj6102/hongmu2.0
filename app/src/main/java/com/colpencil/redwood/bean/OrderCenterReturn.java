package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 订单中心返回值
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/11
 */
public class OrderCenterReturn implements Serializable {
    private int code;
    private String msg;

    /**
     * 订单信息列表集合
     */
    private List<OrderItem> data;

    @Override
    public String toString() {
        return "OrderCenterReturn{" +
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<OrderItem> getData() {
        return data;
    }

    public void setData(List<OrderItem> data) {
        this.data = data;
    }
}
