package com.colpencil.redwood.bean;

import java.io.Serializable;

public class CyclopediaContent implements Serializable {

    private String author;
    private String member_photo;
    private int is_top;
    private String author_face;
    private String author_nickname;
    private int is_splendid;
    private String title;
    private long add_time;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMember_photo() {
        return member_photo;
    }

    public void setMember_photo(String member_photo) {
        this.member_photo = member_photo;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public String getAuthor_face() {
        return author_face;
    }

    public void setAuthor_face(String author_face) {
        this.author_face = author_face;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }
}
