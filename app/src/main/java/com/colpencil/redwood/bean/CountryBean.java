package com.colpencil.redwood.bean;

import java.io.Serializable;

public class CountryBean implements Serializable{
    private String local_name;
    private int region_id;

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }
}
