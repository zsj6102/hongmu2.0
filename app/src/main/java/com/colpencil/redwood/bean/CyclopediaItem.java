package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 百科收藏 与 浏览记录
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class CyclopediaItem implements Serializable {

    private int cat_id;
    /**
     * 浏览记录id
     */
    private int foot_id;
    /**
     * 百科的id
     */
    private String article_id;
    /**
     * 百科标题
     */
    private String title;
    /**
     * 百科封面描述
     */
    private String page_description;
    /**
     * 百科封面图片
     */
    private String image;
    /**
     * 收藏id
     */
    private int favorite_id;

    /**
     * 针对百科的评论数
     */
    private int commends_count;

    private long creatime;

    private int comment_count;

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    @Override
    public String toString() {
        return "CyclopediaItem{" +
                "foot_id=" + foot_id +
                ", article_id='" + article_id + '\'' +
                ", title='" + title + '\'' +
                ", page_description='" + page_description + '\'' +
                ", image='" + image + '\'' +
                ", favorite_id=" + favorite_id +
                ", comment_count=" + commends_count +
                '}';
    }

    public int getCommends_count() {
        return commends_count;
    }

    public void setCommends_count(int commends_count) {
        this.commends_count = commends_count;
    }

    public int getFoot_id() {
        return foot_id;
    }

    public void setFoot_id(int foot_id) {
        this.foot_id = foot_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPage_description() {
        return page_description;
    }

    public void setPage_description(String page_description) {
        this.page_description = page_description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }

    public long getCreatime() {
        return creatime;
    }

    public void setCreatime(long creatime) {
        this.creatime = creatime;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
}
