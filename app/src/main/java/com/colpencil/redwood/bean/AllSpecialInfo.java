package com.colpencil.redwood.bean;

import java.io.Serializable;

public class AllSpecialInfo implements Serializable {

    private int special_id;
    private String special_name;
    private long create_time;
    private int is_top;
    private Integer cat_id;
    private String spe_picture;

    public Integer getCat_id() {
        return cat_id;
    }

    public void setCat_id(Integer cat_id) {
        this.cat_id = cat_id;
    }

    public int getSpecial_id() {
        return special_id;
    }

    public void setSpecial_id(int special_id) {
        this.special_id = special_id;
    }

    public String getSpecial_name() {
        return special_name;
    }

    public void setSpecial_name(String special_name) {
        this.special_name = special_name;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public String getSpe_picture() {
        return spe_picture;
    }

    public void setSpe_picture(String spe_picture) {
        this.spe_picture = spe_picture;
    }
}
