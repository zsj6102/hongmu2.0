package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 筛选大分类
 * @Email 20000263@qq.com
 * @date 2016/8/5
 */
public class SortList implements Serializable {
    /**
     * 筛选项列表
     */
    private String sort_list_name;
    /**
     * 筛选菜单名
     */
    private List<Sort> sort_list;
    /**
     *当前选中的内容
     */
    private String rightTitleName;

    @Override
    public String toString() {
        return "SortList{" +
                "sort_list_name='" + sort_list_name + '\'' +
                ", sort_list=" + sort_list +
                ", rightTitleName='" + rightTitleName + '\'' +
                '}';
    }

    public String getSort_list_name() {
        return sort_list_name;
    }

    public void setSort_list_name(String sort_list_name) {
        this.sort_list_name = sort_list_name;
    }

    public List<Sort> getSort_list() {
        return sort_list;
    }

    public void setSort_list(List<Sort> sort_list) {
        this.sort_list = sort_list;
    }

    public String getRightTitleName() {
        return rightTitleName;
    }

    public void setRightTitleName(String rightTitleName) {
        this.rightTitleName = rightTitleName;
    }
}
