package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 陈宝
 * @Description:我的评论的实体类
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class MyComment implements Serializable {

    private int member_id;
    private int ote_stick;
    private int ote_id;
    private long createtime;
    private String ote_title;
    private int like_count;
    private List<String> ote_img;
    private String type_id;
    private String ote_content;
    private String url;
    private String ote_looknum;
    private int sec_id;
    private String face;
    private int com_count;
    private String nickname;
    private String updatetime;
    private int ote_digest;
    private String status;
    private long systemTime;
    private String member_photo;
    private int like;
    private int ote_sharenum;
    private List<String> original_img;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getOte_stick() {
        return ote_stick;
    }

    public void setOte_stick(int ote_stick) {
        this.ote_stick = ote_stick;
    }

    public int getOte_id() {
        return ote_id;
    }

    public void setOte_id(int ote_id) {
        this.ote_id = ote_id;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getOte_title() {
        return ote_title;
    }

    public void setOte_title(String ote_title) {
        this.ote_title = ote_title;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public List<String> getOte_img() {
        return ote_img;
    }

    public void setOte_img(List<String> ote_img) {
        this.ote_img = ote_img;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getOte_content() {
        return ote_content;
    }

    public void setOte_content(String ote_content) {
        this.ote_content = ote_content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOte_looknum() {
        return ote_looknum;
    }

    public void setOte_looknum(String ote_looknum) {
        this.ote_looknum = ote_looknum;
    }

    public int getSec_id() {
        return sec_id;
    }

    public void setSec_id(int sec_id) {
        this.sec_id = sec_id;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getCom_count() {
        return com_count;
    }

    public void setCom_count(int com_count) {
        this.com_count = com_count;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getOte_digest() {
        return ote_digest;
    }

    public void setOte_digest(int ote_digest) {
        this.ote_digest = ote_digest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getOte_sharenum() {
        return ote_sharenum;
    }

    public void setOte_sharenum(int ote_sharenum) {
        this.ote_sharenum = ote_sharenum;
    }

    public List<String> getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(List<String> original_img) {
        this.original_img = original_img;
    }
}
