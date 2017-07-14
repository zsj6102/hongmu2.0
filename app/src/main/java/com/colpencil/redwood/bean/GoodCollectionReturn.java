package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 商品收藏
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class GoodCollectionReturn implements Serializable {
    private String code;
    private String message;
    /**
     *商品列表
     */
    private List<GoodsItem> result;

    @Override
    public String toString() {
        return "GoodCollection{" +
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

    public List<GoodsItem> getResult() {
        return result;
    }

    public void setResult(List<GoodsItem> result) {
        this.result = result;
    }
}
