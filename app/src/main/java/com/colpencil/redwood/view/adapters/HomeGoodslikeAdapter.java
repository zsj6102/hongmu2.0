package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;


import java.util.List;


public class HomeGoodslikeAdapter extends RecyclerView.Adapter<HomeGoodslikeAdapter.MyViewHolder> {

    private Context context;
    private List<ItemStoreFans> list;
    private  CareClick listener;
    public HomeGoodslikeAdapter(Context context, List<ItemStoreFans> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_laytou_fans, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ImageLoaderUtils.loadImage(context, list.get(position).getFace(), holder.iv);
        holder.tv.setText(list.get(position).getStore_name());
        if(list.get(position).getIsfocus() == 0){
            holder.layout_care.setVisibility(View.GONE);
            holder.layout_uncare.setVisibility(View.VISIBLE);
        }else{
            holder.layout_care.setVisibility(View.VISIBLE);
            holder.layout_uncare.setVisibility(View.GONE);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.careClick(position);
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
        LinearLayout layout_care;
        LinearLayout layout_uncare;
        LinearLayout layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_head);
            tv = (TextView) itemView.findViewById(R.id.store_name);
            layout_care = (LinearLayout) itemView.findViewById(R.id.layout_care);
            layout_uncare = (LinearLayout)itemView.findViewById(R.id.layout_uncare);
            layout = (LinearLayout)itemView.findViewById(R.id.layout_care_uncare);
        }
    }
    public void setListener( CareClick click){
        this.listener = click;
    }
    public interface CareClick{
        void careClick(int position);
    }
}
