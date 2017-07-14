package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CategoryItem;

import java.util.List;

/**
 * @author 陈宝
 * @Description:百科的第一个ListView的适配器
 * @Email DramaScript@outlook.com
 * @date 2016/7/11
 */
public class CyclopediaFirstAdapter extends RecyclerView.Adapter<CyclopediaFirstAdapter.CyclopediaFirstViewHolder> {

    private Context context;
    private List<CategoryItem> list;
    private ItemClickListener listener;

    public CyclopediaFirstAdapter(Context context, List<CategoryItem> list, ItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public CyclopediaFirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_first_cyclopedia, null);
        CyclopediaFirstViewHolder holder = new CyclopediaFirstViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CyclopediaFirstViewHolder holder, final int position) {
        holder.tv_tag.setText(list.get(position).getCat_name());
        holder.tv_tag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(position);
            }
        });
        if (list.get(position).isChoose()) {
            holder.tv_tag.setTextColor(context.getResources().getColor(R.color.main_red));
        } else {
            holder.tv_tag.setTextColor(context.getResources().getColor(R.color.text_color_first));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CyclopediaFirstViewHolder extends RecyclerView.ViewHolder {

        TextView tv_tag;

        public CyclopediaFirstViewHolder(View itemView) {
            super(itemView);
            tv_tag = (TextView) itemView.findViewById(R.id.item_first_cyclopedia_tag);
        }
    }

    public interface ItemClickListener {
        void itemClick(int position);
    }
}
