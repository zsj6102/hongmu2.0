package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description: 邮寄方式
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/12
 */
public class Postages implements Serializable {
    /**
     * 快递方式ID
     */
    private String postageId;
    /**
     * 快递方式头像地址
     */
    private String postageImgPath;
    /**
     * 快递名称
     */
    private String postageName;
    /**
     *选中状态
     */
    private boolean chooseState;

    private float postagePrice;

    @Override
    public String toString() {
        return "Postages{" +
                "postageId='" + postageId + '\'' +
                ", postageImgPath='" + postageImgPath + '\'' +
                ", postageName='" + postageName + '\'' +
                ", chooseState=" + chooseState +
                '}';
    }

    public String getPostageId() {
        return postageId;
    }

    public void setPostageId(String postageId) {
        this.postageId = postageId;
    }

    public String getPostageName() {
        return postageName;
    }

    public void setPostageName(String postageName) {
        this.postageName = postageName;
    }

    public String getPostageImgPath() {
        return postageImgPath;
    }

    public void setPostageImgPath(String postageImgPath) {
        this.postageImgPath = postageImgPath;
    }

    public boolean isChooseState() {
        return chooseState;
    }

    public void setChooseState(boolean chooseState) {
        this.chooseState = chooseState;
    }

    public float getPostagePrice() {
        return postagePrice;
    }

    public void setPostagePrice(float postagePrice) {
        this.postagePrice = postagePrice;
    }
}
