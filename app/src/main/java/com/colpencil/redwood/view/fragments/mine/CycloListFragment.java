package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CycloParams;
import com.colpencil.redwood.bean.EntityResult;
import com.colpencil.redwood.bean.MyCyclopediaInfo;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.function.widgets.list.ItemRemoveRecyclerView;
import com.colpencil.redwood.function.widgets.list.OnItemClickListener;
import com.colpencil.redwood.present.mine.CycloListPresenter;
import com.colpencil.redwood.view.activity.cyclopedia.CyclopediaDetailActivity;
import com.colpencil.redwood.view.adapters.ItemMyCycloAdapter;
import com.colpencil.redwood.view.impl.ICycloListView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

/**
 * @author 陈宝
 * @Description:我的百科的子界面
 * @Email DramaScript@outlook.com
 * @date 2016/7/27
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_my_cyclopedia)
public class CycloListFragment extends ColpencilFragment implements ICycloListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @Bind(R.id.common_listview)
    ItemRemoveRecyclerView listView;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private CycloListPresenter presenter;
    private ItemMyCycloAdapter adapter;
    private List<MyCyclopediaInfo> mdata = new ArrayList<>();
    private String mType;
    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private Observable<RefreshMsg> observable;
    private Subscriber subscriber;

    public static CycloListFragment newInstance(String type) {
        Bundle bundle = new Bundle();
        CycloListFragment fragment = new CycloListFragment();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        mType = getArguments().getString("type");
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter = new ItemMyCycloAdapter(getActivity(), mdata, mType);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mType.equals("0")) {     //已通过才可以进入详情
                    Intent intent = new Intent(getActivity(), CyclopediaDetailActivity.class);
                    CycloParams params = new CycloParams();
                    params.article_id = mdata.get(position).getId() + "";
                    params.type = "cyclopedia";
                    params.title = mdata.get(position).getTitle();
                    params.content = mdata.get(position).getPage_description();
                    params.image = mdata.get(position).getImage();
                    params.time = mdata.get(position).getCreatime();
                    intent.putExtra("params", params);
                    startActivity(intent);
                }
            }

            @Override
            public void onDeleteClick(int position) {
                showLoading("正在删除中...");
                presenter.delete(mdata.get(position).getId());
            }

            @Override
            public void cancleClick(int position) {
                showLoading("正在取消审核中...");
                presenter.cancleAudit(mdata.get(position).getId());
            }
        });
        initBus();
    }
    @Override
    public void loadData() {
        presenter.loadList(mType, page, pageSize);
    }
    private void initBus() {
        observable = RxBus.get().register("refreshmsg", RefreshMsg.class);
        subscriber = new Subscriber<RefreshMsg>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RefreshMsg msg) {
                if (msg.getType() == 10) {
                    page = 1;
                    presenter.loadList(mType, page, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CycloListPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(List<MyCyclopediaInfo> list) {
        isLoadMore(list);
        mdata.clear();
        mdata.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<MyCyclopediaInfo> list) {
        isLoadMore(list);
        mdata.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void isLoadMore(List<MyCyclopediaInfo> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
    }

    @Override
    public void loadError(String s) {

    }

    @Override
    public void delete(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 0) {
            RxBus.get().post("refreshmsg", new RefreshMsg(10));
        }
        ToastTools.showShort(getActivity(), result.getMessage());
    }

    @Override
    public void cancle(EntityResult<String> result) {
        hideLoading();
        if (result.getCode() == 0) {
            RxBus.get().post("refreshmsg", new RefreshMsg(10));
        }
        ToastTools.showShort(getActivity(), result.getMessage());
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        presenter.loadList(mType, page, pageSize);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            presenter.loadList(mType, page, pageSize);
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("refreshmsg", observable);
    }
}
