package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.MeFragmentPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.fragments.mine.FamousStoreFragment;
import com.colpencil.redwood.view.fragments.mine.MineBrandFragment;
import com.colpencil.redwood.view.impl.IMeFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.android.segmented.SegmentedGroup;
import rx.Observable;
import rx.Subscriber;

/**
 * 名师名匠 入口
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_brand_store)
public class FamousStoreActivity extends ColpencilActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener,IMeFragmentView {

    @Bind(R.id.viewpager)
    NoScrollViewPager viewpager;
    @Bind(R.id.segment)
    SegmentedGroup segment;
    @Bind(R.id.btn_brand)
    RadioButton btn_brand;
    @Bind(R.id.btn_mine)
    RadioButton btn_mine;
    private MeFragmentPresenter presenter;
    private int inttype = 0;
    private MyPagerAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private int store_id;
    private int store_type;
    private Observable<RefreshMsg> refreshMsgObservable;
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.btn_brand:
                viewpager.setCurrentItem(0);
                break;

        }
    }
    @OnClick(R.id.btn_store)
    void toStore() {
        inttype = 2;
        storeClick();
    }

    @OnClick(R.id.btn_mine)
    void toMine() {
        inttype = 1;
        mineClick();
    }
    public void showLogin() {
        final CommonDialog dialog = new CommonDialog(this, "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(FamousStoreActivity.this, LoginActivity.class);
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
    private void mineClick() {

        if (SharedPreferencesUtil.getInstance(getApplication()).getBoolean(StringConfig.ISLOGIN, false)) {
            viewpager.setCurrentItem(1);
        } else {
            showLogin();
        }
    }

    private void storeClick() {
        if (!SharedPreferencesUtil.getInstance(getApplication()).getBoolean(StringConfig.ISLOGIN, false)) {
            showLogin();
        } else {
            Intent intent = new Intent(FamousStoreActivity.this, BusinessActivity.class);
            startActivity(intent);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        presenter.getApplyStatus(params);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            if (inttype == 1) {
                viewpager.setCurrentItem(1, false);
            } else if (inttype == 2) {
                Intent intent = new Intent(FamousStoreActivity.this, BusinessActivity.class);
                startActivity(intent);
            }
        }

    }
    @Override
    protected void initViews(View view) {
        fragments.add(new FamousStoreFragment());
        fragments.add(MineBrandFragment.newInstance(3));
        btn_brand.setText("名师名匠");
        viewpager.setOffscreenPageLimit(3);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(0);
        segment.setOnCheckedChangeListener(this);
        if (SharedPreferencesUtil.getInstance(getApplication()).getBoolean(StringConfig.ISLOGIN, false)) {
            HashMap<String, String> params = new HashMap<>();
            params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            presenter.getApplyStatus(params);
        }
//        if(!SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)){
//            segment.removeViewAt(segment.getChildCount()-1);
//            segment.updateBackground();
//            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//            layoutParams.setMargins(0, 0, 0, 0);
//            segment.getChildAt(segment.getChildCount() - 1).setLayoutParams(layoutParams);
//        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new MeFragmentPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
    @Override
    public void getStatusSucess(ApplyStatusReturn applyStatusReturn) {
        if (applyStatusReturn.getCode() == 0) {//成功
            if (applyStatusReturn.getData().getDisabled() == 1) {
                store_type = applyStatusReturn.getData().getStore_type();
                SharedPreferencesUtil.getInstance(App.getInstance()).setInt("store_type", store_type);
                store_id = applyStatusReturn.getData().getStore_id();
            }
            RxBusMsg msg = new RxBusMsg();
            msg.setType(97);
            msg.setItem_id(store_id);
            msg.setCpns_id(store_type);
            msg.setStatus(applyStatusReturn.getData().getDisabled());
            RxBus.get().post("rxBusMsg", msg);
        } else if (applyStatusReturn.getCode() == 1) {              //失败

        }
    }
    @OnClick(R.id.ll_back)
    void back(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshMsgObservable = RxBus.get().register("refreshmsg", RefreshMsg.class);
        Subscriber<RefreshMsg> subscriber2 = new Subscriber<RefreshMsg>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RefreshMsg msg) {
                if (msg.getType() == 0) {
                    btn_brand.performClick();
                }
            }
        };
        refreshMsgObservable.subscribe(subscriber2);
    }
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public void loadUserInfor(LoginBean loginBean) {

    }

    @Override
    public void loadGoodInfor(List<HomeGoodInfo> goodInfos) {

    }

    @Override
    public void callPhone(String phone) {

    }

    @Override
    public void fail(LoginBean loginBean) {

    }

    @Override
    public void getStatusError(String message) {

    }


}
