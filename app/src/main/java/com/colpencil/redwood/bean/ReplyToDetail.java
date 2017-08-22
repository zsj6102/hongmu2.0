package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

public class ReplyToDetail implements Serializable {
    private Integer member_id;      // 评论会员ID
    private String store_name;       // 评论会员昵称或商家名称
    private String member_photo;       // 评论会员等级图标
    private Integer re_id;      // 评论ID
    private Integer store_type;      // 评论商家类型
    private String store_path;       // 评论会员头像
    private String re_content;       // 评论内容
    private String create_time;         // 评论时间
    private List<ReplyToItem> replay;         // 回复评论的集合

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }

    public Integer getRe_id() {
        return re_id;
    }

    public void setRe_id(Integer re_id) {
        this.re_id = re_id;
    }

    public Integer getStore_type() {
        return store_type;
    }

    public void setStore_type(Integer store_type) {
        this.store_type = store_type;
    }

    public String getStore_path() {
        return store_path;
    }

    public void setStore_path(String store_path) {
        this.store_path = store_path;
    }

    public String getRe_content() {
        return re_content;
    }

    public void setRe_content(String re_content) {
        this.re_content = re_content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<ReplyToItem> getReplay() {
        return replay;
    }

    public void setReplay(List<ReplyToItem> replay) {
        this.replay = replay;
    }
}
