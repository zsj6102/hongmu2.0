package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 我的积分
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/8
 */
public class IntegralReturn implements Serializable {
    private String code;
    private String message;
    /**
     * 我的积分
     */
    private int myPoint;
    /**
     * 积分列表
     */
    private List<Integral> pointList;

    @Override
    public String toString() {
        return "IntegralReturn{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", myPoint=" + myPoint +
                ", pointList=" + pointList +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMyPoint() {
        return myPoint;
    }

    public void setMyPoint(int myPoint) {
        this.myPoint = myPoint;
    }

    public List<Integral> getPointList() {
        return pointList;
    }

    public void setPointList(List<Integral> pointList) {
        this.pointList = pointList;
    }
}

