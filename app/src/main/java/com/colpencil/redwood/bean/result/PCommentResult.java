package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.PostsComment;

import java.io.Serializable;
import java.util.List;

public class PCommentResult implements Serializable {

    /**
     * replyList : [{"re_content":"哈哈哈哈","nickname":"艾瑞莉娅"}]
     * code : 1
     * msg : 获取帖子评论成功
     */

    private String code;
    private String msg;
    /**
     * re_content : 哈哈哈哈
     * nickname : 艾瑞莉娅
     */

    private List<PostsComment> replyList;
    private int count;
    private int sharenum;
    private int dianzhan_num;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PostsComment> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<PostsComment> replyList) {
        this.replyList = replyList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSharenum() {
        return sharenum;
    }

    public void setSharenum(int sharenum) {
        this.sharenum = sharenum;
    }

    public int getDianzhan_num() {
        return dianzhan_num;
    }

    public void setDianzhan_num(int dianzhan_num) {
        this.dianzhan_num = dianzhan_num;
    }
}
