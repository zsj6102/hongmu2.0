package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 定制界面
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/11
 */
public class CustomReturn implements Serializable {
    private int code;
    private String msg;
    /**
     * 定制列表集合
     */
    private List<Custom> customizeds;

    @Override
    public String toString() {
        return "CustomReturn{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", customizeds=" + customizeds +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Custom> getCustomizeds() {
        return customizeds;
    }

    public void setCustomizeds(List<Custom> customizeds) {
        this.customizeds = customizeds;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
