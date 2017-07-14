package com.colpencil.redwood.view.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CodeBean;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.function.tools.ImageLoaderUtils;
import com.colpencil.redwood.present.home.CodePresenter;
import com.colpencil.redwood.view.impl.ICodeView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈 宝
 * @Description:首页二维码
 * @Email 1041121352@qq.com
 * @date 2016/10/20
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_code
)
public class CodeActivity extends ColpencilActivity implements ICodeView {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.android_iv)
    ImageView androidImageView;
    @Bind(R.id.ios_iv)
    ImageView iosImageView;
    @Bind(R.id.wechat_iv)
    ImageView wechatImageView;

    private CodePresenter presenter;

    @Override
    protected void initViews(View view) {
        tv_title.setText("二维码");
        presenter.loadUrl();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CodePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @Override
    public void codeResult(EntityResult<CodeBean> result) {
        if (result.getCode() == 1) {
            CodeBean bean = result.getImgs();
            ImageLoaderUtils.loadImage(this, bean.getAnroidImg(), androidImageView);
            ImageLoaderUtils.loadImage(this, bean.getIosImg(), iosImageView);
            ImageLoaderUtils.loadImage(this, bean.getWeixinImg(), wechatImageView);
        }
    }
}
