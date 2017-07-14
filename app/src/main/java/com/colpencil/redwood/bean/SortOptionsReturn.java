package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 获取商品筛选项接口返回参数
 * @Email 20000263@qq.com
 * @date 2016/8/5
 */
public class SortOptionsReturn implements Serializable {

    private String code;

    private String message;
    /**
     * 筛选菜单列表
     */
    private List<SortList> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SortList> getResult() {
        return result;
    }

    public void setResult(List<SortList> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SortOptionsReturn{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
