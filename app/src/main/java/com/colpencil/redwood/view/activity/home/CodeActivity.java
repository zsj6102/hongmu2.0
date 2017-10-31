package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CodeBean;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.view.activity.HomeActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.RxBusLMsg;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.RxLBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import solid.ren.skinlibrary.base.SkinBaseActivity;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * @author 陈 宝
 * @Description:首页二维码
 * @Email 1041121352@qq.com
 * @date 2016/10/20
 * //
 */
public class CodeActivity extends SkinBaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private TextView  tvTitle;
    private ImageView nightView;
    private ImageView smallView;
    private ImageView middleView;
    private ImageView largeView;
    private ImageView androidImageView;
    private ImageView iosImageView;
    private ImageView wechatImageView;
    private WebView webview;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    EntityResult<CodeBean> result = (EntityResult<CodeBean>) msg.obj;
                    if(result.getCode() == 1){
                        CodeBean bean = result.getImgs();
                        Glide.with(CodeActivity.this.getApplicationContext()).load(bean.getAnroidImg()).into(androidImageView);
                        Glide.with(CodeActivity.this.getApplicationContext()).load(bean.getIosImg()).into(iosImageView);
                        Glide.with(CodeActivity.this.getApplicationContext()).load(bean.getWeixinImg()).into(wechatImageView);
                        setWebView(bean.getContent());
                    }else{
                        ToastTools.showShort(CodeActivity.this,result.getMsg());
                    }

                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        changeScale();
        initView();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(UrlConfig.PHILHARMONIC_HOST + "MobileErWeiMaAndContent2!getErWeiMaAndContent.do").build();
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<EntityResult<CodeBean>>() {
                    }.getType();
                    final EntityResult<CodeBean> result = gson.fromJson(response.body().string(), type);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = result;
                        handler.sendMessage(msg);

                }
            }
        });
    }
    private void changeScale(){
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        if(SharedPreferencesUtil.getInstance(getApplicationContext()).getFloat("scale") == 0.0f){
            config.fontScale = 1.0f;
        }else{
            config.fontScale = SharedPreferencesUtil.getInstance(getApplicationContext()).getFloat("scale");
        }

        res.updateConfiguration(config, res.getDisplayMetrics());
    }
    private void initView() {
        smallView = (ImageView) findViewById(R.id.small_img);
        middleView = (ImageView)findViewById(R.id.middle_img);
        largeView = (ImageView)findViewById(R.id.large_img);
        nightView = (ImageView) findViewById(R.id.night_img);
        androidImageView = (ImageView) findViewById(R.id.android_iv);
        iosImageView = (ImageView) findViewById(R.id.ios_iv);
        wechatImageView = (ImageView) findViewById(R.id.wechat_iv);
        webview = (WebView) findViewById(R.id.webview);
        tvTitle = (TextView)findViewById(R.id.tv_main_title);
        ivBack = (ImageView)findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        tvTitle.setText("二维码");
        smallView.setOnClickListener(this);
        middleView.setOnClickListener(this);
        largeView.setOnClickListener(this);
        nightView.setOnClickListener(this);
    }

    private void setWebView(String content) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setDefaultTextEncodingName("UTF-8");
        webview.getSettings().setBlockNetworkImage(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(webview.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
        }
        if(SharedPreferencesUtil.getInstance(getApplicationContext()).getBoolean("night",false)) {
                     webview.setBackgroundColor(getResources().getColor(R.color.main_background_night)); // 设置背景色
        }else{
            webview.setBackgroundColor(getResources().getColor(R.color.main_background));
        }
        webview.loadDataWithBaseURL(null, getNewContent(content), "text/html", "UTF-8", null);
    }



    //
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.night_img:
                if(SharedPreferencesUtil.getInstance(getApplicationContext()).getBoolean("night",false)){
                    SharedPreferencesUtil.getInstance(getApplicationContext()).setBoolean("night",false);
                    SkinManager.getInstance().restoreDefaultTheme();
                }else{
                    SharedPreferencesUtil.getInstance(getApplicationContext()).setBoolean("night",true);
                    SkinManager.getInstance().NightMode();
                }
                break;
            case R.id.small_img:

                SharedPreferencesUtil.getInstance(getApplicationContext()).setFloat("scale",0.8f);
                changeScale();
                RxBusLMsg msg1 = new RxBusLMsg();
                msg1.setType(0);
                RxLBus.get().post("rxBusMsg",msg1);
                recreate();
                break;
            case R.id.middle_img:
                SharedPreferencesUtil.getInstance(getApplicationContext()).setFloat("scale",1.0f);
                changeScale();
                RxBusLMsg msg2 = new RxBusLMsg();
                msg2.setType(0);
                RxLBus.get().post("rxBusMsg",msg2);
                recreate();
                break;
            case R.id.large_img:
                SharedPreferencesUtil.getInstance(getApplicationContext()).setFloat("scale",1.2f);
                changeScale();
                RxBusLMsg msg3 = new RxBusLMsg();
                msg3.setType(0);
                RxLBus.get().post("rxBusMsg",msg3);
                recreate();
                break;
            case R.id.iv_back:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static String getNewContent(String htmltext) {

        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("style", "");
                element.attr("max-width", "100%").attr("height", "auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
}
