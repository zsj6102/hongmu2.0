package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class CategoryAdapter extends CommonAdapter<CategoryVo> {

    public CategoryAdapter(Context context, List<CategoryVo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, CategoryVo item, int position) {
        holder.setText(R.id.item_category_name, item.getCat_name());
        holder.getView(R.id.item_category_remove).setVisibility(View.VISIBLE);
    }
}
