package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * 描述：关于与登录相关的实体类
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/26 14 38
 */
public class LoginBean implements Serializable {

    private int code;//结果码
    private String msg;//结果消息
    private String yzm;//验证码
    private boolean flag;
    private int member_id;//用户Id
    private String token;//登录标识
    private UserInfor data;//个人信息
    private String facePath;
    private int sex;
    private String mobile;
    private String password;
    @Override
    public String toString() {
        return "LoginBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", yzm='" + yzm + '\'' +
                ", flag=" + flag +
                ", member_id=" + member_id +
                ", token='" + token + '\'' +
                ", data=" + data +
                '}';
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfor getData() {
        return data;
    }

    public void setData(UserInfor data) {
        this.data = data;
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
