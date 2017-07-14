package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.tools.WebViewTool;
import com.colpencil.redwood.present.mine.WebViewPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.impl.IWebViewView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;

import butterknife.Bind;

/**
 * @author 陈宝
 * @Description:webview的通用activity
 * @Email DramaScript@outlook.com
 * @date 2016/7/27
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_webview)
public class WebViewActivity extends ColpencilActivity implements IWebViewView {

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.common_webview)
    WebView webView;

    @Bind(R.id.common_btn)
    Button button;
    private String type;
    private String url;

    private WebViewPresenter presenter;

    @Override
    protected void initViews(View view) {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("key")) && getIntent().getStringExtra("key").equals("OrderCenterFragmentPresenter")) {//查看物流详情
            tv_main_title.setText("查看物流");
            presenter.logisticsInfor(getIntent().getIntExtra("orderId", 0));
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("key")) && getIntent().getStringExtra("key").equals("PwdActivity")) {
            tv_main_title.setText("顶藏网服务协议");
            showLoading(Constants.progressName);
            presenter.getH5Url(1);
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("key")) && getIntent().getStringExtra("key").equals("aboutApp")) {
            tv_main_title.setText("关于我们");
            showLoading(Constants.progressName);
            presenter.getH5Url(2);
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("key")) && getIntent().getStringExtra("key").equals("rule")) {
            tv_main_title.setText("规则");
            showLoading(Constants.progressName);
            presenter.loadRule();
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("key")) && getIntent().getStringExtra("key").equals("service")) {
            tv_main_title.setText("客服中心");
            showLoading(Constants.progressName);
            presenter.loadService();
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("key")) && getIntent().getStringExtra("key").equals("info")) {
            tv_main_title.setText("帮助中心");
            showLoading(Constants.progressName);
            presenter.loadInfo();
        } else {
            initParams();
            button.setVisibility(View.GONE);
            WebViewTool tool = new WebViewTool();
            tool.load(webView, url, null);
        }
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
    }

    private void initParams() {
        type = getIntent().getStringExtra(StringConfig.INTENT_TYPE);
        url = getIntent().getStringExtra(StringConfig.WEBVIEWURL);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new WebViewPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void fail(String code, String msg) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(findViewById(android.R.id.content), msg);
        if (code.equals("3")) {//未登录
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void webUrl(String url) {
        hideLoading();
        WebViewTool tool = new WebViewTool();
        tool.load(webView, url, null);
    }

    @Override
    public void serviceResult(EntityResult<String> result) {
        hideLoading();
        WebViewTool tool = new WebViewTool();
        if (result.getCode() == 1) {
            url = "http://chat8.live800.com/live800/chatClient/chatbox.jsp?companyID=730700&jid=3736553973&enterurl=app&info=" + result.getInfoValue();
        } else {
            url = UrlConfig.SERVICE_URL;
        }
        tool.load(webView, url, null);
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
