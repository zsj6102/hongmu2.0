package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CyclopediaItem;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.List;

/**
 * @author 曾 凤
 * @Description: 商品
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class ItemBrowsingCyclopediaAdapter extends CommonAdapter<CyclopediaItem> {

    public ItemBrowsingCyclopediaAdapter(Context context, List<CyclopediaItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final CyclopediaItem item, int position) {
        holder.setImageByUrl(mContext, R.id.item_ivBrowsingCyc, item.getImage());
        holder.setText(R.id.item_titleBrowsingCyc, item.getTitle());
        holder.setText(R.id.item_contentBrowsingCyc, item.getPage_description());
        holder.setText(R.id.item_commentBrowsingCyc, item.getCommends_count() + "评论");
        holder.getView(R.id.item_ivBrowsingCycShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshMsg msg = new RefreshMsg();
                msg.setType(11);
                msg.setTitle(item.getTitle());
                msg.setContent(item.getPage_description());
                msg.setId(Integer.valueOf(item.getArticle_id()));
                msg.setImage(item.getImage());
                msg.setSort(2);
                RxBus.get().post("refresh", msg);
            }
        });
    }

}
