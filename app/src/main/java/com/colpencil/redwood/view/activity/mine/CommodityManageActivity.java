package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
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
    private int goodid;
    private int storeid;
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
            url = UrlConfig.PHILHARMONIC_HOST + "orderCenter!orderCenter.do";//订单管理
        } else if (type.equals("4")) {
            url = UrlConfig.PHILHARMONIC_HOST + "appStoreRightsOrder!rights_order_list.do";//维权订单
        } else if (type.equals("7")) {
            url = UrlConfig.PHILHARMONIC_HOST + "appChat!chatList.do";//聊天列表
        } else if (type.equals("8")) {
            url = UrlConfig.PHILHARMONIC_HOST + "appevaluate!evaluate.do";//评价管理
        } else if (type.equals("9")) {
            url = UrlConfig.PHILHARMONIC_HOST + "appWallet!indexWallet.do";//我的钱包
        }else if(type.equals("10")){
            url = UrlConfig.PHILHARMONIC_HOST + "appChat!chatList.do";//我的聊天列表
        }else if(type.equals("11")){
            url = UrlConfig.PHILHARMONIC_HOST+"appChat!chatGoodsDetail.do";
            goodid = getIntent().getExtras().getInt("goodid");
            storeid = getIntent().getExtras().getInt("storeid");
        }
        webView.loadUrl(url);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

        @JavascriptInterface
        public int getGoodid(){
            return goodid;
        }
        @JavascriptInterface
        public int getStoreid(){
            return storeid;
        }
        @JavascriptInterface
        public int getSessionType() {
            return SharedPreferencesUtil.getInstance(App.getInstance()).getInt("session_type");
        }

        @JavascriptInterface
        public void back() {
            finish();
        }

        /**
         * id:商品id
         * type:商品的类型(supai,pinpai,zhuanchang)
         * @param id
         * @param type
         */
        @JavascriptInterface
        public void goToModify(int id, String type) {
            Intent intent = new Intent();
            if (type.equals("supai")) {
                intent.setClass(CommodityManageActivity.this, ModifySpeedActivity.class);
            } else if (type.equals("pinpai")) {
                intent.setClass(CommodityManageActivity.this, ModifyBrandActivity.class);
            } else if(type.equals("zhuanchang")) {
                intent.setClass(CommodityManageActivity.this, ModifyZcActivity.class);
            }
            intent.putExtra("goodid", id);
            startActivity(intent);
        }
        @JavascriptInterface
        public void tel(String phone){
            Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));//跳转到拨号界面，同时传递电话号码
            startActivity(dialIntent);
        }
        @JavascriptInterface
        public void toStoreDetail(int store_id,int store_type){
            Intent detailIntent = new Intent(CommodityManageActivity.this,StoreHomeActivity.class);
            detailIntent.putExtra("type",store_type);
            detailIntent.putExtra("store_id",store_id);
            startActivity(detailIntent);
        }
    }
}
