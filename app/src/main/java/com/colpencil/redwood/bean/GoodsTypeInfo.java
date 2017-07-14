package com.colpencil.redwood.bean;

import java.io.Serializable;

public class GoodsTypeInfo implements Serializable {
    private int cat_id;
    private String name;

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
