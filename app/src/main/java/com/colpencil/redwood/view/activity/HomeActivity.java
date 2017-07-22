package com.colpencil.redwood.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.ApplyStatusReturn;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.HomeRecommend;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.present.mine.ApplyStatusPresenter;
import com.colpencil.redwood.view.fragments.FragmentFactory;
import com.colpencil.redwood.view.impl.IBuisnessView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 曾 凤
 * @Description: 主界面
 * @Email 20000263@qq.com
 * @date 2016/7/6
 */
@ActivityFragmentInject(
        contentViewId = R.layout.activity_home
)

public class HomeActivity extends ColpencilActivity implements View.OnClickListener  {

    @Bind(R.id.iv_homepage)
    ImageView iv_homepage;

    @Bind(R.id.iv_classification)
    ImageView iv_classification;

    @Bind(R.id.iv_discovery)
    ImageView iv_discovery;

    @Bind(R.id.iv_cyclopedia)
    ImageView iv_cyclopedia;

    @Bind(R.id.iv_me)
    ImageView iv_me;

    @Bind(R.id.tv_homepage)
    TextView tv_homepage;

    @Bind(R.id.tv_classification)
    TextView tv_classification;

    @Bind(R.id.tv_discovery)
    TextView tv_discovery;

    @Bind(R.id.tv_cyclopedia)
    TextView tv_cyclopedia;

    @Bind(R.id.tv_me)
    TextView tv_me;

    @Bind(R.id.ll_homepage)
    LinearLayout ll_homepage;

    @Bind(R.id.ll_classification)
    LinearLayout ll_classification;

    @Bind(R.id.ll_discovery)
    LinearLayout ll_discovery;

    @Bind(R.id.ll_cyclopedia)
    LinearLayout ll_cyclopedia;

    @Bind(R.id.ll_me)
    LinearLayout ll_me;
    @Bind(R.id.main_viewpage)
    NoScrollViewPager main_viewpage;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private boolean isExit = false;
    public static EntityResult<HomeRecommend> result;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.main_brown));
        }
        setSwipeBackEnable(false);
        initData();
        result = (EntityResult<HomeRecommend>) getIntent().getSerializableExtra("data");
    }

    /**
     * 数据初始化
     */
    private void initData() {
        ll_homepage.setOnClickListener(this);
        ll_classification.setOnClickListener(this);
        ll_discovery.setOnClickListener(this);
        ll_cyclopedia.setOnClickListener(this);
        ll_me.setOnClickListener(this);
        main_viewpage.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        main_viewpage.setOffscreenPageLimit(6);
        setBottomMeunStyle();
        iv_homepage.setImageResource(R.mipmap.btn_homepage_pressure);
        tv_homepage.setTextColor(this.getResources().getColor(R.color.main_red));
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 3 || msg.getType() == 37 || msg.getType() == 63) {
                    setBottomMeunStyle();
                    main_viewpage.setCurrentItem(0, false);
                    iv_homepage.setImageResource(R.mipmap.btn_homepage_pressure);
                    tv_homepage.setTextColor(getResources().getColor(R.color.main_red));
                } else if (msg.getType() == 56) {
                    setBottomMeunStyle();
                    iv_cyclopedia.setImageResource(R.mipmap.btn_cyclopedia_pressure);
                    tv_cyclopedia.setTextColor(HomeActivity.this.getResources().getColor(R.color.main_red));
                    main_viewpage.setCurrentItem(3, false);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        return null;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_homepage:
                setBottomMeunStyle();
                iv_homepage.setImageResource(R.mipmap.btn_homepage_pressure);
                tv_homepage.setTextColor(this.getResources().getColor(R.color.main_red));
                main_viewpage.setCurrentItem(0, false);
                break;
            case R.id.ll_classification:
                setBottomMeunStyle();
                iv_classification.setImageResource(R.mipmap.btn_classification_pressure);
                tv_classification.setTextColor(this.getResources().getColor(R.color.main_red));
                main_viewpage.setCurrentItem(1, false);
                break;
            case R.id.ll_discovery:
                setBottomMeunStyle();
                iv_discovery.setImageResource(R.mipmap.btn_discovery_pressure);
                tv_discovery.setTextColor(this.getResources().getColor(R.color.main_red));
                main_viewpage.setCurrentItem(2, false);
                break;
            case R.id.ll_cyclopedia:
                setBottomMeunStyle();
                iv_cyclopedia.setImageResource(R.mipmap.btn_cyclopedia_pressure);
                tv_cyclopedia.setTextColor(this.getResources().getColor(R.color.main_red));
                main_viewpage.setCurrentItem(3, false);
                break;
            case R.id.ll_me:
                setBottomMeunStyle();
                RxClickMsg msg1 = new RxClickMsg();
                msg1.setType(3);
                RxBus.get().post("meClick",msg1);
                iv_me.setImageResource(R.mipmap.btn_tab_me_pressure);
                tv_me.setTextColor(this.getResources().getColor(R.color.main_red));
                main_viewpage.setCurrentItem(4, false);
                break;
        }
    }

    /**
     * 设置底部菜单样式
     */
    private void setBottomMeunStyle() {
        iv_homepage.setImageResource(R.mipmap.btn_homepage_unpressure);
        iv_classification.setImageResource(R.mipmap.btn_classification_unpressure);
        iv_discovery.setImageResource(R.mipmap.btn_discovery_unpressure);
        iv_cyclopedia.setImageResource(R.mipmap.btn_cyclopedia_unpressure);
        iv_me.setImageResource(R.mipmap.btn_tab_me_unpressure);
        tv_homepage.setTextColor(this.getResources().getColor(R.color.white));
        tv_classification.setTextColor(this.getResources().getColor(R.color.white));
        tv_discovery.setTextColor(this.getResources().getColor(R.color.white));
        tv_cyclopedia.setTextColor(this.getResources().getColor(R.color.white));
        tv_me.setTextColor(this.getResources().getColor(R.color.white));
    }

    class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // 每个条目返回的fragment
        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.creatFragment(position, HomeActivity.this);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                exit();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastTools.showShort(this, "再按一次退出程序");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            SharedPreferencesUtil.getInstance(this).setString("token", "");
            SharedPreferencesUtil.getInstance(this).setInt("member_id", 0);
            finish();
            System.exit(0);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

}
