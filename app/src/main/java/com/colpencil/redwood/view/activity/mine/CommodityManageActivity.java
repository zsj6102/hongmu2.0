package com.colpencil.redwood.view.activity.mine;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.function.config.UrlConfig;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;


import butterknife.Bind;



/**
 * 郑少杰
 * 商品管理
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_webview)
public class CommodityManageActivity extends ColpencilActivity {
    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.common_webview)
    WebView webView;
    @Bind(R.id.common_progress)
    ProgressBar bar;
    @Bind(R.id.header)
    LinearLayout layouthead;
    private String type;
    private String url;

    @Override
    protected void initViews(View view) {
        type = getIntent().getStringExtra("type");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        layouthead.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        UserInfo info = new UserInfo();
        webView.addJavascriptInterface(info, "UserInfo");

        webView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                if (bar != null) {
                    bar.setProgress(newProgress);
                }

                if (newProgress == 100) {
                    if (bar != null) {
                        bar.setVisibility(View.GONE);
                    } else {

                    }
                }

                super.onProgressChanged(view, newProgress);
            }

            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });


        if (type.equals("2")) {//商品管理
//            url = "http://192.168.0.233:80/wwhm/appStoreGoods!index.do";
            url = UrlConfig.PHILHARMONIC_HOST+"appStoreGoods!index.do";
        } else if (type.equals("3")) {
            url = UrlConfig.PHILHARMONIC_HOST + "orderCenter!orderCenter.do";
        }
        webView.loadUrl(url);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    public class UserInfo {
        @JavascriptInterface
        public String getId() {
            return SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "";
        }

        @JavascriptInterface
        public String getToken() {
            return SharedPreferencesUtil.getInstance(App.getInstance()).getString("token");
        }

        @JavascriptInterface
        public int getStoreType() {
            return SharedPreferencesUtil.getInstance(App.getInstance()).getInt("store_type");
        }

        //        @JavascriptInterface
        //        public Integer getSessionType(){
        //            return
        //        }
        @JavascriptInterface
        public void back() {
            finish();
        }
    }
}
