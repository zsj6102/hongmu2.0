package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.colpencil.redwood.R;

/**
 * @author 曾 凤
 * @Description: 定制界面中的弹窗
 * @Email 20000263@qq.com
 * @date 2016/7/8
 */
public class CustomIntroduceDialog extends Dialog {
    private Context context;
    public CustomIntroduceDialog(Context context) {
        super(context, R.style.selectorDialog);
        this.context = context;
        setCanceledOnTouchOutside(true);
        initalize();
    }

    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_customintroduce, null);
        setContentView(view);
        initWindow();
        TextView custom_content_first = (TextView) findViewById(R.id.custom_content_first);
        TextView custom_content_second = (TextView) findViewById(R.id.custom_content_second);
        TextView tv_realize_produce = (TextView) findViewById(R.id.tv_realize_produce);
        /**
         * 对产品进行了解操作
         */
        tv_realize_produce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //调用RxBus
            }
        });

    }
    private void initWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();// 获取屏幕尺寸
        lp.gravity = Gravity.TOP; // 中央居中
        lp.alpha=0.8f;
        dialogWindow.setAttributes(lp);
    }
}
