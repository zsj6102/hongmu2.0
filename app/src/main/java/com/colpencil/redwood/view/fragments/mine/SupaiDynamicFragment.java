package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.AllGoodsInfo;
import com.colpencil.redwood.bean.RefreshMsg;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.SupaiPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.OrderActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.NodeReplyActivity;
import com.colpencil.redwood.view.adapters.ItemAllAuctionAdapter;
import com.colpencil.redwood.view.impl.ISupaiDynamic;
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
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * 拍品动态 我的收藏
 * 登录多余
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_listview)
public class SupaiDynamicFragment extends ColpencilFragment implements ISupaiDynamic {

    @Bind(R.id.supai_listview)
    ListView supaiListview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    //    @Bind(R.id.no_data_layout)
    //    LinearLayout nodataLayout;
    @Bind(R.id.refreshLayout2)
    BGARefreshLayout refreshLayout2;
    private SupaiPresenter presenter;
    private boolean isRefresh = false;
    private ItemAllAuctionAdapter adapter;
    private List<AllGoodsInfo> allGoodsInfoList = new ArrayList<>();
    private int page = 1;
    private int pageSize = 10;
    private int type;
    private int pos;
    private PopupWindow window;
    private String content;
    private EditText et_content;
    private View view;
    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private int intenttype;
    private BGARefreshLayout.BGARefreshLayoutDelegate delegate;

    public static SupaiDynamicFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        SupaiDynamicFragment fragment = new SupaiDynamicFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        this.view = view;
        type = getArguments().getInt("type");
        final HashMap<String, String> params = new HashMap<String, String>();

        params.put("type", "supai");
        params.put("pageSize", pageSize + "");
        delegate = new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                params.put("page", page + "");
                params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                if (type == 1) {

                    presenter.getSupaiDynamic(page, params);
                } else {
                    presenter.getSupaiCol(page, params);
                }
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    page++;
                    params.put("page", page + "");
                    params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    if (type == 1) {
                        presenter.getSupaiDynamic(page, params);
                    } else {
                        presenter.getSupaiCol(page, params);
                    }
                }
                return false;
            }
        };
        refreshLayout.setDelegate(delegate);
        refreshLayout2.setDelegate(delegate);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        refreshLayout2.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout2.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
        adapter = new ItemAllAuctionAdapter(getActivity(), allGoodsInfoList, R.layout.item_allauctionitem);
        initAdapter();
        supaiListview.setAdapter(adapter);
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(RxBusMsg tagMsg) {
                if (tagMsg.getType() == 4) {
                    page = 1;
                    params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    params.put("page", page + "");
                    if (intenttype == 1) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("goods_id", allGoodsInfoList.get(pos).getGoods_id() + "");
                        map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                        map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                        presenter.getLike(map);
                    } else {
                        if (type == 1) {
                            presenter.getSupaiDynamic(1, params);
                        } else {
                            presenter.getSupaiCol(1, params);

                        }
                    }

                }else  if(tagMsg.getType() == 90){
                    //点赞后重新刷新
                    page  = 1;
                    params.put("page", page + "");
                    params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    if (type == 1) {
                        presenter.getSupaiDynamic(page, params);
                    } else {
                        presenter.getSupaiCol(page, params);
                    }
                }
            }
        };
        observable.subscribe(subscriber);

    }

    /**
     * 适配器初始化
     */
    private void initAdapter() {
        adapter.setListener(new ItemAllAuctionAdapter.MyListener() {
            @Override
            public void click(int position) {
                pos = position;
                showPopWindow(view);
            }

            @Override
            public void buy(int position) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("key", "订单确定");
                intent.putExtra("goFrom", "SupaiDynamicFragment");
                intent.putExtra("product_id", allGoodsInfoList.get(position).getProduct_id());    //int类型
                intent.putExtra("goods_id", allGoodsInfoList.get(position).getGoods_id());     //int类型
                intent.putExtra("num", 1);    //int类型
                startActivity(intent);
            }

            @Override
            public void like(int position) {
                pos = position;
                intenttype = 1;
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("goods_id", allGoodsInfoList.get(position).getGoods_id() + "");
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    presenter.getLike(map);
                } else {
                    showDialog();
                }

            }

            @Override
            public void reply(int position) {
                intenttype = 2;
                Intent intent = new Intent(getActivity(), NodeReplyActivity.class);
                intent.putExtra("goodsid", allGoodsInfoList.get(position).getGoods_id());
                startActivity(intent);
            }
        });
    }

    private void showPopWindow(View view) {
        if (window == null) {
            window = new PopupWindow(initPopWindow(), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        window.setFocusable(true);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        window.showAtLocation(view, Gravity.BOTTOM, 100, 0);
        window.showAsDropDown(view);
    }

    private View initPopWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.post_reply, null);
        et_content = (EditText) view.findViewById(R.id.et_content);
        view.findViewById(R.id.popwindow_null).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        view.findViewById(R.id.pop_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = et_content.getText().toString().trim();
                if (SharedPreferencesUtil.getInstance(getActivity()).getBoolean(StringConfig.ISLOGIN, false)) {
                    if (TextUtils.isEmpty(content)) {
                        ToastTools.showShort(getActivity(), "请输入评论内容");
                        return;
                    }
                    showLoading("正在提交中...");
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("h_id", allGoodsInfoList.get(pos).getGoods_id() + "");
                    map.put("type", "1");
                    map.put("content", content);
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    presenter.getAddCommentResult(map);
                } else {
                    showDialog();
                }
                dismissPop();
            }
        });
        return view;
    }

    private void dismissPop() {
        if (window != null) {
            window.dismiss();
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {  //登录返回来的结果

            if (!TextUtils.isEmpty(content)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("h_id", allGoodsInfoList.get(pos).getGoods_id() + "");
                map.put("type", "1");
                map.put("content", content);
                map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                presenter.getAddCommentResult(map);
            } else {
                ToastTools.showShort(getActivity(), "请输入评论的内容");
            }

        }
    }

    @Override
    public void loadData() {
        showLoading("加载中...");
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        params.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("type", "supai");
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        if (type == 1) {
            presenter.getSupaiDynamic(page, params);
        } else {
            presenter.getSupaiCol(page, params);
        }
    }

    @Override
    public ColpencilPresenter getPresenter() {
        presenter = new SupaiPresenter();
        return presenter;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

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
    public void loadMoreSp(AllGoodsResult result) {
        isLoadMore(result.getData());
        allGoodsInfoList.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refreshSp(AllGoodsResult result) {
        isLoadMore(result.getData());
        allGoodsInfoList.clear();
        allGoodsInfoList.addAll(result.getData());
        adapter.notifyDataSetChanged();
        if (ListUtils.listIsNullOrEmpty(result.getData())) {
            refreshLayout2.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
        } else {
            refreshLayout2.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }
        hideLoading();
    }

    private void isLoadMore(List<AllGoodsInfo> list) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void addComment(AddResult result) {
        hideLoading();
        ToastTools.showShort(getActivity(), result.getMessage());
        if (result.getCode() == 0) {
            allGoodsInfoList.get(pos).setComment_count(allGoodsInfoList.get(pos).getComment_count() + 1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addLike(AddResult result) {
        ToastTools.showShort(getActivity(), result.getMessage());
        if (result.getCode() == 0) {
            if (type == 11) {   //我的收藏
                allGoodsInfoList.remove(pos);
                if (ListUtils.listIsNullOrEmpty(allGoodsInfoList)) {
                    refreshLayout2.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                } else {
                    refreshLayout2.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            } else {
                if (allGoodsInfoList.get(pos).getHave_collection() == 0) {
                    allGoodsInfoList.get(pos).setHave_collection(1);
                    allGoodsInfoList.get(pos).setCollectmember(allGoodsInfoList.get(pos).getCollectmember() + 1);
                    adapter.notifyDataSetChanged();
                } else {
                    allGoodsInfoList.get(pos).setHave_collection(0);
                    allGoodsInfoList.get(pos).setCollectmember(allGoodsInfoList.get(pos).getCollectmember() - 1);
                    adapter.notifyDataSetChanged();
                }
            }
            RxBusMsg msg = new RxBusMsg();
            msg.setType(91);
            RxBus.get().post("rxBusMsg", msg);
        }
    }
}
