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
import com.colpencil.redwood.view.impl.IMarginView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
@ActivityFragmentInject(contentViewId = R.layout.margin_layout)
/**
 * 保证金
 */
public class MarginOrderActivity extends ColpencilActivity implements IMarginView {


    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.tv_storename)
    TextView tvStoreName;
    @Bind(R.id.tv_margin)
    TextView tvMargin;
    @Bind(R.id.shoppingCart_buyPrice)
    TextView shoppingCartBuyPrice;

    private MarginPresenter presenter;

    @Override
    protected void initViews(View view) {
        tvMainTitle.setText("订单确认");
        presenter.getMargin();
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
    void pay() {
        Intent intent = new Intent(this, OrderCenterToPay.class);
        intent.putExtra("from", "margin");
        startActivity(intent);
        finish();
    }

    @Override
    public void loadInfo(ResultInfo<Margin> info) {
        if (info.getCode() == 0) {
            tvStoreName.setText(info.getData().getStore_name());
            tvMargin.setText("￥" + info.getData().getMargin());
            shoppingCartBuyPrice.setText("￥" + info.getData().getMargin());
        } else {
            ToastTools.showShort(this, info.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void loadSettle(ResultInfo<List<Settled>> info) {

    }
}
