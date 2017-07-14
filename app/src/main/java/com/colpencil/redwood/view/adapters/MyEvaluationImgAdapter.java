package com.colpencil.redwood.view.adapters;

import android.content.Context;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/8/22 15 49
 */
public class MyEvaluationImgAdapter extends CommonAdapter<String> {

    public MyEvaluationImgAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, String item, int position) {
        helper.setImageByUrl(mContext, R.id.item_evaluation_img, item);
    }
}
