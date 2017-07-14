package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description:发帖选择的类型
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public class PostTypeInfo implements Serializable {

    private String typename;
    private boolean isChoose;
    private String sec_id;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getSec_id() {
        return sec_id;
    }

    public void setSec_id(String sec_id) {
        this.sec_id = sec_id;
    }
}
