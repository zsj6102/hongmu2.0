package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 订单的item项
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/11
 */
public class OrderItem implements Serializable {
    /**
     * 订单ID
     */
    private int order_id;
    /**
     * 1：待付款 2：已付款 3：已完成 4：已取消5:退款中 6：已退款 7：退款被拒绝
     */
    private int status;
    /**
     * 1：待付款 2：已付款 3：已完成 4：已取消5:退款中 6：已退款 7：退款被拒绝
     */
    private String statusName;
    /**
     * 订单号
     */
    private String order_sn;
    /**
     *退款理由
     */
    private String refuse_reason;
    /**
     *
     */
    private List<GoodOfOrder> orderitems;
    /**
     * 退款id
     */
    private  int return_order_id;

    private int optType;

    @Override
    public String toString() {
        return "OrderItem{" +
                "order_id=" + order_id +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", order_sn='" + order_sn + '\'' +
                ", refuse_reason='" + refuse_reason + '\'' +
                ", orderitems=" + orderitems +
                ", return_order_id=" + return_order_id +
                '}';
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getRefuse_reason() {
        return refuse_reason;
    }

    public void setRefuse_reason(String refuse_reason) {
        this.refuse_reason = refuse_reason;
    }

    public List<GoodOfOrder> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<GoodOfOrder> orderitems) {
        this.orderitems = orderitems;
    }

    public int getReturn_order_id() {
        return return_order_id;
    }

    public void setReturn_order_id(int return_order_id) {
        this.return_order_id = return_order_id;
    }

    public int getOptType() {
        return optType;
    }

    public void setOptType(int optType) {
        this.optType = optType;
    }
}
