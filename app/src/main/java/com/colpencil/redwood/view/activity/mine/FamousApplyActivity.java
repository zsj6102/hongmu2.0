package com.colpencil.redwood.view.activity.mine;

import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.base.PhotoBaseActivity;
import com.jph.takephoto.model.TResult;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_famous_apply
)
public class FamousApplyActivity extends PhotoBaseActivity implements OnClickListener {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.iv_positive)
    ImageView iv_positive;
    @Bind(R.id.iv_negative)
    ImageView iv_negative;
    @Bind(R.id.iv_certificate)
    ImageView iv_certificate;

    private int type = 0;

    @Override
    protected void initViews(View view) {
        tv_title.setText("名师名匠申请");
        iv_back.setOnClickListener(this);
        iv_positive.setOnClickListener(this);
        iv_negative.setOnClickListener(this);
        iv_certificate.setOnClickListener(this);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (type == 0) {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_positive);
        } else if (type == 1) {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_negative);
        } else {
            Glide.with(this).load(result.getImages().get(0).getCompressPath()).into(iv_certificate);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.iv_positive) {
            type = 0;
            openSelect(false, 1);
        } else if (id == R.id.iv_negative) {
            type = 1;
            openSelect(false, 1);
        } else if (id == R.id.iv_certificate) {
            type = 2;
            openSelect(false, 1);
        }
    }
}
