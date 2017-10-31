package com.colpencil.redwood.holder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.RoundImageView;
import com.colpencil.redwood.holder.SpecialCategory;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ImgTool;

import java.util.ArrayList;
import java.util.List;

public class SeparatedListAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;  //
    private static final int TYPE_SEPARATOR = 1; //时间分割
    private List<SpecialCategory> mlist = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private comItemClickListener listener;

    public SeparatedListAdapter(Context context, List<SpecialCategory> list) {
        this.mContext = context;
        this.mlist = list;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        int count = 0;
        if (null != mlist) {
            for (SpecialCategory category : mlist) {
                count += category.getItemCount();
            }
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        if (null == mlist || position < 0 || position > getCount()) {
            return null;
        }
        int categoryFirstIndex = 0;
        for (SpecialCategory category : mlist) {
            int size = category.getItemCount();
            int categoryIndex = position - categoryFirstIndex;
            if (categoryIndex < size) {
                return category.getItem(categoryIndex);
            }
            categoryFirstIndex += size;
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setComListener(comItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {


        int type = getItemViewType(position);

        switch (type) {

            case TYPE_SEPARATOR:
                if (null == view) {
                    view = mInflater.inflate(R.layout.item_special_past_group, null);
                }
                TextView textView = (TextView) view.findViewById(R.id.tv_time);
                textView.setText((String) getItem(position));
                break;
            case TYPE_ITEM:

                ViewHolder holder;
                if (null == view) {
                    view = mInflater.inflate(R.layout.item_special_past_child, null);
                    holder = new ViewHolder();
                    holder.iv_special = (RoundImageView) view.findViewById(R.id.iv_special);
                    holder.iv_special.setType(RoundImageView.TYPE_ROUND);
                    holder.iv_special.setRoundRadius(10);
                    holder.tv = (TextView)view.findViewById(R.id.tv_name);

                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }
                holder.iv_special.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.click(((AllSpecialInfo) getItem(position)).getSpecial_id() + "", ((AllSpecialInfo) getItem(position)).getSpecial_name());
                    }
                });
                holder.tv.setText(((AllSpecialInfo) getItem(position)).getSpecial_name());
                ImageLoaderUtils.loadImage(mContext,((AllSpecialInfo) getItem(position)).getSpe_picture(),holder.iv_special);
//                ImgTool.getImgToolInstance(mContext).loadImgByString(((AllSpecialInfo) getItem(position)).getSpe_picture(), holder.iv_special);
                break;
        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (null == mlist || position < 0 || position > getCount()) {
            return TYPE_ITEM;
        }
        int categoryFirstIndex = 0;
        for (SpecialCategory category : mlist) {
            int size = category.getItemCount();
            int categoryIndex = position - categoryFirstIndex;
            if (categoryIndex == 0) {
                return TYPE_SEPARATOR;
            }
            categoryFirstIndex += size;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

      class ViewHolder {
          RoundImageView iv_special;
          TextView tv;
    }

    public interface comItemClickListener {
        void click(String id, String name);
    }
}
