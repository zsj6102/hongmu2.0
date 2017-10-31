package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.colpencil.redwood.R;

/**
 * 描述：修改用户信息
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/26 10 57
 */
public class UpdateUserInforDialog extends Dialog {
    private TextView tv_updateTitle;
    private TextView tv_updateSure;
    private TextView tv_updateCancle;
    private EditText et_updateContent;
    private Context context;
    private OnUpdateInforDialogClickListener onUpdateInforDialogClickListener;
    private String mTitile;
    private String mContent;

    public UpdateUserInforDialog(Context context, String title, String content) {
        super(context, R.style.selectorDialog);
        this.context = context;
        this.mTitile = title;
        this.mContent = content;
        this.setCanceledOnTouchOutside(true);
        initalize();
    }

    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_updateuserinfor, null);
        setContentView(view);
        initWindow();
        et_updateContent = (EditText) view.findViewById(R.id.et_updateContent);
        tv_updateTitle = (TextView) view.findViewById(R.id.tv_updateTitle);
        tv_updateSure = (TextView) view.findViewById(R.id.tv_updateSure);
        tv_updateCancle = (TextView) view.findViewById(R.id.tv_updateCancle);
        tv_updateTitle.setText("修改" + mTitile);
        et_updateContent.setHint("请输入" + mTitile);
        et_updateContent.setText(mContent);
        tv_updateSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stu
                if (onUpdateInforDialogClickListener == null) {
                    dismiss();
                    return;
                }
                onUpdateInforDialogClickListener.onUpdateSure(et_updateContent.getText().toString());
            }
        });
        tv_updateCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void setOnUpdateInforDialogClickListener(OnUpdateInforDialogClickListener onUpdateInforDialogClickListener) {
        this.onUpdateInforDialogClickListener = onUpdateInforDialogClickListener;
    }

    /**
     * 添加黑色半透明背景
     */
    private void initWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();// 获取屏幕尺寸
        lp.width = (int) (d.widthPixels * 0.8); // 宽度为屏幕80%
        lp.gravity = Gravity.CENTER; // 中央居中
        dialogWindow.setAttributes(lp);
    }

    /**
     * 添加按钮点击事件
     */
    public interface OnUpdateInforDialogClickListener {
        void onUpdateSure(String content);
//        void cancelClick();
    }
}


