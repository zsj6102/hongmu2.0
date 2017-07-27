package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
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


@ActivityFragmentInject(contentViewId = R.layout.activity_publish_list)
public class PublishListActivity extends ColpencilActivity {
    @Bind(R.id.iv_brand_dashi)
    ImageView ivBrandDashi;
    @Bind(R.id.tv_brand_dashi)
    TextView tvBrandDashi;
    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    private String type;

    @Override
    protected void initViews(View view) {
        type = getIntent().getStringExtra("type");
        tvMainTitle.setText("发布商品");
        if (type != null) {
            if (type.equals("2")) {
                ivBrandDashi.setImageResource(R.mipmap.pub_list_brand);
                tvBrandDashi.setText("品牌商区");
            } else if (type.equals("3")) {
                ivBrandDashi.setImageResource(R.mipmap.pub_list_mingshi);
                tvBrandDashi.setText("名师名匠");
            }
        }
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.layout_pinpai_mingshi)
    void brandClick() {
        Intent intent = new Intent(this, PublishFamousActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);

    }

    @OnClick(R.id.layout_supai)
    void supaiClick() {

        Intent intent = new Intent(this, PublishStoreActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);

    }

    @OnClick(R.id.layout_zhuanchang)
    void zhuanClick() {
        Intent intent = new Intent(this, PublishZcActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);

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
