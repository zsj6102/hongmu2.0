package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/9 15 55
 */
public class ResultCodeInt implements Serializable {
    private int code;
    private String msg;
    /**
     * 客服电话
     */
    private String servicePhone;
    /**
     * 退款理由集合
     */
    private List<RefundReason> refundResons;
    /**
     * 退货理由集合
     */
    private List<RefundReason> returnResons;
    /**
     * 换货理由集合
     */
    private List<RefundReason> exchangeResons;
    /**
     *查看物流的链接
     */
    private String url;

    @Override
    public String toString() {
        return "ResultCodeInt{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", servicePhone='" + servicePhone + '\'' +
                ", refundResons=" + refundResons +
                ", returnResons=" + returnResons +
                ", exchangeResons=" + exchangeResons +
                ", url='" + url + '\'' +
                '}';
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public List<RefundReason> getRefundResons() {
        return refundResons;
    }

    public void setRefundResons(List<RefundReason> refundResons) {
        this.refundResons = refundResons;
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

    public List<RefundReason> getReturnResons() {
        return returnResons;
    }

    public void setReturnResons(List<RefundReason> returnResons) {
        this.returnResons = returnResons;
    }

    public List<RefundReason> getExchangeResons() {
        return exchangeResons;
    }

    public void setExchangeResons(List<RefundReason> exchangeResons) {
        this.exchangeResons = exchangeResons;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
