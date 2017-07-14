package com.colpencil.redwood.view.activity.discovery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.result.CustomGoodResult;
import com.colpencil.redwood.bean.result.CustomResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.CustomPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.PaymentActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.ICustomView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 曾 凤
 * @Description: 官方推荐定制
 * @Email 20000263@qq.com
 * @date 2016/7/7
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_custom
)
public class CustomActivity extends ColpencilActivity implements ICustomView {

    @Bind(R.id.tv_main_title)
    TextView tv_main_title;
    @Bind(R.id.tv_assist_title)
    TextView tv_assist_title;
    @Bind(R.id.listview)
    ListView listview;

    private CustomPresenter presenter;
    private int official_id;
    private CustomResult custom;
    private MyViewHolder holder;
    private View header;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        official_id = getIntent().getIntExtra("officialid", 0);
        tv_main_title.setText(R.string.customActivityTitle);
        presenter.getContent(official_id);//网络请求数据
        presenter.customGood(official_id);
        initData();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        header = LayoutInflater.from(this).inflate(R.layout.custom_header, null);
        holder = new MyViewHolder(header);
        listview.addHeaderView(header);
        listview.setAdapter(new NullAdapter(this, new ArrayList<String>(), R.layout.item_null));
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CustomPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    @OnClick(R.id.tv_custom)
    void customClick() {
        if (SharedPreferencesUtil.getInstance(this).getBoolean(StringConfig.ISLOGIN, false)) {
            Intent intent = new Intent(CustomActivity.this, PaymentActivity.class);
            intent.putExtra("key", "订单确认");
            intent.putExtra("goFrom", "CustomActivity");
            if (custom != null) {
                intent.putExtra("product_id", custom.getProduct_id());   //int类型
                intent.putExtra("goods_id", custom.getGoods_id());   //int类型
            }
            startActivity(intent);
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(CustomActivity.this, LoginActivity.class);
                intent.putExtra(StringConfig.REQUEST_CODE, 100);
                startActivityForResult(intent, Constants.REQUEST_LOGIN);
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void loadUrl(CustomResult result) {
        custom = result;
        tv_assist_title.setText("(￥" + result.getGoods_price() + ")");
        WebSettings setting = holder.webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        setting.setDomStorageEnabled(true); //开放内存空间
        holder.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        holder.webView.loadUrl(result.getUrl());
    }

    @Override
    public void loadGoods(CustomGoodResult result) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        holder.unBind();
    }

    class MyViewHolder {

        @Bind(R.id.webview)
        WebView webView;

        public MyViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        public void unBind() {
            ButterKnife.unbind(this);
        }

    }
}
