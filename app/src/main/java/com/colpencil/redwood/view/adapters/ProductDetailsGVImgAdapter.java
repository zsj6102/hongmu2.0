package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 商品详情图片适配器
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/11 09 18
 */
public class ProductDetailsGVImgAdapter extends CommonAdapter<String> {

    private List<String> imgs;

    public ProductDetailsGVImgAdapter(Context context, List<String> mDatas, int itemLayoutId, List<String> imgs) {
        super(context, mDatas, itemLayoutId);
        this.imgs = imgs;
    }

    @Override
    public void convert(CommonViewHolder helper, String item, final int position) {
        helper.setImageByUrl(mContext, R.id.product_img, item);
        if (!ListUtils.listIsNullOrEmpty(mDatas)) {
            if (mDatas.size() > 9) {
                if (position == 8) {
                    helper.getView(R.id.product_tv).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.product_tv).setVisibility(View.GONE);
                }
            } else {
                helper.getView(R.id.product_tv).setVisibility(View.GONE);
            }
        }
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GalleyActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("data", (ArrayList<String>) imgs);
                mContext.startActivity(intent);
            }
        });
    }

}
