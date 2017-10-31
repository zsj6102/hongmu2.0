package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;

import java.util.List;


public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<HomeGoodInfo> list;

    public HomeRecyclerAdapter(Context context, List<HomeGoodInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_good, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageLoaderUtils.loadImage(context, list.get(position).getImage(), holder.iv);
        holder.tv.setText(list.get(position).getGoodsname());
        holder.price.setText("￥" + list.get(position).getCostprice());
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodDetailActivity.class);
                intent.putExtra("goodsId", list.get(position).getGoodsid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv;
        TextView price;
        LinearLayout ll_content;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_good_img);
            tv = (TextView) itemView.findViewById(R.id.item_good_name);
            price = (TextView) itemView.findViewById(R.id.item_good_price);
            ll_content = (LinearLayout) itemView.findViewById(R.id.item_home_regood_ll);
        }
    }
}