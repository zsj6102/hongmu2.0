package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CardInfo;
import com.colpencil.redwood.bean.CardWallInfo;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.AttachUtil;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.CardPresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.StoreHomeActivity;
import com.colpencil.redwood.view.adapters.CardWallAdapter;
import com.colpencil.redwood.view.impl.ICardView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

import rx.Observable;
import rx.Subscriber;

/**
 * 商品区店铺名片墙
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_common_list)

public class CardWallItemFragment extends ColpencilFragment implements ICardView {

    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.common_listview)
    ListView listview;

    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private CardWallAdapter mAdapter;
    private List<CardInfo> mlist = new ArrayList<>();
    private int type;
    private int origion;
    private CardPresenter presenter;
    private int pos;
    private Observable<RxClickMsg> clickMsgObservable;

    public static CardWallItemFragment newInstance(int type, int origion) {
        CardWallItemFragment fragment = new CardWallItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("origion", origion);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
        origion = getArguments().getInt("origion");
        final HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("page", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("cat_id", type + "");
        if(type!=4){
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            params.put("store_type", origion + "");

        }
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;

                if(type!=4){
                    presenter.getCardStore(pageNo, params);
                }else{
                    presenter.getMRCard(pageNo,params);
                }

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    if(type!=4){
                        presenter.getCardStore(pageNo, params);
                    }else{
                        presenter.getMRCard(pageNo,params);
                    }
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        mAdapter = new CardWallAdapter(getActivity(), mlist, R.layout.item_card_wall);
        listview.setAdapter(mAdapter);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                EventBus.getDefault().post(AttachUtil.isAdapterViewAttach(absListView));
            }
        });
        initAdatper();
        initBus();
    }
    @Override
    public void loadData() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("page", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("cat_id", type + "");
        if(type!=4){
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            params.put("store_type", origion + "");

        }
        if(type!=4){
            presenter.getCardStore(pageNo, params);
        }else{
            presenter.getMRCard(pageNo,params);
        }

        showLoading("加载中...");
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
                    listview.setSelection(0);
                }
            }

        };
        clickMsgObservable.subscribe(observer);
    }

    private void initAdatper() {
        mAdapter.setListener(new CardWallAdapter.ComOnClickListener() {
            @Override
            public void contentClick(int position) {
                Intent intent = new Intent(getActivity(), StoreHomeActivity.class);
                intent.putExtra("type",origion);
                intent.putExtra("store_id",Integer.parseInt(mlist.get(position).getStore_id()));
                startActivity(intent);
            }

            @Override
            public void careClick(int position) {
                pos = position;
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    HashMap<String, String> params = new HashMap<String, String>();
                    if (mlist.get(pos).getIsfocus() == 0) {
                        params.put("fans_type", 6 + "");                       //取消关注
                    } else {
                        params.put("fans_type", 5 + "");                       //关注
                    }
                    params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    params.put("concern_id", mlist.get(pos).getMember_id() + "");
                    showLoading("");
                    presenter.getCareReturn(params);
                } else {
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
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
//            HashMap<String, String> params = new HashMap<String, String>();
//            if (mlist.get(pos).getIsfocus() == 0) {
//                params.put("fans_type", 6 + "");//取消关注
//            } else {
//                params.put("fans_type", 5 + "");//关注
//            }
//            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
//            params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
//            params.put("concern_id", mlist.get(pos).getMember_id() + "");
//            presenter.getCareReturn(params);
            final HashMap<String, String> params = new HashMap<>();
            params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            params.put("page", pageNo + "");
            params.put("pageSize", pageSize + "");
            params.put("cat_id", type + "");
            if(type!=4){
                params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                params.put("store_type", origion + "");

            }
            if(type!=4){
                presenter.getCardStore(pageNo, params);
            }else{
                presenter.getMRCard(pageNo,params);
            }

            showLoading("加载中...");
        }
    }

    @Override
    public void loadSuccess() {
        hideLoading();
    }

    @Override
    public void loadFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void loadMore(CardWallInfo info) {
        isLoadMore(info);
        mlist.addAll(info.getData());
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refresh(CardWallInfo info) {
        isLoadMore(info);
        mlist.clear();
        mlist.addAll(info.getData());
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

    private void isLoadMore(CardWallInfo info) {
        if (info.getData().size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    @Override
    public void operate(CareReturn result) {

        if (result.getCode() == 0) {
            hideLoading();
            final HashMap<String, String> params = new HashMap<>();
            params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            params.put("cat_id", type + "");
            params.put("store_type", origion + "");
            params.put("page", pageNo + "");
            params.put("pageSize", pageSize + "");
            presenter.getCardStore(pageNo, params);
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new CardPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }
}
