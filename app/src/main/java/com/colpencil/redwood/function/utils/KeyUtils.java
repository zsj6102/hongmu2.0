package com.colpencil.redwood.function.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class KeyUtils {

    /**
     * 隐藏键盘操作
     */
    public static void key(Activity context) {
        // 虚拟键盘隐藏 判断view是否为空
        if (context.getWindow().peekDecorView() != null) {
            //隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(context.getWindow().peekDecorView().getWindowToken(), 0);
        }
    }

}
