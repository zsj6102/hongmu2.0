package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodsItem;
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
public class ItemBrowsingGoodAdapter extends CommonAdapter<GoodsItem> {

    public ItemBrowsingGoodAdapter(Context context, List<GoodsItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final GoodsItem item, int position) {
        holder.setImageByUrl(mContext, R.id.item_ivBrowsingGood, item.getImage());
        holder.setText(R.id.item_titleBrowsingGood, item.getName());
        holder.setText(R.id.item_priceBrowsingGood, "¥" + item.getPrice());
        holder.getView(R.id.item_ivBrowsingGoodShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshMsg msg = new RefreshMsg();
                msg.setType(11);
                msg.setTitle(item.getName());
                msg.setContent(item.getName());
                msg.setId(Integer.valueOf(item.getGoods_id()));
                msg.setSort(3);
                RxBus.get().post("refresh", msg);
            }
        });
    }
}
