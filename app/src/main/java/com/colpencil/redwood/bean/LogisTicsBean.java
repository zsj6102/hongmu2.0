package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 陈 宝
 * @Description:物流实体类
 * @Email 1041121352@qq.com
 * @date 2016/11/3
 */
public class LogisTicsBean implements Serializable {

    private int logi_id;
    private String logi_name;
    private boolean isChoose;

    public int getLogi_id() {
        return logi_id;
    }

    public void setLogi_id(int logi_id) {
        this.logi_id = logi_id;
    }

    public String getLogi_name() {
        return logi_name;
    }

    public void setLogi_name(String logi_name) {
        this.logi_name = logi_name;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
