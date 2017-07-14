package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 优惠券返回参数
 * @Email 20000263@qq.com
 * @date 2016/8/8
 */
public class CouponsResult implements Serializable {

    private int code;
    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private List<Coupon> cashList;
        private List<Coupon> voucherList;
        private int cashListCount;
        private int voucherListCount;

        public List<Coupon> getCashList() {
            return cashList;
        }

        public void setCashList(List<Coupon> cashList) {
            this.cashList = cashList;
        }

        public List<Coupon> getVoucherList() {
            return voucherList;
        }

        public void setVoucherList(List<Coupon> voucherList) {
            this.voucherList = voucherList;
        }

        public int getCashListCount() {
            return cashListCount;
        }

        public void setCashListCount(int cashListCount) {
            this.cashListCount = cashListCount;
        }

        public int getVoucherListCount() {
            return voucherListCount;
        }

        public void setVoucherListCount(int voucherListCount) {
            this.voucherListCount = voucherListCount;
        }
    }
}
