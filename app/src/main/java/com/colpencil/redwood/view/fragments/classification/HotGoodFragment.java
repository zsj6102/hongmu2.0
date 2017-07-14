package com.colpencil.redwood.view.fragments.classification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.HomeGoodInfo;
import com.colpencil.redwood.bean.LoginBean;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.present.mine.HotGoodPresenter;
import com.colpencil.redwood.view.activity.home.GoodDetailActivity;
import com.colpencil.redwood.view.adapters.HotGoodAdapter;
import com.colpencil.redwood.view.impl.IHotGoodView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ColpenciSnackbarUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.BaseGridView;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author 曾 凤
 * @Description: 热卖
 * @Email 20000263@qq.com
 * @date 2016/8/5
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_hotgood
)
public class HotGoodFragment extends ColpencilFragment implements IHotGoodView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.bga_hotGoodFragment)
    BGARefreshLayout bga_hotGoodFragment;

    @Bind(R.id.gridview_hotGoodFragment)
    BaseGridView gridview_hotGoodFragment;

    private List<HomeGoodInfo> mdata = new ArrayList<>();

    private HotGoodAdapter meAdapter;

    private HotGoodPresenter presenter;

    /**
     * 是否可以加载更多
     */
    private boolean isLoad;
    /**
     * 页数
     */
    private int pageNo = 1;
    /**
     * 分页长度
     */
    private int pageSize = 10;


    @Override
    protected void initViews(View view) {
        bga_hotGoodFragment.setDelegate(this);
        bga_hotGoodFragment.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        bga_hotGoodFragment.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.main_red));
        initData();
    }

    private void initData() {
        gridview_hotGoodFragment.setBackgroundColor(getResources().getColor(R.color.main_background));
        gridview_hotGoodFragment.setVerticalSpacing(0);
        meAdapter = new HotGoodAdapter(mdata, getActivity());
        gridview_hotGoodFragment.setAdapter(meAdapter);
        presenter.loadGoodInfor(pageNo, pageSize);
        gridview_hotGoodFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodDetailActivity.class);
                intent.putExtra("goodsId", mdata.get(position).getGoodsid());
                startActivity(intent);
            }
        });
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HotGoodPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadGoodInfor(List<HomeGoodInfo> goodInfos) {
        if (goodInfos != null && goodInfos.size() == pageSize) {
            isLoad = true;
        } else {
            isLoad = false;
        }
        mdata.addAll(goodInfos);
        meAdapter.notifyDataSetChanged();
        bga_hotGoodFragment.endRefreshing(0);
        bga_hotGoodFragment.endLoadingMore();
        pageNo++;
        hideLoading();
    }

    @Override
    public void fail(LoginBean loginBean) {
        hideLoading();
        ColpenciSnackbarUtil.downShowing(getActivity().findViewById(android.R.id.content), loginBean.getMsg());
        bga_hotGoodFragment.endRefreshing(0);
        bga_hotGoodFragment.endLoadingMore();

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        showLoading(Constants.progressName);
        mdata.clear();
        meAdapter.notifyDataSetChanged();
        pageNo = 1;
        presenter.loadGoodInfor(pageNo, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isLoad == true) {
            showLoading(Constants.progressName);
            presenter.loadGoodInfor(pageNo, pageSize);
        }
        return false;
    }
}
