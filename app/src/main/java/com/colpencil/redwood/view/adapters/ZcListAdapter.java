package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.view.View;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.widgets.RoundImageView;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import java.util.List;
public class ZcListAdapter extends CommonAdapter<AllSpecialInfo> {
    private comItemClickListener listener;

    public ZcListAdapter(Context context, List<AllSpecialInfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(CommonViewHolder helper, final AllSpecialInfo item, int position) {
        RoundImageView iv = helper.getView(R.id.iv_special);
        iv.setType(RoundImageView.TYPE_ROUND);
        iv.setRoundRadius(10);
        helper.setText(R.id.tv_name, item.getSpecial_name());
        ImageLoaderUtils.loadImage(mContext, item.getSpe_picture(), iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(item.getSpecial_id() + "", item.getSpecial_name(),item.getCat_id());
            }
        });
    }

    public interface comItemClickListener {
        void click(String id, String name,Integer cat_id);
    }

    public void setComListener(comItemClickListener listener) {
        this.listener = listener;
    }
}
