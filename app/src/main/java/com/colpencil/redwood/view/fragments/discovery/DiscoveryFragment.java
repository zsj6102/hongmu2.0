package com.colpencil.redwood.view.fragments.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.view.activity.discovery.CustomListActivity;
import com.colpencil.redwood.view.activity.discovery.MusicCenterActivity;
import com.colpencil.redwood.view.activity.discovery.ReCyclopediaActivity;
import com.colpencil.redwood.view.activity.home.CollectionCircleActivity;
import com.colpencil.redwood.view.activity.mine.AllSpecialActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;

import butterknife.OnClick;

import static com.unionpay.mobile.android.global.a.A;

/**
 * 描述：发现
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/6 11 01
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_discovery
)
public class DiscoveryFragment extends ColpencilFragment {


    @Override
    protected void initViews(View view) {

    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
    @OnClick(R.id.musicCenter)
    void musicCenterOnClick() {
        Intent intent = new Intent(getActivity(), MusicCenterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_circle)
    void circleOnClick() {
        Intent intent = new Intent(getActivity(), CollectionCircleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_recommend)
    void recomOnClick() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ReCyclopediaActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.personal_custom)
    void customOnClick() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), CustomListActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.rl_zc)
    void zcOnClick() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), AllSpecialActivity.class);
        startActivity(intent);
    }
    @Override
    public void loadData() {

    }
}
