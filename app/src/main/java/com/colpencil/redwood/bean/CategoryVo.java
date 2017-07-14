package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:分类的实体类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CategoryVo implements Serializable {

    private String cat_name;
    private String cat_id;
    private List<CategoryItem> cat_child;

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public List<CategoryItem> getCat_child() {
        return cat_child;
    }

    public void setCat_child(List<CategoryItem> cat_child) {
        this.cat_child = cat_child;
    }
}
