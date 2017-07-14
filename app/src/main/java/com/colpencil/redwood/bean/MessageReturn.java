package com.colpencil.redwood.bean;

import com.colpencil.redwood.Massage;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 我的消息
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class MessageReturn implements Serializable {

    private String code;
    private String message;
    /**
     * 消息列表
     */
    private List<Massage> result;

    @Override
    public String toString() {
        return "MessageReturn{" +
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

    public List<Massage> getResult() {
        return result;
    }

    public void setResult(List<Massage> result) {
        this.result = result;
    }
}
