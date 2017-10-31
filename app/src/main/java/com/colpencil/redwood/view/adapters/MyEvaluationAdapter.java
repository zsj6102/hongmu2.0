package com.colpencil.redwood.view.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Comment;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.view.activity.commons.GalleyActivity;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.FormatUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.MosaicGridView;

import java.util.ArrayList;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者：我的评价
 * 邮箱：20000263@qq.com
 * 日期：2016/7/29 12 01
 */
public class MyEvaluationAdapter extends BGARecyclerViewAdapter<Comment> {

    public MyEvaluationAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_evaluation);
    }

    @Override
    public void fillData(BGAViewHolderHelper helper, int position, final Comment comment) {
        MosaicGridView gridView = (MosaicGridView) helper.getConvertView().findViewById(R.id.item_gridview_evaluation);
        helper.getView(R.id.viewBottom_evaluation).setVisibility(View.GONE);
        if (position == mDatas.size() - 1) {
            helper.getView(R.id.viewBottom_evaluation).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.comment_goodName, comment.getName());
        helper.setText(R.id.comment_goodPrice, FormatUtils.formatDouble(comment.getPayPrice()));
        helper.setText(R.id.comment_content, comment.getContent());
        helper.setText(R.id.comment_time, TimeUtil.timeFormat(comment.getDateline(), "yyyy/MM/dd HH:mm"));
        if (!ListUtils.listIsNullOrEmpty(comment.getImgList())) {
            gridView.setAdapter(new MyEvaluationImgAdapter(mContext, comment.getImgList(), R.layout.item_evaluation_img));
        } else {
            gridView.setAdapter(new MyEvaluationImgAdapter(mContext, new ArrayList<String>(), R.layout.item_evaluation_img));
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, GalleyActivity.class);
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("data", (ArrayList<String>) comment.getOri_imgList());
                mContext.startActivity(intent);
            }
        });
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comment.getCom_type() == 1) {
                    Intent intent = new Intent(mContext, GoodDetailActivity.class);
                    intent.putExtra("goodsId",comment.getGood_id());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    /**
     * 如果要实现Item 项的单击事件，该方法必须填写
     *
     * @param viewHolderHelper
     */
    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {

    }

}

