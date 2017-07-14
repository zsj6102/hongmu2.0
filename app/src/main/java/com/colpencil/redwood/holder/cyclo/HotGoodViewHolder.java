package com.colpencil.redwood.holder.cyclo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.list.Decomposers;

public class HotGoodViewHolder extends Decomposers<HomeGoodInfo> {

    private ImageView iv;
    private TextView title;

    public HotGoodViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hsearch_gridview, null);
        iv = (ImageView) view.findViewById(R.id.item_hsearchImg);
        title = (TextView) view.findViewById(R.id.item_hsearchName);
        return view;
    }

    @Override
    public void refreshView(HomeGoodInfo info, int position) {
        ImageLoaderUtils.loadImage(mContext, info.getImage(), iv);
        title.setText(info.getGoodsname());
    }
}
