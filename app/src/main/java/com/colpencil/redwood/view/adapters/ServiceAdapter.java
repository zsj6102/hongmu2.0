package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colpencil.redwood.R;

import java.util.List;

/**
 * @author 陈宝
 * @Description:商品服务的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;

    public ServiceAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_good_service, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.good_service);
        }
    }

    public void notifys(List<String> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

}
