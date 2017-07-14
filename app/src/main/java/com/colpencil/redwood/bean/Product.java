package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description:
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class Product implements Serializable {
    /**
     * 不同规格组合下商品别名
     */
    private String sn;
    /**
     * 不同规格组合下商品价格
     */
    private double price;
    /**
     * 规格的组合(例如:{ 红色,10nn })
     */
    private String specs;
    private int product_id;
    private int store;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Product{" +
                "sn='" + sn + '\'' +
                ", price=" + price +
                ", specs='" + specs + '\'' +
                ", product_id=" + product_id +
                ", store=" + store +
                '}';
    }
}
