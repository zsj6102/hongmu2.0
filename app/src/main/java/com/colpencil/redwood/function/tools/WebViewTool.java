package com.colpencil.redwood.function.tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * @author 陈宝
 * @Description:webview的工具类
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class WebViewTool {

    private WebSettings settings;
    private Activity activity;

    public WebViewTool() {
    }

    public WebViewTool(Activity activity) {
        this.activity = activity;
    }

    public void load(WebView webView, String loadUrl, final ProgressBar progressBar) {
        this.settings = webView.getSettings();
        this.settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);    //开放内存空间
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
                if (progressBar != null) {
                    progressBar.setProgress(newProgress);
                }

                if (newProgress == 100) {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    } else {

                    }
                }

                super.onProgressChanged(view, newProgress);
            }

            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webView.loadUrl(loadUrl);
    }
    public void X5Load(com.tencent.smtt.sdk.WebView webView, String loadUrl, final ProgressBar progressBar){
        com.tencent.smtt.sdk.WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);    //开放内存空间
        webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            public void onPageStarted(com.tencent.smtt.sdk.WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(com.tencent.smtt.sdk.WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {
                if (progressBar != null) {
                    progressBar.setProgress(newProgress);
                }

                if (newProgress == 100) {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    } else {

                    }
                }

                super.onProgressChanged(view, newProgress);
            }

            public void onReceivedTitle(com.tencent.smtt.sdk.WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webView.loadUrl(loadUrl);
    }
}
