package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/8 14 34
 */
public class PostItem implements Serializable {
    /**
     * 帖子id
     */
    private int ote_id;
    /**
     * 帖子图片
     */
    private String ote_img;
    /**
     * 帖子标题
     */
    private String ote_title;
    /**
     * 帖子内容
     */
    private String ote_content;
    /**
     * 收藏id
     */
    private int favorite_id;
    /**
     * 浏览记录id
     */

    private int foot_id;

    private int commends_count;

    @Override
    public String toString() {
        return "PostItem{" +
                "ote_id=" + ote_id +
                ", ote_img='" + ote_img + '\'' +
                ", ote_title='" + ote_title + '\'' +
                ", ote_content='" + ote_content + '\'' +
                ", favorite_id=" + favorite_id +
                ", foot_id=" + foot_id +
                ", commends_count=" + commends_count +
                '}';
    }

    public int getFoot_id() {
        return foot_id;
    }

    public void setFoot_id(int foot_id) {
        this.foot_id = foot_id;
    }

    public int getCommends_count() {
        return commends_count;
    }

    public void setCommends_count(int commends_count) {
        this.commends_count = commends_count;
    }

    public int getOte_id() {
        return ote_id;
    }

    public void setOte_id(int ote_id) {
        this.ote_id = ote_id;
    }

    public String getOte_img() {
        return ote_img;
    }

    public void setOte_img(String ote_img) {
        this.ote_img = ote_img;
    }

    public String getOte_title() {
        return ote_title;
    }

    public void setOte_title(String ote_title) {
        this.ote_title = ote_title;
    }

    public String getOte_content() {
        return ote_content;
    }

    public void setOte_content(String ote_content) {
        this.ote_content = ote_content;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }
}
