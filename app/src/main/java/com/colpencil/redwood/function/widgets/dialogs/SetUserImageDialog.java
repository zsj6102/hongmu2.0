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
 * @Description: 相册选择
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/7/8
 */
public class SetUserImageDialog extends Dialog {
    private TextView tv_selectpic_from_album;
    private TextView tv_selectpic_from_takephoto;
    private TextView dialogDis;
    private Context context;
    private OnPhotoDialogClickListener onPhotoDialogClickListener;

    public SetUserImageDialog(Context context) {
        super(context, R.style.selectorDialog);
        this.context = context;
        this.setCanceledOnTouchOutside(true);
        initalize();
    }

    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_setuserimage, null);
        setContentView(view);
        initWindow();
        tv_selectpic_from_album = (TextView) view.findViewById(R.id.tv_selectpic_from_album);
        tv_selectpic_from_takephoto = (TextView) view.findViewById(R.id.tv_selectpic_from_takephoto);
        dialogDis = (TextView) view.findViewById(R.id.photocancel);
        tv_selectpic_from_album.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stu
                if(onPhotoDialogClickListener==null){
                    dismiss();
                    return;
                }
                onPhotoDialogClickListener.onSelectPhoto();
            }
        });
        tv_selectpic_from_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onPhotoDialogClickListener==null){
                    dismiss();
                    return;
                }
                onPhotoDialogClickListener.onTakePhoto();
            }
        });
        dialogDis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(onPhotoDialogClickListener==null){
                    dismiss();
                    return;
                }
                onPhotoDialogClickListener.onCancelClick();
            }
        });
    }
    public void setOnPhotoDialogClickListener( OnPhotoDialogClickListener onPhotoDialogClickListener){
        this.onPhotoDialogClickListener=onPhotoDialogClickListener;
    }

    /**
     * 添加黑色半透明背景
     */
    private void initWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();// 获取屏幕尺寸
        lp.width = (int) (d.widthPixels * 1); // 宽度为屏幕80%
        lp.gravity = Gravity.BOTTOM; // 中央居中
        dialogWindow.setAttributes(lp);
    }

    /**
     * 添加按钮点击事件
     */
    public interface OnPhotoDialogClickListener {
        void onTakePhoto();
        void onSelectPhoto();
        void onCancelClick();
    }
}

