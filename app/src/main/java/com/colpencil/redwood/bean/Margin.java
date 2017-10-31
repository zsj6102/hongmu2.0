package com.colpencil.redwood.bean;

import java.io.Serializable;

public class Margin implements Serializable {
    private String store_name;
    private Double margin;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }
}
