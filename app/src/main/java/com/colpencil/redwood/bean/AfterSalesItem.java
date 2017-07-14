package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 售后中心的Item 项
 * @Email 20000263@qq.com
 * @date 2016/8/11
 */
public class AfterSalesItem implements Serializable {
    /**
     * 售后ID
     */
    private String afterSaleId;
    /**
     * 状态
     */
    private int afterSaleState;
    /**
     * 状态名称
     */
    private String stateName;
    /**
     * 卖家拒绝售后的理由
     */
    private String refuseReason;
    /**
     * 商品信息
     */
    private List<GoodOfOrder> items;

    @Override
    public String toString() {
        return "AfterSalesItem{" +
                "afterSaleId='" + afterSaleId + '\'' +
                ", afterSaleState=" + afterSaleState +
                ", stateName='" + stateName + '\'' +
                ", refuseReason='" + refuseReason + '\'' +
                ", items=" + items +
                '}';
    }

    public String getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(String afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public int getAfterSaleState() {
        return afterSaleState;
    }

    public void setAfterSaleState(int afterSaleState) {
        this.afterSaleState = afterSaleState;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public List<GoodOfOrder> getItems() {
        return items;
    }

    public void setItems(List<GoodOfOrder> items) {
        this.items = items;
    }
}
