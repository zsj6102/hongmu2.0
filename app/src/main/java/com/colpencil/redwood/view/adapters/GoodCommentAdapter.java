package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodComment;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.activity.mine.WebViewActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈宝
 * @Description:商品评论的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class GoodCommentAdapter extends CommonAdapter<GoodComment> {

    public GoodCommentAdapter(Context context, List<GoodComment> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final GoodComment item, int position) {
        holder.setImageByUrl(mContext, R.id.item_good_comment_head, item.getFace());
        holder.setText(R.id.item_good_comment_nickname, item.getNickname());
        holder.setText(R.id.item_good_comment_content, item.getContent());
        holder.setText(R.id.item_good_comment_spec, item.getSpecs());
        holder.setText(R.id.item_good_comment_time, TimeUtil.getTimeDiffDay(item.getDateline(), System.currentTimeMillis()));
        ImageLoaderUtils.loadImage(mContext, item.getMember_photo(), (ImageView) holder.getView(R.id.answer_level));
        if (ListUtils.listIsNullOrEmpty(item.getImglist())) {
            ((MosaicGridView) (holder.getView(R.id.item_good_recycler))).setAdapter(
                    new CircleImageAdapter(mContext, new ArrayList<String>(), R.layout.item_circle_image));
        } else {
            ((MosaicGridView) (holder.getView(R.id.item_good_recycler))).setAdapter(
                    new CircleImageAdapter(mContext, item.getImglist(), R.layout.item_circle_image));
        }
        ((MosaicGridView) (holder.getView(R.id.item_good_recycler))).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, GalleyActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("data", (ArrayList<String>) item.getImglist());
                mContext.startActivity(intent);
            }
        });
        holder.getView(R.id.ll_condition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("key", "info");
                mContext.startActivity(intent);
            }
        });
    }
}
