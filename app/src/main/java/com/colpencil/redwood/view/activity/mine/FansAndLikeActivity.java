package com.colpencil.redwood.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.FansLikePresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.FansLikeAdapter;
import com.colpencil.redwood.view.impl.IFansAndLike;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商家粉丝和关注
 */
@ActivityFragmentInject(contentViewId = R.layout.like_fans_layout)
public class FansAndLikeActivity extends ColpencilActivity implements IFansAndLike {


    @Bind(R.id.common_listview)
    ListView commonListview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    private FansLikePresenter presenter;
    private int pageNo = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private List<ItemStoreFans> mdata = new ArrayList<>();
    private FansLikeAdapter mAdapter;
    private String type;
    private int store_id;
    private int pos;
    private String cat_id;
    Map<String, String> map = new HashMap<>();

    @Override
    protected void initViews(View view) {
        store_id = getIntent().getExtras().getInt("store_id");
        type = getIntent().getStringExtra("type");
        map.put("store_id", store_id + "");
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        map.put("page", pageNo + "");
        map.put("pageSiez", pageSize + "");
        if (type.equals("0")) {
            map.put("fans_type", "0");
        } else {
            map.put("fans_type", "1");
        }
        if (type.equals("0")) {
            tvMainTitle.setText("粉丝");
        } else if(type.equals("1")) {
            tvMainTitle.setText("关注");
        }else{
            cat_id = getIntent().getStringExtra("cat_id");
            tvMainTitle.setText("热门关注");
        }
        initData();
        mAdapter = new FansLikeAdapter(this, mdata, R.layout.item_card_wall);
        initAdatper();
        commonListview.setAdapter(mAdapter);

        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                if(!type.equals("2")){

                    map.put("page", pageNo + "");
                    presenter.getData(pageNo, map);
                }else{//热门关注
                    Map<String, String> map2 = new HashMap<>();
                    map2.put("cat_id", cat_id);
                    map2.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    map2.put("page", pageNo+"");
                    map2.put("pageSize", pageSize+"");
                    presenter.getHotFans(pageNo,map2);
                }

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    if(!type.equals("2")){
                        map.put("page", pageNo + "");
                        presenter.getData(pageNo, map);
                    }else{  //热门关注
                        Map<String, String> map2 = new HashMap<>();
                        map2.put("cat_id", cat_id);
                        map2.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                        map2.put("page", pageNo+"");
                        map2.put("pageSize",  pageSize+"");
                        presenter.getHotFans(pageNo,map2);
                    }

                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(this.findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
    }

    private void initData() {
        showLoading("");
        if(!type.equals("2")){
            presenter.getData(1, map);
        }else{
            Map<String, String> map2 = new HashMap<>();
            map2.put("cat_id", cat_id);
            map2.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            map2.put("page", "1");
            map2.put("pageSize",  pageSize+"");
            presenter.getHotFans(1,map2);
        }

    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new FansLikePresenter();
        return presenter;
    }

    private void initAdatper() {
        mAdapter.setListener(new FansLikeAdapter.ComOnClickListener() {
            @Override
            public void contentClick(int position) {
                if (mdata.get(position).getStore_id() != null && mdata.get(position).getStore_type() != null) {
                    Intent intent = new Intent(FansAndLikeActivity.this, StoreHomeActivity.class);
                    intent.putExtra("type", mdata.get(position).getStore_type() + "");
                    intent.putExtra("store_id",  mdata.get(position).getStore_id());
                    startActivity(intent);
                } else {
                    ToastTools.showShort(FansAndLikeActivity.this,"他还不是商家");
                }

            }

            @Override
            public void careClick(int position) {
                pos = position;
                if (SharedPreferencesUtil.getInstance(App.getInstance()).getBoolean(StringConfig.ISLOGIN, false)) {
                    HashMap<String, String> params = new HashMap<String, String>();
                    if (mdata.get(position).getIsfocus() == 0) {
                        params.put("fans_type", 6 + "");                       //取消关注
                    } else {
                        params.put("fans_type", 5 + "");                       //关注
                    }
                    params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    params.put("concern_id", mdata.get(position).getMember_id() + "");
                    showLoading("");
                    presenter.getCareReturn(params);
                } else {
                    final CommonDialog dialog = new CommonDialog(FansAndLikeActivity.this, "你还没登录喔!", "去登录", "取消");
                    dialog.setListener(new DialogOnClickListener() {
                        @Override
                        public void sureOnClick() {
                            Intent intent = new Intent(FansAndLikeActivity.this, LoginActivity.class);
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
            HashMap<String, String> params = new HashMap<String, String>();
            if (mdata.get(pos).getIsfocus() == 0) {
                params.put("fans_type", 6 + "");                       //取消关注
            } else {
                params.put("fans_type", 5 + "");                       //关注
            }
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            params.put("concern_id", mdata.get(pos).getMember_id() + "");
            showLoading("");
            presenter.getCareReturn(params);
            showLoading("加载中...");
        }
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void operate(CareReturn result) {
        if (result.getCode() == 0) {
            if (mdata.get(pos).getIsfocus() == 0) {
                mdata.get(pos).setIsfocus(1);

            } else {
                mdata.get(pos).setIsfocus(0);
            }
            mAdapter.notifyDataSetChanged();
        }
        hideLoading();
    }

    @Override
    public void loadMore(ResultInfo<List<ItemStoreFans>> info) {
        isLoadMore(info.getData());
        mdata.addAll(info.getData());
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refresh(ResultInfo<List<ItemStoreFans>> info) {
        isLoadMore(info.getData());
        mdata.clear();
        mdata.addAll(info.getData());
        mAdapter.notifyDataSetChanged();
        hideLoading();
    }

        @OnClick(R.id.iv_back)
        void back() {
            finish();
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void isLoadMore(List<ItemStoreFans> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }
}
