package com.property.colpencil.colpencilandroidlibrary.Ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.property.colpencil.colpencilandroidlibrary.R;

import java.util.zip.Inflater;

public class MyProgressDialog extends Dialog {

    public MyProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static MyProgressDialog createDialog(Context context,String msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progressbar,null);
        TextView textView = (TextView) view.findViewById(R.id.tv_progressBar);
        textView.setText(msg);
        MyProgressDialog dialog = new MyProgressDialog(context, R.style.RedWoodProgress);
        dialog.setContentView(view);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return dialog;
    }


}
