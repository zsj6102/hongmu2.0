package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ReplyToItem;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.utils.StringFormatUtil;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;

public class ReplytToAdapter extends CommonAdapter<ReplyToItem> {
    public  MyListener listener;
    public ReplytToAdapter(Context context, List<ReplyToItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, ReplyToItem item, final int position) {
        ImageLoaderUtils.loadImage(mContext,item.getStore_path(), (ImageView)helper.getView(R.id.iv_meHead));
        ImageLoaderUtils.loadImage(mContext,item.getMember_photo(), (ImageView)helper.getView(R.id.iv_level));
        helper.setText(R.id.tv_title1,item.getStore_name());
        if(item.getTo_member_name() == null){
            helper.setText(R.id.content,item.getRe_content());
        }else{
            String whole = "回复 "+item.getTo_member_name()+":"+item.getRe_content();
            StringFormatUtil spanStr = new StringFormatUtil(mContext,whole,"回复 "+item.getTo_member_name()+":",R.color.text_gray_color).fillColor();
            helper.setText(R.id.content,spanStr.getResult());
        }

         helper.setText(R.id.time,item.getCreate_time());
        helper.getView(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.commentClick(position);
            }
        });
    }
    public void setListener( MyListener listener){
        this.listener = listener;
    }
    public interface MyListener{
        void commentClick(int position);
    }
}

