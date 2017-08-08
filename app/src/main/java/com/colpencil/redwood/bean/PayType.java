package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 支付类型
 * @Email 20000263@qq.com
 * @date 2016/8/12
 */
public class PayType implements Serializable {
    /**
     * 支付方式ID
     */
    private int payId;
    /**
     * 支付方式头像地址
     */
    private String payImgPath;
    /**
     * 支付方式名称
     */
    private String payName;

    /**
     * 选中状态
     */
    private boolean chooseState;

    @Override
    public String toString() {
        return "PayType{" +
                "payId='" + payId + '\'' +
                ", payImgPath='" + payImgPath + '\'' +
                ", payName='" + payName + '\'' +
                ", chooseState=" + chooseState +
                '}';
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayImgPath() {
        return payImgPath;
    }

    public void setPayImgPath(String payImgPath) {
        this.payImgPath = payImgPath;
    }

    public boolean isChooseState() {
        return chooseState;
    }

    public void setChooseState(boolean chooseState) {
        this.chooseState = chooseState;
    }
}
