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
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
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
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.NoLazyFrament;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

import rx.Observable;
import rx.Subscriber;

import static com.colpencil.redwood.view.activity.HomeActivity.result;

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
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private BGARefreshLayout.BGARefreshLayoutDelegate delegate;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private CardWallAdapter mAdapter;
    private List<CardInfo> mlist = new ArrayList<>();
    private int type;
    private int origion;
    private CardPresenter presenter;
    private int pos;
    private Observable<RxClickMsg> clickMsgObservable;
    private Observable<RxBusMsg> observable;
    private boolean visible;
    public static CardWallItemFragment newInstance(int type, int origion) {
        CardWallItemFragment fragment = new CardWallItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("origion", origion);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            visible = true;
        }else{
            visible = false;
        }
    }
    @Override
    protected void initViews(View view) {
        /**
         * origion = 1 速拍 名片墙
         * origion = 2 商品名片墙
         * origion = 3 名师名片墙
         * origion = 4 名人堂
         * type = cat_id
         */
        type = getArguments().getInt("type");
        origion = getArguments().getInt("origion");
        final HashMap<String, String> params = new HashMap<>();
        params.put("page", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("cat_id", type + "");
        if (origion != 4) {
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            params.put("store_type", origion + "");

        }
        delegate = new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                loadPageOne();

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    pageNo++;
                    if (origion != 4) {
                        presenter.getCardStore(pageNo, params);
                    } else {
                        presenter.getMRCard(pageNo, params);
                    }
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
        loadPageOne();
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

        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        Subscriber<RxBusMsg> subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxBusMsg rxBusMsg) {
                if (rxBusMsg.getType() == 80) {
                    loadPageOne();
                }else if(rxBusMsg.getType() == 81){
                    if(!visible){
                        loadPageOne();
                    }
                }
            }
        };
        observable.subscribe(subscriber);
    }

    private void initAdatper() {
        mAdapter.setListener(new CardWallAdapter.ComOnClickListener() {
            @Override
            public void contentClick(int position) {
                if (mlist.get(position).getStore_type() != null) {
                    Intent intent = new Intent(getActivity(), StoreHomeActivity.class);
                    intent.putExtra("type", mlist.get(position).getStore_type());
                    intent.putExtra("store_id", mlist.get(position).getStore_id());
                    startActivity(intent);
                } else {
                    ToastTools.showShort(getActivity(), "他还不是商家");
                }

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
            loadPageOne();
            showLoading("加载中...");
        }
    }

    @Override
    public void loadSuccess() {
        hideLoading();
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
        listview.removeAllViewsInLayout();
        mAdapter.notifyDataSetChanged();

        if (ListUtils.listIsNullOrEmpty(info.getData())) {
            refreshLayout2.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            refreshLayout2.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

    private void isLoadMore(CardWallInfo info) {
        if (info.getData().size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();
    }

    private void loadPageOne() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("page", 1 + "");
        params.put("pageSize", pageSize + "");
        params.put("cat_id", type + "");
        if (origion != 4) {
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            params.put("store_type", origion + "");

        }
        if (origion != 4) {
            presenter.getCardStore(1, params);
        } else {
            presenter.getMRCard(1, params);
        }
    }

    @Override
    public void operate(CareReturn result) {
        hideLoading();
        ToastTools.showShort(getActivity(), result.getMessage());
        if (result.getCode() == 0) {
            if (mlist.get(pos).getIsfocus() == 0) {
                mlist.get(pos).setIsfocus(1);
                mlist.get(pos).setStore_count(mlist.get(pos).getStore_count() - 1);
            } else {
                mlist.get(pos).setIsfocus(0);
                mlist.get(pos).setStore_count(mlist.get(pos).getStore_count() + 1);
            }

            mAdapter.notifyDataSetChanged();
            RxBusMsg msg = new RxBusMsg();
            msg.setType(81);
            msg.setItem_id(mlist.get(pos).getStore_id());
            RxBus.get().post("rxBusMsg", msg);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
        RxBus.get().unregister("totop", clickMsgObservable);
    }
}
