package com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP;

import java.util.HashMap;

public class RxBusLMsg {
    /**
     * 处理类型表示 0:标识处理expandListView  1:标识处理购物车中数量增减 2：标识处理多张相册选择问题
     * 3:销毁登录界面  4:更新个人中信息 5:修改密码成功  6:个人中心信息修改成功   7:进行订单退货
     * 8:进入立即支付 9：申请退款  10:查看物流  11：确认收货 12：确认取消退款 13申请售后  14进行评价
     * 15：取消售后 16：填写物流  17：联系客服   18:我的周拍-立即付款 19:我的周拍—去竞拍
     * 20:我的定制-立即付款  21:我的定制-新的定制  22:领取购物券  23：使用优惠券 24:退款，售后理由适配器的选择
     * 25：提交申请理由  26:成功清空收藏记录  27:刷新商品收藏界面  28：刷新百科收藏界面  29：刷新帖子收藏界面
     * 30:消息记录成功删除  31:禁止一键清除按钮的使用  32：一键清除按钮解锁   33:兑换优惠券成功
     * 34:地址列表有所变动   35:取消订单成功—通知刷新订单界面    36：取消售后操作成功—通知刷新界面
     * 37:进入首页   38删除浏览记录  39:在浏览记录里面分享  40：刷新浏览列表
     * 41：邮寄方式发生了更改  42:支付方式发生了变化  43:选择使用优惠券  44:选择收货地址
     * 45:支付宝支付成功  46:支付宝支付失败   47:微信支付成功   48：微信支付失败    49:微信支付取消
     * 50:刷新我的定制界面  51：提醒卖家发货  52:进入订单详情  53:退出登录   54:银联支付     55:银联支付失败
     * 56:跳转到百科     57:刷新购物车的数据   58:刷新标签
     * 60:删除百科浏览记录      61:删除帖子浏览记录     62:删除商品浏览记录     63:我的周拍跳转到周拍详情
     * 64:删除订单   65:我的周拍立即付款
     *70:商家修改地址
     * 71:商家修改退货地址
     * 80:个人名片夹操作后   81:商品名片夹操作后
     *
     * 90：速拍收藏之后
     *
     * 95:速拍获取申请状态判断是不是商家
     * 96:品牌判断是不是商家
     * 97:名师
     * 98:专场
     *100:添加商品详情
     */
    private int type = -1;

    private int position;//集合位置

    private int positonGroupMsg;

    private int positonChildMsg;

    private String msg;

    private String orderNum;//订单编号

    private String cpns_sn;//优惠券券号

    private int cpns_id;//优惠券id

    private int point;//兑换需要的积分

    private int foot_id;//浏览记录id

    private int return_order_id;//退款id
    /**
     * 定制标识
     */
    private int customType;

    private int product_id;

    private int goods_id;

    private int order_id;

    /**
     * 0：待审核 1：立即付款2:待付款3:新的定制4:待付首款5:待付尾款
     */
    private int stateName;
    /**
     * 订单项id
     */
    private int item_id;
    /**
     * 名称
     */
    private String name;

    private HashMap<String, String> map;

    private String sn;

    private int status;//订单状态

    private float scale;

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    public int getStateName() {
        return stateName;
    }

    public void setStateName(int stateName) {
        this.stateName = stateName;
    }

    public int getCustomType() {
        return customType;
    }

    public void setCustomType(int customType) {
        this.customType = customType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPositonGroupMsg() {
        return positonGroupMsg;
    }

    public void setPositonGroupMsg(int positonGroupMsg) {
        this.positonGroupMsg = positonGroupMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPositonChildMsg() {
        return positonChildMsg;
    }

    public void setPositonChildMsg(int positonChildMsg) {
        this.positonChildMsg = positonChildMsg;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCpns_sn() {
        return cpns_sn;
    }

    public void setCpns_sn(String cpns_sn) {
        this.cpns_sn = cpns_sn;
    }

    public int getCpns_id() {
        return cpns_id;
    }

    public void setCpns_id(int cpns_id) {
        this.cpns_id = cpns_id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getFoot_id() {
        return foot_id;
    }

    public void setFoot_id(int foot_id) {
        this.foot_id = foot_id;
    }

    public int getReturn_order_id() {
        return return_order_id;
    }

    public void setReturn_order_id(int return_order_id) {
        this.return_order_id = return_order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
