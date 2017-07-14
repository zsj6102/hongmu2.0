package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.colpencil.redwood.Massage;
import com.colpencil.redwood.R;
import com.colpencil.redwood.function.widgets.ExpandTextView;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;

import java.util.List;

/**
 * 作者：我的消息
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 01
 */
public class MyMessageAdapter extends CommonAdapter<Massage> {

    private DeleteListener listener;

    public MyMessageAdapter(Context context, List<Massage> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, Massage msg, final int position) {
        final ExpandTextView tv_content = holder.getView(R.id.expandTextView);
        final TextView tv_expand = holder.getView(R.id.item_message_expend);
        holder.setText(R.id.item_message_title, msg.getTitle());
        holder.setText(R.id.item_message_time, TimeUtil.timeFormat(msg.getSendTime(), "yy/MM/dd HH:mm"));
        tv_content.setText(msg.getContent());
        holder.setImageByUrl(mContext, R.id.item_message_img, msg.getImg());
        holder.getView(R.id.item_message_expend).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.onClickTextView();
                if (tv_content.ismCollapsed()) {
                    tv_expand.setText("展开");
                } else {
                    tv_expand.setText("收起");
                }
            }
        });
        holder.getView(R.id.item_message_delete).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.delete(position);
            }
        });
    }

    public void setListener(DeleteListener listener) {
        this.listener = listener;
    }

    public interface DeleteListener {
        void delete(int position);
    }
}

