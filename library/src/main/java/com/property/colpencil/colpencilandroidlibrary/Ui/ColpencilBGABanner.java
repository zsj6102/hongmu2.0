package com.property.colpencil.colpencilandroidlibrary.Ui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;
import com.property.colpencil.colpencilandroidlibrary.R;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * 作者：ZengFeng
 * <p>
 * 描述： BGABanner 设置
 */
public class ColpencilBGABanner {
//    /**
//     * 布局View对象
//     */
//    public View viewBanner;
//    /**
//     * 上下文
//     */
//    private Context mcontext;
//    /**
//     * 图片地址集合
//     */
//    private List<String> mimgUrls;
//    /**
//     * 样式类型
//     */
//    private int mbgatype;
//    /**
//     * BGABanner对象
//     */
//    private BGABanner mbgaBanner;
//    /**
//     * 轮播的ImageView 控件
//     */
//    private List<ImageView> imageViews;
//    /**
//     * 图片加载工具
//     */
//    private ImgTool imgTool;
//
//    /**
//     * @param context   上下文 图片url
//     * @param bgatype   显示样式
//     * @param bgatype   图片集合
//     * @param bgaBanner BGABanner对象
//     */
//    public ColpencilBGABanner(Context context, int bgatype, List<String> imgUrls, BGABanner bgaBanner) {
//        this.mcontext = context;
//        this.mbgatype = bgatype;
//        this.mimgUrls = imgUrls;
//        if (bgaBanner == null) {
//            viewBanner = View.inflate(mcontext, R.layout.view_bga_banner, null);
//            mbgaBanner = (BGABanner) viewBanner.findViewById(R.id.banner_pager);
//        } else {
//            mbgaBanner = bgaBanner;
//        }
//        setBgaBannerStyle();
//    }
//
//    /**
//     * 设置样式
//     */
//    public void setBgaBannerStyle() {
//        if (mbgatype == 2) {//banner_main_cube 立体转
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Cube);
//        } else if (mbgatype == 3) {//banner_main_accordion 翻书效果
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Accordion);
//        } else if (mbgatype == 4) {//banner_main_flip 旋转切换
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Flip);
//        } else if (mbgatype == 5) {//banner_main_rotate 斜着旋转
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Rotate);
//        } else if (mbgatype == 6) {//平推
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Alpha);
//        } else if (mbgatype == 7) {//闪动
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.ZoomFade);
//        } else if (mbgatype == 8) {//渐变出现
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Fade);
//        } else if (mbgatype == 9) {//缩小方式
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.ZoomCenter);
//        } else if (mbgatype == 10) {//推与翻书效果
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Zoom);
//        } else if (mbgatype == 11) {//抽屉
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Stack);
//        } else if (mbgatype == 12) {//不可描述
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.ZoomStack);
//        } else if (mbgatype == 13) {//
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Depth);
//        } else {//默认形式
//            mbgaBanner.setTransitionEffect(BGABanner.TransitionEffect.Default);
//        }
//        //设置动画
//        initData();
//    }
//
//    /**
//     * 设置轮播动画
//     */
//    private void initData() {
//        imgTool = ImgTool.getImgToolInstance(mcontext);
//        imageViews = getViews();
//        mbgaBanner.setViews(imageViews);
//        ImageView imageView;
//        for (int i = 0; i < imageViews.size(); i++) {
//            imageView = imageViews.get(i);
//            //下载图片
//            imgTool.loadImgByString(mimgUrls.get(i), imageView);
//            mbgaBanner.setTips(mimgUrls);
//        }
//    }
//
//    /**
//     * 设置轮播ImageView 控件
//     *
//     * @return
//     */
//    private List<ImageView> getViews() {
//        List<ImageView> views = new ArrayList<>();
//        for (int i = 0; i < mimgUrls.size(); i++) {
//            views.add((ImageView) View.inflate(mcontext, R.layout.view_image, null));
//        }
//        return views;
//    }

}
