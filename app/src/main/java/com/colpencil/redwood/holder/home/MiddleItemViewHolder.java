package com.colpencil.redwood.holder.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RecommendVo;
import com.colpencil.redwood.bean.RemoduleVo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.list.Decomposers;
import com.colpencil.redwood.holder.adapter.MiddleItemAdapter;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;

import java.util.ArrayList;
import java.util.List;

public class MiddleItemViewHolder extends Decomposers<RemoduleVo> {

    private ImageView imageView;
    private MosaicListView recyclerView;

    public MiddleItemViewHolder(int position, Context context) {
        super(position, context);
    }

    @Override
    public View initView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_middle, null);
        imageView = (ImageView) view.findViewById(R.id.home_page_middlehead);
        recyclerView = (MosaicListView) view.findViewById(R.id.item_middle_recycler);
        return view;
    }

    @Override
    public void refreshView(RemoduleVo vo, int position) {
        if (!ListUtils.listIsNullOrEmpty(vo.getRecHead())) {
            String url = vo.getRecHead().get(0).getUrl();
            ImageLoaderUtils.loadImage(mContext, url, imageView);
        }
        List<RecommendVo> list = new ArrayList<>();
        list.addAll(vo.getRecModuleList());
        recyclerView.setAdapter(new MiddleItemAdapter(vo.getRecModuleList(), mContext));
    }
}
