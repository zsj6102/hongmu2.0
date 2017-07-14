package com.colpencil.redwood.bean;

import java.io.Serializable;

/**
 * @author 曾 凤
 * @Description: 订单中的商品
 * @Email 20000263@qq.com
 * @date 2016/8/11
 */
public class GoodOfOrder implements Serializable {
    /**
     * 商品ID
     */
    private int goods_id;
    /**
     * 是否申请售后(0:可以申请.1已申请过了)
     */
    private int isAfterSales;
    /**
     * 商品头像地址(订单中心的头像地址)
     */
    private String image;
    /**
     * 商品头像地址（售后中心的头像地址）
     */
    private String goodHeadPath;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格（订单中心的商品价格显示）
     */
    private float price;
    /**
     * 商品价格（售后中的商品价格显示）
     */
    private float salePrice;
    /**
     * 商品规格
     */
    private String specs;
    /**
     * 购买数量（订单中心的商品数量的显示）
     */
    private int num;
    /**
     * 购买数量（售后中心的商品数量的显示）
     */
    private int count;
    /**
     * 订单项id
     */
    private int item_id;
    /**
     * 是否评价(0:未1:已)
     */
    private int addcommentflag;
    /**
     * 0:正常,1:申请退款,2:申请换货,3:退货已拒绝,4: 换货已拒绝,5: 退货已通过审核6: 换货已通过审核
     * 7: 退货已收货,8: 已收货,换货已发出9:退货完成10:换货完成11:申请退款12:退款已拒绝
     * 13:换货完成14:退款已经收到15:退款完成16:买家已发货17:买家已收货
     */
    private int state;
    /**
     * 状态名称
     */
    private String itemstatus_name;
    /**
     * 0:未填写物流 1:已填写
     */
    private int is_write;

    private String img;

    @Override
    public String toString() {
        return "GoodOfOrder{" +
                "goods_id=" + goods_id +
                ", isAfterSales=" + isAfterSales +
                ", image='" + image + '\'' +
                ", goodHeadPath='" + goodHeadPath + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", salePrice=" + salePrice +
                ", specs='" + specs + '\'' +
                ", num=" + num +
                ", count=" + count +
                ", item_id=" + item_id +
                ", state=" + state +
                ", itemstatus_name='" + itemstatus_name + '\'' +
                ", is_write=" + is_write +
                ", img='" + img + '\'' +
                '}';
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getItemstatus_name() {
        return itemstatus_name;
    }

    public void setItemstatus_name(String itemstatus_name) {
        this.itemstatus_name = itemstatus_name;
    }

    public String getGoodHeadPath() {
        return goodHeadPath;
    }

    public void setGoodHeadPath(String goodHeadPath) {
        this.goodHeadPath = goodHeadPath;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getIsAfterSales() {
        return isAfterSales;
    }

    public void setIsAfterSales(int isAfterSales) {
        this.isAfterSales = isAfterSales;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getIs_write() {
        return is_write;
    }

    public void setIs_write(int is_write) {
        this.is_write = is_write;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getAddcommentflag() {
        return addcommentflag;
    }

    public void setAddcommentflag(int addcommentflag) {
        this.addcommentflag = addcommentflag;
    }
}
