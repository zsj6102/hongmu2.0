package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.DynamicInfo;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.DynamicResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.DynamicPresent;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.BusinessActivity;
import com.colpencil.redwood.view.activity.mine.PublishFamousActivity;
import com.colpencil.redwood.view.activity.mine.PublishZcActivity;
import com.colpencil.redwood.view.adapters.DynamicAdapter;
import com.colpencil.redwood.view.impl.IDynamicView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout.BGARefreshLayoutDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_gridview)
public class SpecialCollectionFragment extends ColpencilFragment implements IDynamicView {

    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.gridview)
    GridView gridview;
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private BGARefreshLayout.BGARefreshLayoutDelegate delegate;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private DynamicAdapter mAdapter;
    private List<DynamicInfo> mlist = new ArrayList<>();
    private DynamicPresent dynamicPresent;
    private int type;
    private int store_id;
    private int store_type;
    private int disabled;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    public static SpecialCollectionFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        SpecialCollectionFragment fragment = new SpecialCollectionFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void loadData() {
        final HashMap<String, Integer> intparams = new HashMap<>();
        intparams.put("cat_id", type);
        intparams.put("page", pageNo);
        intparams.put("pageSize", pageSize);
        final HashMap<String, RequestBody> strparams = new HashMap<>();
        strparams.put("type", RequestBody.create(null, "zhuanchang"));
        showLoading("加载中...");
        dynamicPresent.getDynamic(pageNo, intparams, strparams);
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
        Log.d("pretty", type + "");
        final HashMap<String, Integer> intparams = new HashMap<>();
        intparams.put("cat_id", type);
        intparams.put("page", pageNo);
        intparams.put("pageSize", pageSize);
        final HashMap<String, RequestBody> strparams = new HashMap<>();
        strparams.put("type", RequestBody.create(null, "zhuanchang"));
        initBus();
        delegate = new BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                showLoading("加载中...");
                dynamicPresent.getDynamic(pageNo, intparams, strparams);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    showLoading("加载中...");
                    dynamicPresent.getDynamic(pageNo, intparams, strparams);
                }
                return false;
            }
        };
        refreshLayout.setDelegate(delegate);
        refreshLayout2.setDelegate(delegate);
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout2.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        mAdapter = new DynamicAdapter(getActivity(), mlist, R.layout.item_brand_good);
        gridview.setAdapter(mAdapter);


    }
    private void initBus() {
        observable = RxBus.get().register("rxBusMsg",RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if(rxBusMsg.getType()==98){
                    store_id = rxBusMsg.getItem_id();
                    disabled = rxBusMsg.getStatus();
                    store_type = rxBusMsg.getCpns_id();
                }
            }
        };
        observable.subscribe(subscriber);

    }
    @Override
    public ColpencilPresenter getPresenter() {
        dynamicPresent = new DynamicPresent();
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
        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();

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

        mAdapter.notifyDataSetChanged();
        hideLoading();
    }
    @OnClick(R.id.iv_post)
    void postGood() {
        if (SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)) {
            if(disabled == 1){
                if(store_type == 2 || store_type == 3){
                    Intent intent = new Intent(getActivity(), PublishZcActivity.class);
                    intent.putExtra("type", store_type+"");
                    intent.putExtra("id", store_id + "");
                    getActivity().startActivity(intent);
                }else{
                    ToastTools.showShort(getActivity(),"亲，只有成为名师名匠或者品牌商家才可以发布专场！");
                }

            }else if (disabled == 0) {
                ToastTools.showShort(getActivity(), "您的店铺正在审核中，暂时不能发布商品");
            } else if (disabled == 2) {
                ToastTools.showShort(getActivity(), "您的店铺已被冻结，暂时不能发布商品");
            } else if (disabled == -1 || disabled == -2) {
                //disabled :-2 未成为商家  0 审核中  -1 未通过审核 2 冻结  1运营中
                showDialog2();

            }

        }else{
            showDialog();
        }

    }
    private void showDialog2() {
        final CommonDialog dialog = new CommonDialog(getActivity(), "亲，只有成为名师名匠或者品牌商家才可以发布专场哦!", "成为商家", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                Intent intent = new Intent(getActivity(), BusinessActivity.class);
                getActivity().startActivity(intent);
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void showDialog() {
        final CommonDialog dialog = new CommonDialog(getActivity(), "你还没登录喔!", "去登录", "取消");
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                intent();
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void intent() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(StringConfig.REQUEST_CODE, 100);
        startActivityForResult(intent, Constants.REQUEST_LOGIN);
    }
    @OnClick(R.id.totop_iv)
    void totop() {
         gridview.setSelection(0);
    }
    @Override
    public void refresh(DynamicResult dynamicResult) {
        isLoadMore(dynamicResult.getData());
        mlist.clear();
        mlist.addAll(dynamicResult.getData());
        mAdapter.notifyDataSetChanged();
        if (ListUtils.listIsNullOrEmpty(dynamicResult.getData())) {
            refreshLayout2.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            refreshLayout2.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg",observable);
    }
}
