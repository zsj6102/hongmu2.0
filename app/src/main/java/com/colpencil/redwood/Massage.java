package com.colpencil.redwood;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 我的消息
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class Massage implements Serializable {
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息id
     */
    private int id;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息时间
     */
    private long sendTime;

    private String img;

    @Override
    public String toString() {
        return "Massage{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
