package com.colpencil.redwood.view.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AllSpecialInfo;
import com.colpencil.redwood.bean.CategoryItem;
import com.colpencil.redwood.bean.CategoryVo;

import com.colpencil.redwood.bean.Goods_list;
import com.colpencil.redwood.bean.Goods_list_item;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;

import com.colpencil.redwood.bean.result.AdResult;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.holder.GoodItemsViewHolder;
import com.colpencil.redwood.holder.adapter.HGlistAdapter;
import com.colpencil.redwood.holder.home.GoodItemViewHolder;
import com.colpencil.redwood.holder.home.HotFansViewHolder;
import com.colpencil.redwood.holder.home.TopBannerViewHolder;
import com.colpencil.redwood.holder.home.ZcViewHolder;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.HomeGoodsPagePresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.FansAndLikeActivity;
import com.colpencil.redwood.view.activity.mine.StoreHomeActivity;
import com.colpencil.redwood.view.adapters.CyclopediaFirstAdapter;
import com.colpencil.redwood.view.adapters.FansItemAdapter;
import com.colpencil.redwood.view.adapters.NullAdapter;
import com.colpencil.redwood.view.impl.IHomeGoodsPageView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;


@ActivityFragmentInject(contentViewId = R.layout.fragment_home_pagetitem)
public class HomeGoodsPageFragment extends ColpencilFragment implements IHomeGoodsPageView, CyclopediaFirstAdapter.ItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    @Bind(R.id.lable_recycler)
    RecyclerView recycler;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;

    @Bind(R.id.second_goods_listview)
    GridView secondListview;
    @Bind(R.id.goods_recylcerview)
    ListView goods_recylcerview;
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private CyclopediaFirstAdapter firstAdapter;
    private LinearLayoutManager manager;
    private HomeGoodsPagePresenter presenter;
    private CategoryVo categoryVo;
    private List<CategoryItem> itemList = new ArrayList<>();
    private int pageNo = 1, pageSize = 10;
    private TopBannerViewHolder topBanner;
    private ZcViewHolder zcholder;
    private HotFansViewHolder fansViewHolder;
    private GoodItemsViewHolder goodItemViewHolder;
    private Observable<RxClickMsg> observable;
    private Subscriber subcriber;
    //    private List<CategoryItem> cat_child = new ArrayList<>();
    private List<Goods_list_item> secondGoodsList = new ArrayList<>();
    private HGlistAdapter secondAdapter;
    private boolean isRefresh = false;
    private int catchild_id;
    List<ItemStoreFans> hotFans = new ArrayList<>();
    private int pos;

    public static HomeGoodsPageFragment newInstance(CategoryVo categoryVo) {
        Bundle bundle = new Bundle();
        HomeGoodsPageFragment fragment = new HomeGoodsPageFragment();
        bundle.putSerializable("categoryVo", categoryVo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        categoryVo = (CategoryVo) getArguments().getSerializable("categoryVo");

        if (categoryVo != null) {
            if (!ListUtils.listIsNullOrEmpty(categoryVo.getCat_child())) {
                recycler.setVisibility(View.VISIBLE);
                //                cat_child.addAll(categoryVo.getCat_child());
            } else {
                recycler.setVisibility(View.GONE);
            }
            itemList.addAll(categoryVo.getCat_child());
        }
        refreshLayout.setDelegate(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));

        refreshLayout2.setDelegate(this);
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout2.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));

        initAdapter();
        initHolder();
        initBus();
    }

    private void initAdapter() {
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setLayoutManager(manager);
        firstAdapter = new CyclopediaFirstAdapter(getActivity(), itemList, this);
        recycler.setAdapter(firstAdapter);
    }

    private void initHolder() {
        topBanner = new TopBannerViewHolder(0, getActivity());
        zcholder = new ZcViewHolder(1, getActivity());
        fansViewHolder = new HotFansViewHolder(2, getActivity());
        goodItemViewHolder = new GoodItemsViewHolder(3, getActivity());
        goods_recylcerview.addHeaderView(topBanner.getContentView());
        goods_recylcerview.addHeaderView(zcholder.getContentView());
        goods_recylcerview.addHeaderView(fansViewHolder.getContentView());
        goods_recylcerview.addHeaderView(goodItemViewHolder.getContentView());
        goods_recylcerview.setAdapter(new NullAdapter(getActivity(), new ArrayList<String>(), R.layout.item_null));
        secondAdapter = new HGlistAdapter(getActivity(), secondGoodsList, R.layout.item_home_good);
        secondListview.setAdapter(secondAdapter);


    }

    private void initBus() {
        observable = RxBus.get().register("refreshmsg", RxClickMsg.class);
        subcriber = new Subscriber<RxClickMsg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RxClickMsg rxClickMsg) {
                if (rxClickMsg.getType() == 3000) {
                    refreshLayout.setVisibility(View.VISIBLE);
                    refreshLayout2.setVisibility(View.GONE);
                    for (CategoryItem item : itemList) {
                        item.setChoose(false);
                    }
                    firstAdapter.notifyDataSetChanged();
                }
            }
        };
        observable.subscribe(subcriber);
    }

    @Override
    protected void loadData() {
        presenter.getAd(categoryVo.getCat_id());
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("pageSize", "4");
        map.put("cat_id", categoryVo.getCat_id());
        presenter.getCoverSpecial(map);
        Map<String, String> map1 = new HashMap<>();
        map1.put("cat_id", categoryVo.getCat_id());
        presenter.getGoodsList(map1);
        Map<String, String> map2 = new HashMap<>();
        map2.put("cat_id", categoryVo.getCat_id());
        map2.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        map2.put("page", "1");
        map2.put("pageSize", "3");
        presenter.getHotFans(map2);
    }

    @Override
    public void loadAdv(AdResult result) {
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        topBanner.setData(result.getData());
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new HomeGoodsPagePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void operate(CareReturn result) {
        hideLoading();
        if (result.getCode() == 0) {
            if (hotFans.get(pos).getIsfocus() == 0) {
                hotFans.get(pos).setIsfocus(1);
            } else {
                hotFans.get(pos).setIsfocus(0);
            }
            fansViewHolder.getAdapter().notifyDataSetChanged();
        }else {
            ToastTools.showShort(getActivity(),result.getMessage());
        }
    }

    private void showLogin() {
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
    public void loadZc(ResultInfo<List<AllSpecialInfo>> resultInfo) {
        zcholder.setData(resultInfo.getData());
    }

    @Override
    public void loadGoods(ResultInfo<List<Goods_list>> resultInfo) {
        goodItemViewHolder.setData(resultInfo.getData());
    }

    @Override
    public void loadError(String message) {
        ToastTools.showShort(getActivity(), message);
    }

    @Override
    public void itemClick(int position) {
        refreshLayout2.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
        for (CategoryItem item : itemList) {
            if (itemList.get(position) == item) {
                item.setChoose(true);
            } else {
                item.setChoose(false);
            }
        }
        firstAdapter.notifyDataSetChanged();
        Map<String, String> map = new HashMap<>();
        catchild_id = itemList.get(position).getCat_id();
        map.put("cat_id", catchild_id + "");
        map.put("page", pageNo + "");
        map.put("pageSize", "" + pageSize);
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        presenter.getSecondGoods(1, map);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (refreshLayout2.getVisibility() == View.VISIBLE) {
            pageNo = 1;
            Map<String, String> map = new HashMap<>();
            map.put("cat_id", catchild_id + "");
            map.put("page", pageNo + "");
            map.put("pageSize", "" + pageSize);
            map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            presenter.getSecondGoods(1, map);
        } else {
            presenter.getAd(categoryVo.getCat_id());
        }

    }

    @Override
    public void loadFans(ResultInfo<List<ItemStoreFans>> resultInfo) {
        //        fanslist.clear();
        //        fanslist.addAll(resultInfo.getData());
        //        wrapper.notifyDataSetChanged();
        hotFans.clear();
        hotFans.addAll(resultInfo.getData());
        fansViewHolder.setData(hotFans);
        if (fansViewHolder.getAdapter() != null) {
            fansViewHolder.getAdapter().setListener(new FansItemAdapter.CareClick() {
                @Override
                public void toClick(int position) {
                    if (hotFans.get(position).getStore_id() != null && hotFans.get(position).getStore_type() != null) {
                        Intent intent = new Intent(getActivity(), StoreHomeActivity.class);
                        intent.putExtra("type", hotFans.get(position).getStore_type() + "");
                        intent.putExtra("store_id", hotFans.get(position).getStore_id());
                        getActivity().startActivity(intent);
                    }else{
                        ToastTools.showShort(getActivity(),"他还不是商家");
                    }

                }

                @Override
                public void careClick(int position) {
                    pos = position;
                    if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                        HashMap<String, String> params = new HashMap<String, String>();
                        if (hotFans.get(position).getIsfocus() == 0) {
                            params.put("fans_type", 6 + "");                       //取消关注
                        } else {
                            params.put("fans_type", 5 + "");                       //关注
                        }
                        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                        params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                        params.put("concern_id", hotFans.get(position).getMember_id() + "");
                        showLoading("");
                        presenter.getOperate(params);
                    } else {
                        showLogin();
                    }
                }
            });

        }
        fansViewHolder.getMorelayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FansAndLikeActivity.class);
                intent.putExtra("type", "2");
                intent.putExtra("cat_id", categoryVo.getCat_id() + "");
                getActivity().startActivity(intent);

            }
        });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (refreshLayout2.getVisibility() == View.VISIBLE) {
            if (isRefresh) {
                pageNo++;
                Map<String, String> map = new HashMap<>();
                map.put("cat_id", catchild_id + "");
                map.put("page", pageNo + "");
                map.put("pageSize", "" + pageSize);
                map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                presenter.getSecondGoods(pageNo, map);
            }
        } else {
            presenter.getAd(categoryVo.getCat_id());
        }

        return false;
    }

    @Override
    public void refresh(ResultInfo<List<Goods_list_item>> resultInfo) {
        isLoadMore(resultInfo.getData());
        secondGoodsList.clear();
        secondGoodsList.addAll(resultInfo.getData());
        secondAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(ResultInfo<List<Goods_list_item>> resultInfo) {
        isLoadMore(resultInfo.getData());
        secondGoodsList.addAll(resultInfo.getData());
        secondAdapter.notifyDataSetChanged();
    }

    private void isLoadMore(List<Goods_list_item> result) {
        if (!ListUtils.listIsNullOrEmpty(result)) {
            if (result.size() < pageSize) {
                isRefresh = false;
            } else {
                isRefresh = true;
            }
        }
        refreshLayout2.endRefreshing(0);
        refreshLayout2.endLoadingMore();
    }
}
