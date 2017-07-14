package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.colpencil.redwood.bean.MinePostInfo;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

import static com.colpencil.redwood.R.id.minepost_recycler;

public class MinePostAdapter extends CommonAdapter<MinePostInfo> {

    public MinePostAdapter(Context context, List<MinePostInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, MinePostInfo item, int position) {
        RecyclerView recycler=helper.getView(minepost_recycler);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(new PostItemAdapter(mContext, mDatas.get(position).getPostItemInfos()));
        if (mDatas.get(position).getPostItemInfos().size() > 3) {
            recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        } else {
            recycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        }
    }
}
