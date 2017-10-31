package com.colpencil.redwood.bean;

import java.io.Serializable;

public class CoverSpecialItem implements Serializable {
    private int special_id;//封面专场的id
    private String special_name;//名字
    private String img;//封面
    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
