package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 收货地址列表的返回值
 * @Email 20000263@qq.com
 * @date 2016/8/9
 */
public class AddressReturn implements Serializable {
    private String code;
    private String msg;
    /**
     * 地址信息
     */
    private List<Address> addressInfo;

    @Override
    public String toString() {
        return "AddressReturn{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", addressInfo=" + addressInfo +
                '}';
    }

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

    public List<Address> getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(List<Address> addressInfo) {
        this.addressInfo = addressInfo;
    }
}
