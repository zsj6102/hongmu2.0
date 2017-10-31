package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Settled;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;

import java.util.List;


public class SettledTypeAdapter extends CommonAdapter<Settled> {
    private SettledClick listener;

    public SettledTypeAdapter(Context context, List<Settled> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, final Settled item, final int position) {
        ImageView select_item_payment_other = (ImageView) holder.getConvertView().findViewById(R.id.select_item_payment_other);
        TextView tvTitle_item_payment_other = (TextView) holder.getConvertView().findViewById(R.id.tvTitle_item_payment_other);
        TextView tvPrice = (TextView) holder.getConvertView().findViewById(R.id.total_price);
        tvTitle_item_payment_other.setText(item.getYear_string());
        tvPrice.setText("合计" + "￥" + item.getCost());
        if (item.isChoose() == false) {
            select_item_payment_other.setImageResource(R.mipmap.select_no);
        } else {
            select_item_payment_other.setImageResource(R.mipmap.select_yes_red);
        }
        select_item_payment_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.imgClick(position);
            }
        });
    }

    public void setListener(SettledClick listener) {
        this.listener = listener;
    }

    public interface SettledClick {
        void imgClick(int position);
    }
}
