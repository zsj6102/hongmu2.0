package com.colpencil.redwood.view.fragments.classification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.Cat;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.cyclopedia.HSearchPresent;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.ShoppingCartActivity;
import com.colpencil.redwood.view.activity.classification.SearchGoodActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.HSearchLVAdapter;
import com.colpencil.redwood.view.impl.IHSearchView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseListView;
import com.property.colpencil.colpencilandroidlibrary.Ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 描述：分类
 * 作者：曾 凤
 * 邮箱：20000263@qq.com
 * 日期：2016/7/6 11 03
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_classification
)
public class ClassificationFragment extends ColpencilFragment
        implements IHSearchView, AdapterView.OnItemClickListener {

    @Bind(R.id.listview_hSearch)
    BaseListView listview_hSearch;
    @Bind(R.id.tv_hSearchChoose)
    TextView tv_hSearchChoose;
    @Bind(R.id.vp_classification)
    NoScrollViewPager vp_classification;
    @Bind(R.id.circle_hot)
    TextView tv_hot;

    private HSearchPresent hSearchPresent;
    private HSearchLVAdapter hSearchLVAdapter;
    /**
     * 商品总分类
     */
    private List<Cat> lvdatas = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initViews(View view) {
        listview_hSearch.setOnItemClickListener(this);
    }

    /**
     * 数据初始化
     */
    @Override
    public void loadData() {
        hSearchPresent.loadListViewData();
    }
    @Override
    public ColpencilPresenter getPresenter() {
        hSearchPresent = new HSearchPresent();
        return hSearchPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadListViewData(List<Cat> datas) {
        lvdatas.clear();
        lvdatas.addAll(datas);
        fragments.add(new HotGoodFragment());
        for (int i = 0; i < lvdatas.size(); i++) {
            fragments.add(SecondaryClassificationFragment.getInstance(lvdatas.get(i).getChildren()));
        }
        hSearchLVAdapter = new HSearchLVAdapter(getActivity(), lvdatas, R.layout.item_hsearch_listview);
        listview_hSearch.setAdapter(hSearchLVAdapter);
        vp_classification.setAdapter(new ClassificationPageAdapter(getActivity().getSupportFragmentManager()));
        vp_classification.setOffscreenPageLimit(lvdatas.size() + 1);
        tv_hSearchChoose.setText("热卖品");
        vp_classification.setCurrentItem(0, false);
    }

    /**
     * 数据请求失败提示
     */
    @Override
    public void fail(String msg) {
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), msg);
    }

    /**
     * 搜索
     */
    @OnClick({R.id.search_tv, R.id.ll_classification})
    void searchOnClick() {
        Intent intent = new Intent(getActivity(), SearchGoodActivity.class);
        startActivity(intent);
    }

    /**
     * 藏友热卖
     */
    @OnClick(R.id.circle_hot)
    void hotOnClick() {
        tv_hSearchChoose.setText("热卖品");
        vp_classification.setCurrentItem(0, false);
        hSearchLVAdapter.notifys();
        tv_hot.setTextColor(getResources().getColor(R.color.color_bf5d5d));
    }

    /**
     * 购物车
     */
    @OnClick(R.id.iv_gotoShoppingCart)
    void shopOnClick() {
        if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
            Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
            startActivity(intent);
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(getActivity(), "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int i = 0; i < lvdatas.size(); i++) {
            if (i == position) {
                lvdatas.get(i).setChooseState(true);
            } else {
                lvdatas.get(i).setChooseState(false);
            }
        }
        hSearchLVAdapter.notifyDataSetChanged();
        tv_hSearchChoose.setText(lvdatas.get(position).getName());
        vp_classification.setCurrentItem(position + 1, false);
        tv_hot.setTextColor(getResources().getColor(R.color.color_613b33));
    }

    class ClassificationPageAdapter extends FragmentStatePagerAdapter {

        public ClassificationPageAdapter(FragmentManager fm) {
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
}
