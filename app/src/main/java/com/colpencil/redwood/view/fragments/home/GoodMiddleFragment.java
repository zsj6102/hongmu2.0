package com.colpencil.redwood.view.fragments.home;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.result.AnnounceResult;
import com.colpencil.redwood.function.tools.WebViewTool;
import com.colpencil.redwood.present.home.AnnouncePresenter;
import com.colpencil.redwood.view.impl.IAnnounceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.Bind;

/**
 * @author 陈宝
 * @Description:商品的中间的Fragment
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_webview)
public class GoodMiddleFragment extends ColpencilFragment implements IAnnounceView {

    private int goodsId;
    private AnnouncePresenter presenter;
    @Bind(R.id.common_webview)
    com.tencent.smtt.sdk.WebView webView;
    @Bind(R.id.common_progress)
    ProgressBar bar;
    @Bind(R.id.base_header_layout)
    RelativeLayout rl_head;
    @Bind(R.id.header)
    LinearLayout ll_head;
    private WebViewTool tool;

    public static GoodMiddleFragment getInstance(int goodid) {
        Bundle bundle = new Bundle();
        bundle.putInt("goodsId", goodid);
        GoodMiddleFragment fragment = new GoodMiddleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        rl_head.setVisibility(View.GONE);
        ll_head.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
        goodsId = getArguments().getInt("goodsId");
        tool = new WebViewTool();
    }
    @Override
    public void loadData() {
        presenter.loadGoodMiddle(goodsId);
    }
    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new AnnouncePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess(AnnounceResult result) {
        if ("1".equals(result.getCode())) {
            tool.X5Load(webView, result.getUrl(), bar);
        }
    }

}
