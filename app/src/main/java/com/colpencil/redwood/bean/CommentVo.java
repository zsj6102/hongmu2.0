package com.colpencil.redwood.bean;


import java.io.Serializable;
import java.util.List;

/**
 * @author 陈宝
 * @Description:评论的实体类
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class CommentVo implements Serializable {

    private int member_id;
    private int ote_stick;
    private int ote_id;
    private long createtime;
    private String type_name;
    private int fscount;
    private String ote_title;
    private int like_count;
    private String uname;
    private List<String> ote_img;
    private String type_id;
    private String ote_content;
    private int likecount;
    private String url;
    private String ote_looknum;
    private int sec_id;
    private String face;
    private int com_count;
    private String name;
    private String nickname;
    private String updatetime;
    private int ote_digest;
    private String status;
    private long systemTime;
    private String member_photo;
    private int like;
    private int ote_sharenum;
    private List<String> original_img;

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public void setOte_stick(int ote_stick) {
        this.ote_stick = ote_stick;
    }

    public void setOte_id(int ote_id) {
        this.ote_id = ote_id;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public void setFscount(int fscount) {
        this.fscount = fscount;
    }

    public void setOte_title(String ote_title) {
        this.ote_title = ote_title;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public void setOte_content(String ote_content) {
        this.ote_content = ote_content;
    }

    public void setLikecount(int likecount) {
        this.likecount = likecount;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOte_looknum(String ote_looknum) {
        this.ote_looknum = ote_looknum;
    }

    public void setSec_id(int sec_id) {
        this.sec_id = sec_id;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public void setCom_count(int com_count) {
        this.com_count = com_count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setOte_digest(int ote_digest) {
        this.ote_digest = ote_digest;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMember_id() {
        return member_id;
    }

    public int getOte_stick() {
        return ote_stick;
    }

    public int getOte_id() {
        return ote_id;
    }

    public long getCreatetime() {
        return createtime;
    }

    public String getType_name() {
        return type_name;
    }

    public int getFscount() {
        return fscount;
    }

    public String getOte_title() {
        return ote_title;
    }

    public int getLike_count() {
        return like_count;
    }

    public String getUname() {
        return uname;
    }


    public String getType_id() {
        return type_id;
    }

    public String getOte_content() {
        return ote_content;
    }

    public int getLikecount() {
        return likecount;
    }

    public String getUrl() {
        return url;
    }

    public String getOte_looknum() {
        return ote_looknum;
    }

    public int getSec_id() {
        return sec_id;
    }

    public String getFace() {
        return face;
    }

    public int getCom_count() {
        return com_count;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public int getOte_digest() {
        return ote_digest;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getOte_img() {
        return ote_img;
    }

    public void setOte_img(List<String> ote_img) {
        this.ote_img = ote_img;
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
