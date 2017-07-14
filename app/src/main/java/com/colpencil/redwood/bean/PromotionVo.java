package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 陈宝
 * @Description:
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class PromotionVo implements Serializable {
    /**
     * 促销类型
     */
    private String name;
    private String brief;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
