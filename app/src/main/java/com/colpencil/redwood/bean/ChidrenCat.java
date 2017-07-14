package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 商品二级分类
 * @Email 20000263@qq.com
 * @date 2016/8/5
 */
public class ChidrenCat implements Serializable {
    /**
     * 子类别id
     */
    private int child_cat_id;
    /**
     * 子类别名称
     */
    private String child_name;
    /**
     * 子类别图标
     */
    private String child_image;

    @Override
    public String toString() {
        return "ChidrenCat{" +
                "child_cat_id=" + child_cat_id +
                ", child_name='" + child_name + '\'' +
                ", child_image='" + child_image + '\'' +
                '}';
    }

    public int getChild_cat_id() {
        return child_cat_id;
    }

    public void setChild_cat_id(int child_cat_id) {
        this.child_cat_id = child_cat_id;
    }

    public String getChild_name() {
        return child_name;
    }

    public void setChild_name(String child_name) {
        this.child_name = child_name;
    }

    public String getChild_image() {
        return child_image;
    }

    public void setChild_image(String child_image) {
        this.child_image = child_image;
    }
}
