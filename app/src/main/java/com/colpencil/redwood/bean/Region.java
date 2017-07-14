package com.colpencil.redwood.bean;

import java.io.Serializable;

public class Region implements Serializable {
    private int region_id;
    private String local_name;
    private int p_region_id;
    private boolean haschild;

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getLocal_name() {
        return local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public int getP_region_id() {
        return p_region_id;
    }

    public void setP_region_id(int p_region_id) {
        this.p_region_id = p_region_id;
    }

    public boolean isHaschild() {
        return haschild;
    }

    public void setHaschild(boolean haschild) {
        this.haschild = haschild;
    }
}
