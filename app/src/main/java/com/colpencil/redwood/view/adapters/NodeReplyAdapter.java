package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.NodeReplyItem;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


public class NodeReplyAdapter extends CommonAdapter<NodeReplyItem> {
    public MyListener listener;
    public NodeReplyAdapter(Context context, List<NodeReplyItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, NodeReplyItem item, final int position) {
        ImageLoaderUtils.loadImage(mContext,item.getRe_face(), (ImageView)helper.getView(R.id.iv_meHead));
        ImageLoaderUtils.loadImage(mContext,item.getRe_member_photo(), (ImageView)helper.getView(R.id.iv_level));
        helper.setText(R.id.tv_title1,item.getRe_store_name());
        helper.setText(R.id.like_num,item.getRe_like_count()+"");
        helper.setText(R.id.comment_count,item.getRe_con_count()+"");
        helper.setText(R.id.content,item.getRe_content());
        helper.setText(R.id.time,item.getRe_create_time());
        helper.getView(R.id.comment_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.commentClick(position);
            }
        });
        if (item.getIsfocus() == 1) {
            helper.setImageById(R.id.iv_like, R.mipmap.iv_like_icon);
        } else {
            helper.setImageById(R.id.iv_like, R.mipmap.iv_unlike_icon);
        }
        helper.getView(R.id.iv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.addLike(position);
            }
        });
    }
    public void setListener(MyListener listener){
        this.listener = listener;
    }
   public interface MyListener{
        void commentClick(int position);

        void addLike(int position);
    }
}
