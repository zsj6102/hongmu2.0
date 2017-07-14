package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CyclopediaItem;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * @author 曾 凤
 * @Description: 商品
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class CyclopediaFragmentAdapter extends CommonAdapter<CyclopediaItem> {

    public CyclopediaFragmentAdapter(Context context, List<CyclopediaItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, CyclopediaItem cyclopediaItem, int position) {
        holder.setImageByUrl(mContext, R.id.iv_cyclopediaCollection, cyclopediaItem.getImage());
        holder.setText(R.id.title_cyclopediaCollection, cyclopediaItem.getTitle());
        holder.setText(R.id.content_cyclopediaCollection, cyclopediaItem.getPage_description());
        holder.setText(R.id.comments_cyclopediaCollection, cyclopediaItem.getComment_count() + "评论");
    }
}
