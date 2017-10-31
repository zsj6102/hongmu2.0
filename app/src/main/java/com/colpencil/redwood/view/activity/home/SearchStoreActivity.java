package com.colpencil.redwood.view.activity.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ItemStoreFans;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.dao.DaoUtils;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.FansLikePresenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;

import com.colpencil.redwood.view.activity.mine.FansAndLikeActivity;
import com.colpencil.redwood.view.activity.mine.StoreHomeActivity;
import com.colpencil.redwood.view.adapters.FansLikeAdapter;
import com.colpencil.redwood.view.impl.IFansAndLike;
import com.jaeger.library.StatusBarUtil;
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

import static com.colpencil.redwood.view.activity.HomeActivity.result;


/**
 * 搜索商家         界面
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_search_store)
public class SearchStoreActivity extends ColpencilActivity implements IFansAndLike {


    @Bind(R.id.base_header_edit)
    EditText editText;
    @Bind(R.id.search_all_header)
    LinearLayout ll_header;
    @Bind(R.id.search_store_list)
    ListView listView;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private FansLikePresenter presenter;
    private int pageNo = 1;
    private String keyword;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private List<ItemStoreFans> mdata = new ArrayList<>();
    private FansLikeAdapter mAdapter;
    private int pos;
    Map<String, String> map = new HashMap<>();
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private BGARefreshLayout.BGARefreshLayoutDelegate delegate;

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        ll_header.setBackgroundColor(getResources().getColor(R.color.line_color_thirst));
        keyword = getIntent().getStringExtra("keyword");
        editText.setHint("搜索商家");
        editText.setText(keyword);

        delegate = new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                loadPageOne();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    map.put("keyword", keyword);
                    map.put("page", pageNo + "");
                    map.put("pageSiez", pageSize + "");
                    presenter.getSearch(pageNo, map);
                }

                return false;
            }
        };
        refreshLayout.setDelegate(delegate);
        refreshLayout2.setDelegate(delegate);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout.setSnackStyle(this.findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(this, true));
        refreshLayout2.setSnackStyle(this.findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        initData();
        mAdapter = new FansLikeAdapter(this, mdata, R.layout.item_card_wall);
        initAdatper();
        listView.setAdapter(mAdapter);
    }

    private void initAdatper() {
        mAdapter.setListener(new FansLikeAdapter.ComOnClickListener() {
            @Override
            public void contentClick(int position) {
                if (mdata.get(position).getStore_id() != null && mdata.get(position).getStore_type() != null) {
                    Intent intent = new Intent(SearchStoreActivity.this, StoreHomeActivity.class);
                    intent.putExtra("type", mdata.get(position).getStore_type() + "");
                    intent.putExtra("store_id", mdata.get(position).getStore_id());
                    startActivity(intent);
                } else {
                    ToastTools.showShort(SearchStoreActivity.this, "他还不是商家");
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
                    final CommonDialog dialog = new CommonDialog(SearchStoreActivity.this, "你还没登录喔!", "去登录", "取消");
                    dialog.setListener(new DialogOnClickListener() {
                        @Override
                        public void sureOnClick() {
                            Intent intent = new Intent(SearchStoreActivity.this, LoginActivity.class);
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
            //            if (mdata.get(pos).getIsfocus() == 0) {
            //                params.put("fans_type", 6 + "");                       //取消关注
            //            } else {
            //                params.put("fans_type", 5 + "");                       //关注
            //            }
            //            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            //            params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            //            params.put("concern_id", mdata.get(pos).getMember_id() + "");
            //            showLoading("");
            //            presenter.getCareReturn(params);
            //            showLoading("加载中...");
            loadPageOne();
        }
    }

    private void loadPageOne() {
        Map<String, String> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("page", 1 + "");
        map.put("pageSiez", pageSize + "");
        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        presenter.getSearch(1, map);

    }

    @OnClick(R.id.base_header_search)
    void search() {
        keyword = editText.getText().toString();
        loadData();
    }

    @OnClick(R.id.iv_back)
    void backOnClick() {
        finish();
    }

    private void initData() {
        showLoading(Constants.progressName);
        map.put("keyword", keyword);
        map.put("page", 1 + "");
        map.put("pageSiez", pageSize + "");
        presenter.getSearch(1, map);
    }

    private void loadData() {
        showLoading(Constants.progressName);
        loadPageOne();
        //保存搜索结果
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(keyword)) {
                    DaoUtils.saveHistory(9, keyword, SearchStoreActivity.this);
                }
            }
        }).start();
    }

    @Override
    public void loadFail(String message) {
        hideLoading();
        ToastTools.showShort(this, message);
        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();
        refreshLayout2.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
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
        listView.removeAllViewsInLayout();
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

    @Override
    public void operate(CareReturn result) {
        hideLoading();
        ToastTools.showShort(this, result.getMessage());
        if (result.getCode() == 0) {
            if (mdata.get(pos).getIsfocus() == 0) {
                mdata.get(pos).setIsfocus(1);
                mdata.get(pos).setStore_count(mdata.get(pos).getStore_count() - 1);
            } else {
                mdata.get(pos).setIsfocus(0);
                mdata.get(pos).setStore_count(mdata.get(pos).getStore_count() + 1);
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new FansLikePresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

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
        refreshLayout2.endRefreshing(0);
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
        refreshLayout2.endLoadingMore();
    }
}
