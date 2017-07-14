package com.colpencil.redwood.view.activity.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.function.tools.WebViewTool;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;

/**
 * @author 陈宝
 * @Description:webview的Activity
 * @Email DramaScript@outlook.com
 * @date 2016/8/12
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_webview)
public class MyWebViewActivity extends ColpencilActivity {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.common_webview)
    WebView webView;
    @Bind(R.id.common_progress)
    ProgressBar bar;
    @Bind(R.id.header)
    LinearLayout header;
    private String url;
    private String type;

    @Override
    protected void initViews(View view) {
        bar.setVisibility(View.VISIBLE);
        url = getIntent().getStringExtra("webviewurl");
        type = getIntent().getStringExtra("type");
        if (type.equals("banner")) {
            tv_title.setText("Banner详情");
            header.setVisibility(View.GONE);
        } else if (type.equals("server")) {
            tv_title.setText("客服中心");
            header.setVisibility(View.VISIBLE);
        }
        WebViewTool tool = new WebViewTool();
        tool.load(webView, url, bar);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
