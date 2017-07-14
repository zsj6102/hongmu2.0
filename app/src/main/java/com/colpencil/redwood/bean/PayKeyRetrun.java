package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 支付方式返回值
 * @Email 20000263@qq.com
 * @date 2016/8/15
 */
public class PayKeyRetrun implements Serializable {
    private  int code;
    private String msg;
    private int type;
    /**
     * 支付返回参数
     */
    private PayResult result;
    private String tn;

    @Override
    public String toString() {
        return "PayKeyRetrun{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                ", result=" + result +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PayResult getResult() {
        return result;
    }

    public void setResult(PayResult result) {
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}
