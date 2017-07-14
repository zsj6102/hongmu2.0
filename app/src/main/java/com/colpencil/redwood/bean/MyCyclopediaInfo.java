package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description:我的百科的实体类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public class MyCyclopediaInfo implements Serializable {

    private int id;
    private String title;
    private String page_description;
    private String image;
    private long creatime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPage_description() {
        return page_description;
    }

    public void setPage_description(String page_description) {
        this.page_description = page_description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getCreatime() {
        return creatime;
    }

    public void setCreatime(long creatime) {
        this.creatime = creatime;
    }
}
