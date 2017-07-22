package com.colpencil.redwood.bean;

import java.io.Serializable;

public class StatusData implements Serializable {
    private int disabled;
    private String reason;
    private int store_id;
    private String store_name;
    private int store_type;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getStore_type() {
        return store_type;
    }

    public void setStore_type(int store_type) {
        this.store_type = store_type;
    }


    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
