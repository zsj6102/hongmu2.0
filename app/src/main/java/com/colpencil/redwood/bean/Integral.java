package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 我的积分
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/8
 */
public class Integral implements Serializable {
    /**
     *获取的积分
     */
    private String mp;
    /**
     * 获取时间
     */
    private long time;
    /**
     * 获取来源，理由
     */
    private String reason;

    @Override
    public String toString() {
        return "Integral{" +
                "mp=" + mp +
                ", time=" + time +
                ", reason='" + reason + '\'' +
                '}';
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
