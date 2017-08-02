package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.CartItem;

import java.io.Serializable;
import java.util.List;

public class CartList implements Serializable {
    private int store_id;
    private String store_name;
    private boolean allChoose;
    private List<CartItem> goodslist;

    public int getStore_id() {
        return store_id;
    }

    public boolean isAllChoose() {
        return allChoose;
    }

    public void setAllChoose(boolean allChoose) {
        this.allChoose = allChoose;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public List<CartItem> getGoodslist() {
        return goodslist;
    }

    public void setGoodslist(List<CartItem> goodslist) {
        this.goodslist = goodslist;
    }

    public Object getItem(int position) {
        if (position == 0) {
            return store_name;
        } else {
            return goodslist.get(position - 1);
        }
    }
    public int getItemCount(){
        return goodslist.size()+1;
    }
}
