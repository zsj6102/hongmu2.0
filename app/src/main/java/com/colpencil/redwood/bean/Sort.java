package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 筛选小项
 * @Email 20000263@qq.com
 * @date 2016/8/5
 */
public class Sort implements Serializable {
    /**
     * 筛选项id
     */
    private String sort_id;
    /**
     * 筛选项名
     */
    private String sort_name;

    /**
     * 是否选中
     */
    private boolean chooseState;


    @Override
    public String toString() {
        return "Sort{" +
                "sort_id='" + sort_id + '\'' +
                ", sort_name='" + sort_name + '\'' +
                ", chooseState=" + chooseState +
                '}';
    }

    public String getSort_id() {
        return sort_id;
    }

    public void setSort_id(String sort_id) {
        this.sort_id = sort_id;
    }

    public String getSort_name() {
        return sort_name;
    }

    public void setSort_name(String sort_name) {
        this.sort_name = sort_name;
    }

    public boolean isChooseState() {
        return chooseState;
    }

    public void setChooseState(boolean chooseState) {
        this.chooseState = chooseState;
    }
}
