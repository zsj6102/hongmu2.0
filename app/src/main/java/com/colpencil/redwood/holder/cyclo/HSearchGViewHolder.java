package com.colpencil.redwood.holder.cyclo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ChidrenCat;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.list.Decomposers;

public class HSearchGViewHolder extends Decomposers<ChidrenCat> {

    private ImageView imageView;
    private TextView title;

    public HSearchGViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hsearch_type, null);
        imageView = (ImageView) view.findViewById(R.id.item_hsearchImg);
        title = (TextView) view.findViewById(R.id.item_hsearchName);
        return view;
    }

    @Override
    public void refreshView(ChidrenCat chidrenCat, int position) {
        ImageLoaderUtils.loadImage(mContext, chidrenCat.getChild_image(), imageView);
        title.setText(chidrenCat.getChild_name());
    }
}
