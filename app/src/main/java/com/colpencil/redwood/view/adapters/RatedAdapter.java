package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ArticalItem;
import com.colpencil.redwood.bean.RatedInfo;
import com.colpencil.redwood.bean.RatedItem;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

import static com.umeng.socialize.utils.DeviceConfig.context;

public class RatedAdapter extends CommonAdapter<RatedItem> {

    private List<RatedItem> mDatas;
    private Context context;
    public RatedAdapter(Context context, List<RatedItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public void convert(CommonViewHolder helper, RatedItem item, int position) {
        ImageLoaderUtils.loadImage(context, mDatas.get(position).getFace(), (ImageView)helper.getView(R.id.iv_meHead));
        helper.setText(R.id.tv_title1,item.getNickname());
        helper.setText(R.id.tv_title2,item.getStore_name());
        helper.setText(R.id.content,item.getContent());
        helper.setText(R.id.time,item.getDeteline());
        helper.setText(R.id.like_num,item.getLike_num()+"");
        helper.setText(R.id.comment_count,item.getDiscuss_num()+"");
    }
}
