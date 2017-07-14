package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 支付实体类
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/15
 */
public class PayResult implements Serializable {
    /**
     * 微信支付
     */
    private String appid;
    private String partnerid;
    private long timestamp;
    private String noncestr;
    private String packageName;
    private String prepayid;
    private String sign;
    /**
     * 支付宝支付
     */
    private String reStr;

    @Override
    public String toString() {
        return "PayResult{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", timestamp=" + timestamp +
                ", noncestr='" + noncestr + '\'' +
                ", packageName='" + packageName + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", sign='" + sign + '\'' +
                ", reStr='" + reStr + '\'' +
                '}';
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getReStr() {
        return reStr;
    }

    public void setReStr(String reStr) {
        this.reStr = reStr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
