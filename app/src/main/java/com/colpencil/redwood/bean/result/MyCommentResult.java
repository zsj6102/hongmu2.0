package com.colpencil.redwood.bean.result;

import com.colpencil.redwood.bean.MyComment;

import java.io.Serializable;
import java.util.List;

/**
 * @author 陈宝
 * @Description:我的帖子返回结果集
 * @Email DramaScript@outlook.com
 * @date 2016/8/2
 */
public class MyCommentResult implements Serializable {

    private String msg;
    private String code;
    private List<MyComment> myNoteList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MyComment> getMyNoteList() {
        return myNoteList;
    }

    public void setMyNoteList(List<MyComment> myNoteList) {
        this.myNoteList = myNoteList;
    }
}
