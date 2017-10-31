package com.colpencil.redwood.view.fragments.mine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.SpecialIntroduceInfo;
import com.colpencil.redwood.bean.result.SpecialIntroduceResult;
import com.colpencil.redwood.present.mine.SpecialIntroducePresent;

import com.colpencil.redwood.view.impl.ISpecialIntroduceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;

import butterknife.Bind;

import static com.colpencil.redwood.R.id.webview;


@ActivityFragmentInject(
        contentViewId = R.layout.fragment_specialintroduce)

public class SpecialIntroduceFragment extends ColpencilFragment implements ISpecialIntroduceView {
    @Bind(webview)
    WebView webView;
    @Bind(R.id.common_progress)
    ProgressBar bar;
    private int id;
    private SpecialIntroducePresent specialIntroducePresent;


    public static SpecialIntroduceFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        SpecialIntroduceFragment fragment = new SpecialIntroduceFragment();
        bundle.putInt("special_id", id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected void initViews(View view) {
        id = getArguments().getInt("special_id");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        ZhuanChang zc = new ZhuanChang();

        webView.addJavascriptInterface(zc, "ZhuanChang");

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

    }

    @Override
    public ColpencilPresenter getPresenter() {
        specialIntroducePresent = new SpecialIntroducePresent();
        return specialIntroducePresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess(SpecialIntroduceResult specialIntroduceResult) {
        SpecialIntroduceInfo info = specialIntroduceResult.getData();
        webView.loadUrl(info.getUrl());
        hideLoading();
    }

    @Override
    public void loadData() {
        showLoading("加载中...");
        specialIntroducePresent.getSpecialIntroduce(id);
    }

    @Override
    public void loadFail(String message) {

        hideLoading();
        ToastTools.showShort(getActivity(), "加载错误");
    }

    public class ZhuanChang {
        @JavascriptInterface
        public int getId() {
            return  id;
        }
    }
}
