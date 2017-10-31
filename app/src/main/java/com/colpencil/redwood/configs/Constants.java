package com.colpencil.redwood.configs;

import android.os.Environment;

/**
 * @author 陈 宝
 * @Description:常量
 * @Email DramaScript@outlook.com
 * @date 2016/6/30
 */
public class Constants {

    public static String sdCardPath = Environment.getExternalStorageDirectory()
            + "/RedWood/";

    public static String[] WeekShootType = {"红木", "紫檀", "花梨", "沉香", "檀香", "文房"};

    public static String[] OrderType = {"全部", "待付款", "已付款", "已完成", "已取消", "退款"};

    public static String[] AfterSalesCenterType = {"处理中", "已完成"};

    public static String[] MyWeekShoot = {"全部", "竞拍中", "竞拍成功", "竞拍失败"};

    public static String[] MyCustom = {"全部", "定制中", "定制完成", "已取消"};

    public static String[] MyCollection = {"收藏商品", "收藏百科", "收藏新闻", "收藏帖子"};

    public static String APP_ID = "wx8ff267ef571440e5";

    public static String DB_NAME = "redwood";

    public static String progressName = "正在加载中";

    public static final int IMAGE_ITEM_ADD = -1;

    public static final int THREE_ADD = -3;
    //请求登录
    public static final int REQUEST_LOGIN = 100;

    //收藏商品
    public static final int KEEP_GOOD = 1;

    //收藏百科
    public static final int KEEP_CYCLOPEDIA = 2;

    //收藏帖子
    public static final int KEEP_POSTS = 3;

    //收藏新闻
    public static final int KEEP_NEWS = 4;

}
