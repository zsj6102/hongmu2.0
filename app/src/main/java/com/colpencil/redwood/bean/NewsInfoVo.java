package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 陈宝
 * @Description:新闻的实体类
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class NewsInfoVo implements Serializable {

    private int comment_count;
    private int share_count;
    private String author_face;
    private String image;
    private long current_time;
    private String author_nickname;
    private int is_splendid;
    private int cat_id;
    private String author;
    private int article_id;
    private String page_description;
    private int is_top;
    private float image_scale;
    private long add_time;
    private String page_title;
    private String member_photo;

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public String getAuthor_face() {
        return author_face;
    }

    public void setAuthor_face(String author_face) {
        this.author_face = author_face;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(long current_time) {
        this.current_time = current_time;
    }

    public String getAuthor_nickname() {
        return author_nickname;
    }

    public void setAuthor_nickname(String author_nickname) {
        this.author_nickname = author_nickname;
    }

    public int getIs_splendid() {
        return is_splendid;
    }

    public void setIs_splendid(int is_splendid) {
        this.is_splendid = is_splendid;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getPage_description() {
        return page_description;
    }

    public void setPage_description(String page_description) {
        this.page_description = page_description;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public float getImage_scale() {
        return image_scale;
    }

    public void setImage_scale(float image_scale) {
        this.image_scale = image_scale;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getPage_title() {
        return page_title;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }
}
