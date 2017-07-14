package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 退款理由
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/9
 */
public class RefundReason implements Serializable {
    /**
     * 退款原因ID
     */
    private int refundResonID;
    /**
     * 退款原因
     */
    private String refundResonContent;
    /**
     * 选中状态
     */
    private boolean isChooseState;

    @Override
    public String toString() {
        return "RefundReason{" +
                "refundResonID=" + refundResonID +
                ", refundResonContent='" + refundResonContent + '\'' +
                ", isChooseState=" + isChooseState +
                '}';
    }

    public int getRefundResonID() {
        return refundResonID;
    }

    public void setRefundResonID(int refundResonID) {
        this.refundResonID = refundResonID;
    }

    public String getRefundResonContent() {
        return refundResonContent;
    }

    public void setRefundResonContent(String refundResonContent) {
        this.refundResonContent = refundResonContent;
    }

    public boolean isChooseState() {
        return isChooseState;
    }

    public void setChooseState(boolean chooseState) {
        isChooseState = chooseState;
    }
}
