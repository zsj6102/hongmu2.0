package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * @author 陈宝
 * @Description:热门搜索的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/11
 */
public class HotSearchAdapter extends CommonAdapter<String> {

    public HotSearchAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, String item, int position) {
        TextView tv = holder.getView(R.id.item_search_history_tv);
        tv.setBackgroundDrawable(null);
        tv.setText(item);
    }
}
