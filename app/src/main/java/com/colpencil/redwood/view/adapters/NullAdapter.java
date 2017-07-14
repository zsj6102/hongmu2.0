package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * @author 陈宝
 * @Description:这个是空的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/11
 */
public class NullAdapter extends CommonAdapter<String> {

    public NullAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, String item, int position) {

    }
}
