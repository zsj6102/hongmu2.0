package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import android.content.Context;

/**
 * @author 作者：LigthWang
 * @date 创建时间：2015年8月29日 下午5:07:05
 * @version 1.0
 * @parameter 内容描述：dp与px之间的转换。
 * @since
 * @return
 */
public class DpAndPx {

	/** 
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}
