package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

/**
 * 描述：出价Dialog
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/11 15 22
 */
public class OfferDialog extends Dialog {

    private Context context;
    private DialogClickListener listener;
    private TextView tv_tips;

    public OfferDialog(Context context) {
        super(context, R.style.selectorDialog);
        this.context = context;
        setCanceledOnTouchOutside(true);
        initalize();
    }

    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_offer, null);
        setContentView(view);
        initWindow();
        final EditText et_offerPrice = (EditText) findViewById(R.id.et_offerPrice);
        RelativeLayout sumbit_offerPrice = (RelativeLayout) findViewById(R.id.sumbit_offerPrice);
        RelativeLayout cancel_offerPrice = (RelativeLayout) findViewById(R.id.cancel_offerPrice);
        tv_tips = (TextView) findViewById(R.id.offerPrice_tips);
        /**
         * 提交出价
         */
        sumbit_offerPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = et_offerPrice.getText().toString();
                if (!TextUtils.isEmpty(price)) {
                    listener.confirmOnClick(price);
                } else {
                    ToastTools.showShort(context, "出价失败");
                }
            }
        });
        /**
         * 取消出价
         */
        cancel_offerPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用RxBus
                listener.cancelOnClick();
            }
        });

    }

    private void initWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();// 获取屏幕尺寸
        lp.gravity = Gravity.CENTER; // 中央居中
        lp.alpha = 1f;
        dialogWindow.setAttributes(lp);
    }

    public void setTips(float increasePrice) {
        tv_tips.setText("提示：加价幅度" + increasePrice + "元");
    }

    public void setListener(DialogClickListener listener) {
        this.listener = listener;
    }

    public interface DialogClickListener {
        void confirmOnClick(String price);

        void cancelOnClick();
    }
}
