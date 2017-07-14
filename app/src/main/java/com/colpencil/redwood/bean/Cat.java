package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：商品一级分类信息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/5 08 53
 */
public class Cat implements Serializable {
    /**
     * 类别id
     */
    private int cat_id;
    /**
     * 类别名称
     */
    private String name;
    /**
     * 类别1下的子类别
     */
    private List<ChidrenCat> children;
    /**
     * 是否有选中标识
     */
    private boolean chooseState;

    @Override
    public String toString() {
        return "Cat{" +
                "cat_id=" + cat_id +
                ", name='" + name + '\'' +
                ", children=" + children +
                ", chooseState=" + chooseState +
                '}';
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChidrenCat> getChildren() {
        return children;
    }

    public void setChildren(List<ChidrenCat> children) {
        this.children = children;
    }


    public boolean isChooseState() {
        return chooseState;
    }

    public void setChooseState(boolean chooseState) {
        this.chooseState = chooseState;
    }

}
