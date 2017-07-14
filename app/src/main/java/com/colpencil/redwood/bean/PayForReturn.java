package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 结算中心返回值
 * @Email 20000263@qq.com
 * @date 2016/8/12
 */
public class PayForReturn implements Serializable {
    private int code;
    private String msg;
    /**
     * 返回信息
     */
    private PayForData data;

    @Override
    public String toString() {
        return "PayForReturn{" +
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

    public PayForData getData() {
        return data;
    }

    public void setData(PayForData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
