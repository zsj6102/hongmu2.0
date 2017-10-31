package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.PostItem;
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
public class ItemBrowsingPostAdapter extends CommonAdapter<PostItem> {

    public ItemBrowsingPostAdapter(Context context, List<PostItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final PostItem postItem, final int position) {
        holder.setImageByUrl(mContext, R.id.item_ivBrowsingCyc, postItem.getOte_img());
        holder.setText(R.id.item_titleBrowsingCyc, postItem.getOte_title());
        holder.setText(R.id.item_contentBrowsingCyc, postItem.getOte_content());
        holder.setText(R.id.item_commentBrowsingCyc, postItem.getCommends_count() + "评论");
        holder.getView(R.id.item_ivBrowsingCycShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshMsg msg = new RefreshMsg();
                msg.setType(10);//帖子
                msg.setTitle(postItem.getOte_title());
                msg.setContent(postItem.getOte_content());
                msg.setId(postItem.getOte_id());
                msg.setSort(1);
                RxBus.get().post("refresh", msg);
            }
        });
    }
}
