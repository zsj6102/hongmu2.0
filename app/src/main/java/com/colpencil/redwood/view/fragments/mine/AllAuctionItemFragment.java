package com.colpencil.redwood.view.fragments.mine;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddResult;
import com.colpencil.redwood.bean.AllGoodsInfo;
import com.colpencil.redwood.bean.Info.RxClickMsg;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.widgets.AttachUtil;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;
import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.home.AllAuctionItemPresent;

import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.activity.mine.NodeReplyActivity;
import com.colpencil.redwood.view.adapters.ItemAllAuctionAdapter;
import com.colpencil.redwood.view.impl.AllAuctionItemView;
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
import de.greenrobot.event.EventBus;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * 所有拍品子页
 *
 */
@ActivityFragmentInject(
        contentViewId = R.layout.fragment_allauctionitem)
public class AllAuctionItemFragment extends ColpencilFragment implements AllAuctionItemView {

    @Bind(R.id.allauctionitem_listview)
    ListView allauctionitem;
    @Bind(R.id.refreshLayout)
    BGARefreshLayout refreshLayout;
    private boolean isRefresh = false;
    private ItemAllAuctionAdapter adapter;
    private List<AllGoodsInfo> allGoodsInfoList = new ArrayList<>();
    private AllAuctionItemPresent allAuctionItemPresent;
    private int page = 1;
    private int pageSize = 10;
    private View view;
    private int type;
    private int pos;
    private PopupWindow window;
    private String content;
    private EditText et_content;
    private Observable<RxClickMsg> clickMsgObservable;
    public static AllAuctionItemFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        AllAuctionItemFragment fragment = new AllAuctionItemFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews(View view) {
        this.view = view;
        type = getArguments().getInt("type");
        refreshLayout.setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
                page = 1;
                HashMap<String, RequestBody> strparams = new HashMap<>();
                strparams.put("type", RequestBody.create(null, "supai"));
                strparams.put("member_id", RequestBody.create(null,SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+""));
                HashMap<String, Integer> intparams = new HashMap<>();
                intparams.put("page", page);
                intparams.put("pageSize", pageSize);
                intparams.put("cat_id", type);
                Log.d("pretty", type + "");
                showLoading("加载中...");
                allAuctionItemPresent.getAllGoods(page,strparams, intparams);
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
                if(isRefresh){
                    page++;
                    HashMap<String, RequestBody> strparams = new HashMap<>();
                    strparams.put("type", RequestBody.create(null, "supai"));
                    strparams.put("member_id", RequestBody.create(null, SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+""));
                    HashMap<String, Integer> intparams = new HashMap<>();
                    intparams.put("page", page);
                    intparams.put("pageSize", pageSize);
                    intparams.put("cat_id", type);
                    Log.d("pretty", type + "");
                    showLoading("加载中...");
                    allAuctionItemPresent.getAllGoods(page,strparams, intparams);
                }
                return false;
            }
        });
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getActivity(), true));
        refreshLayout.setSnackStyle(getActivity().findViewById(android.R.id.content),
                getResources().getColor(R.color.material_drawer_primary),
                getResources().getColor(R.color.white));
        adapter = new ItemAllAuctionAdapter(getActivity(), allGoodsInfoList, R.layout.item_allauctionitem);
        initAdapter();
        allauctionitem.setAdapter(adapter);
        allauctionitem.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    private void initAdapter(){
        adapter.setListener(new ItemAllAuctionAdapter.MyListener() {
            @Override
            public void click(int position) {
                pos = position;
                showPopWindow(view);
            }

            @Override
            public void reply(int position) {
                Intent intent = new Intent(getActivity(), NodeReplyActivity.class);
                intent.putExtra("goodsid",allGoodsInfoList.get(position).getGoods_id());
                startActivity(intent);
            }

            @Override
            public void like(int position) {
                pos = position;
                Map<String,String> map = new HashMap<String, String>();
                map.put("goods_id",allGoodsInfoList.get(position).getGoods_id()+"");
                map.put("member_id",SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
                map.put("token",SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
              allAuctionItemPresent.getLike(map);

            }
        });
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

                    allauctionitem.setSelection(0);
                }
            }

        };
        clickMsgObservable.subscribe(observer);
    }

    @Override
    public void addComment(AddResult result) {
        hideLoading();
        ToastTools.showShort(getActivity(),result.getMessage());
        if(result.getCode() == 0){
            allGoodsInfoList.get(pos).setComment_count(allGoodsInfoList.get(pos).getComment_count()+1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void addLike(AddResult result) {
        ToastTools.showShort(getActivity(),result.getMessage());
        if(result.getCode() == 0){
            if(allGoodsInfoList.get(pos).getHave_collection() == 0){
                allGoodsInfoList.get(pos).setHave_collection(1);
                allGoodsInfoList.get(pos).setCollectmember(allGoodsInfoList.get(pos).getCollectmember()+1);
                adapter.notifyDataSetChanged();
            }else{
                allGoodsInfoList.get(pos).setHave_collection(0);
                allGoodsInfoList.get(pos).setCollectmember(allGoodsInfoList.get(pos).getCollectmember()-1);
                adapter.notifyDataSetChanged();
            }

        }
    }


    private void showPopWindow(View view) {
        if (window == null) {
            window = new PopupWindow(initPopWindow(),
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("h_id",allGoodsInfoList.get(pos).getGoods_id()+"");
                    map.put("type","1");
                    map.put("content",content);
                    map.put("member_id",SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
                    map.put("token",SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    allAuctionItemPresent.getAddCommentResult(map);
                } else {
                    showDialog();
                }
                dismissPop();
            }
        });
        return view;
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
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("h_id",allGoodsInfoList.get(pos).getGoods_id()+"");
                    map.put("type","1");
                    map.put("content",content);
                    map.put("member_id",SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+"");
                    map.put("token",SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
                    allAuctionItemPresent.getAddCommentResult(map);
                } else {
                    ToastTools.showShort(getActivity(), "请输入评论的内容");
                }

        }
    }
    private void dismissPop() {
        if (window != null) {
            window.dismiss();
        }
    }
    @Override
    public void loadData() {
        HashMap<String, RequestBody> strparams = new HashMap<>();
        strparams.put("type", RequestBody.create(null, "supai"));
        strparams.put("member_id", RequestBody.create(null,SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id")+""));
        HashMap<String, Integer> intparams = new HashMap<>();
        intparams.put("page", page);
        intparams.put("pageSize", pageSize);
        intparams.put("cat_id", type);
        Log.d("pretty", type + "");
        showLoading("加载中...");
        allAuctionItemPresent.getAllGoods(page,strparams, intparams);
    }
    @Override
    public ColpencilPresenter getPresenter() {
        allAuctionItemPresent = new AllAuctionItemPresent();
        return allAuctionItemPresent;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFail(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }



    @Override
    public void loadMore(AllGoodsResult result) {
        isLoadMore(result.getData());
        allGoodsInfoList.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }

    @Override
    public void refresh(AllGoodsResult result) {
        isLoadMore(result.getData());
        allGoodsInfoList.clear();
        allGoodsInfoList.addAll(result.getData());
        adapter.notifyDataSetChanged();
        hideLoading();
    }
    private void isLoadMore(List<AllGoodsInfo> list) {
        if (list.size() < pageSize) {
            isRefresh = false;
        } else {
            isRefresh = true;
        }
        refreshLayout.endRefreshing(0);
        refreshLayout.endLoadingMore();
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
}
