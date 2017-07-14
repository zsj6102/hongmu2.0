package com.colpencil.redwood.view.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.Sort;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;

import java.util.List;

/**
 * 描述：筛选GridView 适配器
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/13 09 55
 */
public class ScreenSearchGVAdapter extends CommonAdapter<Sort> {
    /**
     * 选中标识
     */
    private int mposition = -1;

    /**
     * 大组标识
     */
    private int mgroupPosition;

    public ScreenSearchGVAdapter(Context context, List<Sort> mDatas, int itemLayoutId, int groupPosition) {
        super(context, mDatas, itemLayoutId);
        this.mgroupPosition = groupPosition;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void convert(CommonViewHolder helper, final Sort sort, final int position) {
        TextView item_tv_childName = (TextView) helper.getConvertView().findViewById(R.id.item_tv_childName);
        item_tv_childName.setText(sort.getSort_name());
        //更改TextViewUi显示
        if (sort.isChooseState() == false) {
            item_tv_childName.setBackground(mContext.getResources().getDrawable(R.drawable.tv_backstyle_unpress));
        } else {
            item_tv_childName.setBackground(mContext.getResources().getDrawable(R.drawable.tv_backstyle_press));
        }
        item_tv_childName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行ui更新操作
                //判断之前是否有选中
//                if (sort.isChooseState() == false) {
//                    //用RxBus
//                    RxBusMsg rxBusMsg = new RxBusMsg();
//                    rxBusMsg.setType(0);
//                    rxBusMsg.setPositonGroupMsg(mgroupPosition);
//                    rxBusMsg.setPositonChildMsg(position);
//                    RxBus.get().post("rxBusMsg", rxBusMsg);
//                } else {
//                }
                RxBusMsg rxBusMsg = new RxBusMsg();
                rxBusMsg.setType(0);
                rxBusMsg.setPositonGroupMsg(mgroupPosition);
                rxBusMsg.setPositonChildMsg(position);
                RxBus.get().post("rxBusMsg", rxBusMsg);
            }
        });
    }
}

