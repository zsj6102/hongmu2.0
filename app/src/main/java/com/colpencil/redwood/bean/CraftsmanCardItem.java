package com.colpencil.redwood.bean;

import java.io.Serializable;

public class CraftsmanCardItem implements Serializable{
    private String cat_name;
    private int cat_id;

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {

        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
}
