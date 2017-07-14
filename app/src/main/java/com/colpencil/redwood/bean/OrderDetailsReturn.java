package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 订单详情返回值
 * @Email 20000263@qq.com
 * @date 2016/8/22
 */
public class OrderDetailsReturn implements Serializable {
    private int code;

    private String msg;

    private OrderDetailsBean result;

    @Override
    public String toString() {
        return "OrderDetailsReturn{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
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

    public OrderDetailsBean getResult() {
        return result;
    }

    public void setResult(OrderDetailsBean result) {
        this.result = result;
    }
}
