package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 订单详情
 * @Email 20000263@qq.com
 * @date 2016/8/22
 */
public class OrderDetailsBean implements Serializable {
//二期添加   商家ID，名称，留言
    private int store_id;
    private String store_name;
    private String leave_message;

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStrore_name() {
        return store_name;
    }

    public void setStrore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getLeave_message() {
        return leave_message;
    }

    public void setLeave_message(String leave_message) {
        this.leave_message = leave_message;
    }

    /**
     * 订单ID
     */
    private String order_sn;
    /**
     * 状态
     */
    private int stateFlag;
    /**
     * 1：待付款 2：已付款 3：已完成 4：已取消5:退款中 6：已退款 7：退款被拒绝
     */
    private String stateName;
    /**
     * 收货地址
     */
    private String ship_area;
    /**
     * 收货人姓名，以及联系方式
     */
    private String addresscontact;
    /**
     * 商品总价
     */
    private float countPrice;
    /**
     * 订单邮费
     */
    private float postagePrice;
    /**
     * 优惠券抵用价
     */
    private float moneydiscount;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 订单总价
     */
    private float payPrice;
    /**
     * 代金券
     */
    private float coupondiscount;
    /**
     * 购买商品信息
     */
    private List<GoodOfOrder> orderItemsList;

    private float discount;

    private float voucherids;

    private String create_time;

    @Override
    public String toString() {
        return "OrderDetailsBean{" +
                "order_sn='" + order_sn + '\'' +
                ", stateFlag=" + stateFlag +
                ", stateName='" + stateName + '\'' +
                ", ship_area='" + ship_area + '\'' +
                ", addresscontact='" + addresscontact + '\'' +
                ", countPrice=" + countPrice +
                ", postagePrice=" + postagePrice +
                ", moneydiscount=" + moneydiscount +
                ", payType='" + payType + '\'' +
                ", payPrice=" + payPrice +
                ", orderItemsList=" + orderItemsList +
                '}';
    }

    public float getCountPrice() {
        return countPrice;
    }

    public void setCountPrice(float countPrice) {
        this.countPrice = countPrice;
    }

    public float getPostagePrice() {
        return postagePrice;
    }

    public void setPostagePrice(float postagePrice) {
        this.postagePrice = postagePrice;
    }

    public float getMoneydiscount() {
        return moneydiscount;
    }

    public void setMoneydiscount(float moneydiscount) {
        this.moneydiscount = moneydiscount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public float getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(float payPrice) {
        this.payPrice = payPrice;
    }

    public List<GoodOfOrder> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<GoodOfOrder> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    public int getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(int stateFlag) {
        this.stateFlag = stateFlag;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getShip_area() {
        return ship_area;
    }

    public void setShip_area(String ship_area) {
        this.ship_area = ship_area;
    }

    public String getAddresscontact() {
        return addresscontact;
    }

    public void setAddresscontact(String addresscontact) {
        this.addresscontact = addresscontact;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public float getCoupondiscount() {
        return coupondiscount;
    }

    public void setCoupondiscount(float coupondiscount) {
        this.coupondiscount = coupondiscount;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getVoucherids() {
        return voucherids;
    }

    public void setVoucherids(float voucherids) {
        this.voucherids = voucherids;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
