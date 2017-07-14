package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;

/**
 * @author 陈宝
 * @Description:商家合作
 * @Email DramaScript@outlook.com
 * @date 2016/8/23
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_business)
public class BusinessActivity extends ColpencilActivity implements View.OnClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.ll_person)
    LinearLayout ll_person;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.ll_brand)
    LinearLayout ll_brand;
    @Bind(R.id.ll_famous)
    LinearLayout ll_famous;

    @Override
    protected void initViews(View view) {
        tv_title.setText("商家合作");
        ll_person.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ll_brand.setOnClickListener(this);
        ll_famous.setOnClickListener(this);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.ll_person) {
            Intent intent = new Intent(this, PersonApplyActivity.class);
            startActivity(intent);
        } else if (id == R.id.ll_brand) {
            Intent intent = new Intent(this, BrandApplyActivity.class);
            startActivity(intent);
        } else if (id == R.id.ll_famous) {
            Intent intent = new Intent(this, FamousApplyActivity.class);
            startActivity(intent);
        }
    }
}
