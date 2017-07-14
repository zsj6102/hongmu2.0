package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 百科收藏返回值
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class CyclopediaCollectionReturn implements Serializable {
    private String code;
    private String message;
    /**
     * 百科列表
     */
    private List<CyclopediaItem> result;

    @Override
    public String toString() {
        return "CyclopediaCollectionReturn{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

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

    public List<CyclopediaItem> getResult() {
        return result;
    }

    public void setResult(List<CyclopediaItem> result) {
        this.result = result;
    }
}
