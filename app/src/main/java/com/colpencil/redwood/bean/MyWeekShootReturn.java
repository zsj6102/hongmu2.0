package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 我的周拍返回值
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/11
 */
public class MyWeekShootReturn implements Serializable {
    private String code;
    private String msg;

    /**
     * 竞拍集合
     */
    private List<MyWeekShootItem> result;

    @Override
    public String toString() {
        return "MyWeekShootReturn{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MyWeekShootItem> getResult() {
        return result;
    }

    public void setResult(List<MyWeekShootItem> result) {
        this.result = result;
    }
}
