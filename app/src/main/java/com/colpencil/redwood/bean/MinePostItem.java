package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class MinePostItem implements Serializable {
    private int ote_id;//帖子id;
    private String ote_title;//帖子标题
    private String ote_content;//帖子内容
    private int con_count;//评价数
    private int share_count;//分享数
    private int like_count;//点赞数
    private String createtime;//帖子发帖时间
    private String nowtime;//当前时间
    private int is_top;//是否置顶 0：不置顶 1：置顶
    private int ote_digest;//是否加精 0不加精 1：加精
    private int islike;//1:已点赞 0：未点赞
    private List<String> img_list;//商品图片集合

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getOte_id() {
        return ote_id;
    }

    public void setOte_id(int ote_id) {
        this.ote_id = ote_id;
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

    public int getCon_count() {
        return con_count;
    }

    public void setCon_count(int con_count) {
        this.con_count = con_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public String getCreate_time() {
        return createtime;
    }

    public void setCreate_time(String create_time) {
        this.createtime = create_time;
    }

    public String getNowtime() {
        return nowtime;
    }

    public void setNowtime(String nowtime) {
        this.nowtime = nowtime;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getOte_digest() {
        return ote_digest;
    }

    public void setOte_digest(int ote_digest) {
        this.ote_digest = ote_digest;
    }

    public int getIslike() {
        return islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }

    public List<String> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<String> img_list) {
        this.img_list = img_list;
    }
}
