package com.colpencil.redwood.view.fragments.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.result.StatisticResult;
import com.colpencil.redwood.present.home.CircleRightPresenter;
import com.colpencil.redwood.view.impl.ICircleRightView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:藏友圈的评论
 * @Email DramaScript@outlook.com
 * @date 2016/7/11
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_circle_right
)
public class CircleRightFragment extends ColpencilFragment implements ICircleRightView {

    private CircleRightPresenter presenter;
    private MyPageAdapter adapter;

    @Bind(R.id.circle_right_view1)
    View view1;
    @Bind(R.id.circle_right_view2)
    View view2;
    @Bind(R.id.circle_right_view3)
    View view3;
    @Bind(R.id.circle_right_view4)
    View view4;
    @Bind(R.id.no_scroll_viewpager)
    NoScrollViewPager nvp;
    @Bind(R.id.right_first_tv)
    TextView first_tv;
    @Bind(R.id.circle_right_tv_commend)
    TextView second_tv;
    @Bind(R.id.circle_right_tv_like)
    TextView three_tv;
    @Bind(R.id.circle_right_tv_beshare)
    TextView four_tv;
    private Observable<RefreshMsg> observable;
    private Subscriber subscriber;

    @Override
    protected void initViews(View view) {
        adapter = new MyPageAdapter(getChildFragmentManager());

        adapter.addFragment(CircleRightItemFragment.newInstance("10000"));
        adapter.addFragment(CircleRightItemFragment.newInstance("20000"));
        adapter.addFragment(CircleRightItemFragment.newInstance("30000"));
        adapter.addFragment(CircleRightItemFragment.newInstance("40000"));

        nvp.setAdapter(adapter);


        observable = RxBus.get().register("refreshmsg", RefreshMsg.class);
        subscriber = new Subscriber<RefreshMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RefreshMsg refreshMsg) {
                if (2 == refreshMsg.getType() || 3 == refreshMsg.getType() || 4 == refreshMsg.getType()) {
                    presenter.loadStatic(
                            SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                            String.valueOf(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")));
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CircleRightPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadData() {
        presenter.loadStatic(
                SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"),
                String.valueOf(SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")));
    }
    @OnClick(R.id.circle_right_posts)
    void postsOnClick() {
        view1.setBackgroundColor(getResources().getColor(R.color.main_red));
        view2.setBackgroundColor(getResources().getColor(R.color.transparent));
        view3.setBackgroundColor(getResources().getColor(R.color.transparent));
        view4.setBackgroundColor(getResources().getColor(R.color.transparent));
        nvp.setCurrentItem(0, false);
    }

    @OnClick(R.id.circle_right_comment)
    void commentOnClick() {
        view1.setBackgroundColor(getResources().getColor(R.color.transparent));
        view2.setBackgroundColor(getResources().getColor(R.color.main_red));
        view3.setBackgroundColor(getResources().getColor(R.color.transparent));
        view4.setBackgroundColor(getResources().getColor(R.color.transparent));
        nvp.setCurrentItem(1, false);
    }

    @OnClick(R.id.circle_right_like)
    void likeOnClick() {
        view1.setBackgroundColor(getResources().getColor(R.color.transparent));
        view2.setBackgroundColor(getResources().getColor(R.color.transparent));
        view3.setBackgroundColor(getResources().getColor(R.color.main_red));
        view4.setBackgroundColor(getResources().getColor(R.color.transparent));
        nvp.setCurrentItem(2, false);
    }

    @OnClick(R.id.circle_right_beshare)
    void beshareOnClick() {
        view1.setBackgroundColor(getResources().getColor(R.color.transparent));
        view2.setBackgroundColor(getResources().getColor(R.color.transparent));
        view3.setBackgroundColor(getResources().getColor(R.color.transparent));
        view4.setBackgroundColor(getResources().getColor(R.color.main_red));
        nvp.setCurrentItem(3, false);
    }

    @Override
    public void loadStatistics(StatisticResult result) {
        if (result.getCode().equals("1")) {
            first_tv.setText(String.format(getString(R.string.item_num), result.getNotesCount()));
            second_tv.setText(result.getComCount() + "");
            three_tv.setText(result.getLikeCount() + "");
            four_tv.setText(result.getShareCount() + "");
        } else {
            first_tv.setText(String.format(getString(R.string.item_num), 0));
            second_tv.setText(0 + "");
            three_tv.setText(0 + "");
            four_tv.setText(0 + "");
        }
    }

    @Override
    public void loadError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refreshmsg", observable);
    }

    class MyPageAdapter extends FragmentPagerAdapter {

        public final List<Fragment> mfragment = new ArrayList<>();

        public void addFragment(Fragment fragment) {
            mfragment.add(fragment);
        }

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mfragment.get(position);
        }

        @Override
        public int getCount() {
            return mfragment.size();
        }
    }
}
