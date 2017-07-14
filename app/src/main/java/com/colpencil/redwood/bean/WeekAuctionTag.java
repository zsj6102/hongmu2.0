package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description:周拍分类的子分类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/8/2
 */
public class WeekAuctionTag implements Serializable {

    private String cat_name;
    private int cat_id;

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
