package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 商品分类接口返回值
 * @Email 20000263@qq.com
 * @date 2016/8/5
 */
public class CatReturnData implements Serializable {

    private String code;

    private String message;

    private List<Cat> result;

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

    public List<Cat> getResult() {
        return result;
    }

    public void setResult(List<Cat> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CatReturnData{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
