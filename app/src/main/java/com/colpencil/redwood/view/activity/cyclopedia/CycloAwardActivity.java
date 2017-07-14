package com.colpencil.redwood.view.activity.cyclopedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.CycloAwardPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.impl.ICycloAwardView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.TimeUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.RushBuyCountDownTimerView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author 陈宝
 * @Description:百科奖励
 * @Email DramaScript@outlook.com
 * @date 2016/8/5
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_cyclo_award)
public class CycloAwardActivity extends ColpencilActivity implements ICycloAwardView {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.common_webview)
    WebView webView;
    @Bind(R.id.common_progress)
    ProgressBar bar;
    @Bind(R.id.cyclo_day)
    TextView tv_day;
    @Bind(R.id.cyclo_time)
    RushBuyCountDownTimerView tv_time;
    @Bind(R.id.btn_post)
    Button btn_post;
    private CycloAwardPresenter presenter;
    private WebSettings settings;

    @Override
    protected void initViews(View view) {
        tv_title.setText("百科奖励");
        this.settings = webView.getSettings();
        presenter.loadUrl();
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CycloAwardPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadSuccess(EntityResult<String> result) {
        if (result.getCode() == 1) {
            load(webView, result.getUrl(), bar);
            if (result.getStatus() == 1) {
                if (TimeUtil.getDay(result.getSysTime(), result.getTime()) > 0) {
                    tv_day.setVisibility(View.VISIBLE);
                    tv_day.setText(TimeUtil.getDay(result.getSysTime(), result.getTime()) + "天");
                } else {
                    tv_day.setVisibility(View.GONE);
                }
                tv_time.setTime(TimeUtil.getHourse(result.getSysTime(), result.getTime()),
                        TimeUtil.getMinute(result.getSysTime(), result.getTime()),
                        TimeUtil.getSecond(result.getSysTime(), result.getTime()));
                tv_time.start();
                btn_post.setClickable(true);
                btn_post.setBackgroundColor(getResources().getColor(R.color.main_red));
                btn_post.setText("我要报名参加");
            } else {
                tv_time.setTime(0, 0, 0);
                btn_post.setBackgroundColor(getResources().getColor(R.color.text_gray_color));
                btn_post.setClickable(false);
                btn_post.setText("已结束");
            }
        }
    }

    public void load(final WebView webView, String loadUrl, final ProgressBar progressBar) {
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

    @OnClick(R.id.btn_post)
    void postClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            Intent intent = new Intent(this, PostCyclopediaActivity.class);
            intent.putExtra("game", 1);
            startActivity(intent);
        } else {
            final CommonDialog dialog = new CommonDialog(this, "你还没登录喔！", "去登录", "取消");
            dialog.setListener(new DialogOnClickListener() {
                @Override
                public void sureOnClick() {
                    Intent intent = new Intent(CycloAwardActivity.this, LoginActivity.class);
                    intent.putExtra(StringConfig.REQUEST_CODE, 100);
                    startActivity(intent);
                    dialog.dismiss();
                }

                @Override
                public void cancleOnClick() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }
}
