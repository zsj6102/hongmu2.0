package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.LogisTicsBean;
import com.colpencil.redwood.view.adapters.LogisticAdapter;

import java.util.List;

public class LogisticDialog extends Dialog {

    private List<LogisTicsBean> mList;
    private Context context;
    private PostClickListener listener;
    private ListView listView;
    private TextView tv_title;
    private LogisticAdapter adapter;

    public LogisticDialog(Context context, List<LogisTicsBean> list) {
        super(context, R.style.PostDialogTheme);
        mList = list;
        this.context = context;
        initView();
        initWindow();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_post_type, null);
        view.findViewById(R.id.post_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.closeClick();
            }
        });
        tv_title = (TextView) view.findViewById(R.id.dialog_title);
        setCanceledOnTouchOutside(false);
        setContentView(view);
        initListView(view);
    }

    private void initWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();// 获取屏幕尺寸
        lp.width = (int) (d.widthPixels * 0.7); // 宽度为屏幕80%
        lp.height = (int) (d.heightPixels * 0.5);
        dialogWindow.setAttributes(lp);
    }

    private void initListView(View view) {
        listView = (ListView) view.findViewById(R.id.post_dialog_listview);
        adapter = new LogisticAdapter(context, mList, R.layout.post_dialog_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (LogisTicsBean info : mList) {
                    if (info == mList.get(position)) {
                        if (info.isChoose()) {
                            info.setChoose(false);
                            listener.itemUnClick();
                        } else {
                            info.setChoose(true);
                            listener.itemClick(position);
                        }
                    } else {
                        info.setChoose(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setListener(PostClickListener listener) {
        this.listener = listener;
    }

    public interface PostClickListener {
        void closeClick();

        void itemUnClick();

        void itemClick(int position);
    }
}
