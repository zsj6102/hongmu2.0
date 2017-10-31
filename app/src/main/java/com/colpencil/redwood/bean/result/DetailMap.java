package com.colpencil.redwood.bean.result;

import java.io.Serializable;
import java.util.List;


public class DetailMap implements Serializable {
    private Integer member_id  ;   //用户id
    private Integer comment_id ;   //评价的id
    private Integer goods_id   ;   //商品的id
    private String content     ;   //评论内容
    private Integer grade      ;   //星级
    private String deteline    ;   //评价时间
    private String nickname    ;   //用户名
    private Double price       ;   //商品价格
    private String thumbnail   ;   //商品图片
    private String name        ;   //商品名称
    private List<String> img           ;   //用户的评价的图片
    private String reply       ;   //回复内容
    private String replytime   ;   //回复时间

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getDeteline() {
        return deteline;
    }

    public void setDeteline(String deteline) {
        this.deteline = deteline;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReplytime() {
        return replytime;
    }

    public void setReplytime(String replytime) {
        this.replytime = replytime;
    }
}
