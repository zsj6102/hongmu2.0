package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @Description:Banner的实体类
 * @author 陈宝
 * @Email  DramaScript@outlook.com
 * @date 2016/7/29
 */
public class BannerVo implements Serializable {

    private int goodsId;
    private String htmlurl;
    private String type;
    private String url;
    private int advId;
    private float image_scale;

    public void setHtmlurl(String htmlurl) {
        this.htmlurl = htmlurl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlurl() {
        return htmlurl;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getAdvId() {
        return advId;
    }

    public void setAdvId(int advId) {
        this.advId = advId;
    }

    public float getImage_scale() {
        return image_scale;
    }

    public void setImage_scale(float image_scale) {
        this.image_scale = image_scale;
    }
}
