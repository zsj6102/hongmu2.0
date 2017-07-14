package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 陈宝
 * @Description:商品的尺寸实体类
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class GoodNorm implements Serializable {
    /**
     * 类型的id
     */
    private int spec_value_id;
    private String spec_value;
    private boolean isChoose;

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

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
