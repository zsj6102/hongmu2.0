package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class SpecialPastItem implements Serializable {

    private String time;
    private List<String> imgUrl;
    private int special_id;
    private String special_name;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
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
}
