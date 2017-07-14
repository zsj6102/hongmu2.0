package com.colpencil.redwood.bean.result;

import java.io.Serializable;
import java.util.List;

public class WeekShootDetailResult implements Serializable {

    private float price;
    private String type_name;
    private String goods_name;
    private long begin_time;
    private long end_time;
    private long system_time;
    private int code;
    private String msg;
    private List<String> img;
    private float mark_up;
    private int status;
    private int winner;
    private int buy_status;
    private int goods_id;
    private int product_id;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public long getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getSystem_time() {
        return system_time;
    }

    public void setSystem_time(long system_time) {
        this.system_time = system_time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getMark_up() {
        return mark_up;
    }

    public void setMark_up(float mark_up) {
        this.mark_up = mark_up;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getBuy_status() {
        return buy_status;
    }

    public void setBuy_status(int buy_status) {
        this.buy_status = buy_status;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
