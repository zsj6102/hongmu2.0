package com.colpencil.redwood.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ArticalItem;
import com.colpencil.redwood.bean.RatedInfo;
import com.colpencil.redwood.bean.RatedItem;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicGridView;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.utils.DeviceConfig.context;

public class RatedAdapter extends CommonAdapter<RatedItem> {

    private List<RatedItem> mDatas;
    private Context context;
    public RatedAdapter(Context context, List<RatedItem> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public void convert(CommonViewHolder helper, final RatedItem item, int position) {
        ImageLoaderUtils.loadImage(context, mDatas.get(position).getFace(), (ImageView)helper.getView(R.id.iv_meHead));
        helper.setText(R.id.tv_title1,item.getNickname());
        helper.setText(R.id.content,item.getContent());
        helper.setText(R.id.time,item.getDeteline());
        if (ListUtils.listIsNullOrEmpty(item.getImg())) {
            ((MosaicGridView) (helper.getView(R.id.item_good_recycler))).setAdapter(
                    new CircleImageAdapter(mContext, new ArrayList<String>(), R.layout.item_circle_image));
        } else {
            ((MosaicGridView) (helper.getView(R.id.item_good_recycler))).setAdapter(
                    new CircleImageAdapter(mContext, item.getImg(), R.layout.item_circle_image));
        }
        ((MosaicGridView) (helper.getView(R.id.item_good_recycler))).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, GalleyActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("data", (ArrayList<String>) item.getImg());
                mContext.startActivity(intent);
            }
        });

    }
}
