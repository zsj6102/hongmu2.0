package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.present.mine.MeFragmentPresenter;
import com.colpencil.redwood.view.fragments.SpecialPastFragment;
import com.colpencil.redwood.view.fragments.mine.CardWallFragment;
import com.colpencil.redwood.view.fragments.mine.DynamicFragment;
import com.colpencil.redwood.view.fragments.mine.SpecialCollectionFragment;
import com.colpencil.redwood.view.fragments.mine.SpecialIntroduceFragment;
import com.colpencil.redwood.view.fragments.mine.ZcFamousFragment;
import com.colpencil.redwood.view.impl.IMeFragmentView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static android.view.View.Z;

/**@author  QFZ
 * @deprecated  专场
 * @date 2017/3/3.
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_special
)

public class SpecialActivity extends ColpencilActivity implements IMeFragmentView{
    @Bind(R.id.tv_main_title)
    TextView tv_main_title;

    @Bind(R.id.tab_layout)
    TabLayout tab_layout;

    @Bind(R.id.viewpager)
    ViewPager viewpage;
    private MeFragmentPresenter presenter;
    private int store_id;
    private int store_type;
    private MyPageAdapter adapter;
    private int special_id;
    private String special_name;
    private Integer cat_id;
    @Override
    protected void initViews(View view) {
        special_id=Integer.parseInt(getIntent().getStringExtra("special_id"));
        special_name=getIntent().getStringExtra("special_name");
        cat_id = getIntent().getExtras().getInt("cat_id");
        tv_main_title.setText(special_name);
        adapter=new MyPageAdapter(getSupportFragmentManager());
        adapter.addFragment(SpecialIntroduceFragment.newInstance(special_id) ,"专场介绍");
        adapter.addFragment(SpecialCollectionFragment.newInstance(cat_id) ,"该场藏品");
        adapter.addFragment(ZcFamousFragment.newInstance(special_id) ,"驻场名人");

        tab_layout.addTab(tab_layout.newTab().setText("专场介绍"));
        tab_layout.addTab(tab_layout.newTab().setText("该场藏品"));
        tab_layout.addTab(tab_layout.newTab().setText("驻场名人"));
        if (SharedPreferencesUtil.getInstance(getApplication()).getBoolean(StringConfig.ISLOGIN, false)) {
            HashMap<String, String> params = new HashMap<>();
            params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            presenter.getApplyStatus(params);
        }
        viewpage.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewpage);

    }

    @OnClick(R.id.iv_back)
    void back(){
        finish();
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

    @Override
    public void getStatusSucess(ApplyStatusReturn applyStatusReturn) {
        if(applyStatusReturn.getCode() == 0){
            if (applyStatusReturn.getData().getDisabled() == 1) {
                store_type = applyStatusReturn.getData().getStore_type();
                SharedPreferencesUtil.getInstance(App.getInstance()).setInt("store_type", store_type);
                store_id = applyStatusReturn.getData().getStore_id();
            }
            RxBusMsg msg = new RxBusMsg();
            msg.setType(98);
            msg.setItem_id(store_id);
            msg.setCpns_id(store_type);
            msg.setStatus(applyStatusReturn.getData().getDisabled());
            RxBus.get().post("rxBusMsg", msg);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {
        HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        presenter.getApplyStatus(params); }
    }
    class  MyPageAdapter extends FragmentPagerAdapter{
        private List<Fragment> mFragments=new ArrayList<>();
        private List<String> titles=new ArrayList<>();
        public MyPageAdapter(FragmentManager fm){
            super(fm);

        }
        public void addFragment(Fragment fragment, String title){
            mFragments.add(fragment);
            titles.add(title);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
