package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Margin;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.Settled;
import com.colpencil.redwood.present.mine.MarginPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.OrderCenterToPay;
import com.colpencil.redwood.view.adapters.SettledTypeAdapter;
import com.colpencil.redwood.view.impl.IMarginView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.AdapterView.MosaicListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 入驻费
 */
@ActivityFragmentInject(contentViewId = R.layout.layout_settled)
public class SettledOrderActivity extends ColpencilActivity implements IMarginView {
    @Bind(R.id.pay_post)
    MosaicListView payPost;
    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    private int sec_id = -1;
    private List<Settled> list = new ArrayList<>();
    private SettledTypeAdapter adapter;
    private MarginPresenter presenter;

    @Override
    protected void initViews(View view) {
        tvMainTitle.setText("套餐选择");
        presenter.getSettled();
        adapter = new SettledTypeAdapter(SettledOrderActivity.this, list, R.layout.item_settled);
        adapter.setListener(new SettledTypeAdapter.SettledClick() {
            @Override
            public void imgClick(int position) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setChoose(true);
                        sec_id = list.get(i).getId();
                    } else {
                        list.get(i).setChoose(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        payPost.setAdapter(adapter);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MarginPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.sumbit_pay)
    void toPay() {
        if(sec_id == -1){
            ToastTools.showShort(this,"请选择套餐");
            return;
        }
        Intent intent = new Intent(this, OrderCenterToPay.class);
        intent.putExtra("from", "settled");
        intent.putExtra("sec_id", sec_id);
        startActivity(intent);
        finish();
    }

    @Override
    public void loadInfo(ResultInfo<Margin> info) {

    }

    @Override
    public void loadSettle(ResultInfo<List<Settled>> info) {
        if (info.getCode() == 0) {
            list.addAll(info.getData());
            adapter.notifyDataSetChanged();
        } else {
            ToastTools.showShort(this, info.getMessage());
        }
    }
}
