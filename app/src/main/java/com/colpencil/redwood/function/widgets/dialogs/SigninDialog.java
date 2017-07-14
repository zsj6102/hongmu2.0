package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.colpencil.redwood.R;

/**
 * @author 陈宝
 * @Description:签到结果的弹出框
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class SigninDialog extends Dialog {

    private Context context;
    private TextView tv_tips;
    private TextView dialog_sure;
    private TextView dialog_title;
    private ClickListener listener;

    public SigninDialog(Context context) {
        super(context, R.style.selectorDialog);
        this.context = context;
        initView();
        initWindow();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sign_in, null);
        setContentView(view);
        tv_tips = (TextView) view.findViewById(R.id.sign_in_tips);
        dialog_sure = (TextView) view.findViewById(R.id.dialog_sure);
        dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.sureClick();
            }
        });
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    private void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        lp.width = (int) (metrics.widthPixels * 0.75);
        window.setAttributes(lp);
    }

    public void setTips(String tips) {
        tv_tips.setText(tips);
    }

    public void setTitle(String title) {
        dialog_title.setText(title);
    }

    public interface ClickListener {
        void sureClick();
    }
}
