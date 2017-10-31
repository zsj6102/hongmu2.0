package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.colpencil.redwood.R;

import static com.umeng.socialize.utils.DeviceConfig.context;

public class PhotoDialog extends Dialog {

    private Activity mActivity;
    private OnPhotoDialogClick onPhotoDialogClickListener;
    public PhotoDialog( Activity activity){
        super(activity, R.style.transparentFrameWindowStyle);
        this.mActivity = activity;
        this.setCanceledOnTouchOutside(true);
        initalize();
    }
    private void initalize(){
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        setContentView(view);
        initWindow();
        Button btn_select = (Button) view.findViewById(R.id.btn_select);
        Button btn_carmer = (Button) view.findViewById(R.id.btn_carmer);
        Button btn_diss = (Button) view.findViewById(R.id.btn_diss);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onPhotoDialogClickListener==null){
                    dismiss();
                    return;
                }
                onPhotoDialogClickListener.onSelect();
            }
        });
        btn_carmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onPhotoDialogClickListener==null){
                    dismiss();
                    return;
                }
                onPhotoDialogClickListener.onCamera();
            }
        });
        btn_diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onPhotoDialogClickListener==null){
                    dismiss();
                    return;
                }
                onPhotoDialogClickListener.onCancel();
            }
        });

    }
    private void initWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setWindowAnimations(R.style.main_menu_animstyle);
        lp.x = 0;
        lp.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.onWindowAttributesChanged(lp);
    }
    public void setOnPhotoDialogClickListener( OnPhotoDialogClick onPhotoDialogClickListener){
        this.onPhotoDialogClickListener=onPhotoDialogClickListener;
    }
    public interface OnPhotoDialogClick {
        void onCamera();
        void onSelect();
        void onCancel();
    }
}
