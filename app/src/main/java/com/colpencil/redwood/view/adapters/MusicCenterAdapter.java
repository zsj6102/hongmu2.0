package com.colpencil.redwood.view.adapters;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Music;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者：我的评价
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 01
 */
public class MusicCenterAdapter extends BGARecyclerViewAdapter<Music> {

    private ItemClickListeners listeners;

    public MusicCenterAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_lv_music);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void fillData(BGAViewHolderHelper helper, final int position, final Music music) {
        ImageView iv_music = helper.getView(R.id.iv_music);
        helper.setText(R.id.tv_music_state, music.getPopularity());
        if (position == mDatas.size() - 1) {
            helper.getView(R.id.music_view).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.music_view).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.music_name, music.getTitle());
        helper.setText(R.id.tv_label, "标签：" + music.getTag());
        ImageLoaderUtils.loadImage(mContext, music.getImage(), iv_music);
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeners.itemClick(position);
            }
        });
    }

    /**
     * 如果要实现Item 项的单击事件，该方法必须填写
     *
     * @param viewHolderHelper
     */
    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {


    }

    public interface ItemClickListeners {
        void itemClick(int position);
    }

    public void setListeners(ItemClickListeners listeners) {
        this.listeners = listeners;
    }
}

