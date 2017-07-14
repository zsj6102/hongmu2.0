package com.colpencil.redwood.function.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author 陈 宝
 * @Description:Universal Image Loader的封装类
 * @Email 1041121352@qq.com
 * @date 2016/11/1
 */
public class ImageLoaderUtils {

    public static void loadImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }
}
