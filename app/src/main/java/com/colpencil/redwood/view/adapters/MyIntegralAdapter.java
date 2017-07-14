package com.colpencil.redwood.view.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Integral;
import com.colpencil.redwood.view.base.CommonAdapter;
import com.colpencil.redwood.view.base.CommonViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;

import java.util.List;

/**
 * @author 曾 凤
 * @Description: 积分
 * @Email 20000263@qq.com
 * @date 2016/8/2
 */
public class MyIntegralAdapter extends CommonAdapter<Integral> {

    public MyIntegralAdapter(Context context, List<Integral> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void convert(CommonViewHolder holder, Integral integral, int position) {
        holder.setText(R.id.tv_integralType, integral.getReason());
        holder.setText(R.id.tv_integralDate, TimeUtil.timeFormat(integral.getTime(), "yy/MM/dd HH:mm"));
        holder.setText(R.id.tv_integral, integral.getMp());
        if (position % 2 == 1) {
            holder.getView(R.id.item_myIntegral_back).setBackgroundColor(mContext.getResources().getColor(R.color.main_background));
        } else {
            holder.getView(R.id.item_myIntegral_back).setBackgroundColor(mContext.getResources().getColor(R.color.main_LinearLayout_background));
        }
    }
}
