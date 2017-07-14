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
 * @author 陈 宝
 * @Description:发布百科dialog
 * @Email 1041121352@qq.com
 * @date 2016/9/19
 */
public class RuleDialog extends Dialog {

    private Context context;
    private String title, content;

    public RuleDialog(Context context, String title, String content) {
        super(context, R.style.PostDialogTheme);
        this.context = context;
        this.title = title;
        this.content = content;
        initView();
        initWindow();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.rule_dialog, null);
        view.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ((TextView) (view.findViewById(R.id.dialog_title))).setText(title);
        ((TextView) (view.findViewById(R.id.dialog_content))).setText(content);
        setContentView(view);
    }

    private void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = (int) (dm.widthPixels * 0.9);
        window.setAttributes(lp);
    }

}
