package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodNorm;

import java.util.List;

/**
 * @author 陈宝
 * @Description:商品规格的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class GoodNormAdapter extends RecyclerView.Adapter<GoodNormAdapter.FilterViewHolder>
        implements OnClickListener {

    private Context context;
    private List<GoodNorm> list;
    private OnRecyclerViewItemClickListener listener;

    public GoodNormAdapter(Context context, List<GoodNorm> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_good_size, null);
        FilterViewHolder holder = new FilterViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        holder.tv.setText(list.get(position).getSpec_value());
        holder.itemView.setTag(position);
        if (list.get(position).isChoose()) {
            holder.rl.setBackgroundResource(R.drawable.category_select_shape);
        } else {
            holder.rl.setBackgroundResource(R.drawable.category_unselect_shape);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClick((Integer) v.getTag());
        }
    }

    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        RelativeLayout rl;

        public FilterViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_good_filter_tv);
            rl = (RelativeLayout) itemView.findViewById(R.id.rl_size);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

}
