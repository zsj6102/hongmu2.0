package com.colpencil.redwood.bean;

import java.io.Serializable;

public class StroeOrderItem implements Serializable {
    private Integer store_id;
    private String cart_ids;
    private Integer type_id;
    private Integer coupId;
    private Integer moneyId;
    private String voucherids;
    private String leave_message;

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getCart_ids() {
        return cart_ids;
    }

    public void setCart_ids(String cart_ids) {
        this.cart_ids = cart_ids;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getCoupId() {
        return coupId;
    }

    public void setCoupId(Integer coupId) {
        this.coupId = coupId;
    }

    public Integer getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(Integer moneyId) {
        this.moneyId = moneyId;
    }

    public String getVoucherids() {
        return voucherids;
    }

    public void setVoucherids(String voucherids) {
        this.voucherids = voucherids;
    }

    public String getLeave_message() {
        return leave_message;
    }

    public void setLeave_message(String leave_message) {
        this.leave_message = leave_message;
    }
}
