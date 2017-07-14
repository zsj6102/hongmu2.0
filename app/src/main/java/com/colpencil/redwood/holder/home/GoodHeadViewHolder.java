package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.BannerVo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.list.Decomposers;

import java.util.List;

public class GoodHeadViewHolder extends Decomposers<List<BannerVo>> {

    private ImageView iv;

    public GoodHeadViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_page_good_header, null);
        iv = (ImageView) view.findViewById(R.id.home_page_middlehead);
        return view;
    }

    @Override
    public void refreshView(List<BannerVo> list, int position) {
        if (list != null) {
            ImageLoaderUtils.loadImage(mContext, list.get(0).getUrl(), iv);
        }
    }
}
