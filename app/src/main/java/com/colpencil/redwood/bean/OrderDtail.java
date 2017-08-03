package com.colpencil.redwood.bean;

import com.colpencil.redwood.bean.result.OrderArray;
import com.colpencil.redwood.bean.result.Pays;

import java.io.Serializable;
import java.util.List;

public class OrderDtail implements Serializable {
    private List<OrderArray> orderArray;
    private Address address;
    private List<Pays> pays;
    private String cart_ids;

    public List<OrderArray> getOrderArray() {
        return orderArray;
    }

    public void setOrderArray(List<OrderArray> orderArray) {
        this.orderArray = orderArray;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Pays> getPays() {
        return pays;
    }

    public void setPays(List<Pays> pays) {
        this.pays = pays;
    }

    public String getCart_ids() {
        return cart_ids;
    }

    public void setCart_ids(String cart_ids) {
        this.cart_ids = cart_ids;
    }
}
