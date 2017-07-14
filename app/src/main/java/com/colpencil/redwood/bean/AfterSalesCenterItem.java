package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 售后中心数据
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/18
 */
public class AfterSalesCenterItem implements Serializable {
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
     * 订单号
     */
    private String sn;
    /**
     * 商品信息
     */
    private GoodOfOrder items;
    /**
     * 订单id
     */
    private int order_id;

    @Override
    public String toString() {
        return "AfterSalesCenterItem{" +
                "afterSaleId='" + afterSaleId + '\'' +
                ", afterSaleState=" + afterSaleState +
                ", stateName='" + stateName + '\'' +
                ", refuseReason='" + refuseReason + '\'' +
                ", sn='" + sn + '\'' +
                ", items=" + items +
                ", order_id=" + order_id +
                '}';
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setItems(GoodOfOrder items) {
        this.items = items;
    }

    public String getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(String afterSaleId) {
        this.afterSaleId = afterSaleId;
    }
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getAfterSaleState() {
        return afterSaleState;
    }

    public void setAfterSaleState(int afterSaleState) {
        this.afterSaleState = afterSaleState;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public GoodOfOrder getItems() {
        return items;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
