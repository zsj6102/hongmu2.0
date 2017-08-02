package com.colpencil.redwood.view.activity.ShoppingCartActivitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.CartItem;
import com.colpencil.redwood.bean.RxBusMsg;
import com.colpencil.redwood.bean.result.AllCartList;
import com.colpencil.redwood.bean.result.CartList;
import com.colpencil.redwood.configs.Constants;
import com.colpencil.redwood.configs.StringConfig;
import com.colpencil.redwood.function.utils.ListUtils;
import com.colpencil.redwood.function.widgets.dialogs.CommonDialog;


import com.colpencil.redwood.listener.DialogOnClickListener;
import com.colpencil.redwood.present.ShoppingCartPresenser;
import com.colpencil.redwood.view.activity.login.LoginActivity;
import com.colpencil.redwood.view.adapters.NewCartListAdapter;

import com.colpencil.redwood.view.impl.IShoppingCartView;
import com.jaeger.library.StatusBarUtil;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilActivity;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilPresenter;
import com.property.colpencil.colpencilandroidlibrary.Function.Annotation.ActivityFragmentInject;
import com.property.colpencil.colpencilandroidlibrary.Function.Rx.RxBus;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.FormatUtils;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.ToastTools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

@ActivityFragmentInject(
        contentViewId = R.layout.activity_shoppingcart)
public class NewShopingCartActivity extends ColpencilActivity implements IShoppingCartView {

    @Bind(R.id.tv_main_title)
    TextView tv_title;
    @Bind(R.id.tv_shoppingCartFinish)
    TextView tv_right;
    @Bind(R.id.listView_shoppingCart)
    ListView listView;
    @Bind(R.id.shoppingCart_delete)
    LinearLayout ll_delete;
    @Bind(R.id.shoppingCart_buy)
    LinearLayout ll_purchase;
    @Bind(R.id.relativeLayout_have)
    RelativeLayout rl_content;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;
    @Bind(R.id.shoppingCart_buyImg)
    ImageView iv_purchaseAll;
    @Bind(R.id.shoppingCart_deleteImg)
    ImageView iv_deleteAll;
    @Bind(R.id.shoppingCart_buyPrice)
    TextView tv_purchasePrice;
    @Bind(R.id.shoppingCart_buySumbit)
    TextView tv_purchaseSubmit;
    @Bind(R.id.shoppingCart_deleteSumbit)
    TextView tv_deleteSubmit;

    private Observable<RxBusMsg> observable;
    private Subscriber subscriber;
    private ShoppingCartPresenser present;
    //    private List<CartItem> mdatas = new ArrayList<>();  //购物车的集合
    private List<CartList> cartLists = new ArrayList<>();// 新的购物车的集合
    private NewCartListAdapter adapter;   //购物车的适配器
    private boolean isShowPurchase = true;
//    private int count;
    HashMap<String, String> params = new HashMap<>();

    @Override
    protected void initViews(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.line_color_thirst));
        }
        params.put("memberId", SharedPreferencesUtil.getInstance(App.getInstance()).getInt("member_id") + "");
        params.put("token", SharedPreferencesUtil.getInstance(App.getInstance()).getString("token"));
        present.loadAllcartList(params);
        adapter = new NewCartListAdapter(NewShopingCartActivity.this, cartLists);
        initAdapter();

        listView.setAdapter(adapter);
        tv_title.setText("购物车");
        tv_right.setText("编辑");

        initBus();
    }

    public Object getItem(int position) {
        if (null == cartLists || position < 0 || position > getCount()) {
            return null;
        }
        int categoryFirstIndex = 0;
        for (CartList category : cartLists) {
            int size = category.getItemCount();
            int categoryIndex = position - categoryFirstIndex;
            if (categoryIndex < size) {
                return category.getItem(categoryIndex);
            }
            categoryFirstIndex += size;
        }
        return null;
    }

    public int getCount() {
        int count = 0;
        if (null != cartLists) {
            for (CartList category : cartLists) {
                count += category.getItemCount();

            }
        }
        return count;
    }

    private void initAdapter() {
        adapter.setComListener(new NewCartListAdapter.comItemClickListener() {
            @Override
            public void click(String fuc, int position) {
                if ("reduce".equals(fuc)) {    //减少
                    if (((CartItem) getItem(position)).getNum() > 0) {
                        ((CartItem) getItem(position)).setNum(((CartItem) getItem(position)).getNum() - 1);
                    }
                    present.changeProductInfor(((CartItem) getItem(position)));
                    adapter.notifyDataSetChanged();
                    changeState();
                } else if ("add".equals(fuc)) {   //增加
                    ((CartItem) getItem(position)).setNum(((CartItem) getItem(position)).getNum() + 1);
                    present.changeProductInfor(((CartItem) getItem(position)));
                    adapter.notifyDataSetChanged();
                    changeState();
                } else {    //选中
                    changeState();
                }
            }

            @Override
            public void titleClick(int position) {
                for (CartList cartList : cartLists) {
                    if (cartList.getStore_name().equals(getItem(position))) {
                        List<CartItem> list = cartList.getGoodslist();
                        if (cartList.isAllChoose()) {
                            for (int i = 0; i < list.size(); i++) {
                                list.get(i).setChooseState(true);
                            }

                        } else {
                            for (int i = 0; i < list.size(); i++) {
                                list.get(i).setChooseState(false);
                            }

                        }
                        changeState();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }



    private void changeState() {
        int k = 0;
        int size = 0;
        for (CartList category : cartLists) {
            for (int m = 0; m < category.getGoodslist().size(); m++) {
                k++;
            }
        }
        for (CartList category : cartLists) {
            for (int m = 0; m < category.getGoodslist().size(); m++) {
                if (category.getGoodslist().get(m).isChooseState()) {
                    size++;
                }
            }
        }
        if (size == k) {
            iv_purchaseAll.setImageResource(R.mipmap.select_yes_red);
            iv_deleteAll.setImageResource(R.mipmap.select_yes_red);
        } else {
            iv_purchaseAll.setImageResource(R.mipmap.select_no);
            iv_deleteAll.setImageResource(R.mipmap.select_no);
        }

        float price = 0;
        for (CartList category : cartLists) {
            for (int m = 0; m < category.getGoodslist().size(); m++) {
                if (category.getGoodslist().get(m).isChooseState()) {
                    price += category.getGoodslist().get(m).getPrice() * category.getGoodslist().get(m).getNum();
                }
            }
        }
        tv_purchasePrice.setText("￥" + FormatUtils.formatDouble(price));
        if (size == 0) {
            tv_purchaseSubmit.setBackgroundResource(R.drawable.gary_solid_shape);
            tv_deleteSubmit.setBackgroundResource(R.drawable.gary_solid_shape);
        } else {
            tv_purchaseSubmit.setBackgroundResource(R.drawable.red_solid_shape);
            tv_deleteSubmit.setBackgroundResource(R.drawable.red_solid_shape);
        }
    }

    private void initBus() {
        observable = RxBus.get().register("rxBusMsg", RxBusMsg.class);
        subscriber = new Subscriber<RxBusMsg>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(RxBusMsg msg) {
                if (msg.getType() == 57) {
                    present.loadAllcartList(params);
                }
            }
        };
        observable.subscribe(subscriber);
    }

    @Override
    public ColpencilPresenter getPresenter() {
        present = new ShoppingCartPresenser();
        return present;
    }

    @Override
    public void bindView(Bundle savedInstanceState) {
    }

    @Override
    public void loadShoppingCartData(List<CartItem> datas) {
    }

    @Override
    public void reLoadData(String msg) {
//        count = 0;
        iv_purchaseAll.setImageResource(R.mipmap.select_no);
        iv_deleteAll.setImageResource(R.mipmap.select_no);
        //切换到购买状态
        ll_purchase.setVisibility(View.VISIBLE);
        ll_delete.setVisibility(View.GONE);
        tv_right.setText("编辑");
        tv_purchasePrice.setText("￥" + 0.00);
        //重新加载数据
        showLoading(Constants.progressName);
        present.loadAllcartList(params);
    }

    /**
     * 删除
     */
    @OnClick(R.id.shoppingCart_deleteSumbit)
    void deleteClick() {
        String cartIds = "";
        for (CartList category : cartLists) {
            for (int m = 0; m < category.getGoodslist().size(); m++) {
                if (category.getGoodslist().get(m).isChooseState()) {
                    cartIds = cartIds + category.getGoodslist().get(m).getCatid() + ",";
                }
            }
        }
        if (!cartIds.isEmpty()) {
            cartIds = cartIds.substring(0, cartIds.length() - 1);
            showDialog("确认要删除吗?", "确认", "取消", cartIds);
        }
    }
    @OnClick(R.id.shoppingCart_buySumbit)
    void submitClick() {
        String cartIds = "";
        for (CartList category : cartLists) {
            for (int m = 0; m < category.getGoodslist().size(); m++) {
                if (category.getGoodslist().get(m).isChooseState()) {
                    cartIds = cartIds + category.getGoodslist().get(m).getCatid() + ",";
                }
            }
        }
        if (!cartIds.isEmpty()) {
            Intent intent = new Intent(NewShopingCartActivity.this, PaymentActivity.class);
            intent.putExtra("key", "订单确定");
            cartIds = cartIds.substring(0, cartIds.length() - 1);
            intent.putExtra("cartIds", cartIds);
            intent.putExtra("goFrom", "ShoppingCartActivity");
            startActivity(intent);
        }
    }
    @Override
    public void deletefail(String code, String msg) {
        ToastTools.showShort(this, msg);
    }

    @Override
    public void loadFail(String code, String msg) {
        if (code.equals("3")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(StringConfig.REQUEST_CODE, 100);
            startActivityForResult(intent, Constants.REQUEST_LOGIN);
        } else {
            ll_empty.setVisibility(View.VISIBLE);
            rl_content.setVisibility(View.GONE);
            tv_right.setVisibility(View.GONE);
            ToastTools.showShort(this, msg);
        }
    }

    @Override
    public void loadNewCartData(AllCartList data) {

        hideLoading();

        if (ListUtils.listIsNullOrEmpty(data.getData())) {
            ll_empty.setVisibility(View.VISIBLE);
            rl_content.setVisibility(View.GONE);
            tv_right.setVisibility(View.GONE);
            ll_delete.setVisibility(View.VISIBLE);
        } else {
            ll_empty.setVisibility(View.GONE);
            rl_content.setVisibility(View.VISIBLE);
            tv_right.setVisibility(View.VISIBLE);
            cartLists.clear();
            cartLists.addAll(data.getData());
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 显示弹出框
     *
     * @param content
     * @param sure
     * @param cancle
     * @param cartIds
     */
    private void showDialog(String content, String sure, String cancle, final String cartIds) {
        final CommonDialog dialog = new CommonDialog(this, content, sure, cancle);
        dialog.setListener(new DialogOnClickListener() {
            @Override
            public void sureOnClick() {
                showLoading(Constants.progressName);
                present.deleteShoppingCart(cartIds);
                dialog.dismiss();
            }

            @Override
            public void cancleOnClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    /**
     * 编辑
     */
    @OnClick(R.id.tv_shoppingCartFinish)
    void editClick() {
        if (isShowPurchase) {
            isShowPurchase = false;
            ll_purchase.setVisibility(View.GONE);
            ll_delete.setVisibility(View.VISIBLE);
            tv_right.setText("完成");
        } else {
            isShowPurchase = true;
            ll_purchase.setVisibility(View.VISIBLE);
            ll_delete.setVisibility(View.GONE);
            tv_right.setText("编辑");
        }
    }

    /**
     * 选中全部
     */
    @OnClick({R.id.shoppingCart_buySelectAll, R.id.shoppingCart_deleteSelectAll})
    void purchaseAll() {
        int size = 0;

        int k = 0;
        for (CartList category : cartLists) {
            for (int m = 0; m < category.getGoodslist().size(); m++) {
                if (category.getGoodslist().get(m).isChooseState()) {
                    size++;
                }
            }
        }
        for (CartList category : cartLists) {
            for (int m = 0; m < category.getGoodslist().size(); m++) {
                k++;
            }
        }
        if (size == k) {    //全选状态,要改成未选中
            for (CartList category : cartLists) {
                for (int m = 0; m < category.getGoodslist().size(); m++) {
                    category.getGoodslist().get(m).setChooseState(false);
                }
            }
            iv_purchaseAll.setImageResource(R.mipmap.select_no);
            iv_deleteAll.setImageResource(R.mipmap.select_no);
            tv_purchasePrice.setText("￥" + 0.00);
            tv_purchaseSubmit.setBackgroundResource(R.drawable.gary_solid_shape);
            tv_deleteSubmit.setBackgroundResource(R.drawable.gary_solid_shape);
//            count = 0;
        } else {   //未全选状态,要改成全选
            for (CartList category : cartLists) {
                for (int m = 0; m < category.getGoodslist().size(); m++) {
                    category.getGoodslist().get(m).setChooseState(true);
                }
            }
//            count = k;
            iv_purchaseAll.setImageResource(R.mipmap.select_yes_red);
            iv_deleteAll.setImageResource(R.mipmap.select_yes_red);
            tv_purchaseSubmit.setBackgroundResource(R.drawable.red_solid_shape);
            tv_deleteSubmit.setBackgroundResource(R.drawable.red_solid_shape);
            float prices = 0.00f;
            //            for (int i = 0; i < mdatas.size(); i++) {
            //                CartItem item = mdatas.get(i);
            //                if (item.isChooseState()) {
            //                    prices += item.getPrice() * item.getNum();
            //                }
            //            }
            for (CartList category : cartLists) {
                for (int m = 0; m < category.getGoodslist().size(); m++) {
                    if (category.getGoodslist().get(m).isChooseState()) {
                        prices += category.getGoodslist().get(m).getPrice() * category.getGoodslist().get(m).getNum();
                    }
                }
            }
            tv_purchasePrice.setText("￥" + FormatUtils.formatDouble(prices));
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 返回首页
     */
    @OnClick(R.id.tv_empty)
    void toHomeClick() {
        RxBusMsg rxBusMsg = new RxBusMsg();
        rxBusMsg.setType(37);
        RxBus.get().post("rxBusMsg", rxBusMsg);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REQUEST_LOGIN) {
            present.loadAllcartList(params);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("rxBusMsg", observable);
    }
}
