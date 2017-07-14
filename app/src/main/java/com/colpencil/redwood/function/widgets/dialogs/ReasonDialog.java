package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RefundReason;
import com.colpencil.redwood.view.adapters.ReasonAdapter;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 曾 凤
 * @Description: 售后理由 退款理由
 * @Email 20000263@qq.com
 * @date 2016/8/4
 */
public class ReasonDialog extends Dialog {

    private Context context;
    private List<RefundReason> mdatas = new ArrayList<>();
    private ItemClickListener listener;
    private ReasonAdapter adapter;

    public ReasonDialog(Context context, String mainTitleContent, List<RefundReason> datas) {
        super(context, R.style.PostDialogTheme);
        this.context = context;
        mdatas.addAll(datas);
        initView(mainTitleContent);
        initWindow();
    }

    private void initView(String mainTitleContent) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_reason, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        ((TextView) (view.findViewById(R.id.selectTile))).setText(mainTitleContent);
        BaseListView listView = (BaseListView) view.findViewById(R.id.reason_listView);
        adapter = new ReasonAdapter(context, mdatas, R.layout.item_reason);
        listView.setAdapter(adapter);
        view.findViewById(R.id.selectSure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (RefundReason info : mdatas) {
                    if (info == mdatas.get(position)) {
                        if (info.isChooseState()) {
                            info.setChooseState(false);
                            listener.itemUnClick();
                        } else {
                            info.setChooseState(true);
                            listener.itemClick(mdatas.get(position));
                        }
                    } else {
                        info.setChooseState(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();// 获取屏幕尺寸
        lp.width = (int) (d.widthPixels * 0.8); // 宽度为屏幕80%
        lp.height = (int) (d.heightPixels * 0.4);
        lp.gravity = Gravity.CENTER; // 中央居中
        dialogWindow.setAttributes(lp);
    }

//    /**
//     * 改变选择类型
//     *
//     * @param position
//     */
//    public void changeSelectState(int position) {
//        if (selectPosition != position) {
//            Log.e("返回值", "理由选择接收" + position);
//            if (selectPosition != -1) {
//                mdatas.get(selectPosition).setChooseState(false);
//            }
//            selectPosition = position;
//            mdatas.get(selectPosition).setChooseState(true);
//            mAdapter.notifyDataSetChanged();
//        }
//    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener {
        void itemClick(RefundReason reason);

        void itemUnClick();
    }

}
