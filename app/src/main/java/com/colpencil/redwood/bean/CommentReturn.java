package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description:评论返回值
 * @Email 20000263@qq.com
 * @date 2016/8/9
 */
public class CommentReturn implements Serializable {
    private String code;
    private String message;
    /**
     * 评价列表
     */
    private List<Comment> result;

    @Override
    public String toString() {
        return "CommentReturn{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Comment> getResult() {
        return result;
    }

    public void setResult(List<Comment> result) {
        this.result = result;
    }
}
