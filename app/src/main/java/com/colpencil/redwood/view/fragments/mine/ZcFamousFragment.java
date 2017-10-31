package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.ZcCardInfo;
import com.colpencil.redwood.bean.result.CareReturn;
import com.colpencil.redwood.bean.result.ZcAllCardInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.ZcFamousPrensenter;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.StoreHomeActivity;
import com.colpencil.redwood.view.adapters.ZcFamousAdapter;
import com.colpencil.redwood.view.impl.IZcFoumousView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGANormalRefreshViewHolder;
import com.property.colpencil.colpencilandroidlibrary.Ui.ColpenciListview.BGARefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_common_list)
public class ZcFamousFragment extends ColpencilFragment implements IZcFoumousView {

    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    @Bind(R.id.common_listview)
    ListView listview;
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private BGARefreshLayout.BGARefreshLayoutDelegate delegate;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private List<ZcCardInfo> mList = new ArrayList<>();
    private ZcFamousPrensenter prensenter;
    private ZcFamousAdapter mAdapter;
    private int type;
    private int pos;

    public static ZcFamousFragment newInstance(int type) {
        ZcFamousFragment fragment = new ZcFamousFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        type = getArguments().getInt("type");
        final HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("special_id", type + "");
        params.put("page", pageNo + "");
        params.put("pageSize", pageSize + "");
        delegate = new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                prensenter.getAllFamous(pageNo, params);

            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    prensenter.getAllFamous(pageNo, params);
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
        mAdapter = new ZcFamousAdapter(getActivity(), mList, R.layout.item_card_wall);
        listview.setAdapter(mAdapter);
        initAdatper();
    }

    private void initAdatper() {
        mAdapter.setListener(new ZcFamousAdapter.ComOnClickListener() {
            @Override
            public void contentClick(int position) {
                if(mList.get(position).getStore_type()!= null){
                    Intent intent = new Intent(getActivity(), StoreHomeActivity.class);
                    intent.putExtra("type", mList.get(position).getStore_type());
                    intent.putExtra("store_id", mList.get(position).getStore_id());
                    startActivity(intent);
                }else{
                    ToastTools.showShort(getActivity(),"他还不是商家");
                }

            }
            @Override
            public void careClick(int position) {
                pos = position;
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    HashMap<String, String> params = new HashMap<String, String>();
                    if (mList.get(pos).getIsfocus() == 0) {
                        params.put("fans_type", 6 + "");                       //取消关注
                    } else {
                        params.put("fans_type", 5 + "");                       //关注
                    }
                    params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    params.put("concern_id", mList.get(pos).getMember_id() + "");
                    prensenter.getCareReturn(params);
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
    protected void loadData() {
        final HashMap<String, String> params = new HashMap<>();
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("special_id", type + "");
        params.put("page", pageNo + "");
        params.put("pageSize", pageSize + "");
        prensenter.getAllFamous(pageNo, params);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果
            HashMap<String, String> params = new HashMap<String, String>();
            if (mList.get(pos).getIsfocus() == 0) {
                params.put("fans_type", 6 + "");//取消关注
            } else {
                params.put("fans_type", 5 + "");//关注
            }
            params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
            params.put("fans_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            params.put("concern_id", mList.get(pos).getMember_id() + "");
            prensenter.getCareReturn(params);
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        prensenter = new ZcFamousPrensenter();
        return prensenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void refresh(ZcAllCardInfo info) {
        isLoadMore(info);
        mList.clear();
        mList.addAll(info.getData());
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
    public void loadMore(ZcAllCardInfo info) {
        isLoadMore(info);
        mList.addAll(info.getData());
        mAdapter.notifyDataSetChanged();
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
    public void operate(CareReturn result) {
        if (result.getCode() == 0) {
            final HashMap<String, String> params = new HashMap<>();
            params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
            params.put("special_id", type + "");
            params.put("page", pageNo + "");
            params.put("pageSize", pageSize + "");
            prensenter.getAllFamous(pageNo, params);
        }
            ToastTools.showShort(getActivity(),result.getMessage());

    }

    private void isLoadMore(ZcAllCardInfo info) {
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
}
