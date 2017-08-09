package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class OrderDetailInfo implements Serializable {
    private int order_status;
    private String order_status_str;
    private int order_id;
    private String ship_consignee;
    private String order_sn;
    private String create_time;
    private String ship_area;

    private String ship_type;
    private List<OrderItemGoodsView> orderItemsList;
    private int store_id;
    private String store_name;
    private String leave_message;
    private double countPrice;
    private double postagePrice;
    private double moneydiscount;
    private double voucherids;
    private double discount;
    private double payPrice;
    private String payType;

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getOrder_status_str() {
        return order_status_str;
    }

    public void setOrder_status_str(String order_status_str) {
        this.order_status_str = order_status_str;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getShip_consignee() {
        return ship_consignee;
    }

    public void setShip_consignee(String ship_consignee) {
        this.ship_consignee = ship_consignee;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getShip_area() {
        return ship_area;
    }

    public void setShip_area(String ship_area) {
        this.ship_area = ship_area;
    }

    public String getShip_type() {
        return ship_type;
    }

    public void setShip_type(String ship_type) {
        this.ship_type = ship_type;
    }

    public List<OrderItemGoodsView> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItemGoodsView> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getLeave_message() {
        return leave_message;
    }

    public void setLeave_message(String leave_message) {
        this.leave_message = leave_message;
    }

    public double getCountPrice() {
        return countPrice;
    }

    public void setCountPrice(double countPrice) {
        this.countPrice = countPrice;
    }

    public double getPostagePrice() {
        return postagePrice;
    }

    public void setPostagePrice(double postagePrice) {
        this.postagePrice = postagePrice;
    }

    public double getMoneydiscount() {
        return moneydiscount;
    }

    public void setMoneydiscount(double moneydiscount) {
        this.moneydiscount = moneydiscount;
    }

    public double getVoucherids() {
        return voucherids;
    }

    public void setVoucherids(double voucherids) {
        this.voucherids = voucherids;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
