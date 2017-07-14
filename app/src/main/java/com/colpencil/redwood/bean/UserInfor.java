package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/28 17 57
 */
public class UserInfor implements Serializable {
    private String face;//用户头像
    private String headPath;//个人中心头像
    private String nickName;//昵称
    private String userName;//个人中心用户名
    private int cllectionCount;//收藏商品总数
    private int integralBalance;//当前用户积分余额，及可用积分
    private int msgCount;//消息中心消息总数
    private String customerGrade;//会员等级
    private String phone;//绑定手机号码
    private String weChatNo;//微信账号
    private String qqNo;//QQ号码
    private String email;//邮箱号码
    private int sex;//性别

    @Override
    public String toString() {
        return "UserInfor{" +
                "face='" + face + '\'' +
                ", headPath='" + headPath + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", cllectionCount=" + cllectionCount +
                ", integralBalance=" + integralBalance +
                ", msgCount=" + msgCount +
                ", customerGrade='" + customerGrade + '\'' +
                ", phone='" + phone + '\'' +
                ", weChatNo='" + weChatNo + '\'' +
                ", qqNo='" + qqNo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCllectionCount() {
        return cllectionCount;
    }

    public void setCllectionCount(int cllectionCount) {
        this.cllectionCount = cllectionCount;
    }

    public int getIntegralBalance() {
        return integralBalance;
    }

    public void setIntegralBalance(int integralBalance) {
        this.integralBalance = integralBalance;
    }

    public String getCustomerGrade() {
        return customerGrade;
    }

    public void setCustomerGrade(String customerGrade) {
        this.customerGrade = customerGrade;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeChatNo() {
        return weChatNo;
    }

    public void setWeChatNo(String weChatNo) {
        this.weChatNo = weChatNo;
    }

    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
