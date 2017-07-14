package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.PostItem;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

/**
 * @author 曾 凤
 * @Description: 商品
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class PostFragmentAdapter extends CommonAdapter<PostItem> {

    public PostFragmentAdapter(Context context, List<PostItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, PostItem postItem, int position) {
        ImageView iv_cyclopediaCollection = (ImageView) holder.getConvertView().findViewById(R.id.iv_cyclopediaCollection);
        ImageLoaderUtils.loadImage(mContext, postItem.getOte_img(), iv_cyclopediaCollection);
        holder.setText(R.id.title_cyclopediaCollection, postItem.getOte_title());
        holder.setText(R.id.content_cyclopediaCollection, postItem.getOte_content());
        holder.setText(R.id.comments_cyclopediaCollection, postItem.getCommends_count() + "评论");

    }
}
