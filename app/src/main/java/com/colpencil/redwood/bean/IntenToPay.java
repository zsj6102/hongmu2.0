package com.colpencil.redwood.bean;


import com.colpencil.redwood.bean.result.OrderArray;

import java.io.Serializable;
import java.util.List;

public class IntenToPay implements Serializable {
    private int addr_id;
    private List<PayType> pays;
    private List<OrderArray> orderArrays;

    public int getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(int addr_id) {
        this.addr_id = addr_id;
    }

    public List<PayType> getPays() {
        return pays;
    }

    public void setPays(List<PayType> pays) {
        this.pays = pays;
    }

    public List<OrderArray> getOrderArrays() {
        return orderArrays;
    }

    public void setOrderArrays(List<OrderArray> orderArrays) {
        this.orderArrays = orderArrays;
    }
}
