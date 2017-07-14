package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 规格 Item项
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/5
 */
public class ItemSpecVo implements Serializable {
    /**
     *具体规格id
     */
    private int spec_value_id;
    /**
     *具体的规格(如:红色)
     */
    private String spec_value;

    public int getSpec_value_id() {
        return spec_value_id;
    }

    public void setSpec_value_id(int spec_value_id) {
        this.spec_value_id = spec_value_id;
    }

    public String getSpec_value() {
        return spec_value;
    }

    public void setSpec_value(String spec_value) {
        this.spec_value = spec_value;
    }

    @Override
    public String toString() {
        return "ItemSpecVo{" +
                "spec_value_id=" + spec_value_id +
                ", spec_value='" + spec_value + '\'' +
                '}';
    }
}
