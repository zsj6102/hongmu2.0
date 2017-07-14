package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.bean.GoodNorm;
import com.colpencil.redwood.bean.Goodspec;
import com.colpencil.redwood.configs.StringConfig;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.List;

public class GoodTypeAdapter extends RecyclerView.Adapter<GoodTypeAdapter.MyViewHolder> {

    private Context context;
    private List<Goodspec> data;

    public GoodTypeAdapter(Context context, List<Goodspec> list) {
        this.context = context;
        this.data = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_good_type, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int mposition) {
        holder.type_name.setText(data.get(mposition).getSpec_name() + ":");
        GridLayoutManager manager = new GridLayoutManager(context, 4);
        holder.re_type.setLayoutManager(manager);
        final GoodNormAdapter adapter = new GoodNormAdapter(context, data.get(mposition).getValueList());
        holder.re_type.setAdapter(adapter);
        adapter.setListener(new GoodNormAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GoodNorm norm = data.get(mposition).getValueList().get(position);
                for (GoodNorm good : data.get(mposition).getValueList()) {
                    if (good == norm) {
                        GoodBusMsg msg = new GoodBusMsg();
                        msg.setType(StringConfig.CHOOSENORM);
                        msg.setSpecification(data.get(mposition).getSpec_name());
                        if (norm.isChoose()) {  //当前状态为选中，设置为未选中
                            norm.setChoose(false);
                            msg.setNorm("  ");
                        } else {
                            norm.setChoose(true);
                            msg.setNorm(norm.getSpec_value());
                        }
                        RxBus.get().post(StringConfig.GOODSBUS, msg);
                    } else {
                        good.setChoose(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void notifys(List<Goodspec> mdata) {
        data.clear();
        data.addAll(mdata);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type_name;
        RecyclerView re_type;

        public MyViewHolder(View itemView) {
            super(itemView);
            type_name = (TextView) itemView.findViewById(R.id.good_type_name);
            re_type = (RecyclerView) itemView.findViewById(R.id.re_type);
        }
    }
}
