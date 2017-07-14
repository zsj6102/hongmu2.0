package com.property.colpencil.colpencilandroidlibrary.Ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.property.colpencil.colpencilandroidlibrary.R;

public class MyProgressDialog extends Dialog {

    public MyProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static MyProgressDialog createDialog(Context context) {
        MyProgressDialog dialog = new MyProgressDialog(context, R.style.RedWoodProgress);
        dialog.setContentView(R.layout.dialog_progressbar);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return dialog;
    }


}
