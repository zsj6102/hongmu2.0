package com.colpencil.redwood.view.fragments.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AllGoodsInfo;
import com.colpencil.redwood.bean.ItemAllAuction;
import com.colpencil.redwood.bean.result.AllGoodsResult;
import com.colpencil.redwood.present.home.AllAuctionItemPresent;
import com.colpencil.redwood.view.adapters.ItemAllAuctionAdapter;
import com.colpencil.redwood.view.fragments.home.CircleLeftItemFragment;
import com.colpencil.redwood.view.impl.AllAuctionItemView;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilFragment;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;

@ActivityFragmentInject(
        contentViewId = R.layout.fragment_allauctionitem
)
public class AllAuctionItemFragment extends ColpencilFragment implements AllAuctionItemView {
    @Bind(R.id.allauctionitem_listview)
    ListView allauctionitem;

    private ItemAllAuctionAdapter adapter;
    private List<AllGoodsInfo> allGoodsInfoList=new ArrayList<>();
    private AllAuctionItemPresent allAuctionItemPresent;
    private int page=1;
    private int pageSize=10;
    private int type;

    public static AllAuctionItemFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        AllAuctionItemFragment fragment = new AllAuctionItemFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void initViews(View view) {
        type=getArguments().getInt("type");
        adapter=new ItemAllAuctionAdapter(getActivity(),allGoodsInfoList,R.layout.item_allauctionitem);
        allauctionitem.setAdapter(adapter);
        HashMap<String , RequestBody> strparams=new HashMap<>();
        strparams.put("type",RequestBody.create(null,"supai"));
        strparams.put("token",RequestBody.create(null, SharedPreferencesUtil.getInstance(getActivity()).getString("token")));

        HashMap<String , Integer> intparams=new HashMap<>();
        intparams.put("page",page);
        intparams.put("pageSize",pageSize);
        intparams.put("cat_id",type);
        Log.d("pretty",type+"");
        showLoading("加载中...");
        allAuctionItemPresent.getAllGoods(strparams,intparams);

    }

    @Override
    public ColpencilPresenter getPresenter() {
        allAuctionItemPresent=new AllAuctionItemPresent();
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

    }

    @Override
    public void getAllGoods(AllGoodsResult allGoodsResult) {
        List<AllGoodsInfo> list=allGoodsResult.getData();
        allGoodsInfoList.addAll(list);
        adapter.notifyDataSetChanged();
        hideLoading();
    }
}
