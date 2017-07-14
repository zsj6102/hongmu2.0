package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.MyCyclopediaInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.list.ListViewHolder;

import java.util.List;

/**
 * @author 陈宝
 * @Description:我的百科
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class ItemMyCycloAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<MyCyclopediaInfo> mList;
    private String mType;

    public ItemMyCycloAdapter(Context mContext, List<MyCyclopediaInfo> mList, String mType) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.mList = mList;
        this.mType = mType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(mInflater.inflate(R.layout.item_my_cyclopedia, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListViewHolder viewHolder = (ListViewHolder) holder;
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.content.setText(mList.get(position).getPage_description());
        ImageLoaderUtils.loadImage(mContext, mList.get(position).getImage(), viewHolder.ivIcon);
        if (mType.equals("2")) {
            viewHolder.cancle.setVisibility(View.VISIBLE);
        } else {
            viewHolder.cancle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
