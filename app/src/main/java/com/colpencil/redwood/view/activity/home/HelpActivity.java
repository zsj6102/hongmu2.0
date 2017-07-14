package com.colpencil.redwood.view.activity.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.present.home.HelpPresenter;
import com.colpencil.redwood.view.impl.IAnnounceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description:帮助与反馈
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_webview)
public class HelpActivity extends ColpencilActivity implements IAnnounceView {

    @Bind(R.id.common_webview)
    WebView webView;
    @Bind(R.id.common_progress)
    ProgressBar progress;
    @Bind(R.id.tv_main_title)
    TextView tv_title;
    private HelpPresenter presenter;

    @Override
    protected void initViews(View view) {
        progress.setVisibility(View.GONE);
        if ("help".equals(getIntent().getStringExtra("type"))) {
            tv_title.setText("帮助中心");
            presenter.loadHelp();
        } else {
            tv_title.setText("关于我们");
            presenter.loadAboutus();
        }
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);    //开放内存空间
        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!"aboutus".equals(getIntent().getStringExtra("type"))) {
                    view.loadUrl(url);
                }
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    @JavascriptInterface
    public void backClick() {
        finish();
    }


    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HelpPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess(AnnounceResult result) {
        webView.loadUrl(result.getUrl());
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
