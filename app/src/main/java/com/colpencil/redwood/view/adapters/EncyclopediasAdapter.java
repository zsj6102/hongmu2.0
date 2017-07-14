package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.EncyclopediasInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;


public class EncyclopediasAdapter extends CommonAdapter<EncyclopediasInfo> {
    private List<EncyclopediasInfo> mDatas;
    private Context context;
    public EncyclopediasAdapter(Context context, List<EncyclopediasInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.mDatas=mDatas;
        this.context=context;
    }

    @Override
    public void convert(CommonViewHolder helper, EncyclopediasInfo item, int position) {
        ImageLoaderUtils.loadImage(context, mDatas.get(position).getImgUrl(), (ImageView)helper.getView(R.id.encyclopedias_imageview));
    }
}
