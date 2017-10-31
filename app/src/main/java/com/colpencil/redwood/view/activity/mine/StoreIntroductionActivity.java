package com.colpencil.redwood.view.activity.mine;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ActivityFragmentInject(contentViewId = R.layout.store_intr_layout)
public class StoreIntroductionActivity extends ColpencilActivity {

    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.tv_intr)
    TextView tvIntr;
    private String detail;
    @Override
    protected void initViews(View view) {
        detail = getIntent().getStringExtra("detail");
//        tvIntr.setText("\u3000\u3000"+detail);
        tvIntr.setText("\u3000\u3000"+Html.fromHtml(detail));
        tvMainTitle.setText("商家介绍");
    }

    @OnClick(R.id.iv_back)
    void back(){
        finish();
    }
    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
