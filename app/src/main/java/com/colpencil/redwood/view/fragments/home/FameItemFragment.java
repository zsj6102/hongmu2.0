package com.colpencil.redwood.view.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.bean.DynamicInfo;
import com.colpencil.redwood.bean.FameInfo;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.result.DynamicResult;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.AttachUtil;
import com.colpencil.redwood.present.mine.DynamicPresent;
import com.colpencil.redwood.view.adapters.FameAdapter;
import com.colpencil.redwood.view.impl.IDynamicView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;


@ActivityFragmentInject(
        contentViewId = R.layout.fragment_fame_list
)
public class FameItemFragment extends ColpencilFragment implements IDynamicView {
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.gridview)
    GridView gridview;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private FameAdapter adapter;
    private List<DynamicInfo> mlist = new ArrayList<>();
    private DynamicPresent dynamicPresent;
    private int type;
    private int origin;
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private BGARefreshLayout.BGARefreshLayoutDelegate delegate;
    private Observable<RxClickMsg> clickMsgObservable;
    public static FameItemFragment newInstance(int type,int origin){
        FameItemFragment fameItemFragment=new FameItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("origin",origin);
        fameItemFragment.setArguments(bundle);
        return fameItemFragment;
    }

    @Override
    protected void initViews(View view) {
        type=getArguments().getInt("type");
        origin = getArguments().getInt("origin");
        delegate = new BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String,Integer> intparams=new HashMap<>();
                intparams.put("cat_id",type);
                intparams.put("page",pageNo);
                intparams.put("pageSize",pageSize);
                HashMap<String, RequestBody> strparams=new HashMap<>();
                if(origin == 2){
                    strparams.put("type",RequestBody.create(null,"pinpai"));
                }else{
                    strparams.put("type",RequestBody.create(null,"mingjiang"));
                }

                showLoading("加载中...");
                dynamicPresent.getDynamic(pageNo,intparams,strparams);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    HashMap<String,Integer> intparams=new HashMap<>();
                    intparams.put("cat_id",type);
                    intparams.put("page",pageNo);
                    intparams.put("pageSize",pageSize);
                    HashMap<String, RequestBody> strparams=new HashMap<>();
                    if(origin == 2){
                        strparams.put("type",RequestBody.create(null,"pinpai"));
                    }else{
                        strparams.put("type",RequestBody.create(null,"mingjiang"));
                    }

                    showLoading("加载中...");
                    dynamicPresent.getDynamic(pageNo,intparams,strparams);
                }
                return false;
            }
        };
        refreshLayout.setDelegate(delegate);
        refreshLayout2.setDelegate(delegate);
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout2.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter=new FameAdapter(getActivity(),mlist,R.layout.item_brand_good);
        gridview.setAdapter(adapter);

        gridview.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                EventBus.getDefault().post(AttachUtil.isAdapterViewAttach(absListView));
            }
        });
        initBus();

    }
    @Override
    public void loadData() {
        final HashMap<String,Integer> intparams=new HashMap<>();
        intparams.put("cat_id",type);
        intparams.put("page",pageNo);
        intparams.put("pageSize",pageSize);
        final HashMap<String, RequestBody> strparams=new HashMap<>();
        if(origin == 2){
            strparams.put("type",RequestBody.create(null,"pinpai"));
        }else{
            strparams.put("type",RequestBody.create(null,"mingjiang"));
        }
        showLoading("加载中...");
        dynamicPresent.getDynamic(pageNo,intparams,strparams);
    }
    private void initBus() {
        clickMsgObservable = RxBus.get().register("totop", RxClickMsg.class);
        Subscriber<RxClickMsg> observer = new Subscriber<RxClickMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxClickMsg rxClickMsg) {
                if (rxClickMsg.getType() == 100) {

                    gridview.smoothScrollToPosition(0);
                }
            }

        };
        clickMsgObservable.subscribe(observer);
    }
    @Override
    public ColpencilPresenter getPresenter() {
        dynamicPresent=new DynamicPresent();
        return dynamicPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
    private void isLoadMore(List<DynamicInfo> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail(String message) {
        hideLoading();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();
        refreshLayout2.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void loadMore(DynamicResult dynamicResult) {
        isLoadMore(dynamicResult.getData());
        mlist.addAll(dynamicResult.getData());
        adapter.notifyDataSetChanged();
        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();
        hideLoading();
    }

    @Override
    public void refresh(DynamicResult dynamicResult) {
        isLoadMore(dynamicResult.getData());
        mlist.clear();
        mlist.addAll(dynamicResult.getData());
        adapter.notifyDataSetChanged();
        if (ListUtils.listIsNullOrEmpty(dynamicResult.getData())) {
            refreshLayout2.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            refreshLayout2.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

}
