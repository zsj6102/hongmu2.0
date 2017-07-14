package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class SystemCategoryAdapter extends CommonAdapter<CategoryVo> {

    private Context context;

    public SystemCategoryAdapter(Context context, List<CategoryVo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(CommonViewHolder holder, CategoryVo item, int position) {
        TextView tv = holder.getView(R.id.item_category_name);
        tv.setText(item.getCat_name());
        ImageView iv = holder.getView(R.id.item_category_remove);
        iv.setVisibility(View.GONE);
    }

}
