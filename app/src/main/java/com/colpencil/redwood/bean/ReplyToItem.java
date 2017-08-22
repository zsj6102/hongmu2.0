package com.colpencil.redwood.bean;

import java.io.Serializable;

public class ReplyToItem implements Serializable {
    private Integer member_id;
    private String store_name;  //   回复评论会员昵称或商家名称
    private String member_photo;  //   回复评论会员等级图标
    private Integer re_id;  //   回复ID
    private Integer store_type;  //   回复商家类型
    private String store_path;  //   回复评论会员头像
    private String re_content;  //   回复内容
    private String create_time;  //   回复时间
//    private Integer replay_type;  //   回复类型
//    private String child_to_member;  //   回复谁的内容
    private String to_member_name; //对谁的回复
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

//    public Integer getReplay_type() {
//        return replay_type;
//    }
//
//    public void setReplay_type(Integer replay_type) {
//        this.replay_type = replay_type;
//    }
//
//    public String getChild_to_member() {
//        return child_to_member;
//    }
//
//    public void setChild_to_member(String child_to_member) {
//        this.child_to_member = child_to_member;
//    }

    public String getTo_member_name() {
        return to_member_name;
    }

    public void setTo_member_name(String to_member_name) {
        this.to_member_name = to_member_name;
    }
}
