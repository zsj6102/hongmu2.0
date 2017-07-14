package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.PromotionVo;
import com.colpencil.redwood.view.adapters.PromotionAdapter;

import java.util.List;

/**
 * @author 陈 宝
 * @Description:促销详情dialog
 * @Email 1041121352@qq.com
 * @date 2016/9/19
 */
public class PromoDialog extends Dialog {

    private Context context;
    private TextView tv_title;
    private ListView listView;
    private List<PromotionVo> promots;

    public PromoDialog(Context context) {
        super(context, R.style.PostDialogTheme);
        this.context = context;
        setCanceledOnTouchOutside(true);
        init();
        initWindow();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_promotion, null);
        setContentView(view);
        tv_title = (TextView) view.findViewById(R.id.dialog_title);
        listView = (ListView) view.findViewById(R.id.listview);
        view.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.nullview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        lp.width = metrics.widthPixels * 1; // 宽度为屏幕80%
        window.setAttributes(lp);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setPromots(List<PromotionVo> promots) {
        this.promots = promots;
        listView.setAdapter(new PromotionAdapter(context, promots, R.layout.item_good_promotion));
    }
}
