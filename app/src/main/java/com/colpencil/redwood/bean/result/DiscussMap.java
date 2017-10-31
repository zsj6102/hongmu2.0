package com.colpencil.redwood.bean.result;

import java.io.Serializable;

public class DiscussMap implements Serializable {

    private String store_logo;//   回复人如果是商家显示这个商家logo
    private String member_photo;//   回复人等级图标
    private String store_type;//   回复人是商家的话显示类型图标
    private String store_type_name;//   名称
    private Integer member_id;//   回复人id
    private String content;//   回复人回复的内容
    private String nickname;//   回复人昵称
    private String create_time;//   咨询时间
    private Integer to_member;//   咨询人id
    private String to_member_nickname;//   咨询人昵称
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStore_logo() {
        return store_logo;
    }

    public void setStore_logo(String store_logo) {
        this.store_logo = store_logo;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }

    public String getStore_type() {
        return store_type;
    }

    public void setStore_type(String store_type) {
        this.store_type = store_type;
    }

    public String getStore_type_name() {
        return store_type_name;
    }

    public void setStore_type_name(String store_type_name) {
        this.store_type_name = store_type_name;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Integer getTo_member() {
        return to_member;
    }

    public void setTo_member(Integer to_member) {
        this.to_member = to_member;
    }

    public String getTo_member_nickname() {
        return to_member_nickname;
    }

    public void setTo_member_nickname(String to_member_nickname) {
        this.to_member_nickname = to_member_nickname;
    }
}
