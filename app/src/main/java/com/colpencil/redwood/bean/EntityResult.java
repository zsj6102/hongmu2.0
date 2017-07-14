package com.colpencil.redwood.bean;

import java.io.Serializable;

public class EntityResult<T> implements Serializable {

    public int code;
    private String msg;
    private String message;
    private T result;
    private int totalPoint;
    private int num;
    private int favorite;
    private String url;
    private int count;
    private int like;
    private int counts;
    private String infoValue;
    private int status;
    private long time;
    private long sysTime;
    private T info;
    private T imgs;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getInfoValue() {
        return infoValue;
    }

    public void setInfoValue(String infoValue) {
        this.infoValue = infoValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getSysTime() {
        return sysTime;
    }

    public void setSysTime(long sysTime) {
        this.sysTime = sysTime;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public T getImgs() {
        return imgs;
    }

    public void setImgs(T imgs) {
        this.imgs = imgs;
    }
}
