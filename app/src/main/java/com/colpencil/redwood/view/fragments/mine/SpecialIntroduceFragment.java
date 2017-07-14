package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.SpecialIntroduceInfo;
import com.colpencil.redwood.bean.result.SpecialIntroduceResult;
import com.colpencil.redwood.present.mine.SpecialIntroducePresent;
import com.colpencil.redwood.view.impl.ISpecialIntroduceView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.tencent.smtt.sdk.WebView;

import butterknife.Bind;


@ActivityFragmentInject(
        contentViewId = R.layout.fragment_specialintroduce
)

public class SpecialIntroduceFragment extends ColpencilFragment implements ISpecialIntroduceView {
    @Bind(R.id.webview)
    WebView webview;

    private int id;
    private SpecialIntroducePresent specialIntroducePresent;


    public static SpecialIntroduceFragment newInstance(int id){
        Bundle bundle = new Bundle();
        SpecialIntroduceFragment fragment = new SpecialIntroduceFragment();
        bundle.putInt("special_id", id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected void initViews(View view) {
        id=getArguments().getInt("special_id");

        showLoading("加载中...");
        specialIntroducePresent.getSpecialIntroduce(id);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        specialIntroducePresent=new SpecialIntroducePresent();
        return specialIntroducePresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess(SpecialIntroduceResult specialIntroduceResult) {
        SpecialIntroduceInfo info=specialIntroduceResult.getData();
        webview.loadUrl(info.getUrl());
        hideLoading();
    }

    @Override
    public void loadFail(String message) {

        hideLoading();
        ToastTools.showShort(getActivity(),"加载错误，请刷新!");
    }
}
