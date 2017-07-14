package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 购物车返回值
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/10
 */
public class ShoppingCartReturn implements Serializable {
    private String code;
    private String msg;
    /**
     * 购物车的列表
     */
    private List<CartItem> goodsItemList;

    @Override
    public String toString() {
        return "ShoppingCartReturn{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", goodsItemList=" + goodsItemList +
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

    public List<CartItem> getGoodsItemList() {
        return goodsItemList;
    }

    public void setGoodsItemList(List<CartItem> goodsItemList) {
        this.goodsItemList = goodsItemList;
    }
}
