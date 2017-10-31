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
import android.widget.ListView;
import android.widget.PopupWindow;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.JiashangItem;
import com.colpencil.redwood.bean.ResultInfo;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.mine.AuctionPresenter;
import com.colpencil.redwood.view.activity.ShoppingCartActivitys.OrderActivity;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.NodeReplyActivity;
import com.colpencil.redwood.view.adapters.AuctionItemAdapter;
import com.colpencil.redwood.view.adapters.ItemAllAuctionAdapter;
import com.colpencil.redwood.view.impl.IAuctionView;
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
import java.util.Map;

import butterknife.Bind;

import static com.colpencil.redwood.R.id.et_content;
import static com.colpencil.redwood.holder.HolderFactory.map;


/**
 * @author QFZ
 * @Description:架上拍品
 * @Email DramaScript@outlook.com
 * @date 2017-03-09
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_auction)
public class AuctionFragment extends ColpencilFragment implements IAuctionView {
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private int store_id;
    private int pageNo = 1, pageSize = 10;
    private boolean isRefresh = false;
    private List<JiashangItem> mdata = new ArrayList<>();
    private AuctionPresenter presenter;
    private AuctionItemAdapter adapter;
    private int pos;
    private PopupWindow window;
    private String content;
    private EditText et_content;
    private View view;

    public static AuctionFragment newInstance(int store_id) {
        AuctionFragment fragment = new AuctionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("store_id", store_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        this.view = view;
        store_id = getArguments().getInt("store_id");
        adapter = new AuctionItemAdapter(getActivity(), mdata, R.layout.item_auction);
        initAdapter();
        listview.setAdapter(adapter);
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                pageNo = 1;
                HashMap<String, String> map = new HashMap<>();
                map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                map.put("store_id", store_id + "");
                map.put("page", pageNo + "");
                map.put("pageSize", pageSize + "");
                presenter.getAllJiashang(pageNo, map);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if (isRefresh) {
                    pageNo++;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                    map.put("store_id", store_id + "");
                    map.put("page", pageNo + "");
                    map.put("pageSize", pageSize + "");
                    presenter.getAllJiashang(pageNo, map);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content), getResources().getColor(R.color.material_drawer_primary), getResources().getColor(R.color.white));
    }

    private void initAdapter() {
        adapter.setListener(new AuctionItemAdapter.MyListener() {
            @Override
            public void click(int position) {
                pos = position;
                showPopWindow(view);
            }

            @Override
            public void reply(int position) {
                Intent intent = new Intent(getActivity(), NodeReplyActivity.class);
                intent.putExtra("goodsid", mdata.get(position).getGoods_id());
                startActivity(intent);
            }

            @Override
            public void buy(int position) {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("key", "订单确定");
                intent.putExtra("goFrom", "AuctionFragment");
                intent.putExtra("product_id", mdata.get(position).getProduct_id());    //int类型
                intent.putExtra("goods_id", mdata.get(position).getGoods_id());     //int类型
                intent.putExtra("num", 1);    //int类型
                startActivity(intent);
            }

            @Override
            public void like(int position) {
                pos = position;
                Map<String, String> map = new HashMap<String, String>();
                map.put("goods_id", mdata.get(position).getGoods_id() + "");
                map.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
                map.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                presenter.getLike(map);

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
                    map.put("h_id", mdata.get(pos).getGoods_id() + "");
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
                map.put("h_id", mdata.get(pos).getGoods_id() + "");
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
    public ColpencilPresenter getPresenter() {
        presenter = new AuctionPresenter();
        return presenter;
    }

    @Override
    public void loadData() {
        HashMap<String, String> auctionMap = new HashMap<>();
        auctionMap.put("member_id", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        auctionMap.put("store_id", store_id + "");
        auctionMap.put("page", "1");
        auctionMap.put("pageSize", "10");
        presenter.getAllJiashang(pageNo, auctionMap);
        showLoading("");
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void refresh(ResultInfo<List<JiashangItem>> data) {
        isLoadMore(data.getData());
        mdata.clear();
        mdata.addAll(data.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadMore(ResultInfo<List<JiashangItem>> data) {
        isLoadMore(data.getData());
        mdata.addAll(data.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void loadFail(String msg) {
        ToastTools.showShort(getActivity(), msg);
        hideLoading();
    }

    private void isLoadMore(List<JiashangItem> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
    }

    @Override
    public void addComment(AddResult result) {
        hideLoading();
        ToastTools.showShort(getActivity(), result.getMessage());
        if (result.getCode() == 0) {
            mdata.get(pos).setComment_count(mdata.get(pos).getComment_count() + 1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addLike(AddResult result) {
        ToastTools.showShort(getActivity(), result.getMessage());
        if (result.getCode() == 0) {
            if (mdata.get(pos).getHave_collection() == 0) {
                mdata.get(pos).setHave_collection(1);
                mdata.get(pos).setCollectmember(mdata.get(pos).getCollectmember() + 1);
                adapter.notifyDataSetChanged();
            } else {
                mdata.get(pos).setHave_collection(0);
                mdata.get(pos).setCollectmember(mdata.get(pos).getCollectmember() - 1);
                adapter.notifyDataSetChanged();
            }

        }
    }
}
