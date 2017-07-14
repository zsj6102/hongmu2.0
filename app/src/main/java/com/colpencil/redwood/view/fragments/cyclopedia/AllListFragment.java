package com.colpencil.redwood.view.fragments.cyclopedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CategoryVo;
import com.colpencil.redwood.bean.CycloParams;
import com.colpencil.redwood.bean.CyclopediaInfoVo;
import com.colpencil.redwood.bean.GoodBusMsg;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.holder.adapter.CycloItemAdapter;
import com.colpencil.redwood.present.cyclopedia.CycloPediaPresenter;
import com.colpencil.redwood.view.activity.cyclopedia.CyclopediaDetailActivity;
import com.colpencil.redwood.view.impl.ICyclopediaTagView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;

@ActivityFragmentInject(contentViewId = R.layout.fragment_common)
public class AllListFragment extends ColpencilFragment implements ICyclopediaTagView, BGARefreshLayoutDelegate {

    @Bind(R.id.common_listview)
    ListView listView;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;

    private CycloItemAdapter adapter;
    private CycloPediaPresenter presenter;
    private List<CyclopediaInfoVo> cylist = new ArrayList<>();
    private int page = 1;
    private int pageSize = 10;
    private CategoryVo categoryVo;
    private boolean isRefresh = true;
    private Observable<GoodBusMsg> observable;
    private Subscriber subscriber;
    private boolean isSearch = false;
    private String keywood;

    public static AllListFragment newInstance(CategoryVo categoryVo) {
        Bundle bundle = new Bundle();
        AllListFragment fragment = new AllListFragment();
        bundle.putSerializable("categoryVo", categoryVo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        categoryVo = (CategoryVo) getArguments().getSerializable("categoryVo");
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        initAdapter();
        initBus();
        presenter.loadTagItem("3", categoryVo.getCat_id(), page, pageSize, "");
    }

    private void initAdapter() {
        adapter = new CycloItemAdapter(cylist, getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CycloParams params = new CycloParams();
                Intent intent = new Intent(getActivity(), CyclopediaDetailActivity.class);
                params.article_id = cylist.get(position).getArticle_id() + "";
                params.type = "cyclopedia";
                params.title = cylist.get(position).getPage_title();
                params.content = cylist.get(position).getPage_description();
                params.image = cylist.get(position).getImage();
                params.time = cylist.get(position).getAdd_time();
                intent.putExtra("params", params);
                startActivity(intent);
            }
        });
    }

    private void initBus() {
        observable = RxBus.get().register(StringConfig.GOODSBUS, GoodBusMsg.class);
        subscriber = new Subscriber<GoodBusMsg>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(GoodBusMsg msg) {
                if (msg.getType().equals(StringConfig.SMOOTHTOTOP)) {
                    if (getUserVisibleHint()) {
                        smooth();
                    }
                } else if (msg.getType().equals("refresh") || msg.getType().equals("refreshState")) {
                    page = 1;
                    isSearch = false;
                    presenter.loadTagItem("3", categoryVo.getCat_id(), page, pageSize, "");
                } else if (msg.getType().equals("search")) {
                    page = 1;
                    isSearch = false;
                    keywood = msg.getNorm();
                    presenter.search(3, keywood, page, pageSize);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CycloPediaPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }


    private void isLoadMore(List<CyclopediaInfoVo> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(list.size());
        refreshLayout.endRefreshing(0);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        if (!isSearch) {
            presenter.loadTagItem("3", categoryVo.getCat_id(), page, pageSize, "");
        } else {
            presenter.search(3, keywood, page, pageSize);
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (isRefresh) {
            page++;
            if (!isSearch) {
                presenter.loadTagItem("3", categoryVo.getCat_id(), page, pageSize, "");
            } else {
                presenter.search(3, keywood, page, pageSize);
            }
        }
        return false;
    }

    @Override
    public void refresh(List<CyclopediaInfoVo> list) {
        isLoadMore(list);
        cylist.clear();
        cylist.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadmore(List<CyclopediaInfoVo> list) {
        isLoadMore(list);
        cylist.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadError() {
    }

    public void smooth() {
        listView.setSelection(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(StringConfig.GOODSBUS, observable);
    }

}
