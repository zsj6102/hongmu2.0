package com.colpencil.redwood.holder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.CoverSpecialItem;

import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.RoundImageView;
import com.colpencil.redwood.view.activity.mine.SpecialActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

import static android.R.attr.id;

public class GoodsSpecialAdapter extends CommonAdapter<AllSpecialInfo> {

    public GoodsSpecialAdapter(Context context, List<AllSpecialInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, final AllSpecialInfo item, int position) {
        RoundImageView iv = helper.getView(R.id.iv_special);
        iv.setType(RoundImageView.TYPE_ROUND);
        iv.setRoundRadius(10);
        ImageLoaderUtils.loadImage(mContext, item.getSpe_picture(), iv);
        helper.setText(R.id.tv_name, item.getSpecial_name());
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SpecialActivity.class);
                intent.putExtra("special_id", item.getSpecial_id()+"");
                intent.putExtra("special_name", item.getSpecial_name());
                if(item.getCat_id()!=null){
                    intent.putExtra("cat_id",item.getCat_id());
                }

                mContext.startActivity(intent);
            }
        });
    }
}
