package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.PostTypeInfo;
import com.colpencil.redwood.function.widgets.adapter.PostDialogAdapter;
import java.util.List;

public class SelectPostStyleDialog extends Dialog{
    private Context context;
    private List<PostTypeInfo> list;
    private PostClickListener listener;
    private PostDialogAdapter adapter;
    private ListView listView;

    public SelectPostStyleDialog(Context context, List<PostTypeInfo> list) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.list = list;
        setCanceledOnTouchOutside(false);
        initalize();
    }
    private void initalize(){
        View view = LayoutInflater.from(context).inflate(R.layout.action_sheet_dialog, null);
        view.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.closeClick();
            }
        });
        setContentView(view);
        initWindow();
        initListView(view);
    }
    private void initWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        DisplayMetrics d = context.getResources().getDisplayMetrics();// 获取屏幕尺寸
        lp.width = (int) (d.widthPixels * 0.8); // 宽度为屏幕80%
        lp.y = 20;
//        lp.gravity = Gravity.BOTTOM;
//        lp.alpha = 1f;
        dialogWindow.setAttributes(lp);
    }
    private void initListView(View view){
        listView = (ListView) view.findViewById(R.id.post_dialog_listview);
        adapter = new PostDialogAdapter(context, list, R.layout.post_dialog_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (PostTypeInfo info : list) {
                    if (info == list.get(position)) {
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
                Log.e("list", list.toString());
            }
        });
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
