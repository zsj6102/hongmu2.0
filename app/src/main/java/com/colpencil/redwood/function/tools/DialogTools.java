package com.colpencil.redwood.function.tools;

import android.content.Context;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * @author 汪 亮
 * @Description: 对话框的工具
 * @Email DramaScript@outlook.com
 * @date 2016/8/16
 */
public class DialogTools {

    private static MaterialDialog materialDialog;

    public DialogTools() {

    }

    public static void showLoding(Context context, String title, String content) {
        materialDialog = new MaterialDialog(context)
                .setTitle(title)
                .setMessage(content);
//                .set(true, 0)
        materialDialog.show();
    }

    public static void dissmiss() {
        if (materialDialog != null) {
            materialDialog.dismiss();
        }
    }

}
