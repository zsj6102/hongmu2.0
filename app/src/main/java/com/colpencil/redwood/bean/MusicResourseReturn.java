package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 音乐接口返回值
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/30
 */
public class MusicResourseReturn implements Serializable {
    private String code;
    private String message;
    /**
     * 禅音列表
     */
    private List<Music> result;

    @Override
    public String toString() {
        return "MusicResourseReturn{" +
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

    public List<Music> getResult() {
        return result;
    }

    public void setResult(List<Music> result) {
        this.result = result;
    }
}
