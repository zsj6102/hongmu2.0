package com.colpencil.redwood.view.activity.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.function.tools.WebViewTool;
import com.colpencil.redwood.present.home.AnnouncePresenter;
import com.colpencil.redwood.view.impl.IAnnounceView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description:公告列表
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_webview)
public class AnnounceActivity extends ColpencilActivity implements IAnnounceView {

    @Bind(R.id.common_webview)
    WebView webView;
    @Bind(R.id.common_progress)
    ProgressBar progress;
    @Bind(R.id.tv_main_title)
    TextView tv_title;

    private WebViewTool tool;
    private AnnouncePresenter presenter;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        tv_title.setText("公告");
        progress.setVisibility(View.VISIBLE);
        tool = new WebViewTool();
        presenter.loadUrl();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AnnouncePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess(AnnounceResult result) {
        if (result.getCode().equals("1")) {
            tool.load(webView, result.getUrl(), progress);
        }
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

}
